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
package org.megamek.mekbuilder.javafx.view

import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.component.*
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.UnitType
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import tornadofx.*

/**
 *
 */
internal class UnitSummaryTest: ApplicationTest() {
    lateinit var summary: UnitSummary
    lateinit var model: UnitViewModel

    @Start
    override fun start(stage: Stage) {
        summary = UnitSummary()
        model = summary.model
        stage.scene = Scene(summary.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        Platform.runLater {
            model.rebind {
                unitModel = MekModel(MekBuild())
            }
        }
    }

    @Test
    fun setInitialUnitName() {
        Platform.runLater {
            val mek = MekBuild()
            mek.chassis = "New"
            mek.model = "Unit"
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals("New Unit", summary.lblUnitName.text)
        }
    }

    @Test
    fun updateUnitName() {
        Platform.runLater {
            model.chassisName = "New Chassis"
            model.modelName = ""

            assertEquals("New Chassis", summary.lblUnitName.text)
        }
    }

    @Test
    fun setInitialWeight() {
        Platform.runLater {
            val mek = MekBuild()
            mek.tonnage = 55.0
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals(55.0, summary.lblMaxWeight.text.toDouble(), 0.001)
        }
    }

    @Test
    fun updateWeight() {
        Platform.runLater {
            model.tonnage = 35.0

            assertEquals(35.0, summary.lblMaxWeight.text.toDouble(), 0.001)
        }
    }

    @Test
    fun showPodSpaceInitialOmni() {
        Platform.runLater {
            val mek = MekBuild()
            mek.isOmni = true
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertAll(
                    Executable {assertTrue(summary.lblPodSpaceText.isVisible)},
                    Executable {assertTrue(summary.lblPodSpace.isVisible)}
            )
        }
    }

    @Test
    fun showPodSpaceWhenOmniSelected() {
        Platform.runLater {
            model.omni = true

            assertAll(
                    Executable {assertTrue(summary.lblPodSpaceText.isVisible)},
                    Executable {assertTrue(summary.lblPodSpace.isVisible)}
            )
        }
    }

    @Test
    fun hidePodSpaceWhenOmniNotSelected() {
        Platform.runLater {
            model.omni = false

            assertAll(
                    Executable {assertFalse(summary.lblPodSpaceText.isVisible)},
                    Executable {assertFalse(summary.lblPodSpace.isVisible)}
            )
        }
    }

    @Test
    fun setInitialMovementHeat() {
        Platform.runLater {
            val mek = MekBuild()
            mek.secondaryMotiveType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
            mek.baseWalkMP = 4
            mek.secondaryMP = 4

            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals("4", summary.lblMovementHeat.text)
        }
    }

    @Test
    fun updateMovementHeatWhenEngineChanges() {
        Platform.runLater {
            model.engineType = ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_XXL_IS) as MVFEngine

            assertEquals("6", summary.lblMovementHeat.text)
        }
    }

    @Test
    fun updateMovementHeatWhenJumpMPChanges() {
        Platform.runLater {
            model.secondaryMotiveType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
            model.baseWalkMP = 5
            model.baseSecondaryMP = 4

            assertEquals("4", summary.lblMovementHeat.text)
        }
    }

    @Test
    fun setInitialWeaponHeat() {
        Platform.runLater {
            val mek = MekBuild()
            val laser = mek.createMount(ComponentLibrary.getInstance().getComponent(ComponentKeys.MEDIUM_LASER))
            mek.addMount(laser)

            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals("3", summary.lblWeaponHeat.text)
        }
    }

    @Test
    fun weaponHeatChangesWhenWeaponOrEnhancementAdded() {
        Platform.runLater {
            val ppc = ComponentLibrary.getInstance().getComponent(ComponentKeys.PPC)
            val mount = model.unitModel.addEquipment(ppc)
            val startHeat = summary.lblWeaponHeat.text

            mount.otherComponent = ComponentLibrary.getInstance().getComponent(ComponentKeys.PPC_CAPACITOR)

            assertAll(
                    Executable {assertEquals("10", startHeat)},
                    Executable {assertEquals("15", summary.lblWeaponHeat.text)}
            )
        }
    }

