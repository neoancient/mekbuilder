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

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Label
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import tornadofx.*

/**
 * Shows a summary of the unit including occupied slots, weight, and heat profile
 */
class UnitSummary: View() {
    internal val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    internal val lblUnitName: Label by fxid()
    internal val lblUnitDescription: Label by fxid()
    internal val lblWeight: Label by fxid()
    internal val lblHeatGenerated: Label by fxid()
    internal val lblHeatDissipation: Label by fxid()
    internal val tblSummary: TreeTableView<SummaryItem> by fxid()
    internal val colName: TreeTableColumn<SummaryItem, String> by fxid()
    internal val colSlots: TreeTableColumn<SummaryItem, Number> by fxid()
    internal val colWeight: TreeTableColumn<SummaryItem, Number> by fxid()

    init {
        lblUnitName.textProperty().bind(stringBinding(model.chassisNameProperty, model.modelNameProperty) {
            "${model.chassisName} ${model.modelName}".trim()
        })
        lblWeight.bind(model.tonnageProperty.stringBinding {
            it.toString()
        })

        tblSummary.columnResizePolicy = TreeTableSmartResize.POLICY
        colName.remainingWidth()
    }

    class SummaryItem {
        val nameProperty = SimpleStringProperty()
        val slotProperty = SimpleIntegerProperty()
        val weightProperty = SimpleDoubleProperty()
    }
}