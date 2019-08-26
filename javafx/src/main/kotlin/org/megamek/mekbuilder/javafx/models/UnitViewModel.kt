package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
import org.megamek.mekbuilder.unit.UnitWeightClass
import tornadofx.*

/**
 *
 */
class UnitViewModel(): ViewModel() {
    var unitModel: UnitModel = MekModel(MekBuild())

    val unitType = bind{unitModel.unitTypeProperty}
    val baseOption = bind(true) {unitModel.baseOptionProperty}
    val weightIncrement = doubleBinding(baseOption) {unitModel.baseConstructionOption.weightIncrement}
    val chassisName = bind(true) {unitModel.chassisProperty}
    val modelName = bind(true) {unitModel.modelProperty}
    val source = bind(true) {unitModel.sourceProperty}
    val introYear = bind(true) {unitModel.yearProperty}
    val techBase = bind(true) {unitModel.techBaseProperty}
    val faction = bind(true) {unitModel.factionProperty}
    val tonnage = bind(true) {unitModel.tonnageProperty}
    val componentList = bind{ SimpleListProperty(unitModel.componentList) }

    val omni = bind(true) {unitModel.omniProperty}
    val kgStandard = bind(true) {unitModel.kgStandardProperty}
    val structureTonnage = bind(true) {unitModel.structureTonnageProperty}
    val armorTonnage = bind(true) {unitModel.armorTonnageProperty}
    val totalArmorPoints = bind(true) {unitModel.totalArmorPointsProperty}
    val defaultArmorName = bind(true) {unitModel.defaultArmorNameProperty}
    val baseWalkMP = bind(true) {unitModel.baseWalkMPProperty}
    val baseRunMP = bind(true) {unitModel.baseRunMPProperty}
    val walkMP = bind(true) {unitModel.walkMPProperty}
    val runMP = bind(true) {unitModel.runMPProperty}

    val minTechLevelProperty = SimpleObjectProperty(TechLevel.INTRO)
    val weightClassProperty = SimpleObjectProperty<UnitWeightClass>()

    // Mek properties
    val mekConfiguration = bind(true) {if (unitModel is MekModel) (unitModel as MekModel).configurationProperty else SimpleObjectProperty<MekConfiguration>()}
    val internalStructure = bind {if (unitModel is MekModel) (unitModel as MekModel).internalStructureProperty else SimpleObjectProperty<Component>()}

    init {
        weightClassProperty.bind(objectBinding(unitModel) {weightClass.value})
    }
}