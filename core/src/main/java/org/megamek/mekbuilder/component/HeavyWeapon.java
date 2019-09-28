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

import com.fasterxml.jackson.annotation.JsonAlias;
import org.megamek.mekbuilder.unit.HeatStrategy;
import org.megamek.mekbuilder.unit.UnitType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Standard and capital scale weapons. This includes BA weapons but not conventional infantry.
 */
public class HeavyWeapon extends Component {

    private int heat = 0;
    private AmmunitionType ammoType = AmmunitionType.NONE;
    private int rackSize = 0;
    private String damageTypeStr = "standard:0";
    private WeaponRange range = new WeaponRange(0, 0, 0, 0, 0);
    private Set<WeaponFlag> weaponFlags = EnumSet.noneOf(WeaponFlag.class);
    @JsonAlias("aeroTechDamage")
    private WeaponRange aeroAV = new WeaponRange(0, 0, 0, 0, 0);
    @JsonAlias("maxAeroTechRange")
    private int maxAeroRange = 0;
    @JsonAlias("aeroTechClass")
    private AeroWeaponClass aeroWeaponClass = AeroWeaponClass.UNKNOWN;
    //Some weapons can have multiple values at each range due to Artemis/Apollo/PPC Capacitor, etc,
    //so we need a two-dimensional array.
    private List<List<Double>> alphaStrikeDamage = new ArrayList<>();
    private AlphaStrikeWeaponClass alphaStrikeWeaponClass = AlphaStrikeWeaponClass.STANDARD;
    // A vestige of an earlier approach has these intialized lazily from the damageTypeStr
    // TODO: update the json files to contain these values instead
    private DamageType damageType = null;
    private String formattedDamage = null;

    public int getHeat() {
        return heat;
    }

    public AmmunitionType getAmmoType() {
        return ammoType;
    }

    public int getRackSize() {
        return rackSize;
    }

    public String getDamageTypeStr() {
        return damageTypeStr;
    }

    public WeaponRange getRange() {
        return range;
    }

    public boolean hasFlag(WeaponFlag flag) {
        return weaponFlags.contains(flag);
    }

    public WeaponRange getAeroAV() {
        return aeroAV;
    }

    public int getMaxAeroRange() {
        return maxAeroRange;
    }

    public AeroWeaponClass getAeroWeaponClass() {
        return aeroWeaponClass;
    }

    public AlphaStrikeWeaponClass getAlphaStrikeWeaponClass() {
        return alphaStrikeWeaponClass;
    }

    public DamageType getDamageType() {
        if (null == damageType) {
            parseDamageString();
        }
        return damageType;
    }

    public String getFormattedDamage() {
        if (null == formattedDamage) {
            parseDamageString();
        }
        return formattedDamage;
    }

    private void parseDamageString() {
        final String[] fields = damageTypeStr.split(":");
        damageType = DamageType.valueOf(fields[0].toUpperCase());
        switch(damageType) {
            case AREA:
                formattedDamage = fields[1] + "A";
                break;
            case VARIABLE:
                formattedDamage = fields[1].replaceAll(",", "/");
                break;
            case SPECIAL:
                formattedDamage = "*";
                break;
            case CLUSTER:
                formattedDamage = String.valueOf(rackSize);
                break;
            default:
                formattedDamage = fields[1];
        }
    }

    @Override
    public int maxWeaponHeat(UnitType unitType) {
        if (unitType.heatStrategy.equals(HeatStrategy.NOT_TRACKED)
            || (unitType.heatStrategy.equals(HeatStrategy.HEAT_NEUTRAL)
                // Heat neutral units only build up heat for energy weapons that don't use ammo.
                && (!hasFlag(WeaponFlag.DIRECT_FIRE_ENERGY) || getAmmoType() != AmmunitionType.NONE))) {
            return 0;
        }
        if (getAmmoType().equals(AmmunitionType.AC_ULTRA)) {
            return getHeat() * 2;
        } else if (getAmmoType().equals(AmmunitionType.AC_ROTARY)) {
            return getHeat() * 6;
        }
        return getHeat();
    }
}
