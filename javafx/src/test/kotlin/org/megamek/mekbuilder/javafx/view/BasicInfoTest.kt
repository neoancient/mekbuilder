package org.megamek.mekbuilder.javafx.view

import com.sun.javafx.robot.FXRobot
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.api.FxRobot
import org.testfx.api.FxToolkit
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import tornadofx.*

/**
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BasicInfoTest : ApplicationTest() {
    lateinit var basicInfo: BasicInfo

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
        basicInfo.model.chassisName = ""

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
        basicInfo.model.modelName = ""

        clickOn(basicInfo.txtModel)
        write("New Name")

        assertAll(Executable {assertEquals(basicInfo.txtModel.text, "New Name")},
                Executable {assertEquals(basicInfo.txtModel.text, basicInfo.model.modelName)})
    }
}