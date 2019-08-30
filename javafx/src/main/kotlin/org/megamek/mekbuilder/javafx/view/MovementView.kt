package org.megamek.mekbuilder.javafx.view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.SecondaryMotiveSystem
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*
import javax.swing.SpinnerNumberModel

/**
 * View for setting walk/cruise/thrust speed and additional movement modes (e.g. jump/underwater).
 */
class MovementView : View() {
    private val model: UnitViewModel by inject()
    private val techFilter: BasicInfo by inject()

    override val root: AnchorPane by fxml()

    private val lblWalkMP: Label by fxid()
    private val lblRunMP: Label by fxid()
    private val cbSecondaryMotive: ComboBox<SecondaryMotiveSystem> by fxid()
    private val spnWalkBase: Spinner<Int> by fxid()
    private val lblRunBase: Label by fxid()
    private val spnJump: Spinner<Number> by fxid()
    private val lblWalkFinal: Label by fxid()
    private val lblRunFinal: Label by fxid()
    private val lblJumpFinal: Label by fxid()

    private fun isWalker(unit: UnitBuild) =
            unit.unitType.isMech || unit.unitType == UnitType.PROTOMEK || unit.unitType == UnitType.BATTLE_ARMOR

    private fun isAero(unit: UnitBuild) =
            //TODO: Add aero support vehicles
            unit.unitType.isFighter || unit.unitType.isLargeCraft || unit.unitType == UnitType.SMALL_CRAFT

    init {
        lblWalkMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblWalkMP.text"]
                isAero(value) -> messages["lblSafeThrust.text"]
                else -> messages["lblCruiseMP.text"]
            }
        })
        lblRunMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblRunMP.text"]
                isAero(value) -> messages["lblMaxThrust.text"]
                else -> messages["lblFlankMP.text"]
            }
        })

        val baseWalkFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(model.minWalk, model.maxWalk)
        baseWalkFactory.minProperty().bind(model.minWalkProperty)
        baseWalkFactory.maxProperty().bind(model.maxWalkProperty)
        spnWalkBase.valueFactory = baseWalkFactory
        spnWalkBase.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnWalkBase.increment(0)
        }

        spnWalkBase.bind(model.baseWalkMPProperty)
        lblRunBase.textProperty().bind(stringBinding(model.baseRunMPProperty) {
            System.out.println("Setting base run text to ${model.baseRunMP}")
            model.baseRunMP.toString()
        })
        lblWalkFinal.textProperty().bind(stringBinding(model.walkMPProperty) {model.walkMP.toString()})
        lblRunFinal.textProperty().bind(stringBinding(model.runMPProperty) {model.runMP.toString()})
    }
}