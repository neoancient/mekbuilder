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
package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.*
import javafx.collections.ListChangeListener
import org.megamek.mekbuilder.component.*
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.ITechFilter
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
import org.megamek.mekbuilder.unit.UnitWeightClass
import tornadofx.*

/**
 * The actual view model for the UI, which serves as an adapter for the various unit-specific models. These
 * are the properties that are actually bound to controls. The properties of the underlying model are accessed
 * through suppliers that allow the model to be changed without having rebind any properties.
 */
class UnitViewModel(): ViewModel() {
    val techFilterProperty = SimpleObjectProperty<ITechFilter>()
    var techFilter by techFilterProperty

    val unitModelProperty: ObjectProperty<UnitModel> = SimpleObjectProperty(MekModel(MekBuild()))
    var unitModel by unitModelProperty
    val unitProperty = bind{unitModel.unitProperty}
    var unit by unitProperty

    val unitTypeProperty = bind{unitModel.unitTypeProperty}
    var unitType by unitTypeProperty
    val baseOptionProperty = bind(true) {unitModel.baseOptionProperty}
    var baseOption by baseOptionProperty
    val weightIncrementBinding = doubleBinding(baseOptionProperty) {unitModel.baseConstructionOption.weightIncrement}
    val chassisNameProperty = bind(true) {unitModel.chassisProperty}
    var chassisName by chassisNameProperty
    val modelNameProperty = bind(true) {unitModel.modelProperty}
    var modelName by modelNameProperty
    val sourceProperty = bind(true) {unitModel.sourceProperty}
    var source by sourceProperty
    val introYearProperty = bind(true) {unitModel.yearProperty}
    var introYear by introYearProperty
    val techBaseProperty = bind(true) {unitModel.techBaseProperty}
    var techBase by techBaseProperty
    val factionProperty = bind(true) {unitModel.factionProperty}
    var faction by factionProperty
    val tonnageProperty = bind(true) {unitModel.tonnageProperty}
    var tonnage by tonnageProperty
    val availableSlotsProperty = bind{unitModel.availableSlotsProperty}
    val availableSlots by availableSlotsProperty
    val mountListProperty = bind{ unitModel.mountList }
    var mountList by mountListProperty

    val omniProperty = bind(true) {unitModel.omniProperty}
    var omni by omniProperty
    val kgStandardProperty = bind(true) {unitModel.kgStandardProperty}
    var kgStandard by kgStandardProperty
    val structureTonnageProperty = bind {unitModel.structureTonnageProperty}
    var structureTonnage by structureTonnageProperty
    val armorTonnageProperty = bind(true) {unitModel.armorTonnageProperty}
    var armorTonnage by armorTonnageProperty
    val totalArmorPointsProperty = bind(true) {unitModel.totalArmorPointsProperty}
    var totalArmorPoints by totalArmorPointsProperty
    val defaultArmorNameProperty = bind(true) {unitModel.defaultArmorNameProperty}
    var defaultArmorName by defaultArmorNameProperty
    val baseWalkMPProperty = bind(true) {unitModel.baseWalkMPProperty}
    var baseWalkMP by baseWalkMPProperty
    val baseRunMPProperty = bind {unitModel.baseRunMPProperty}
    var baseRunMP by baseRunMPProperty
    val walkMPProperty = bind{unitModel.walkMPProperty}
    var walkMP by walkMPProperty
    val runMPProperty = bind{unitModel.runMPProperty}
    var runMP by runMPProperty
    val minWalkProperty = bind{unitModel.minWalkProperty.asObject()}
    var minWalk by minWalkProperty
    val maxWalkProperty = bind{unitModel.maxWalkProperty.asObject()}
    var maxWalk by maxWalkProperty
    val secondaryMotiveProperty = bind(true) {unitModel.getSecondaryMotive().componentProperty as Property<SecondaryMotiveSystem>}
    var secondaryMotiveType by secondaryMotiveProperty
    val baseSecondaryMPProperty = bind(true) {unitModel.baseSecondaryMPProperty}
    var baseSecondaryMP by baseSecondaryMPProperty
    val secondaryMPProperty = bind{unitModel.secondaryMPProperty}
    var secondaryMP by secondaryMPProperty
    val minSecondaryMPProperty = bind{unitModel.minSecondaryMPProperty.asObject()}
    var minSecondaryMP by minSecondaryMPProperty
    val maxSecondaryMPProperty = bind{unitModel.maxSecondaryMPProperty.asObject()}
    var maxSecondaryMP by maxSecondaryMPProperty
    val heatSinkCountProperty = bind(true) {unitModel.heatSinkCountProperty}
    var heatSinkCount by heatSinkCountProperty

