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
 * Generic view for all unit types that sets name, year, tech base, and tech level. The settings
 * in this view are used to filter options in others.
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
                min(max(Integer.valueOf(str), model.baseOption.introDate()), MAX_YEAR)
            } else {
                model.introYear
            }
        }
    }

    internal val txtChassis: TextField by fxid()
    internal val txtModel: TextField by fxid()
    internal val txtYear: TextField by fxid()
    internal val chkShowExtinct: CheckBox by fxid()
    internal val txtSource: TextField by fxid()
    internal val cbTechBase: ComboBox<TechBase> by fxid()
    internal val cbTechLevel: ComboBox<TechLevel> by fxid()
    internal val chkEraBasedProgression: CheckBox by fxid()
    internal val cbFaction: ComboBox<Faction> by fxid()

    init {
        model.techFilter = this
        model.techFilterProperty.addListener(this)

        val techBaseList = SimpleListProperty<TechBase>()
        techBaseList.bind(objectBinding(model.baseOptionProperty) {
            if (model.baseOption == null
                    || model.baseOption.techBase() == TechBase.ALL) {
                TechBase.values().toList().observable()
            } else {
                observableList(model.baseOption.techBase(), TechBase.ALL)
            }
        })

        txtYear.textFormatter = TextFormatter(yearFieldConverter)
        txtYear.textProperty().addListener { _, oldVal, newVal ->
            if (!newVal.matches("[0-9]*".toRegex())) {
                txtYear.text = oldVal
            }
        }
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

        txtChassis.bind(model.chassisNameProperty)
        txtModel.bind(model.modelNameProperty)
        txtYear.bind(model.introYearProperty)
        txtSource.bind(model.sourceProperty)
        cbTechBase.bind(model.techBaseProperty)
        cbFaction.bind(model.factionProperty)

        txtYear.focusedProperty().addListener(this)
        cbTechBase.selectionModel.selectedItemProperty().addListener(this)
        cbTechLevel.selectionModel.selectedItemProperty().addListener(this)
        cbFaction.selectionModel.selectedItemProperty().addListener(this)
        chkEraBasedProgression.selectedProperty().addListener(this)
        chkShowExtinct.selectedProperty().addListener(this)

        model.baseOptionProperty.onChange {
            if (it != null) {
                if ((it.techBase() != TechBase.ALL) && (model.techBase != TechBase.ALL)) {
                    model.techBase = it.techBase()
                }
                cbTechBase.selectionModel.select(model.techBase)
            }
        }
    }

    /**
     * When the minimum tech level changes due to chosen construction options, refilters
     * the available tech level and makes sure the current selection is at least the minimum.
     */
    private fun setMininumTechLevel(min: TechLevel) {
        cbTechLevel.items = TechLevel.values().filter{
            it >= model.baseOption.staticTechLevel()
        }.toList().observable()
        if (min > cbTechLevel.selectedItem ?: TechLevel.INTRO) {
            cbTechLevel.selectionModel.select(min)
        }
    }

    override fun getYear() =
            if (txtYear.textProperty().value.isNotEmpty()) {
                yearFieldConverter.fromString(txtYear.text) ?: model.introYear
            } else {
                model.introYear
            }

    override fun getTechBase() = cbTechBase.selectedItem ?: model.techBase

    override fun getTechLevel() = cbTechLevel.selectedItem ?: TechLevel.STANDARD

    override fun getFaction() = cbFaction.selectedItem

    override fun eraBasedProgression() = chkEraBasedProgression.isSelected

    override fun hideExtinct() = !chkShowExtinct.isSelected
}