package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.component.MoveEnhancement
import org.megamek.mekbuilder.component.SecondaryMotiveSystem
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.ComponentComboBoxCellFactory
import org.megamek.mekbuilder.javafx.util.ComponentListNameLookup
import org.megamek.mekbuilder.javafx.util.shortNameWithTechBase
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*
import tornadofx.FX.Companion.messages

/**
 * View for setting walk/cruise/thrust speed and additional movement modes (e.g. jump/underwater).
 */
class MovementView : View(), InvalidationListener {
    private val model: UnitViewModel by inject()
    private val techFilter: BasicInfo by inject()

    override val root: AnchorPane by fxml()

    private val lblWalkMP: Label by fxid()
    private val lblRunMP: Label by fxid()
    private val cbSecondaryMotive: ComboBox<SecondaryMotiveSystem> by fxid()
    private val spnWalkBase: Spinner<Int> by fxid()
    private val lblRunBase: Label by fxid()
    private val spnBaseSecondary: Spinner<Int> by fxid()
    private val lblWalkFinal: Label by fxid()
    private val lblRunFinal: Label by fxid()
    private val lblSecondaryFinal: Label by fxid()
    private val tblEnhancement: TableView<EnhancementEntry> by fxid()
    private val colEnhancementInstalled: TableColumn<EnhancementEntry, Boolean> by fxid()
    private val colEnhancementName: TableColumn<EnhancementEntry, String> by fxid()
    private val colEnhancementSize: TableColumn<EnhancementEntry, Number> by fxid()

    private val enhancementLookup = ComponentListNameLookup()
    private val allSecondaryMotive = ComponentLibrary.getInstance().allComponents
            .filter {it.type == ComponentType.SECONDARY_MOTIVE_SYSTEM}
            .sortedBy{it.shortName}.sortedBy{!it.isDefault}
            .toList().observable()
    private val allEnhancements: SortedFilteredList<EnhancementEntry> = SortedFilteredList(ComponentLibrary.getInstance().allComponents
            .filter {it.type == ComponentType.MOVE_ENHANCEMENT}
            .sortedBy{it.shortNameWithTechBase()}
            .map {EnhancementEntry(it as MoveEnhancement)}
            .toList().observable())


    private fun isWalker(unit: UnitBuild) =
        unit.unitType.isMech || unit.unitType == UnitType.PROTOMEK || unit.unitType == UnitType.BATTLE_ARMOR

    private fun isAero(unit: UnitBuild) =
        //TODO: Add aero support vehicles
        unit.unitType.isFighter || unit.unitType.isLargeCraft || unit.unitType == UnitType.SMALL_CRAFT

