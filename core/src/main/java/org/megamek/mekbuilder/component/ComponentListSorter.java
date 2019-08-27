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
package org.megamek.mekbuilder.component;

import java.util.Comparator;
import java.util.function.Function;

/**
 *
 */
public class ComponentListSorter implements Comparator<Component> {
    private final Function<Component, String> stringConverter;

    public ComponentListSorter() {
        this(Component::getFullName);
    }

    public ComponentListSorter(Function<Component, String> converter) {
        this.stringConverter = converter;
    }

    @Override
    public int compare(Component o1, Component o2) {
        if (o1.isDefault() != o2.isDefault()) {
            return o1.isDefault() ? -1 : 1;
        }
        return stringConverter.apply(o1).compareTo(stringConverter.apply(o2));
    }

    @Override
    public Comparator<Component> thenComparing(Comparator<? super Component> other) {
        return null;
    }
}
