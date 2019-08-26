package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
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

    val omni = bind{unitModel.omniProperty}
    val kgStandard = bind{unitModel.kgStandardProperty}
    val declaredTonnage = bind{unitModel.declaredTonnageProperty}
    val structureTonnage = bind{unitModel.structureTonnageProperty}
    val armorTonnage = bind{unitModel.armorTonnageProperty}
    val totalArmorPoints = bind{unitModel.totalArmorPointsProperty}
    val defaultArmorName = bind{unitModel.defaultArmorNameProperty}
    val baseWalkMP = bind{unitModel.baseWalkMPProperty}
    val baseRunMP = bind{unitModel.baseRunMPProperty}
    val walkMP = bind{unitModel.walkMPProperty}
    val runMP = bind{unitModel.runMPProperty}

    val minTechLevelProperty = SimpleObjectProperty(TechLevel.INTRO)

    // Mek properties
    val mekConfiguration = bind(true) {if (unitModel is MekModel) (unitModel as MekModel).configurationProperty else SimpleObjectProperty<MekConfiguration>()}
    val internalStructure = bind {if (unitModel is MekModel) (unitModel as MekModel).internalStructureProperty else SimpleObjectProperty<Component>()}
}