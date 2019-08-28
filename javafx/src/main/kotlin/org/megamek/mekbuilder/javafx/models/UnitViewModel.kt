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

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.megamek.mekbuilder.component.Cockpit
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.MVFEngine
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
    var unitModel: UnitModel = MekModel(MekBuild())
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
    val componentListProperty = bind{ SimpleListProperty(unitModel.componentList) }
    var componentList by componentListProperty

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
    val baseRunMPProperty = bind(true) {unitModel.baseRunMPProperty}
    var baseRunMP by baseRunMPProperty
    val walkMPProperty = bind(true) {unitModel.walkMPProperty}
    var walkMP by walkMPProperty
    val runMPProperty = bind(true) {unitModel.runMPProperty}
    var runMP by runMPProperty

    val minTechLevelProperty = SimpleObjectProperty(TechLevel.INTRO)
    var minTechLevel by minTechLevelProperty
    val weightClassProperty = SimpleObjectProperty<UnitWeightClass>()
    var weightClass by weightClassProperty

    // Properties used for several unit types, but not all models have an equivalent property
    val engineTypeProperty = bind (true) {
        if (unitModel is MekModel)
            (unitModel as MekModel).engineTypeProperty
        else SimpleObjectProperty<MVFEngine>()
    }
    var engineType by engineTypeProperty
    val engineRatingProperty = bind (true) {
        if (unitModel is MekModel)
            (unitModel as MekModel).engineRatingProperty
        else SimpleObjectProperty<Int>()
    }
    var engineRating by engineRatingProperty


    // Mek properties
    val mekConfigurationProperty = bind(true) {if (unitModel is MekModel) (unitModel as MekModel).configurationProperty else SimpleObjectProperty<MekConfiguration>()}
    var mekConfiguration by mekConfigurationProperty
    val internalStructureProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).internalStructureProperty else SimpleObjectProperty<Component>()}
    var internalStructure by internalStructureProperty
    val cockpitProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).cockpitTypeProperty else SimpleObjectProperty<Cockpit>()}
    var cockpit by cockpitProperty
    val gyroProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).gyroTypeProperty else SimpleObjectProperty<Component>()}
    var gyro by gyroProperty
    val myomerProperty = bind (true) {if (unitModel is MekModel) (unitModel as MekModel).myomerTypeProperty else SimpleObjectProperty<Component>()}
    var myomer by myomerProperty

    init {
        weightClassProperty.bind(objectBinding(unitModel) {weightClass.value})
    }
}