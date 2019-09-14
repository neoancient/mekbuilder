package org.megamek.mekbuilder.javafx.view

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.UnitConstructionOption
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
import org.megamek.mekbuilder.unit.UnitType
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.ComboBoxMatchers
import tornadofx.*

/**
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MekConfigPanelTest : ApplicationTest() {
    private lateinit var configPanel: MekConfigPanel
    private lateinit var model: UnitViewModel

    @Start
    override fun start(stage: Stage) {
        configPanel = MekConfigPanel()
        model = configPanel.model
        stage.scene = Scene(configPanel.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        model.rebind{
            unitModel = MekModel(MekBuild())
        }
    }

    @Test
    fun testSetInitialConfiguration() {
        Platform.runLater {
            val lamStd = MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.LAM_STANDARD)
            val lamBimodal = MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.LAM_BIMODAL)

            model.mekConfiguration = lamStd

            assertAll(Executable {assertThat(configPanel.cbBaseType,
                        ComboBoxMatchers.hasSelectedItem(MekConfiguration.BaseType.LAM))},
                    Executable {assertThat(configPanel.cbSubType,
                        ComboBoxMatchers.hasSelectedItem(lamStd))},
                    Executable {assertThat(configPanel.cbSubType,
                            ComboBoxMatchers.containsItems(lamStd, lamBimodal))},
                    Executable {assertFalse(configPanel.cbConstructionOption.isVisible)},
                    Executable {assertFalse(configPanel.chkOmni.isVisible)})
        }
    }

    @Test
    fun testSelectConfiguration() {
        val baseTypeChange = MekConfiguration.BaseType.QUADVEE.ordinal - model.mekConfiguration.baseType.ordinal

        clickOn(configPanel.cbBaseType)
        for (i in 1..baseTypeChange) {
            type(KeyCode.DOWN)
        }
        type(KeyCode.ENTER)
        clickOn(configPanel.cbSubType)
        type(KeyCode.DOWN)
        type(KeyCode.ENTER)

        assertThat(configPanel.cbSubType, ComboBoxMatchers.hasSelectedItem(model.mekConfiguration))
    }

    @Test
    fun testInitialConstructionOption() {
        Platform.runLater {
            val option = ConstructionOptionKey.MEK_SUPERHEAVY.get() as UnitConstructionOption
            model.baseOption = option

            assertAll(Executable { assertThat(configPanel.cbConstructionOption, ComboBoxMatchers.hasSelectedItem(option)) },
                    Executable { configPanel.cbConstructionOption.isVisible })
        }
    }

    @Test
    fun testSetConstructionOption() {
        val option = ConstructionOptionKey.MEK_ULTRALIGHT.get() as UnitConstructionOption
        model.mekConfiguration = MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_BIPED)

        clickOn(configPanel.cbConstructionOption)
        type(KeyCode.UP)
        type(KeyCode.ENTER)

        assertAll(Executable { assertTrue(model.baseOption.maxWeight < 20.0) },
                Executable {assertThat(configPanel.cbConstructionOption,
                        ComboBoxMatchers.hasSelectedItem(option))}
        )
    }

    @Test
    fun testInitialOmni() {
        model.omni = true

        assertTrue(configPanel.chkOmni.isSelected)
    }

    @Test
    fun testSetOmni() {
        model.omni = false;

        clickOn(configPanel.chkOmni)

        assertTrue(model.omni)
    }
}