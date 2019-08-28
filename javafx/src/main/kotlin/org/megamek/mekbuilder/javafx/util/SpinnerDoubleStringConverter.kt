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
package org.megamek.mekbuilder.javafx.util

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.control.TextField
import javafx.util.StringConverter
import java.text.NumberFormat
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

/**
 * StringConverter for editable Spinner that doesn't panic at invalid input
 */
class SpinnerDoubleStringConverter(private val textField: TextField,
                                   private val factory: SpinnerValueFactory.DoubleSpinnerValueFactory)
    : StringConverter<Double>() {

    init {
        textField.textProperty().addListener { _, oldVal, newVal ->
            if (newVal != null && newVal.isNotEmpty()) {
                if (factory.min < 0 && newVal.endsWith("-")) {
                            if (newVal.length > 1) {
                                Platform.runLater {textField.text = "-"}
                            }
                    return@addListener
                }
            }

            try {
                newVal.toDouble()
            } catch (ex: NumberFormatException) {
                Platform.runLater {textField.text = oldVal}
            }
        }

        val oldHandler = textField.onAction
        textField.onAction = EventHandler<ActionEvent> { event ->
            // Perform entry validation and possible replacement
            fromString(textField.text)
            oldHandler.handle(event)
        }
    }

    override fun toString(value: Double?): String = NumberFormat.getInstance().format(value ?: 0)

    override fun fromString(string: String?): Double =
        if (string == null || string.isEmpty()) {
            0.0
        } else {
            try {
                var value = round(string.toDouble() / factory.amountToStepBy) * factory.amountToStepBy
                value = min(max(value, factory.min), factory.max)
                textField.text = NumberFormat.getInstance().format(value)
                value
            } catch (ex: NumberFormatException) {
                textField.text = min(max(0.0, factory.min), factory.max).toString()
                0.0
            }
        }
}