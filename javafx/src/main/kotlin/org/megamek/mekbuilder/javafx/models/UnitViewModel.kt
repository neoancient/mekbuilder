package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
class UnitViewModel(): ViewModel() {
    var unitModel: UnitModel = MekModel(MekBuild())

    val baseConfiguration = bind{unitModel.baseOptionProperty}
    val minWeight = doubleBinding(baseConfiguration) {unitModel.baseConstructionOption.minWeight}
    val maxWeight = doubleBinding(baseConfiguration) {unitModel.baseConstructionOption.maxWeight}
    val weightIncrement = doubleBinding(baseConfiguration) {unitModel.baseConstructionOption.weightIncrement}
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
    val internalStructure = bind {if (unitModel is MekModel) (unitModel as MekModel).internalStructureProperty else SimpleObjectProperty<Component>()}
    val limbConfiguration = bind {if (unitModel is MekModel) (unitModel as MekModel).limbConfigurationProperty else SimpleObjectProperty<MekBuild.LimbConfiguration>()}
}