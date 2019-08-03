/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.megamek.mekbuilder.tech;

import java.util.ResourceBundle;

/**
 * Component tech base
 */

public enum TechBase {
    IS,
    CLAN,
    ALL;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(TechBase.class.getName());

    public String fullName() {
        return resourceBundle.getString("fullName." + name());
    }
    /**
     * The displayable name used for units (ALL is shown as "Mixed").
     */
    public String unitDisplayName() {
        return resourceBundle.getString("unitDisplayName." + name());
    }
}
