package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.*
import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableIntegerValue
import javafx.collections.ObservableList
import org.megamek.mekbuilder.component.Mount
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitLocation
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*

/**
 *
 */
abstract class UnitModel (unitBuild: UnitBuild) {
    val unit = unitBuild
    val unitTypeProperty = ReadOnlyObjectWrapper<UnitType>(unit.unitType)

    val baseOptionProperty = unit.observable(UnitBuild::getBaseConstructionOption, UnitBuild::setBaseConstructionOption)
    var baseConstructionOption by baseOptionProperty
    val chassisProperty = unit.observable(UnitBuild::getChassis, UnitBuild::setChassis)
    var chassisName by chassisProperty
    val modelProperty = unit.observable(UnitBuild::getModel, UnitBuild::setModel)
    var modelName by modelProperty
    val sourceProperty = unit.observable(UnitBuild::getSource, UnitBuild::setSource)
    var source by sourceProperty
    val yearProperty = unit.observable(UnitBuild::getYear, UnitBuild::setYear)
    var introYear by yearProperty
    val techBaseProperty = unit.observable(UnitBuild::getTechBase, UnitBuild::setTechBase)
    var techBase by techBaseProperty
    val factionProperty = unit.observable(UnitBuild::getFaction, UnitBuild::setFaction)
    var faction by factionProperty
    val tonnageProperty = unit.observable(UnitBuild::getTonnage, UnitBuild::setTonnage)

    val componentList = ArrayList<Mount>().observable()
    val componentMap = HashMap<UnitLocation, ObservableList<Mount>>().observable()
    val weaponTonnageMap = HashMap<UnitLocation, ObservableDoubleValue>().observable()
    val maxArmorPointsMap = HashMap<UnitLocation, ObservableIntegerValue>().observable()

    val buildWeight = doubleBinding(componentList) {unit.buildWeight()}
    val weightClassProperty = objectBinding(buildWeight) {unit.getWeightClass()}
    val weaponTonnage = doubleBinding(componentList) {unit.getWeaponTonnage()}
    val energyWeaponTonnage = doubleBinding(componentList) {unit.getEnergyWeaponTonnage()}
    val tcompLinkedTonnage = doubleBinding(componentList) {unit.getTCLinkedTonnage()}
    fun weaponTonnage(loc: UnitLocation): ObservableDoubleValue {
        weaponTonnageMap.putIfAbsent(loc, doubleBinding(componentMap[loc] ?: observableList())
            {unit.getWeaponTonnage(loc)})
        return weaponTonnageMap[loc] ?: SimpleDoubleProperty()
    }
    fun maxArmorPointsProperty(loc: UnitLocation) = maxArmorPointsMap[loc] ?: SimpleIntegerProperty()

    val kgStandardProperty = SimpleBooleanProperty(false)
    var usesKgStandard by kgStandardProperty
    val declaredTonnageProperty = SimpleDoubleProperty(0.0)
    var declaredTonnage by declaredTonnageProperty
    val structureTonnageProperty = SimpleDoubleProperty(0.0)
    var structureTonnage by structureTonnageProperty
    val armorTonnageProperty = SimpleDoubleProperty(0.0)
    var armorTonnage by armorTonnageProperty
    val totalArmorPointsProperty = SimpleDoubleProperty(0.0)
    var totalArmorPoints by totalArmorPointsProperty
    val defaultArmorNameProperty = SimpleStringProperty("")
    var defaultArmorName by defaultArmorNameProperty
    val baseWalkMPProperty = SimpleIntegerProperty(0)
    var baseWalkMP by baseWalkMPProperty
    val baseRunMPProperty = SimpleIntegerProperty(0)
    var baseRunMP by baseRunMPProperty
    val walkMPProperty = SimpleIntegerProperty(0)
    var walkMP by walkMPProperty
    val runMPProperty = SimpleIntegerProperty(0)
    var runMP by runMPProperty

}