package org.megamek.mekbuilder.javafx.util

import javafx.scene.control.ComboBox
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback
import javafx.util.StringConverter
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.tech.TechBase
import tornadofx.*

/**
 * Custom cell factory for ComboBox that displays components. Each time the {@code itemsProperty}
 * is changed, the display name is chosen to ensure each is unique. Preference is given to the short name.
 * Any duplicates have (IS) or (Clan) appended to the short name as appropriate. If there are still
 * duplicates, all the components are shown by full name.
 */

fun Component.shortNameWithTechBase() =
        if (techBase().equals(TechBase.ALL) || shortName.toUpperCase().startsWith(techBase().toString())) {
            shortName
        } else {
            "$shortName${techBase().suffix()}"
        }

class ComponentComboBoxCellFactory(private val combobox: ComboBox<out Component>) :
        Callback<ListView<out Component>, ListCell<out Component>> {

    val nameMap = HashMap<Component, String>()

    init {
        combobox.items.onChange {
            refresh()
        }
        refresh()
    }

    fun refresh() {
        val count = combobox.items.groupingBy{it.shortName}.eachCount()
        if (count.filter{it.value > 1}.isEmpty()) {
            combobox.items.forEach{nameMap[it] = it.shortName}
        } else {
            combobox.items.forEach {
                if (count[it.shortName]!! > 1) {
                    nameMap[it] = it.shortNameWithTechBase()
                } else {
                    nameMap[it] = it.shortName
                }
            }
        }
        if (combobox.items.groupingBy{nameMap[it]}.eachCount().filter{it.value > 1}.isNotEmpty()) {
            combobox.items.forEach{nameMap[it] = it.fullName}
        }
    }

    override fun call(param: ListView<out Component>?): ListCell<out Component> {
        return object : ListCell<Component>() {
            override fun updateItem(item: Component?, empty: Boolean) {
                super.updateItem(item, empty)
                text = if (empty || item == null) "" else nameMap[item] ?: item.fullName
            }
        }
    }

    val converter = object : StringConverter<Component>() {
        override fun toString(component: Component): String = nameMap[component] ?: component.fullName

        override fun fromString(string: String?): Component? = null
    }


    companion object {
        /**
         * Convenience method that applies a string conversion to both the current selection display and to the items
         * in the ListBox.
         *
         * @param comboBox    The control to apply the conversion to.
         */
        fun  setConverter(comboBox: ComboBox<out Component>) {
            val factory = ComponentComboBoxCellFactory(comboBox)
            comboBox.cellFactory = factory
            comboBox.converter = factory.converter
        }
    }
}