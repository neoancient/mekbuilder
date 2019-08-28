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
import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableIntegerValue
import javafx.collections.ObservableList
import org.megamek.mekbuilder.component.Mount
import org.megamek.mekbuilder.tech.ITechProgression
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitLocation
import org.megamek.mekbuilder.unit.UnitType
import org.megamek.mekbuilder.unit.UnitWeightClass
import tornadofx.*

/**
 * Data model for UnitModel that wraps fields in JavaFX properties
 */
abstract class UnitModel (unitBuild: UnitBuild) {
    val unitProperty = SimpleObjectProperty(unitBuild)
    var unit by unitProperty
    val unitTypeProperty = ReadOnlyObjectWrapper<UnitType>(unit.unitType)

    val baseOptionProperty = observable(unit, UnitBuild::getBaseConstructionOption, UnitBuild::setBaseConstructionOption)
    var baseConstructionOption by baseOptionProperty
    val chassisProperty = observable(unit, UnitBuild::getChassis, UnitBuild::setChassis)
    var chassisName by chassisProperty
    val modelProperty = observable(unit, UnitBuild::getModel, UnitBuild::setModel)
    var modelName by modelProperty
    val sourceProperty = observable(unit, UnitBuild::getSource, UnitBuild::setSource)
    var source by sourceProperty
    val yearProperty = observable(unit, UnitBuild::getYear, UnitBuild::setYear)
    var introYear by yearProperty
    val techBaseProperty = observable(unit, UnitBuild::getTechBase, UnitBuild::setTechBase)
    var techBase by techBaseProperty
    val factionProperty = observable(unit, UnitBuild::getFaction, UnitBuild::setFaction)
    var faction by factionProperty
    val tonnageProperty = observable(unit, UnitBuild::getTonnage, UnitBuild::setTonnage)

    val componentList = ArrayList<Mount>().observable()
    val componentMap = HashMap<UnitLocation, ObservableList<Mount>>().observable()
    val weaponTonnageMap = HashMap<UnitLocation, ObservableDoubleValue>().observable()
    val maxArmorPointsMap = HashMap<UnitLocation, ObservableIntegerValue>().observable()

    val weightClass = objectBinding(tonnageProperty) {unit.weightClass}
    val buildWeight = doubleBinding(componentList) {unit.buildWeight()}
    val weaponTonnage = doubleBinding(componentList) {unit.getWeaponTonnage()}
    val energyWeaponTonnage = doubleBinding(componentList) {unit.getEnergyWeaponTonnage()}
    val tcompLinkedTonnage = doubleBinding(componentList) {unit.getTCLinkedTonnage()}
    fun weaponTonnage(loc: UnitLocation): ObservableDoubleValue {
        weaponTonnageMap.putIfAbsent(loc, doubleBinding(componentMap[loc] ?: observableList())
            {unit.getWeaponTonnage(loc)})
        return weaponTonnageMap[loc] ?: SimpleDoubleProperty()
    }
    fun maxArmorPointsProperty(loc: UnitLocation) = maxArmorPointsMap[loc] ?: SimpleIntegerProperty()

    val omniProperty = observable(unit, UnitBuild::isOmni, UnitBuild::setOmni)
    var isOmni by omniProperty
    val kgStandardProperty = SimpleBooleanProperty(false)
    var usesKgStandard by kgStandardProperty
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

    init {
        baseOptionProperty.onChange {
            if (it != null) {
                // If we're not on mixed tech make sure the tech base matches the construction option
                if ((it.techBase() != TechBase.ALL) && (techBase != TechBase.ALL)) {
                    techBase = it.techBase()
                }
                // Make sure we are meeting the minimum intro year
                val intro = it.introDate(techBase);
                if (intro != null && introYear < intro) {
                    introYear = intro
                }
            }
        }
    }
}