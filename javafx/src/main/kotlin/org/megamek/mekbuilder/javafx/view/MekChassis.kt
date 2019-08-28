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

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.*
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.ComponentComboBoxCellFactory
import org.megamek.mekbuilder.javafx.util.SpinnerDoubleStringConverter
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*

/**
 * Creates an {@link ObservableList} of all components that match the criteria, sorted alphabetically
 * by shortName with default value(s) moved to the top.
 */
fun createComponentList(op: (Component) -> Boolean) =
        ComponentLibrary.getInstance().allComponents
                .filter{op(it)}.sortedBy{it.shortName}
                .sortedBy{!it.isDefault}.toList().observable()

/**
 * Panel for setting chassis options: tonnage, engine, cockpit, gyro, myomer
 */

class MekChassis: View(), InvalidationListener {
    override val root: AnchorPane by fxml()
    private val techFilter: BasicInfo by inject()
    private val model: UnitViewModel by inject()

    private val spnTonnage: Spinner<Double> by fxid()
    private val lblWeightClass: Label by fxid()
    private val cbStructure: ComboBox<Component> by fxid()
    private val cbEngine: ComboBox<MVFEngine> by fxid()
    private val cbGyro: ComboBox<Component> by fxid()
    private val cbCockpit: ComboBox<Cockpit> by fxid()
    private val cbMyomer: ComboBox<Component> by fxid()

    val allStructures = createComponentList { it.type == ComponentType.MEK_STRUCTURE }
    val allEngines = createComponentList { it.type == ComponentType.ENGINE }
    val allGyros = createComponentList { it.type == ComponentType.GYRO }
    val allCockpits = createComponentList { it.type == ComponentType.COCKPIT }
    val allMyomer = createComponentList { it.type == ComponentType.MYOMER }

    init {
        val tonnageFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(
                model.baseOption.minWeight, model.baseOption.maxWeight,
                model.tonnage.toDouble(),
                model.weightIncrementBinding.value)
        tonnageFactory.amountToStepByProperty().bind(model.weightIncrementBinding)
        tonnageFactory.valueProperty().bindBidirectional(model.tonnageProperty)
        tonnageFactory.converter = SpinnerDoubleStringConverter(spnTonnage.editor, tonnageFactory)
        spnTonnage.valueFactory = tonnageFactory
        // Force update on lost focus (e.g. tab)
        spnTonnage.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnTonnage.increment(0)
        }
        lblWeightClass.text = messages["lblWeightClass.${model.unit.weightClass}"]
        model.tonnageProperty.onChange {
            lblWeightClass.text = messages["lblWeightClass.${model.unit.weightClass}"]
            allStructures.invalidate()
        }

        val structureList = SimpleListProperty<Component>()
        structureList.bind(objectBinding(allStructures) {
            filter { techFilter.isLegal(it, false) && model.unit.allowed(it)}.toList().observable()
        })
        cbStructure.items = structureList
        cbStructure.bind(model.internalStructureProperty)
        ComponentComboBoxCellFactory.setConverter(cbStructure)

        val engineList = SimpleListProperty<MVFEngine>()
        engineList.bind(objectBinding(allEngines) {
            filter { techFilter.isLegal(it, false)
                    && model.unit.allowed(it)
                    && model.engineRatingProperty.value >= it.variableSizeMin()
                    && model.engineRatingProperty.value <= it.variableSizeMax()
                    && (it.hasFlag(ComponentSwitch.FUSION)
                    || model.unit.unitType == UnitType.INDUSTRIAL_MEK
                    || (model.unit as MekBuild).isPrimitive()
                    || techFilter.isLegal(ConstructionOptionKey.NON_FUSION_BATTLEMEK.get()))}
                    .map{it as MVFEngine}.toList().observable()
        })
        cbEngine.items = engineList
        cbEngine.bind(model.engineTypeProperty)
        ComponentComboBoxCellFactory.setConverter(cbEngine)

        val cockpitList = SimpleListProperty<Cockpit>()
        cockpitList.bind(objectBinding(allCockpits) {
            filter { techFilter.isLegal(it, false)
                    && model.unit.allowed(it)}
                    .map{it as Cockpit}.toList().observable()
        })
        cbCockpit.items = cockpitList
        cbCockpit.bind(model.cockpitProperty)
        ComponentComboBoxCellFactory.setConverter(cbCockpit)

        val gyroList = SimpleListProperty<Component>()
        gyroList.bind(objectBinding(allGyros) {
            filter { techFilter.isLegal(it, false)
                    && model.unit.allowed(it)}
                    .toList().observable()
        })
        cbGyro.items = gyroList
        cbGyro.bind(model.gyroProperty)
        ComponentComboBoxCellFactory.setConverter(cbGyro)

        val myomerList = SimpleListProperty<Component>()
        myomerList.bind(objectBinding(allMyomer) {
            filter { techFilter.isLegal(it, false)
                    && model.unit.allowed(it)}
                    .toList().observable()
        })
        cbMyomer.items = myomerList
        cbMyomer.bind(model.myomerProperty)
        ComponentComboBoxCellFactory.setConverter(cbMyomer)

        model.baseOptionProperty.onChange {
            if (it != null) {
                tonnageFactory.max = it.maxWeight
                tonnageFactory.min = it.minWeight
                if (tonnageFactory.value < it.minWeight) {
                    tonnageFactory.value = it.minWeight
                }
                if (tonnageFactory.value > it.maxWeight) {
                    tonnageFactory.value = it.maxWeight
                }
                allStructures.invalidate()
                if (model.internalStructure !in structureList) {
                    cbStructure.selectionModel.select(structureList.first())
                }
                allEngines.invalidate()
                if (model.engineType !in engineList) {
                    cbEngine.selectionModel.select(engineList.first())
                }
                allGyros.invalidate()
                if (model.gyro !in gyroList) {
                    cbGyro.selectionModel.select(gyroList.first())
                }
                allCockpits.invalidate()
                if (model.cockpit !in cockpitList) {
                    cbCockpit.selectionModel.select(cockpitList.first())
                }
                allMyomer.invalidate()
                if (model.myomer !in myomerList) {
                    cbMyomer.selectionModel.select(myomerList.first())
                }
            }
        }

        techFilter.addListener(this)
    }

    override fun invalidated(observable: Observable) {
        allStructures.invalidate()
        allEngines.invalidate()
        allCockpits.invalidate()
        allGyros.invalidate()
        allMyomer.invalidate()
        if (model.internalStructureProperty.value !in cbStructure.items
                && cbStructure.items.isNotEmpty()) {
            cbStructure.selectionModel.select(cbStructure.items.first())
        }
    }
}