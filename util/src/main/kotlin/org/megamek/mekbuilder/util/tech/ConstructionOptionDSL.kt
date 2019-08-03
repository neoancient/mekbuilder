package org.megamek.mekbuilder.util.tech

import org.megamek.mekbuilder.tech.*
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType

/**
 *
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
    var prevWeightKey: ConstructionOptionKey? = null
    var nextWeightKey: ConstructionOptionKey? = null

    override fun build(): ConstructionOption = UnitConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, weightIncrement, prevWeightKey, nextWeightKey)
}

open class VehicleConstructionOptionBuilder: UnitConstructionOptionBuilder() {
    var motiveType: MotiveType? = null

    override fun build(): ConstructionOption = VehicleConstructionOption(key, techProg, unitType,
            minWeight, maxWeight, weightIncrement, prevWeightKey, nextWeightKey, motiveType)
}

fun constructionOption(block: ConstructionOptionBuilder.() -> Unit) = ConstructionOptionBuilder().apply(block).build()
fun unitConstructionOption(block: UnitConstructionOptionBuilder.() -> Unit) = UnitConstructionOptionBuilder().apply(block).build()
fun vehicleConstructionOption(block: VehicleConstructionOptionBuilder.() -> Unit) = VehicleConstructionOptionBuilder().apply(block).build()