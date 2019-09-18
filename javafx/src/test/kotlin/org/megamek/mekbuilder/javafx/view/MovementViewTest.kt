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
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.component.ComponentKeys
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.component.SecondaryMotiveSystem
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MotiveType
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.ComboBoxMatchers
import tornadofx.*

/**
 *
 */
internal class MovementViewTest: ApplicationTest() {
    private lateinit var movementView: MovementView
    private lateinit var model: UnitViewModel

    @Start
    override fun start(stage: Stage) {
        movementView = MovementView()
        model = movementView.model
        stage.scene = Scene(movementView.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        model.rebind {
            unitModel = MekModel(MekBuild())
        }
    }

    @Test
    fun setInitialWalkMP() {
        Platform.runLater {
            model.baseWalkMP = 4

            assertAll(
                    Executable { assertEquals(4, movementView.spnWalkBase.value) },
                    Executable { assertEquals(4, movementView.lblWalkFinal.text.toInt()) },
                    Executable { assertEquals(6, movementView.lblRunBase.text.toInt()) },
                    Executable { assertEquals(6, movementView.lblRunFinal.text.toInt()) }
            )
        }
    }

    @Test
    fun changeWalkMP() {
        Platform.runLater {
            model.baseWalkMP = 4

            movementView.spnWalkBase.increment()

            assertAll(
                    Executable { assertEquals(5, model.baseWalkMP) },
                    Executable { assertEquals(8, model.baseRunMP) }
            )
        }
    }

    @Test
    fun setInitialSecondaryMotive() {
        Platform.runLater {
            val jj = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
            model.secondaryMotiveType = jj

            assertAll(
                    Executable { assertThat(movementView.cbSecondaryMotive, ComboBoxMatchers.hasSelectedItem(jj))},
                    Executable { assertFalse(movementView.spnBaseSecondary.isDisabled)}
            )
        }
    }

    @Test
    fun changeSecondaryMotive() {
        val jj = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem
        val steps = movementView.cbSecondaryMotive.items.indexOf(jj)
            -movementView.cbSecondaryMotive.items.indexOf(model.secondaryMotiveType)

        clickOn(movementView.cbSecondaryMotive)
        for (i in 1..steps) {
            type(KeyCode.DOWN)
        }
        type(KeyCode.ENTER)

        assertAll(
                Executable { assertEquals(MotiveType.JUMP, model.secondaryMotiveType.mode) },
                Executable { assertEquals(model.baseWalkMP, model.maxSecondaryMP) }
        )
    }

    @Test
    fun setSecondaryMP() {
        Platform.runLater {
            model.baseWalkMP = 4
            model.secondaryMotiveType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ) as SecondaryMotiveSystem

            movementView.spnBaseSecondary.increment(3)

            assertAll(
                    Executable { assertEquals(4, model.baseSecondaryMP) },
                    Executable { assertEquals(4, movementView.lblSecondaryFinal.text.toInt()) }
            )
        }
    }
}