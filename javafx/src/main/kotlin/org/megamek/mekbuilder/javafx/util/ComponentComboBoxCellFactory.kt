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

import javafx.beans.property.ListProperty
import javafx.beans.property.SimpleListProperty
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

class ComponentComboBoxCellFactory(private val combobox: ComboBox<out Component>) :
        Callback<ListView<out Component>, ListCell<out Component>> {

    val nameLookup = ComponentListNameLookup(combobox.items)

    override fun call(param: ListView<out Component>?): ListCell<out Component> {
        return object : ListCell<Component>() {
            override fun updateItem(item: Component?, empty: Boolean) {
                super.updateItem(item, empty)
                text = if (empty || item == null) "" else nameLookup[item] ?: item.fullName
            }
        }
    }

    val converter = object : StringConverter<Component>() {
        override fun toString(component: Component): String = nameLookup[component] ?: component.fullName

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