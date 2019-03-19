/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
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
package org.megamek.mekbuilder.javafx.util;

import com.sun.istack.internal.Nullable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.function.Function;

/**
 * Convenience class that allows setting a combo box cell factory that simply takes a conversion function.
 */
public class SimpleComboBoxCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    final Function<T, String> converter;

    public SimpleComboBoxCellFactory(Function<T, String> converter) {
        this.converter = converter;
    }

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : converter.apply(item));
            }
        };
    }

    /**
     * Convenience method that applies a string conversion to both the current selection display and to the items
     * in the ListBox. If the ComboBox is editable, use {@link #setConverter(ComboBox, Function, Function)}.
     *
     * @param comboBox    The control to apply the conversion to.
     * @param toString    Converts the item to a String
     * @param <T>         The type parameter of the ComboBox.
     */
    public static <T> void setConverter(ComboBox<T> comboBox, Function<T, String> toString) {
        setConverter(comboBox, toString, null);
    }

    /**
     * Convenience method that applies a string conversion to both the current selection display and to the items
     * in the ListBox.
     *
     * @param comboBox    The control to apply the conversion to.
     * @param toString    Converts the item to a String
     * @param fromString  Converts a String to the item. This is not needed unless the ComboBox is editable.
     * @param <T>         The type parameter of the ComboBox.
     */
    public static <T> void setConverter(ComboBox<T> comboBox, Function<T, String> toString,
                                        @Nullable Function<String, T> fromString) {
        comboBox.setCellFactory(new SimpleComboBoxCellFactory(toString));
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return toString.apply(object);
            }

            @Override
            public T fromString(String string) {
                if (null == fromString) {
                    return null;
                } else {
                    return fromString.apply(string);
                }
            }
        });
    }
}
