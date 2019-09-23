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
import javafx.collections.ListChangeListener
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

    fun displayName() = name

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
    internal val lblWeight: Label by fxid()
    internal val lblHeatGenerated: Label by fxid()
    internal val lblHeatDissipation: Label by fxid()
    internal val tblSummary: TreeTableView<SummaryItem> by fxid()
    internal val colName: TreeTableColumn<SummaryItem, String> by fxid()
    internal val colSlots: TreeTableColumn<SummaryItem, Number> by fxid()
    internal val colWeight: TreeTableColumn<SummaryItem, Number> by fxid()

    private val categoryMap = HashMap<Category, TreeItem<SummaryItem>>()

    init {
        lblUnitName.textProperty().bind(stringBinding(model.chassisNameProperty, model.modelNameProperty) {
            "${model.chassisName} ${model.modelName}".trim()
        })
        lblWeight.bind(model.tonnageProperty.stringBinding {
            it.toString()
        })

        tblSummary.columnResizePolicy = TreeTableSmartResize.POLICY
        colName.remainingWidth()

        val root = TreeItem(SummaryItem(Category.MISC_EQUIPMENT))
        val map = model.mountList.map{TreeItem(SummaryItem(it))}.groupBy{it.value.category}.toMap()
        Category.values().forEach {
            val item = TreeItem(SummaryItem(it))
            if (map.containsKey(it)) {
                item.children.setAll(map[it])
            }
            item.value.slotProperty.bind(integerBinding(item.children) {
                map{it.value.slotProperty.value}.sum()
            })
            item.value.weightProperty.bind(doubleBinding(item.children) {
                map{it.value.weightProperty.value}.sum()
            })
            root.children.add(item)
        }
        tblSummary.root = root
        tblSummary.isShowRoot = false

        colName.cellValueFactory = Callback {it.value.value.nameProperty}
        colSlots.cellValueFactory = Callback {it.value.value.slotProperty}
        colWeight.cellValueFactory = Callback {it.value.value.weightProperty}

        model.mountList.addListener(ListChangeListener {
            while (it.next()) {
                if (it.wasAdded()) {
                    it.addedSubList.forEach {
                        categoryMap[Category.of(it.component)]?.children?.add(TreeItem(SummaryItem(it)))
                    }
                }
                if (it.wasRemoved()) {
                    it.removed.forEach {
                        val node = categoryMap[Category.of(it.component)]
                        node?.children?.remove(node.children.find { item ->
                            item.value.mount == it
                        })
                    }
                }
            }
        })
    }

    internal inner class SummaryItem(val category: Category, val mount: MountModel? = null) {

        constructor(mount: MountModel): this(Category.of(mount.component), mount)

        val nameProperty = SimpleStringProperty(if (mount == null) {
            category.displayName()
        } else {
            mount.component.fullName
        })
        val slotProperty = SimpleIntegerProperty()
        val weightProperty = SimpleDoubleProperty()

        init {
            if (mount != null) {
                slotProperty.bind(mount.slots)
                weightProperty.bind(mount.weight)
            }
        }
    }
}