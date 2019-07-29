package org.megamek.mekbuilder.javafx.util

import javafx.scene.control.ComboBox
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback
import javafx.util.StringConverter

/**
 * Convenience class that allows setting a combo box cell factory that simply takes a conversion function.
 */
class SimpleComboBoxCellFactory<T>(internal val converter: (T) -> String) : Callback<ListView<T>, ListCell<T>> {

    override fun call(param: ListView<T>): ListCell<T> {
        return object : ListCell<T>() {
            override fun updateItem(item: T, empty: Boolean) {
                super.updateItem(item, empty)
                text = if (empty) "" else converter(item)
            }
        }
    }

    companion object {

        /**
         * Convenience method that applies a string conversion to both the current selection display and to the items
         * in the ListBox. If the ComboBox is editable, use [.setConverter].
         *
         * @param comboBox    The control to apply the conversion to.
         * @param toString    Converts the item to a String
         * @param <T>         The type parameter of the ComboBox.
        </T> */
        fun <T> setConverter(comboBox: ComboBox<T>, toString: (T) -> String) {
            setConverter(comboBox, toString, null)
        }

        /**
         * Convenience method that applies a string conversion to both the current selection display and to the items
         * in the ListBox.
         *
         * @param comboBox    The control to apply the conversion to.
         * @param toString    Converts the item to a String
         * @param fromString  Converts a String to the item. This is not needed unless the ComboBox is editable.
         * @param <T>         The type parameter of the ComboBox.
        </T> */
        fun <T> setConverter(comboBox: ComboBox<T>, toString: (T) -> String,
                             fromString: ((String) -> T)?) {
            comboBox.cellFactory = SimpleComboBoxCellFactory(toString)
            comboBox.converter = object : StringConverter<T>() {
                override fun toString(`object`: T): String {
                    return toString(`object`)
                }

                override fun fromString(string: String): T? = if (fromString != null) {
                    fromString(string)
                } else {
                    null
                }
            }
        }
    }
}
