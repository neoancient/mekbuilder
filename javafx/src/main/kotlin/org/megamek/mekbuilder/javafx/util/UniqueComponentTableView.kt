package org.megamek.mekbuilder.javafx.util

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import javafx.scene.control.*
import javafx.scene.paint.Color
import javafx.util.Callback
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.javafx.models.MountModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.view.BasicInfo
import tornadofx.*

/**
 * TableView for a group of {@link Component}s that has a checkbox for installation, the name of the Component,
 * and a Spinner for setting the size of variable Components. Rows containing Components which conflict with
 * something already installed are disabled.
 */
class UniqueComponentTableView(val filter: (Component) -> Boolean): View() {
    val techFilter: BasicInfo by inject()
    val model: UnitViewModel by inject()

    private val componentLookup = ComponentListNameLookup()
    private val allComponents: SortedFilteredList<RowItem> = SortedFilteredList(ComponentLibrary.getInstance().allComponents
            .filter {filter(it)}
            .sortedBy{it.shortNameWithTechBase()}
            .map { RowItem(it) }
            .toList().observable())

    override val root = tableview<RowItem> {
        allComponents.bindTo(this)
        columnResizePolicy = SmartResize.POLICY
        fixedCellSize = 30.0
        prefHeightProperty().bind(Bindings.size(items)
                .multiply(fixedCellSizeProperty()).add(30.0))
        rowFactory = Callback<TableView<RowItem>, TableRow<RowItem>> {
            val row = TableRow<RowItem>()
            row.disableProperty().bind(booleanBinding(row.itemProperty(), techFilter, model.mountListProperty) {
                row.item?.allowedProperty?.value != true
            })
            row
        }

        column(messages["column.installed.title"], RowItem::installedProperty) {
            useCheckbox()
            makeEditable()
        }
        column(messages["column.name.title"], RowItem::nameProperty) {
            remainingWidth()
            cellFormat {
                text = it.toString()
                style {
                    if (tableRow != null) {
                        textFill = if (tableRow.isDisabled) {
                            Color.GRAY
                        } else {
                            Color.BLACK
                        }
                    }
                }
            }
        }
        column(messages["column.size.title"], RowItem::sizeProperty) {
            makeEditable()
            contentWidth(padding = 50.0, useAsMax = true)
            cellFactory = Callback<TableColumn<RowItem, Number>,
                    TableCell<RowItem, Number>> {
                SizeCell()
            }
        }
    }

    init {
        allComponents.predicate = {model.unit.allowed(it.component) && techFilter.isLegal(it.component)}
        componentLookup.listProperty.bind(objectBinding(allComponents.filteredItems) {
            map{it.component}.toList().observable()
        })
        model.baseOptionProperty.onChange {filterComponents()}
        model.addMountListener(ListChangeListener {
            while (it.next()) {
                when {
                    it.wasReplaced() -> {
                        removeMounts(it.removed)
                        addNewMounts(it.addedSubList)
                    }
                    it.wasAdded() -> addNewMounts(it.addedSubList)
                    it.wasRemoved() -> removeMounts(it.removed)
                    it.wasUpdated() -> for (i in it.from until it.to) {
                        val entry = allComponents.items.firstOrNull {
                            item -> item.component == it.list[i].component
                        }
                        entry?.installedProperty?.value = entry != null
                        entry?.sizeProperty?.value = it.list[i].size
                    }
                    // We don't care about permutations
                }
                filterComponents()
            }
        })
        // Replacing the list does not trigger a list change event.
        model.unitProperty.onChange {
            filterComponents()
            updateMounts()
        }
        techFilter.addListener{filterComponents()}
        filterComponents()
    }

    private fun filterComponents() {
        allComponents.predicate = {
            model.unit.allowed(it.component) && techFilter.isLegal(it.component)
        }
        allComponents.filteredItems.forEach {
            it.nameProperty.value = componentLookup[it.component]
        }
        allComponents.items.filter {!allComponents.predicate.invoke(it)}
                .forEach{it.installedProperty.value = false}
    }

    private fun addNewMounts(list: List<MountModel>) {
        list.filter{filter(it.component)}.forEach {
            val item = allComponents.items.firstOrNull{i -> i.component == it.component}
            item?.installedProperty?.value = true
            item?.sizeProperty?.value = it.size
        }
    }

    private fun removeMounts(list: List<MountModel>) {
        list.filter{filter(it.component)}.forEach {
            val item = allComponents.items.firstOrNull{i -> i.component == it.component}
            item?.installedProperty?.value = false
        }
    }

    private fun updateMounts() {
        allComponents.items.forEach {
            val mount = model.unitModel.mountList.firstOrNull{m -> m.component == it.component}
            it.installedProperty.value = mount != null
            it.sizeProperty.value = mount?.size ?: 1.0
        }
    }

    inner class RowItem(val component: Component) {
        val installedProperty = SimpleBooleanProperty(false)
        val nameProperty = SimpleStringProperty("")
        val sizeProperty = SimpleDoubleProperty(0.0)
        val allowedProperty = SimpleBooleanProperty(true)

        init {
            val mount = model.mountList.firstOrNull{it.component == component}
            installedProperty.value = mount != null
            sizeProperty.value = mount?.size ?: 0.0

            installedProperty.onChange {
                if (it) {
                    if (model.unitModel.mountList.none{m -> m.component == component}) {
                        model.unitModel.addEquipment(component, sizeProperty.value)
                    }
                } else {
                    val m = model.mountList.firstOrNull{ m -> m.component == component}
                    if (m != null) {
                        model.unitModel.removeEquipment(m)
                    }
                }
            }
            sizeProperty.onChange {
                val m = model.unitModel.mountList.firstOrNull{ m -> m.component == component}
                m?.size = it
            }
            allowedProperty.bind(booleanBinding(model.unitModelProperty, model.mountListProperty, model.baseOptionProperty, techFilter) {
                model.unit.allowed(component) && model.unitModel.unit.compatibleWithInstalled(component)
            })
            allowedProperty.onChange {
                if (!it) {
                    installedProperty.set(false)
                }
            }
        }
    }

    private inner class SizeCell : TableCell<RowItem, Number>() {
        val spinner = Spinner<Double>()
        val valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 0.0)
        val changeListener = ChangeListener<Number> {
            _, _, newValue -> valueFactory.value = newValue.toDouble()
        }

        init {
            spinner.valueFactory = valueFactory
            spinner.valueProperty().onChange {
                if (it != null && index >= 0) {
                    tableView.items[index].sizeProperty.value = it
                }
            }
        }

        fun entry() = tableView?.items?.getOrNull(index)

        override fun updateItem(item: Number?, empty: Boolean) {
            valueFactory.maxProperty().unbind()
            if (item != null) {
                entry()?.sizeProperty?.removeListener(changeListener)
            }
            super.updateItem(item, empty)
            if (empty || item == null || entry()?.component?.variableSize() != true) {
                graphic = null
                text = if (entry() == null) "" else messages["column.size.NA"]
            } else {
                valueFactory.max = entry()?.component?.variableSizeMax() ?: 0.0
                valueFactory.min = entry()?.component?.variableSizeMin() ?: 0.0
                valueFactory.value = item.toDouble()
                entry()?.sizeProperty?.addListener(changeListener)
                graphic = spinner
            }
        }
    }

}