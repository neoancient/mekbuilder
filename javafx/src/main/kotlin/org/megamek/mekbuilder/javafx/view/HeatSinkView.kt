package org.megamek.mekbuilder.javafx.view

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.HeatSink
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import tornadofx.*

/**
 * Controls for setting the type and number of heat sinks
 */
class HeatSinkView: View() {

    internal val model: UnitViewModel by inject()
    internal val techFilter: BasicInfo by inject()

    override val root: AnchorPane by fxml()

    internal val cbHeatSinkType: ComboBox<HeatSink> by fxid()
    internal val spnHeatSinkCount: Spinner<Int> by fxid()
    internal val lblWeightFree: Label by fxid()
    internal val lblSlotsRequired: Label by fxid()
    internal val lblSlotsRequiredText: Label by fxid()
}