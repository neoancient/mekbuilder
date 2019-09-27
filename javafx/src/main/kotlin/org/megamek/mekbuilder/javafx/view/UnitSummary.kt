/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2019 The MegaMek Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.megamek.mekbuilder.javafx.view

import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.javafx.models.MountModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*
import java.util.*

enum class Category(val unitTypes: Set<UnitType> = EnumSet.allOf(UnitType::class.java)) {
    TOTAL,
    ENGINE,
    STRUCTURE,
    CONTROLS,
    SEATING (EnumSet.of(UnitType.SUPPORT_VEHICLE)),
    TURRETS (EnumSet.of(UnitType.COMBAT_VEHICLE, UnitType.SUPPORT_VEHICLE)),
    GYRO (EnumSet.of(UnitType.BATTLE_MEK, UnitType.INDUSTRIAL_MEK)),
    FUEL (EnumSet.of(UnitType.SUPPORT_VEHICLE, UnitType.ASF, UnitType.CONV_FIGHTER,
            UnitType.SMALL_CRAFT, UnitType.DROPSHIP, UnitType.JUMPSHIP, UnitType.WARSHIP, UnitType.SPACE_STATION)),
    HEAT_SINKS,
    ARMOR,
    WEAPONS,
    AMMO,
    MISC_EQUIPMENT,
    TRANSPORT,
    QUARTERS  (EnumSet.of(UnitType.SUPPORT_VEHICLE, UnitType.SMALL_CRAFT, UnitType.DROPSHIP,
            UnitType.JUMPSHIP, UnitType.WARSHIP, UnitType.SPACE_STATION));

    companion object {
        fun of(component: Component) = when (component.type) {
            ComponentType.ARMOR -> ARMOR
            ComponentType.MEK_STRUCTURE -> STRUCTURE
            ComponentType.COCKPIT -> CONTROLS
            ComponentType.ENGINE -> ENGINE
            ComponentType.SECONDARY_MOTIVE_SYSTEM -> ENGINE
            ComponentType.MOVE_ENHANCEMENT -> ENGINE
            ComponentType.HEAT_SINK -> HEAT_SINKS
            ComponentType.GYRO -> GYRO
            ComponentType.MYOMER -> STRUCTURE
            ComponentType.HEAVY_WEAPON -> WEAPONS
            ComponentType.CAPITAL_WEAPON -> WEAPONS
            ComponentType.AMMUNITION -> AMMO
            ComponentType.PHYSICAL_WEAPON -> WEAPONS
            ComponentType.INF_WEAPON -> WEAPONS
            ComponentType.INF_ARMOR -> ARMOR
            else -> MISC_EQUIPMENT
        }
    }
}

/**
 * Shows a summary of the unit including occupied slots, weight, and heat profile
 */
class UnitSummary: View() {
    internal val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    internal val lblUnitName: Label by fxid()
    internal val lblUnitDescription: Label by fxid()
    internal val lblMaxWeight: Label by fxid()
    internal val lblMaxSlots: Label by fxid()
    internal val lblPodSpace: Label by fxid()
    internal val lblPodSpaceText: Label by fxid()
    internal val lblHeatProfile: Label by fxid()
    internal val lblMaxHeat: Label by fxid()
    internal val lblHeatDissipation: Label by fxid()
    internal val tblSummary: TreeTableView<SummaryItem> by fxid()
    internal val colName: TreeTableColumn<SummaryItem, String> by fxid()
    internal val colSlots: TreeTableColumn<SummaryItem, Number> by fxid()
    internal val colWeight: TreeTableColumn<SummaryItem, Number> by fxid()

    private val categoryNodes = Category.values().map{Pair(it, TreeItem(SummaryItem(it)))}.toMap()
    private val categoryChildLists = HashMap<Category, SortedFilteredList<TreeItem<SummaryItem>>>()
    private val mountChangeListener = ListChangeListener<MountModel> {
        while (it.next()) {
            if (it.wasRemoved()) {
                it.removed.forEach {
                    val cat = Category.of(it.component)
                    val item = categoryChildLists[cat]?.find {item -> item.value.mount == it}
                    categoryChildLists[cat]?.remove(item)
                    categoryNodes[cat]?.children?.setAll(categoryChildLists[cat])
                }
            }
            if (it.wasAdded()) {
                it.addedSubList.forEach {
                    val children = categoryChildLists[Category.of(it.component)]
                    if (children?.any{item -> item.value.mount == it} == false) {
                        children.add(TreeItem(SummaryItem(it)))
                    }
                }
            }
            if (it.wasUpdated()) {
                for(i in it.from until it.to) {
                    val cat = Category.of(it.list[i].component)
                    categoryChildLists[cat]?.refilter()
                    categoryNodes[cat]?.children?.setAll(categoryChildLists[cat]?.filteredItems)
                }
            }
        }
    }


