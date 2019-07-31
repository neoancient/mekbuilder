package org.megamek.mekbuilder.tech

import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType

/**
 *
 */

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
    var minWeight = 0.0
    var maxWeight = 0.0
    var prevWeightKey: ConstructionOptionKey? = null
    var nextWeightKey: ConstructionOptionKey? = null

    override fun build(): ConstructionOption = UnitConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, prevWeightKey, nextWeightKey)
}

open class VehicleConstructionOptionBuilder: UnitConstructionOptionBuilder() {
    var motiveType: MotiveType? = null

    override fun build(): ConstructionOption = VehicleConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, prevWeightKey, nextWeightKey, motiveType)
}

fun constructionOption(block: ConstructionOptionBuilder.() -> Unit) = ConstructionOptionBuilder().apply(block).build()
fun unitConstructionOption(block: UnitConstructionOptionBuilder.() -> Unit) = UnitConstructionOptionBuilder().apply(block).build()
fun vehicleConstructionOption(block: VehicleConstructionOptionBuilder.() -> Unit) = VehicleConstructionOptionBuilder().apply(block).build()