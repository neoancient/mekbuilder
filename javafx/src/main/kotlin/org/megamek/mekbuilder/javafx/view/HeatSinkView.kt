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
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.component.HeatSink
import org.megamek.mekbuilder.component.HeatSinkMount
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.ComponentComboBoxCellFactory
import org.megamek.mekbuilder.javafx.util.SpinnerIntStringConverter
import tornadofx.*

/**
 * Controls for setting the type and number of heat sinks
 */
class HeatSinkView: View(), InvalidationListener {

    internal val model: UnitViewModel by inject()
    internal val techFilter: BasicInfo by inject()

    override val root: AnchorPane by fxml()

    internal val cbHeatSinkType: ComboBox<HeatSink> by fxid()
    internal val spnHeatSinkCount: Spinner<Int> by fxid()
    internal val lblWeightFree: Label by fxid()
    internal val lblSlotsIntegrated: Label by fxid()

    val allHeatSinks = createComponentList{it.type == ComponentType.HEAT_SINK}

    init {
        val hsList = SimpleListProperty<HeatSink>()
        hsList.bind(objectBinding(allHeatSinks) {
            filter {techFilter.isLegal(it, false) && model.unit.allowed(it)}
                    .map{it as HeatSink}.toList().observable()
        })
        cbHeatSinkType.items = hsList
        cbHeatSinkType.bind(model.heatSinkTypeProperty)
        ComponentComboBoxCellFactory.setConverter(cbHeatSinkType)

        val countFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, Int.MAX_VALUE,
                model.heatSinkCount, 1)
        countFactory.converter = SpinnerIntStringConverter(spnHeatSinkCount.editor, countFactory)
        spnHeatSinkCount.valueFactory = countFactory
        // Force update on lost focus (e.g. tab)
        spnHeatSinkCount.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnHeatSinkCount.increment(0)
        }
        countFactory.valueProperty().bindBidirectional(model.heatSinkCountProperty)

        lblWeightFree.bind(stringBinding(model.engineTypeProperty) {
            value.weightFreeHeatSinks.toString()
        })
        lblSlotsIntegrated.bind(stringBinding(model.integratedHeatSinkProperty,
                model.unitTypeProperty) {
            if (model.unitType.isMech) {
                model.integratedHeatSinkProperty.value.toString()
            } else {
                messages["notApplicable"]
            }
        })

        techFilter.addListener(this)
    }

    override fun invalidated(observable: Observable) {
        allHeatSinks.invalidate()
        if (model.heatSinkType !in cbHeatSinkType.items) {
            val hs = cbHeatSinkType.items.firstOrNull {
                it.shortName == model.heatSinkType.shortName
            }
            cbHeatSinkType.selectionModel.select(hs ?: cbHeatSinkType.items.firstOrNull())
        }
    }
}