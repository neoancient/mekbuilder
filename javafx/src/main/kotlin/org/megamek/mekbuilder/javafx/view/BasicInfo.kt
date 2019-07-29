package org.megamek.mekbuilder.javafx.view

import javafx.beans.binding.Bindings
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.AnchorPane
import javafx.util.StringConverter
import org.megamek.mekbuilder.tech.Faction
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.tech.TechProgression
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.SimpleComboBoxCellFactory
import tornadofx.*

/**
 *
 */
class BasicInfo: View() {
    val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    private val yearFieldConverter = object : StringConverter<Int>() {
        private val MIN_YEAR = TechProgression.DATE_PS
        private val MAX_YEAR = 3200
        private val DEFAULT_YEAR = 3067

        override fun toString(`object`: Int?): String {
            return `object`?.toString() ?: ""
        }

        override fun fromString(string: String): Int? {
            var str = string.replace("[^\\d]".toRegex(), "")
            return if (!str.isEmpty()) {
                Math.min(Math.max(Integer.valueOf(str), MIN_YEAR), MAX_YEAR)
            } else {
                model.introYear.value
            }
        }
    }

    val txtChassis: TextField by fxid()
    val txtModel: TextField by fxid()
    val txtYear: TextField by fxid()
    val chkShowExtinct: CheckBox by fxid()
    val txtSource: TextField by fxid()
    val cbTechBase: ComboBox<TechBase> by fxid()
    val cbTechLevel: ComboBox<TechLevel> by fxid()
    val chkEraBasedProgression: CheckBox by fxid()
    val cbFaction: ComboBox<Faction> by fxid()

    init {
        txtYear.setTextFormatter(TextFormatter(yearFieldConverter))
        cbTechBase.items = TechBase.values().toList().observable()
        SimpleComboBoxCellFactory.setConverter(cbTechBase, TechBase::unitDisplayName)
        cbTechLevel.items = TechLevel.values().toList().observable()
        SimpleComboBoxCellFactory.setConverter(cbTechLevel, TechLevel::displayName)
        cbTechLevel.getSelectionModel().select(TechLevel.STANDARD)
        cbFaction.items = Faction.values().toList().observable()
        SimpleComboBoxCellFactory.setConverter(cbFaction, Faction::displayName)

        txtChassis.textProperty().bindBidirectional(model.chassisName)
        txtModel.textProperty().bindBidirectional(model.modelName)
        txtSource.textProperty().bindBidirectional(model.source)
        Bindings.bindBidirectional(txtYear.textProperty(), model.introYear, yearFieldConverter)
        cbTechBase.selectionModel.select(model.techBase.value)
        model.techBase.bind(cbTechBase.selectionModel.selectedItemProperty())
        cbFaction.selectionModel.select(model.faction.value)
        model.faction.bind(cbFaction.selectionModel.selectedItemProperty())

    }

}