    init {
        lblWalkMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblWalkMP.text"]
                isAero(value) -> messages["lblSafeThrust.text"]
                else -> messages["lblCruiseMP.text"]
            }
        })
        lblRunMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblRunMP.text"]
                isAero(value) -> messages["lblMaxThrust.text"]
                else -> messages["lblFlankMP.text"]
            }
        })

        val baseWalkFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(model.minWalk, model.maxWalk)
        baseWalkFactory.minProperty().bind(model.minWalkProperty)
        baseWalkFactory.maxProperty().bind(model.maxWalkProperty)
        spnWalkBase.valueFactory = baseWalkFactory
        spnWalkBase.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnWalkBase.increment(0)
        }
        spnWalkBase.bind(model.baseWalkMPProperty)

        val secondaryMotiveList = SimpleListProperty<SecondaryMotiveSystem>()
        secondaryMotiveList.bind(objectBinding(allSecondaryMotive) {
            filter { techFilter.isLegal(it) && model.unit.allowed(it)}
                    .sortedBy{it != model.unit.defaultSecondaryMotiveType}
                    .map{it as SecondaryMotiveSystem}.toList().observable()
        })
        cbSecondaryMotive.items = secondaryMotiveList
        cbSecondaryMotive.bind(model.secondaryMotiveProperty)
        ComponentComboBoxCellFactory.setConverter(cbSecondaryMotive)

        spnBaseSecondary.enableWhen {booleanBinding(model.secondaryMotiveProperty) {value.mode != MotiveType.GROUND}}
        val baseSecondaryFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(model.minSecondaryMP, model.maxSecondaryMP)
        // It would be nice if we could just bind the min and max properties to these, but that could
        // result in an exception for trying to set a bound value when the factory tries to maintain min < max
        model.minSecondaryMPProperty.onChange {
            updateSecondaryMPFactory(baseSecondaryFactory)
        }
        model.maxSecondaryMPProperty.onChange {
            updateSecondaryMPFactory(baseSecondaryFactory)
        }
        spnBaseSecondary.valueFactory = baseSecondaryFactory
        spnBaseSecondary.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnBaseSecondary.increment(0)
        }
        spnBaseSecondary.bind(model.baseSecondaryMPProperty)

        lblRunBase.textProperty().bind(stringBinding(model.baseRunMPProperty) {
            model.baseRunMP.toString()
        })
        lblWalkFinal.textProperty().bind(stringBinding(model.walkMPProperty) {model.walkMP.toString()})
        lblRunFinal.textProperty().bind(stringBinding(model.runMPProperty) {model.runMP.toString()})
        lblSecondaryFinal.textProperty().bind(stringBinding(model.secondaryMPProperty) {model.secondaryMP.toString()})

        allEnhancements.bindTo(tblEnhancement)
        allEnhancements.predicate = {
            model.unit.allowed(it.component) && techFilter.isLegal(it.component)
        }
        enhancementLookup.listProperty.bind(objectBinding(allEnhancements.filteredItems) {
            map{it.component as Component}.toList().observable()
        })
        tblEnhancement.columnResizePolicy = SmartResize.POLICY
        colEnhancementName.remainingWidth()

        colEnhancementInstalled.cellValueFactory = Callback<TableColumn.CellDataFeatures<EnhancementEntry, Boolean>,
                ObservableValue<Boolean>> {
            it.value.installedProperty
        }
        colEnhancementInstalled.cellFactory = Callback<TableColumn<EnhancementEntry, Boolean>,
                TableCell<EnhancementEntry, Boolean>> {
            CheckBoxTableCell()
        }
        colEnhancementName.cellValueFactory = Callback<TableColumn.CellDataFeatures<EnhancementEntry, String>,
                ObservableValue<String>> {
            it.value.nameProperty
        }
        colEnhancementSize.cellValueFactory = Callback<TableColumn.CellDataFeatures<EnhancementEntry, Number>,
                ObservableValue<Number>> {
            it.value.sizeProperty
        }
        colEnhancementSize.cellFactory = Callback<TableColumn<EnhancementEntry, Number>,
                TableCell<EnhancementEntry, Number>> {
            EnhancementSizeCell()
        }
        model.secondaryMotiveProperty.onChange {filterEnhancements()}
        model.baseOptionProperty.onChange {filterEnhancements()}
        filterEnhancements()

        techFilter.addListener(this)
    }

    private fun updateSecondaryMPFactory(factory: SpinnerValueFactory.IntegerSpinnerValueFactory) {
        factory.max = model.maxSecondaryMP
        factory.min = model.minSecondaryMP
        if (factory.value < model.minSecondaryMP) {
            model.baseSecondaryMP = model.minSecondaryMP
        }
        if (factory.value > model.maxSecondaryMP) {
            model.baseSecondaryMP = model.maxSecondaryMP
        }
    }

    override fun invalidated(observable: Observable) {
        allSecondaryMotive.invalidate()
        if (model.secondaryMotiveType !in cbSecondaryMotive.items) {
            model.secondaryMotiveType = cbSecondaryMotive.items.first()
        } else {
            cbSecondaryMotive.selectionModel.select(model.secondaryMotiveType)
        }
        filterEnhancements()
    }

    private fun filterEnhancements() {
        allEnhancements.predicate = {
            model.unit.allowed(it.component) && techFilter.isLegal(it.component)
        }
        allEnhancements.filteredItems.forEach {
            it.nameProperty.value = enhancementLookup[it.component]
        }
    }

    private inner class EnhancementEntry(val component: MoveEnhancement) {
        val installedProperty = SimpleBooleanProperty(model.mountList.any{ it.component == component })
        val nameProperty = SimpleStringProperty("")
        val sizeProperty = SimpleDoubleProperty(0.0)

        init {
            installedProperty.onChange {
                if (it) {
                    model.unitModel.addEquipment(component, sizeProperty.value)
                } else {
                    val mount = model.mountList.first{m -> m.component == component}
                    model.unitModel.removeEquipment(mount)
                }
            }
            sizeProperty.onChange {
                val mount = model.mountList.firstOrNull{m -> m.component == component}
                mount?.size = it
                model.mountList.invalidate()
            }
        }
    }

    private class EnhancementSizeCell : TableCell<EnhancementEntry, Number>() {
        val spinner = Spinner<Double>()
        val valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 0.0)
        val changeListener = ChangeListener<Number> {
            _, _, newValue -> valueFactory.value = newValue.toDouble()
        }

        init {
            spinner.valueFactory = valueFactory
            spinner.valueProperty().onChange {
                if (it != null) {
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

