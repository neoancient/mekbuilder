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

import javafx.beans.property.SimpleIntegerProperty
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 * Data model for MekBuild that provides wraps fields in JavaFX properties
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val configurationProperty = pojoProperty(mekBuild, MekBuild::getConfiguration, MekBuild::setConfiguration)
    val engineRatingProperty = SimpleIntegerProperty(0)

    init {
        engineRatingProperty.bind(integerBinding(mekBuild,
                baseWalkMPProperty, tonnageProperty, configurationProperty) {
            engineRating
        })
        // Possible switch between standard/large engine
        baseWalkMPProperty.onChange {
            getEngine().componentProperty.refresh()
        }
        // Possible switch between standard/large engine or reduction of MP due to exceeding max
        tonnageProperty.onChange {
            getEngine().componentProperty.refresh()
        }
        walkMPProperty.bind(baseWalkMPProperty)
        runMPProperty.bind(stringBinding(mekBuild, baseRunMPProperty,
                getMyomer().componentProperty, mountList) {
            formattedRunMP()
        })
        secondaryMPProperty.bind(integerBinding(mekBuild, baseSecondaryMPProperty, mountList) {
            getSecondaryMP()
        })
        maxWalkProperty.bind(integerBinding(mekBuild,
                configurationProperty, getEngine().componentProperty, tonnageProperty,
                techFilterProperty) {
            maxWalkMP(techFilter)
        })
        minWalkProperty.bind(integerBinding(mekBuild, configurationProperty,
                secondaryMotiveProperty) {
            minWalkMP()
        })
        maxSecondaryMPProperty.bind(integerBinding(mekBuild, baseWalkMPProperty,
                configurationProperty, getSecondaryMotive().componentProperty) {
            maxSecondaryMP()
        })
        minSecondaryMPProperty.bind(integerBinding(mekBuild, configurationProperty, getSecondaryMotive().componentProperty) {
            minSecondaryMP()
        })
        structureTonnageProperty.bind(doubleBinding(
                getInternalStructure().componentProperty, tonnageProperty, configurationProperty)
            {mekBuild.structureTonnage})
    }

    fun getEngine() = mountList.first{it.component.type == ComponentType.ENGINE}!!
    fun getCockpit() = mountList.first{it.component.type == ComponentType.COCKPIT}!!
    fun getHeatSinks() = mountList.first{it.component.type == ComponentType.HEAT_SINK}!!
    fun getInternalStructure() = mountList.first{it.component.type == ComponentType.MEK_STRUCTURE}!!
    fun getGyro() = mountList.first{it.component.type == ComponentType.GYRO}!!
    fun getMyomer() = mountList.first{it.component.type == ComponentType.MYOMER}!!

}