package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
import tornadofx.*

/**
 *
 */
class UnitViewModel(): ViewModel() {
    var unitModel: UnitModel = MekModel(MekBuild())
    val unitType = bind{unitModel.unitTypeProperty}
    val baseOption = bind{unitModel.baseOptionProperty}
    val minWeight = doubleBinding(baseOption) {unitModel.baseConstructionOption.minWeight}
    val maxWeight = doubleBinding(baseOption) {unitModel.baseConstructionOption.maxWeight}
    val weightIncrement = doubleBinding(baseOption) {unitModel.baseConstructionOption.weightIncrement}
    val chassisName = bind{unitModel.chassisProperty}
    val modelName = bind{unitModel.modelProperty}
    val source = bind{unitModel.sourceProperty}
    val introYear = bind{unitModel.yearProperty}
    val techBase = bind{unitModel.techBaseProperty}
    val faction = bind{unitModel.factionProperty}
    val componentList = bind{ SimpleListProperty(unitModel.componentList) }

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

    // Mek properties
    val mekConfiguration = bind{if (unitModel is MekModel) (unitModel as MekModel).configurationProperty else SimpleObjectProperty<MekConfiguration>()}
    val internalStructure = bind {if (unitModel is MekModel) (unitModel as MekModel).internalStructureProperty else SimpleObjectProperty<Component>()}
}