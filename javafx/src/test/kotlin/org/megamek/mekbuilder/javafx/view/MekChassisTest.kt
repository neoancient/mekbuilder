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
import javafx.beans.property.Property
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.stage.Stage
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.component.*
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.UnitConstructionOption
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.ComboBoxMatchers
import tornadofx.*

/**
 *
 */
internal class MekChassisTest: ApplicationTest() {
    private lateinit var mekChassis: MekChassis
    private lateinit var model: UnitViewModel

    @Start
    override fun start(stage: Stage) {
        mekChassis = MekChassis()
        model = mekChassis.model
        stage.scene = Scene(mekChassis.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        model.rebind{
            unitModel = MekModel(MekBuild())
        }
    }

    @Test
    fun setInitialTonnageRoundsUp() {
        Platform.runLater {
            model.tonnage = 60.0

            assertEquals(mekChassis.spnTonnage.value, 60.0, 0.001)
        }
    }

    @Test
    fun increaseTonnage() {
        Platform.runLater {
            model.tonnage = 45.0

            mekChassis.spnTonnage.increment()

            assertEquals(50.0, model.tonnage, 0.001)
        }
    }

    @Test
    fun tonnageRoundsToWeightIncrement() {
        clickOn(mekChassis.spnTonnage)
        type(KeyCode.HOME)
        push(KeyCodeCombination(KeyCode.END, KeyCodeCombination.SHIFT_DOWN))
        write("49").type(KeyCode.ENTER)

        assertEquals(50.0, model.tonnage, 0.001)
    }

    @Test
    fun setHigherTonnageRange() {
        Platform.runLater {
            val option = ConstructionOptionKey.MEK_SUPERHEAVY.get() as UnitConstructionOption
            model.baseOption = option

            assertAll(Executable {
                assertEquals(option.minWeight,
                        (mekChassis.spnTonnage.valueFactory as SpinnerValueFactory.DoubleSpinnerValueFactory).min, 0.001)},
                    Executable {
                        assertEquals(option.maxWeight,
                                (mekChassis.spnTonnage.valueFactory as SpinnerValueFactory.DoubleSpinnerValueFactory).max, 0.001)
                    },
                    Executable { assertTrue(model.tonnage >= option.minWeight) },
                    Executable { assertTrue(model.tonnage <= option.maxWeight) })
        }
    }

    @Test
    fun setLowerTonnageRange() {
        Platform.runLater {
            val option = ConstructionOptionKey.MEK_ULTRALIGHT.get() as UnitConstructionOption
            model.baseOption = option

            assertAll(Executable {
                assertEquals(option.minWeight,
                        (mekChassis.spnTonnage.valueFactory as SpinnerValueFactory.DoubleSpinnerValueFactory).min, 0.001)},
                    Executable {
                        assertEquals(option.maxWeight,
                                (mekChassis.spnTonnage.valueFactory as SpinnerValueFactory.DoubleSpinnerValueFactory).max, 0.001)
                    },
                    Executable { assertTrue(model.tonnage >= option.minWeight) },
                    Executable { assertTrue(model.tonnage <= option.maxWeight) })
        }
    }

    /**
     * Convenience function that changes a combo box selecting to the next item after the current
     * selection. If the current selection is the last in the list, selects the one above instead.
     *
     * @param selected The currently selected item
     * @param cb       The combo box
     * @return         The item at the newly selected index
     */
    private fun<T: Component> changeComboBoxEntry(selected: T, cb: ComboBox<T>): T {
        val initialIndex = cb.items.indexOf(selected)

        clickOn(cb)
        // The default should be the first in the list but we'll allow for the possibility
        // that we're at the end of the list and have to go up.
        return if (initialIndex < cb.items.size - 1) {
            type(KeyCode.DOWN).type(KeyCode.ENTER)
            cb.items[initialIndex + 1]
        } else {
            type(KeyCode.UP).type(KeyCode.ENTER)
            cb.items[initialIndex - 1]
        }
    }

    @Test
    fun setInitialStructureType() {
        Platform.runLater {
            val structure = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_STRUCTURE_IS_ENDO_STEEL)
            model.internalStructure = structure

            assertThat(mekChassis.cbStructure, ComboBoxMatchers.hasSelectedItem(structure))
        }
    }

    @Test
    fun changeInternalStructure() {
        val nextIS = changeComboBoxEntry(model.internalStructure, mekChassis.cbStructure)

        assertEquals(nextIS, model.internalStructure)
    }

    @Test
    fun setInitialEngineType() {
        Platform.runLater {
            val engine = ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_COMPACT) as MVFEngine
            model.engineType = engine

            assertThat(mekChassis.cbEngine, ComboBoxMatchers.hasSelectedItem(engine))
        }
    }

    @Test
    fun changeEngine() {
        val nextEngine = changeComboBoxEntry(model.engineType, mekChassis.cbEngine)

        assertEquals(nextEngine, model.engineType)
    }

    @Test
    fun setInitialCockpitType() {
        Platform.runLater {
            val cockpit = ComponentLibrary.getInstance().getComponent(ComponentKeys.COCKPIT_MEK_SMALL) as Cockpit
            model.cockpit = cockpit

            assertThat(mekChassis.cbCockpit, ComboBoxMatchers.hasSelectedItem(cockpit))
        }
    }

    @Test
    fun changeCockpit() {
        val nextCockpit = changeComboBoxEntry(model.cockpit, mekChassis.cbCockpit)

        assertEquals(nextCockpit, model.cockpit)
    }

    @Test
    fun setInitialGyroType() {
        Platform.runLater {
            val gyro = ComponentLibrary.getInstance().getComponent(ComponentKeys.GYRO_HEAVY_DUTY)
            model.gyro = gyro

            assertThat(mekChassis.cbGyro, ComboBoxMatchers.hasSelectedItem(gyro))
        }
    }

    @Test
    fun changeGyro() {
        val nextGyro = changeComboBoxEntry(model.gyro, mekChassis.cbGyro)

        assertEquals(nextGyro, model.gyro)
    }

    @Test
    fun setInitialMyomerType() {
        Platform.runLater {
            val myomer = ComponentLibrary.getInstance().getComponent(ComponentKeys.MYOMER_TSM)
            model.myomer = myomer

            assertThat(mekChassis.cbMyomer, ComboBoxMatchers.hasSelectedItem(myomer))
        }
    }

    @Test
    fun changeMyomer() {
        val nextGyro = changeComboBoxEntry(model.myomer, mekChassis.cbMyomer)

        assertEquals(nextGyro, model.myomer)
    }
}