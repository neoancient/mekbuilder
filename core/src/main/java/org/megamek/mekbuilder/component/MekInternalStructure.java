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

import org.megamek.mekbuilder.unit.MekBuild;
import org.megamek.mekbuilder.unit.UnitLocation;
import org.megamek.mekbuilder.utilities.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for IS component; in addition to distribution of any required critical slots, calculates
 * cost, weight, and location points.
 */
public class MekInternalStructure extends DistributedMount {

    public MekInternalStructure(MekBuild mek) {
        this(mek, ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_STRUCTURE_STANDARD));
    }

    public MekInternalStructure(MekBuild mek, String structureType) {
        this(mek, ComponentLibrary.getInstance().getComponent(structureType));
    }

    private MekInternalStructure(MekBuild mek, Component structureComponent) {
        super(mek, structureComponent);
        if (!structureComponent.getType().equals(ComponentType.MEK_STRUCTURE)) {
            throw new IllegalArgumentException(structureComponent.getInternalName()
                    + " is not Mek internal structure.");
        }
    }

    /*
     * Head always has 3 points (4 for superheavy). Index is (tonnage - 10) / 5.
     */
    private static final int[] CT_POINTS = {
            4, 5, 6, 8, 10, 11, 12, 14, 16, 18, 20, 21, 22, 23, 25, 27, 29, 30, 31,
            32, 33, 35, 36, 38, 39, 41, 42, 44, 45, 47, 48, 50, 51, 53, 54, 56, 57, 59, 60
    };
    private static final int[] ST_POINTS = {
            3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42
    };
    private static final int[] LEG_POINTS = {
            2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42
    };
    private static final int[] ARM_POINTS = {
            1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 13, 14, 15, 16, 17,
            17, 18, 19, 20, 21, 21, 22, 23, 24, 25, 26, 26, 27, 28, 29, 30, 31, 31, 32, 33
    };

    public int getPoints(UnitLocation loc) {
        switch(loc) {
            case MEK_HEAD:
                return getUnit().getTonnage() > 100? 4 : 3;
            case MEK_CTORSO:
                return CT_POINTS[(int)Math.ceil(getUnit().getTonnage() / 5) - 2];
            case MEK_LTORSO:
            case MEK_RTORSO:
                return ST_POINTS[(int)Math.ceil(getUnit().getTonnage() / 5) - 2];
            case MEK_LARM:
            case MEK_RARM:
                return ARM_POINTS[(int)Math.ceil(getUnit().getTonnage() / 5) - 2];
            case MEK_LLEG:
            case MEK_RLEG:
            case MEK_LFLEG:
            case MEK_RFLEG:
            case MEK_CLEG:
                return LEG_POINTS[(int)Math.ceil(getUnit().getTonnage() / 5) - 2];
            default:
                return 0;
        }
    }

    @Override
    public double getComponentWeight() {
        double base = getComponent().calcWeight(getUnit());
        if (getUnit().isTripod()) {
            base *= 1.1;
        }
        return Round.round(base, getUnit());
    }

    /**
     * Change the structure type. If there are more slots allocated to structure than required
     * by the new type, remove slots until the total drops to the required value.
     *
     * @param structureType The new type of internal structure.
     */
    public void setStructureType(String structureType) {
        Component structure = ComponentLibrary.getInstance().getComponent(structureType);
        if (null == structure) {
            System.err.println("Could not find internal structure type: " + structureType);
            return;
        } else if (!structure.getType().equals(ComponentType.MEK_STRUCTURE)) {
            System.err.println(structure.getInternalName()
                    + " is not Mek internal structure.");
            return;
        }
        setComponent(structure);
        if (getComponent().locationFixed()) {
            updateLocations();
        } else {
            int extraSlots = getLocations().values().stream().mapToInt(Integer::intValue).sum()
                    - getComponent().calcSlots(getUnit());
            if (extraSlots > 0) {
                List<UnitLocation> toRemove = new ArrayList<>();
                for (UnitLocation loc : getLocations().keySet()) {
                    if (getLocations().get(loc) < extraSlots) {
                        getLocations().merge(loc, -extraSlots, Integer::sum);
                        break;
                    } else {
                        extraSlots -= getLocations().get(loc);
                        toRemove.add(loc);
                    }
                }
                for (UnitLocation loc : toRemove) {
                    getLocations().remove(loc);
                }
            }
        }
    }
}
