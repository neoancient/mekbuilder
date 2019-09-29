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

import com.sun.istack.Nullable;
import org.megamek.mekbuilder.unit.UnitBuild;

import java.util.Set;

/**
 * Mount for a weapon that allows linking an additional Component that modifies
 * the weapon's capabilities (e.g. Artemis, PPC capacitor).
 */
public class WeaponMount extends Mount {

    private Component enhancement;

    public WeaponMount(UnitBuild unit, HeavyWeapon weapon, @Nullable Component enhancement) {
        super(unit, weapon, 1.0);
        this.enhancement = enhancement;
    }

    public HeavyWeapon getWeapon() {
        return (HeavyWeapon) getComponent();
    }

    /**
     * @return a {@link Component} that modifies the weapon's performance, such as Artemis, One-Shot, etc.
     */
    public @Nullable Component getEnhancement() {
        return enhancement;
    }

    /**
     * Changes the enchancement for this mount. Setting it to {@code null} clears the enhancement.
     *
     * @param enhancement The enhancement for the weapon.
     * @throws IllegalArgumentException if the enhancement is not an enhancement component
     *                                  or does not have any flag that matches the weapon's
     *                                  list of {@link ComponentSwitch#LINKABLE} flags.
     */
    public void setEnhancement(@Nullable Component enhancement) {
        if (!canLink(enhancement)) {
            throw new IllegalArgumentException(getWeapon()
                    .getInternalName() + " cannot link to " + enhancement.getInternalName());
        }
        this.enhancement = enhancement;
    }

    /**
     * Checks whether the {@link Component} is an enhancement and has a flag that matches
     * the weapon's list of {@link ComponentSwitch#LINKABLE} flags.
     *
     * @param component a component that pontentially can be linked to the weapon.
     * @return            whether the component is legal for the weapon
     */
    public boolean canLink(Component component) {
        if (null == component) {
            return true;
        }
        if (!component.getType().equals(ComponentType.ENHANCEMENT)) {
            return false;
        }
        return ((Set<?>)getWeapon().flagValue(ComponentSwitch.LINKABLE))
                .stream().anyMatch(f -> component.hasFlag((ComponentSwitch) f));
    }

    @Override
    public String displayName() {
        if (null == getEnhancement()) {
            return super.displayName();
        }
        return super.displayName() + " " + getEnhancement().displayName(1.0);
    }

    @Override
    public double getComponentWeight() {
        if (null == getEnhancement()) {
            return super.getComponentWeight();
        }
        return super.getComponentWeight() + getEnhancement().calcWeight(getUnit(), 1.0);
    }

    @Override
    public int getComponentSlots() {
        if (null == getEnhancement()) {
            return super.getComponentSlots();
        }
        return super.getComponentSlots() + getEnhancement().calcSlots(getUnit(), 1.0);
    }

    @Override
    public double getComponentCost() {
        if (null == getEnhancement()) {
            return super.getComponentCost();
        }
        return super.getComponentCost() + getEnhancement().calcCost(getUnit(), 1.0);
    }

    @Override
    public int maxWeaponHeat() {
        int heat = super.maxWeaponHeat();
        if (null != getEnhancement()) {
            if (getEnhancement().hasFlag(ComponentSwitch.PPC_CAPACITOR)) {
                heat += 5;
            }
            if (getEnhancement().hasFlag(ComponentSwitch.PULSE_MODULE)) {
                heat += 2;
            }
            if (getEnhancement().hasFlag(ComponentSwitch.LASER_INSULATOR)) {
                heat--;
            }
        }
        return heat;
    }

    @Override
    public double modifiedWeaponHeat() {
        double heat = maxWeaponHeat();
        if (getWeapon().hasFlag(WeaponFlag.STREAK)) {
            heat *= 0.5;
        }
        if (null != getEnhancement()
                && getEnhancement().hasFlag(ComponentSwitch.ONE_SHOT)) {
            heat *= 0.25;
        }
        return heat;
    }
}
