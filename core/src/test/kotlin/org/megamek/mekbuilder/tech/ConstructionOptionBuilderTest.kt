package org.megamek.mekbuilder.tech

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType

/**
 *
 */
internal class ConstructionOptionBuilderTest {
    @Test
    fun testBaseOption() {
        val option = constructionOption {
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XEED"
                clanProgression {
                    prototype = "~2854(CCY/CSF)"
                    production = "2856(CCY)"
                    common = "2864"
                }
                isProgression {
                    production = "3052(DC)"
                    common = "3052"
                }
                staticLevel = TechLevel.STANDARD
            }
        }

        assertAll(
                Executable {assertEquals(option.techBase(), TechBase.ALL)},
                Executable {assertEquals(option.getDate(TechStage.COMMON, true), 2864)},
                Executable {assertEquals(option.getDate(TechStage.COMMON, false), 3052)}
        )
    }

    @Test
    fun testUnitOption() {
        val option = unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            prevWeightKey = ConstructionOptionKey.MEK_ULTRALIGHT
            nextWeightKey = ConstructionOptionKey.MEK_SUPERHEAVY
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CEDC"
                progression {
                    prototype = "2460(TH)"
                    production = "2470(TH)"
                    common = "2500"
                }
                staticLevel = TechLevel.STANDARD
            }
        }

        assertAll(
                Executable {assertTrue(option is UnitConstructionOption)},
                Executable {assertEquals((option as UnitConstructionOption).unitType, UnitType.BATTLE_MEK)},
                Executable {assertEquals((option as UnitConstructionOption).maxWeight, 100.0, 0.001)}
        )
    }

    @Test
    fun testVehicleOption() {
        val option = vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 5.0
            maxWeight = 80.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_WHEELED
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        }

        assertAll(
                Executable {assertTrue(option is VehicleConstructionOption)},
                Executable {assertEquals((option as VehicleConstructionOption).unitType, UnitType.COMBAT_VEHICLE)},
                Executable {assertEquals((option as VehicleConstructionOption).motiveType, MotiveType.WHEELED)},
                Executable {assertEquals((option as VehicleConstructionOption).maxWeight, 80.0, 0.001)}
        )
    }
}