    init {
        lblUnitName.textProperty().bind(stringBinding(model.chassisNameProperty, model.modelNameProperty) {
            "${model.chassisName} ${model.modelName}".trim()
        })
        lblMaxWeight.bind(model.tonnageProperty.stringBinding { it.toString()})
        lblMaxSlots.bind(model.availableSlotsProperty.stringBinding{it.toString()})

        tblSummary.columnResizePolicy = TreeTableSmartResize.POLICY
        colName.remainingWidth()

        refreshTable()

        model.addMountListener(mountChangeListener)
        model.unitModelProperty.onChange {
            tblSummary.root = refreshTable()
        }

        tblSummary.root = refreshTable()
        tblSummary.isShowRoot = false

        colName.cellValueFactory = Callback {it.value.value.nameProperty}
        colSlots.cellValueFactory = Callback {it.value.value.slotProperty}
        colWeight.cellValueFactory = Callback {it.value.value.weightProperty}
    }

    private fun refreshTable(): TreeItem<SummaryItem> {
        // We need to go directly to the model because when this gets triggered by a change
        // in the unit the properties have not been rebound yet.
        val map = model.unitModel.mountList.map{TreeItem(SummaryItem(it))}.groupBy{it.value.category}.toMap()
        // Only display components that occupy slots or take up weight
        val mountPredicate = {item: TreeItem<SummaryItem> -> item.value.slotProperty.value + item.value.weightProperty.value > 0.0}
        // Display categories appropriate to the unit type
        val categoryPredicate = {item: TreeItem<SummaryItem> -> model.unitType in item.value.category.unitTypes}
        categoryNodes.values.forEach { item ->
            if (item.value.category == Category.TOTAL) {
                item.value.slotProperty.bind(integerBinding(model.mountList) {
                    map{it.slots.value}.sum()
                })
                item.value.weightProperty.bind(doubleBinding(model.mountList) {
                    map{it.weight.value}.sum()
                })
            } else {
                val children = FXCollections.observableArrayList<TreeItem<SummaryItem>> {
                    arrayOf(it.value.slotProperty, it.value.weightProperty)
                }
                if (map.containsKey(item.value.category)) {
                    children.setAll(map[item.value.category])
                }
                val filteredMounts = SortedFilteredList(children)
                filteredMounts.predicate = mountPredicate
                item.children.setAll(filteredMounts)
                categoryChildLists[item.value.category] = filteredMounts
                children.onChange {
                    filteredMounts.refilter()
                    item.children.setAll(filteredMounts.filteredItems)
                }

                item.value.slotProperty.bind(integerBinding(item.children) {
                    map { it.value.slotProperty.value }.sum()
                })
                item.value.weightProperty.bind(doubleBinding(item.children) {
                    map { it.value.weightProperty.value }.sum()
                })
            }
        }
        val sortedCategories = SortedFilteredList(categoryNodes.values.toList().observable())
        sortedCategories.predicate = categoryPredicate
        val root = TreeItem(SummaryItem(Category.MISC_EQUIPMENT))
        root.children.setAll(sortedCategories)
        return root
    }

    internal inner class SummaryItem(val category: Category, val mount: MountModel? = null) {

        constructor(mount: MountModel): this(Category.of(mount.component), mount)

        val nameProperty = SimpleStringProperty(messages["category.${category.name}"])
        val slotProperty = SimpleIntegerProperty()
        val weightProperty = SimpleDoubleProperty()

        init {
            if (mount != null) {
                nameProperty.bind(stringBinding(mount.componentProperty, mount.sizeProperty) {
                    mount.mount.displayName()
                })
                slotProperty.bind(mount.slots)
                weightProperty.bind(mount.weight)
            }
        }
    }
}