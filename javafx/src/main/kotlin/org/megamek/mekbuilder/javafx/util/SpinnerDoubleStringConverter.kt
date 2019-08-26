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