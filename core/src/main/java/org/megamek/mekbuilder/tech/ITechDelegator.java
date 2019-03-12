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

/**
 * Allows a class to implement {@link ITechProgression} by delegating the implementation to another object.
 * The implementing class only needs to override {@link #techDelegate} to return an instance of {@link ITechProgression}
 * and the remaining default methods will handle passing the method calls through to the delegate.
 *
 */
public interface ITechDelegator extends ITechProgression {

    /**
     * Provides pass-through implementation of {@link ITechProgression} by passing the implementation through
     * to a delegate object.
     *
     * @return The delegate that provides the actual implementation of {@link ITechProgression}
     */
    ITechProgression techDelegate();


    @Override
    default boolean clanTech() {
        return techDelegate().clanTech();
    }

    @Override
    default boolean mixedTech() {
        return techDelegate().mixedTech();
    }

    @Override
    default TechBase techBase() {
        return techDelegate().techBase();
    }

    @Override
    default Rating techRating() {
        return techDelegate().techRating();
    }

    @Override
    default Rating baseAvailability(int era) {
        return techDelegate().baseAvailability(era);
    }

    @Override
    default TechLevel staticTechLevel() {
        return techDelegate().staticTechLevel();
    }

    @Override
    default Integer getDate(TechStage techStage, boolean clan, Faction faction) {
        return techDelegate().getDate(techStage, clan, faction);
    }

}
