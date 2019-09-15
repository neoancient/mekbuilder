package org.megamek.mekbuilder.javafx.view

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.MouseButton
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.UnitConstructionOption
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
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
}