    @Test
    fun setInitialHeatDissipation() {
        Platform.runLater {
            val mek = MekBuild()
            mek.heatSinkMount.count = 4

            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals("14", summary.lblHeatDissipation.text)
        }
    }

    @Test
    fun heatDissipationChangesWhenHeatSinkTypeChanges() {
        Platform.runLater {
            model.heatSinkType = ComponentLibrary.getInstance().getComponent(ComponentKeys.HEAT_SINK_DOUBLE_IS) as HeatSink

            assertEquals("20", summary.lblHeatDissipation.text)
        }
    }

    @Test
    fun heatDissipationChangesWithHeatSinkCount() {
        Platform.runLater {
            model.heatSinkCount = 7

            assertEquals("17", summary.lblHeatDissipation.text)
        }
    }

    @Test
    fun heatDissipationChangesWhenPartialWingAdded() {
        Platform.runLater {
            val wing = ComponentLibrary.getInstance().getComponent(ComponentKeys.PARTIAL_WING_IS)
            model.unitModel.addEquipment(wing)

            assertEquals("13", summary.lblHeatDissipation.text)
        }
    }

    @Test
    fun addMountIncreasesCategoryWeightAndSlots() {
        Platform.runLater {
            val engineNode = summary.tblSummary.root.children.find { it.value.category == Category.ENGINE }!!
            val rows = engineNode.children.size
            val startSlots = engineNode.value.slotProperty.value
            val startWeight = engineNode.value.weightProperty.value

            model.unitModel.addEquipment(ComponentLibrary.getInstance().getComponent(ComponentKeys.MASC_IS))

            assertAll(
                    Executable { assertEquals(rows + 1, engineNode.children.size) },
                    Executable { assertTrue(engineNode.value.slotProperty.value > startSlots) },
                    Executable { assertTrue(engineNode.value.weightProperty.value > startWeight) }
            )
        }
    }

    @Test
    fun removeMountDecreasesCategoryWeightAndSlots() {
        Platform.runLater {
            val engineNode = summary.tblSummary.root.children.find { it.value.category == Category.ENGINE }!!
            model.unitModel.addEquipment(ComponentLibrary.getInstance().getComponent(ComponentKeys.MASC_IS))
            val rows = engineNode.children.size
            val startSlots = engineNode.value.slotProperty.value
            val startWeight = engineNode.value.weightProperty.value

            val mount = model.mountList.find { it.component.internalName == ComponentKeys.MASC_IS }!!
            model.mountList.remove(mount)

            assertAll(
                    Executable { assertEquals(rows - 1, engineNode.children.size) },
                    Executable { assertTrue(engineNode.value.slotProperty.value < startSlots) },
                    Executable { assertTrue(engineNode.value.weightProperty.value < startWeight) }
            )
        }
    }

    @Test
    fun hideZeroSlotAndWeightEntries() {
        Platform.runLater {
            val engineNode = summary.tblSummary.root.children.find { it.value.category == Category.ENGINE }!!
            val startCount = engineNode.children.size

            model.secondaryMotiveType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
            model.baseSecondaryMP = 1

            assertEquals(startCount + 1, engineNode.children.size)
        }
    }

    @Test
    fun sizeChangeUpdatesWeightsAndSlots() {
        Platform.runLater {
            val engineNode = summary.tblSummary.root.children.find { it.value.category == Category.ENGINE }!!
            model.secondaryMotiveType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
            model.baseWalkMP = 4
            model.baseSecondaryMP = 2
            val startSlots = engineNode.value.slotProperty.value
            val startWeight = engineNode.value.weightProperty.value

            model.baseSecondaryMP = 4


            assertAll(
                    Executable { assertTrue(engineNode.value.slotProperty.value > startSlots) },
                    Executable { assertTrue(engineNode.value.weightProperty.value > startWeight) }
            )
        }
    }
}