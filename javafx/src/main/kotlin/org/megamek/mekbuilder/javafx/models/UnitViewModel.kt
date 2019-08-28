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
 *
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