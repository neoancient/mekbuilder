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

import megamek.common.annotations.Nullable;

/**
 * Various stages of development of a piece of technology
 */
public enum TechStage {
    PROTOTYPE,
    PRODUCTION,
    COMMON,
    EXTINCTION,
    REINTRODUCTION;

    /**
     * @return the next stage in the production cycle. COMMON and REINTRODUCTION are considered
     *         end points each followed by null
     */
    public @Nullable
    TechStage isFollowedBy() {
        switch (this) {
            case PROTOTYPE:
                return PRODUCTION;
            case PRODUCTION:
                return COMMON;
            case EXTINCTION:
                return REINTRODUCTION;
            default:
                return null;
        }
    }
}
