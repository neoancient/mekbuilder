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
package org.megamek.mekbuilder.util.tech

import org.megamek.mekbuilder.tech.*
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType

/**
 * A domain-specific language for creating ConstructionOption objects
 */

const val PS = "1950"
const val ES = "2100"


open class ConstructionOptionBuilder {
    var key: ConstructionOptionKey? = null
    var techProg = TechProgression()
    fun techProgression(block: TechProgressionBuilder.() -> Unit) {
        techProg = TechProgressionBuilder().apply(block).build()
    }

    open fun build() = ConstructionOption(key, techProg)
}

open class UnitConstructionOptionBuilder: ConstructionOptionBuilder() {
    var unitType: UnitType? = null
    var minWeight = 5.0
    var maxWeight = 100.0
    var weightIncrement = 5.0
    var minKg: Int = 0
        set(value) {
            field = value
            minWeight = value / 1000.0
        }
    var maxKg: Int = 0
        set(value) {
            field = value
            maxWeight = value / 1000.0
        }
    var kgIncrement: Int = 0
        set(value) {
            field = value
            weightIncrement = value / 1000.0
        }

    override fun build(): ConstructionOption = UnitConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, weightIncrement)
}

open class VehicleConstructionOptionBuilder: UnitConstructionOptionBuilder() {
    var motiveType: MotiveType? = null

    override fun build(): ConstructionOption = VehicleConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, weightIncrement, motiveType)
}

fun constructionOption(block: ConstructionOptionBuilder.() -> Unit) = ConstructionOptionBuilder().apply(block).build()
fun unitConstructionOption(block: UnitConstructionOptionBuilder.() -> Unit) = UnitConstructionOptionBuilder().apply(block).build()
fun vehicleConstructionOption(block: VehicleConstructionOptionBuilder.() -> Unit) = VehicleConstructionOptionBuilder().apply(block).build()