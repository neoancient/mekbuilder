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
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.component.Mount
import org.megamek.mekbuilder.tech.ITechFilter
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitLocation
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2

/**
 * Replacement for {@link Properties#observable}. This version fires a value changed event when the setter
 * is called.
 */
fun <S : Any, T> pojoProperty(bean: S, getter: KFunction<T>, setter: KFunction2<S, T, Unit>? = null): PojoProperty<T> {
    val propName = getter.name.substring(3).decapitalize()

    return object : PojoProperty<T>(bean, propName) {
        override fun get() = getter.call(bean)
        override fun set(newValue: T) {
            setter?.invoke(bean, newValue)
            refresh()
        }
    }
}

/**
 * Data model for UnitModel that wraps fields in JavaFX properties
 */
abstract class UnitModel (unitBuild: UnitBuild) {
    val unitProperty = SimpleObjectProperty(unitBuild)
    var unit by unitProperty
    val unitTypeProperty = ReadOnlyObjectWrapper<UnitType>(unit.unitType)
    var unitType by unitTypeProperty
    val techFilterProperty = SimpleObjectProperty<ITechFilter>()
    var techFilter by techFilterProperty

    val baseOptionProperty = pojoProperty(unit, UnitBuild::getBaseConstructionOption, UnitBuild::setBaseConstructionOption)
    var baseConstructionOption by baseOptionProperty
    val chassisProperty = pojoProperty(unit, UnitBuild::getChassis, UnitBuild::setChassis)
    var chassisName by chassisProperty
    val modelProperty = pojoProperty(unit, UnitBuild::getModel, UnitBuild::setModel)
    var modelName by modelProperty
    val sourceProperty = pojoProperty(unit, UnitBuild::getSource, UnitBuild::setSource)
    var source by sourceProperty
    val yearProperty = pojoProperty(unit, UnitBuild::getYear, UnitBuild::setYear)
    var introYear by yearProperty
    val techBaseProperty = pojoProperty(unit, UnitBuild::getTechBase, UnitBuild::setTechBase)
    var techBase by techBaseProperty
    val factionProperty = pojoProperty(unit, UnitBuild::getFaction, UnitBuild::setFaction)
    var faction by factionProperty
    val tonnageProperty = pojoProperty(unit, UnitBuild::getTonnage, UnitBuild::setTonnage)

    val mountList = SimpleListProperty(FXCollections.observableArrayList<MountModel> {
        arrayOf(it.componentProperty, it.sizeProperty, it.locationProperty,
                it.moduleProperty, it.rearFacingProperty, it.armoredProperty)})
    val mountMap = HashMap<UnitLocation, ObservableList<Mount>>().observable()
    val weaponTonnageMap = HashMap<UnitLocation, ObservableDoubleValue>().observable()
    val maxArmorPointsMap = HashMap<UnitLocation, ObservableIntegerValue>().observable()

    val weightClass = objectBinding(tonnageProperty) {unit.weightClass}
    val buildWeight = doubleBinding(mountList) {unit.buildWeight()}
    val weaponTonnage = doubleBinding(mountList) {unit.getWeaponTonnage()}
    val energyWeaponTonnage = doubleBinding(mountList) {unit.getEnergyWeaponTonnage()}
    val tcompLinkedTonnage = doubleBinding(mountList) {unit.getTCLinkedTonnage()}
    fun weaponTonnage(loc: UnitLocation): ObservableDoubleValue {
        weaponTonnageMap.putIfAbsent(loc, doubleBinding(mountMap[loc] ?: observableList())
            {unit.getWeaponTonnage(loc)})
        return weaponTonnageMap[loc] ?: SimpleDoubleProperty()
    }
    fun maxArmorPointsProperty(loc: UnitLocation) = maxArmorPointsMap[loc] ?: SimpleIntegerProperty()

    val omniProperty = pojoProperty(unit, UnitBuild::isOmni, UnitBuild::setOmni)
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
    val baseWalkMPProperty = pojoProperty(unitBuild, UnitBuild::getBaseWalkMP, UnitBuild::setBaseWalkMP)
    var baseWalkMP by baseWalkMPProperty
    val baseRunMPProperty = SimpleIntegerProperty(0)
    var baseRunMP by baseRunMPProperty
    val walkMPProperty = SimpleIntegerProperty(0)
    var walkMP by walkMPProperty
    val runMPProperty = SimpleStringProperty("")
    var runMP by runMPProperty
    val minWalkProperty = SimpleIntegerProperty(0)
    var minWalk by minWalkProperty
    val maxWalkProperty = SimpleIntegerProperty(0)
    var maxWalk by maxWalkProperty
    val secondaryMotiveProperty = pojoProperty(unit, UnitBuild::getSecondaryMotiveType, UnitBuild::setSecondaryMotiveType)
    var secondaryMotiveType by secondaryMotiveProperty
    val baseSecondaryMPProperty = pojoProperty(unit, UnitBuild::getBaseSecondaryMP, UnitBuild::setSecondaryMP)
    var baseSecondaryMP by baseSecondaryMPProperty
    val secondaryMPProperty = SimpleIntegerProperty(0)
    var secondaryMP by secondaryMPProperty
    val minSecondaryMPProperty = SimpleIntegerProperty(0)
    var minSecondaryMP by minSecondaryMPProperty
    val maxSecondaryMPProperty = SimpleIntegerProperty(0)
    var maxSecondaryMP by maxSecondaryMPProperty

    init {
        mountList.setAll(unit.components.map{MountModel(it)})
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
        baseRunMPProperty.bind(integerBinding(unitBuild, baseWalkMPProperty) {baseRunMP})
        secondaryMPProperty.onChange {
            val mount = getSecondaryMotive()
            mount.slots.invalidate()
            mount.weight.invalidate()
        }
        tonnageProperty.onChange {refreshMountCalculations()}
        walkMPProperty.onChange {refreshMountCalculations()}
    }

    fun refreshMountCalculations() {
        mountList.forEach {
            it.weight.invalidate()
            it.slots.invalidate()
            it.cost.invalidate()
        }
    }

    fun getSecondaryMotive() = mountList.first{it.component.type == ComponentType.SECONDARY_MOTIVE_SYSTEM}

    fun addEquipment(c: Component, size: Double = 1.0) {
        val mount = MountModel(unit.createMount(c))
        if (c.variableSize()) {
            mount.size = size
        }
        unit.addMount(mount.mount)
        mountList.add(mount)
    }

    fun removeEquipment(m: MountModel) {
        unit.removeMount(m.mount)
        mountList.remove(m)
    }
}