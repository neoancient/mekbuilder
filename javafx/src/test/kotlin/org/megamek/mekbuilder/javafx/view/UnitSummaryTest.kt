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
import org.junit.jupiter.api.Test
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.unit.MekBuild
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

            assertEquals(55.0, summary.lblWeight.text.toDouble(), 0.001)
        }
    }

    @Test
    fun updateWeight() {
        Platform.runLater {
            model.tonnage = 35.0

            assertEquals(35.0, summary.lblWeight.text.toDouble(), 0.001)
        }
    }
}