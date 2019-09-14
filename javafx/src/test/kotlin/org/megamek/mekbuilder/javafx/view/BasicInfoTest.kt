package org.megamek.mekbuilder.javafx.view

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.ComboBoxMatchers
import tornadofx.*

/**
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BasicInfoTest : ApplicationTest() {
    private lateinit var basicInfo: BasicInfo

    @Start
    override fun start(stage: Stage) {
        basicInfo = BasicInfo()
        stage.scene = Scene(basicInfo.root)
        stage.show()
    }

    @BeforeEach
    fun resetModel() {
        basicInfo.model.rebind{
            unitModel = MekModel(MekBuild())
        }
    }

    @Test
    fun testInitialChassisValue() {
        basicInfo.model.chassisName = "New Unit"
        assertEquals(basicInfo.txtChassis.text, basicInfo.model.chassisName)
    }

    @Test
    fun testSetChassis() {
        basicInfo.txtChassis.text = ""

        clickOn(basicInfo.txtChassis)
        write("New Name")

        assertAll(Executable {assertEquals(basicInfo.txtChassis.text, "New Name")},
                Executable {assertEquals(basicInfo.txtChassis.text, basicInfo.model.chassisName)})
    }

    @Test
    fun testInitialModelValue() {
        basicInfo.model.modelName = "New Unit"
        assertEquals(basicInfo.txtModel.text, basicInfo.model.modelName)
    }

    @Test
    fun testSetModelName() {
        basicInfo.txtModel.text = ""

        clickOn(basicInfo.txtModel)
        write("New Name")

        assertAll(Executable {assertEquals(basicInfo.txtModel.text, "New Name")},
                Executable {assertEquals(basicInfo.txtModel.text, basicInfo.model.modelName)})
    }

    @Test
    fun testInitialTechBaseValue() {
        Platform.runLater {
            basicInfo.model.techBase = TechBase.CLAN

            assertThat(basicInfo.cbTechBase, ComboBoxMatchers.hasSelectedItem(basicInfo.model.techBase))
        }
    }

    @Test
    fun testSetTechBase() {
        basicInfo.model.techBase = TechBase.IS

        clickOn(basicInfo.cbTechBase)
        type(KeyCode.DOWN)
        type(KeyCode.DOWN)
        type(KeyCode.ENTER)

        assertAll(Executable {assertThat(basicInfo.cbTechBase, ComboBoxMatchers.hasSelectedItem(TechBase.ALL))},
                Executable {assertEquals(basicInfo.model.techBase, TechBase.ALL)})
    }

    @Test
    fun testSetTechLevel() {
        basicInfo.cbTechLevel.selectionModel.select(TechLevel.STANDARD)

        clickOn(basicInfo.cbTechLevel)
        type(KeyCode.DOWN)
        type(KeyCode.DOWN)
        type(KeyCode.ENTER)

        assertThat(basicInfo.cbTechLevel, ComboBoxMatchers.hasSelectedItem(TechLevel.EXPERIMENTAL))
    }

    @Test
    fun testInitialSourceValue() {
        basicInfo.model.source = "TRO: Awesome"
        assertEquals(basicInfo.txtSource.text, basicInfo.model.source)
    }

    @Test
    fun testSetSource() {
        basicInfo.txtSource.text = ""

        clickOn(basicInfo.txtSource)
        write("TRO: 400 BC")

        assertAll(Executable {assertEquals(basicInfo.txtSource.text, "TRO: 400 BC")},
                Executable {assertEquals(basicInfo.txtSource.text, basicInfo.model.source)})
    }

    @Test
    fun testMinimumYear() {
        basicInfo.model.introYear = 3100

        clickOn(basicInfo.txtYear)
        eraseText(basicInfo.txtYear.length)
        write("100")
        type(KeyCode.TAB)

        assertEquals(basicInfo.model.introYear, basicInfo.model.baseOption.introDate())
    }
}