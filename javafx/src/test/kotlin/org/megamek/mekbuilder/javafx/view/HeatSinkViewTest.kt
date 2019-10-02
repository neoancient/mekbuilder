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
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.component.ComponentKeys
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.component.HeatSink
import org.megamek.mekbuilder.component.MVFEngine
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.component
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.ComboBoxMatchers
import tornadofx.*

/**
 *
 */
internal class HeatSinkViewTest: ApplicationTest() {
    private lateinit var view: HeatSinkView
    private lateinit var model: UnitViewModel

    @Start
    override fun start(stage: Stage) {
        view = HeatSinkView()
        model = view.model
        stage.scene = Scene(view.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        model.rebind {
            unitModel = MekModel(MekBuild())
        }
    }

    @Test
    fun setInitialHeatSinkType() {
        Platform.runLater {
            val mek = MekBuild()
            val hs = ComponentLibrary.getInstance()
                    .getComponent(ComponentKeys.HEAT_SINK_DOUBLE_IS)
            mek.heatSinkMount.component = hs

            model.rebind {
                unitModel = MekModel(mek)
            }

            assertThat(view.cbHeatSinkType, ComboBoxMatchers.hasSelectedItem(hs))
        }
    }

    @Test
    fun setInitialHeatSinkCount() {
        Platform.runLater {
            val mek = MekBuild()
            mek.heatSinkMount.count = 4

            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals(4, view.spnHeatSinkCount.value)
        }
    }

    @Test
    fun changingTechBaseSelectsCorrespondingHSType() {
        Platform.runLater {
            model.heatSinkType = ComponentKeys.HEAT_SINK_DOUBLE_IS.component() as HeatSink
            view.techFilter.cbTechBase.selectionModel.select(TechBase.CLAN)

            assertEquals(ComponentKeys.HEAT_SINK_DOUBLE_CLAN.component(),
                    model.heatSinkType)
        }
    }

    @Test
    fun changingEngineUpdatesWeightFreeText() {
        Platform.runLater {
            val start = view.lblWeightFree.text

            model.engineType = ComponentKeys.ENGINE_ICE.component() as MVFEngine

            assertAll(
                    Executable{assertEquals("10", start)},
                    Executable{assertEquals("0", view.lblWeightFree.text)}
            )
        }
    }

    @Test
    fun changingEngineRatingIncreasesIntegratedSlots() {
        Platform.runLater {
            model.tonnage = 50.0
            val startSlots = view.lblSlotsIntegrated.text.toInt()

            model.baseWalkMP += 2

            assertTrue(view.lblSlotsIntegrated.text.toInt() > startSlots)
        }
    }

    @Test
    fun switchToCompactIncreasesIntegratedSlots() {
        Platform.runLater {
            model.tonnage = 20.0
            model.baseWalkMP = 4
            val startSlots = view.lblSlotsIntegrated.text.toInt()

            model.heatSinkType = ComponentKeys.HEAT_SINK_COMPACT.component() as HeatSink

            assertEquals(startSlots * 2, view.lblSlotsIntegrated.text.toInt())
        }
    }

    @Test
    fun setInitialIntegratedSlots() {
        Platform.runLater {
            val mek = MekBuild()
            // Engine rating 250 / 25 == 10 integrated
            mek.tonnage = 50.0
            mek.baseWalkMP = 5
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals(10, view.lblSlotsIntegrated.text.toInt())
        }
    }
}