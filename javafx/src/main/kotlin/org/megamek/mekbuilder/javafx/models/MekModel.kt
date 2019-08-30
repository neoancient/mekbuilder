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
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 * Data model for MekBuild that provides wraps fields in JavaFX properties
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val configurationProperty = pojoProperty(mekBuild, MekBuild::getConfiguration, MekBuild::setConfiguration)
    val internalStructureProperty = pojoProperty(mekBuild, MekBuild::getStructureType, MekBuild::setStructureType)
    val engineTypeProperty = pojoProperty(mekBuild, MekBuild::getEngineType, MekBuild::setEngineType)
    val engineRatingProperty = SimpleIntegerProperty(0)
    val cockpitTypeProperty = pojoProperty(mekBuild, MekBuild::getCockpitType, MekBuild::setCockpitType)
    val gyroTypeProperty = pojoProperty(mekBuild, MekBuild::getGyroType, MekBuild::setGyroType)
    val myomerTypeProperty = pojoProperty(mekBuild, MekBuild::getMyomerType, MekBuild::setMyomerType)

    init {
        engineRatingProperty.bind(integerBinding(mekBuild,
                baseWalkMPProperty, tonnageProperty, configurationProperty) {
            engineRating
        })
        // Possible switch between standard/large engine
        baseWalkMPProperty.onChange {
            engineTypeProperty.refresh()
        }
        // Possible switch between standard/large engine or reduction of MP due to exceeding max
        tonnageProperty.onChange {
            engineTypeProperty.refresh()
        }
        walkMPProperty.bind(baseWalkMPProperty)
        runMPProperty.bind(stringBinding(mekBuild, baseRunMPProperty,
                myomerTypeProperty, componentList) {
            formattedRunMP()
        })
        minWalkProperty.bind(integerBinding(mekBuild, configurationProperty) {
            minWalkMP()
        })
        maxWalkProperty.bind(integerBinding(mekBuild,
                configurationProperty, engineTypeProperty, tonnageProperty,
                techFilterProperty) {
            maxWalkMP(techFilter)
        })
        structureTonnageProperty.bind(doubleBinding(
                internalStructureProperty, tonnageProperty, configurationProperty)
            {mekBuild.structureTonnage})
    }
}