    val minTechLevelProperty = SimpleObjectProperty(TechLevel.INTRO)
    var minTechLevel by minTechLevelProperty
    val weightClassProperty = SimpleObjectProperty<UnitWeightClass>()
    var weightClass by weightClassProperty

    // Properties used for several unit types, but not all models have an equivalent property
    val engineTypeProperty = bind (true) {
        when {
            unitModel is MekModel -> (unitModel as MekModel).engineTypeProperty
            else -> SimpleObjectProperty<MVFEngine>()
        }
    }
    var engineType by engineTypeProperty
    val engineRatingProperty = bind {
        if (unitModel is MekModel)
            (unitModel as MekModel).engineRatingProperty
        else SimpleObjectProperty<Int>()
    }
    var engineRating by engineRatingProperty
    val heatSinkTypeProperty = bind(true) {unitModel.heatSinkTypeProperty}
    var heatSinkType by heatSinkTypeProperty

    // Mek properties
    val mekConfigurationProperty = bind(true) {if (unitModel is MekModel) (unitModel as MekModel).configurationProperty else SimpleObjectProperty<MekConfiguration>()}
    var mekConfiguration by mekConfigurationProperty
    val internalStructureProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).getInternalStructure().componentProperty else SimpleObjectProperty<Component>()}
    var internalStructure by internalStructureProperty
    val cockpitProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).getCockpit().componentProperty as Property<Cockpit> else SimpleObjectProperty<Cockpit>()}
    var cockpit by cockpitProperty
    val gyroProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).getGyro().componentProperty else SimpleObjectProperty<Component>()}
    var gyro by gyroProperty
    val myomerProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).getMyomer().componentProperty else SimpleObjectProperty<Component>()}
    var myomer by myomerProperty

    val integratedHeatSinkProperty = bind{if (unitModel is MekModel) (unitModel as MekModel).integratedHeatSinksProperty else SimpleIntegerProperty()}
    val movementHeat = bind{unitModel.movementHeatProperty}
    val maxWeaponHeat = bind{unitModel.maxWeaponHeatProperty}
    val heatDissipation = bind{unitModel.heatDissipationProperty}

    init {
        minTechLevelProperty.bind(baseOptionProperty.objectBinding(omniProperty) {
            var tl = it?.staticTechLevel() ?: TechLevel.STANDARD
            if (omni) {
                val option = if (unit.unitType.isVehicle)
                    ConstructionOptionKey.OMNI_VEHICLE.get()
                else ConstructionOptionKey.OMNI.get()
                if (option.staticTechLevel() > tl) {
                    tl = option.staticTechLevel()
                }
            }
            tl
        })
        unitModel.techFilterProperty.bind(techFilterProperty)
        unitModelProperty.addListener{
            _, oldValue, newValue ->
                oldValue.techFilterProperty.unbind()
                newValue.techFilterProperty.bind(techFilterProperty)
        }
        weightClassProperty.bind(objectBinding(unitModel) {weightClass.value})
    }

    /**
     * Adds a change listener to the mount list and handles rebinding when the mountList
     * changes, since the ViewModel rebinding isn't handling ListChangeListeners reliably. Note that
     * if the listener uses the value of mountList, it should use the one from the underlying model,
     * since this listener fires when the unitModel is updated, which is before the properties are rebound.
     *
     * @param l The listener to be called when {@link #mountList} is invalidated.
     */
    fun addMountListener(l: ListChangeListener<MountModel>) {
        mountList.addListener(l)
        unitModelProperty.addListener {_, oldV, newV ->
            oldV.mountList.removeListener(l)
            newV.mountList.addListener(l)
        }
    }
}