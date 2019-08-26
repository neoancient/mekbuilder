package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.AnchorPane
import javafx.util.StringConverter
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.SimpleComboBoxCellFactory
import org.megamek.mekbuilder.tech.*
import tornadofx.*
import kotlin.math.max
import kotlin.math.min

/**
 *
 */

class BasicInfo: View(), ITechFilter, Observable, InvalidationListener {
    val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    private val listeners = ArrayList<InvalidationListener>()
    override fun addListener(listener: InvalidationListener) { listeners.add(listener)}
    override fun removeListener(listener: InvalidationListener) { listeners.remove(listener)}
    override fun invalidated(observable: Observable) {listeners.forEach{it.invalidated(this)}}

    private val yearFieldConverter = object : StringConverter<Int>() {
        private val MAX_YEAR = 3200

        override fun toString(`object`: Int?): String {
            return `object`?.toString() ?: ""
        }

        override fun fromString(string: String): Int? {
            val str = string.replace("[^\\d]".toRegex(), "")
            return if (str.isNotEmpty()) {
                min(max(Integer.valueOf(str), model.baseOption.value.introDate()), MAX_YEAR)
            } else {
                model.introYear.value
            }
        }
    }

    private val txtChassis: TextField by fxid()
    private val txtModel: TextField by fxid()
    private val txtYear: TextField by fxid()
    private val chkShowExtinct: CheckBox by fxid()
    private val txtSource: TextField by fxid()
    private val cbTechBase: ComboBox<TechBase> by fxid()
    private val cbTechLevel: ComboBox<TechLevel> by fxid()
    private val chkEraBasedProgression: CheckBox by fxid()
    private val cbFaction: ComboBox<Faction> by fxid()

    init {
        val techBaseList = SimpleListProperty<TechBase>()
        techBaseList.bind(objectBinding(model.baseOption) {
            if (model.baseOption.value == null
                    || model.baseOption.value.techBase() == TechBase.ALL) {
                TechBase.values().toList().observable()
            } else {
                observableList(model.baseOption.value.techBase(), TechBase.ALL)
            }
        })

        txtYear.textFormatter = TextFormatter(yearFieldConverter)
        cbTechBase.items = techBaseList
        SimpleComboBoxCellFactory.setConverter(cbTechBase, TechBase::unitDisplayName)
        cbTechLevel.items = TechLevel.values().toList().observable()
        SimpleComboBoxCellFactory.setConverter(cbTechLevel, TechLevel::displayName)
        cbTechLevel.selectionModel.select(TechLevel.STANDARD)
        cbFaction.items = Faction.values().toList().observable()
        SimpleComboBoxCellFactory.setConverter(cbFaction, Faction::displayName)

        model.minTechLevelProperty.onChange {
            if (it != null) setMininumTechLevel(it)
        }

        txtChassis.bind(model.chassisName)
        txtModel.bind(model.modelName)
        txtSource.bind(model.source)
        txtYear.bind(model.introYear, false, yearFieldConverter)
        cbTechBase.bind(model.techBase)
        cbFaction.bind(model.faction)

        txtYear.textProperty().addListener(this)
        cbTechBase.selectionModel.selectedItemProperty().addListener(this)
        cbTechLevel.selectionModel.selectedItemProperty().addListener(this)
        cbFaction.selectionModel.selectedItemProperty().addListener(this)
        chkEraBasedProgression.selectedProperty().addListener(this)
        chkShowExtinct.selectedProperty().addListener(this)

        model.baseOption.onChange {
            if (it != null) {
                if ((it.techBase() != TechBase.ALL) && (model.techBase.value != TechBase.ALL)) {
                    model.techBase.value = it.techBase()
                }
            }
        }
    }

    /**
     * When the minimum tech level changes due to chosen construction options, refilters
     * the available tech level and makes sure the current selection is at least the minimum.
     */
    private fun setMininumTechLevel(min: TechLevel) {
        cbTechLevel.items = TechLevel.values().filter{
            it >= model.baseOption.value.staticTechLevel()
        }.toList().observable()
        if (min > cbTechLevel.selectedItem ?: TechLevel.INTRO) {
            cbTechLevel.selectionModel.select(min)
        }
    }

    override fun getYear() = txtYear.textProperty().value.toInt()

    override fun getTechBase() = cbTechBase.selectedItem ?: TechBase.ALL

    override fun getTechLevel() = cbTechLevel.selectedItem ?: TechLevel.STANDARD

    override fun getFaction() = cbFaction.selectedItem

    override fun eraBasedProgression() = chkEraBasedProgression.isSelected

    override fun showExtinct() = chkShowExtinct.isSelected
}