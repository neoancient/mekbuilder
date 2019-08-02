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
package org.megamek.mekbuilder.unit;

import org.megamek.mekbuilder.component.*;
import org.megamek.mekbuilder.tech.ConstructionOptionKey;
import org.megamek.mekbuilder.tech.UnitConstructionOption;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Construction data for a Mek
 */
public class MekBuild extends UnitBuild {
    
    public enum LimbConfiguration {
        BIPED, QUAD, TRIPOD, ARMLESS
    };

    private final Map<UnitLocation, Integer> criticalSlots;
    private LimbConfiguration limbConfiguration;
    private double tonnage = 20;

    private MekInternalStructure internalStructure;
    private MekEngineMount engineMount;
    private CockpitMount cockpitMount;
    private CompoundMount secondaryMotiveMount;
    private CompoundMount heatSinkMount;
    private Mount gyroMount;
    private DistributedMount myomerMount;
    private ArmorMount armorMount;

    public MekBuild() {
        super((UnitConstructionOption) ConstructionOptionKey.MEK_STANDARD.get());
        criticalSlots = new EnumMap<>(UnitLocation.class);
        limbConfiguration = LimbConfiguration.BIPED;
        internalStructure = new MekInternalStructure(this);
        getComponents().add(internalStructure);
        engineMount = new MekEngineMount(this, (MVFEngine) ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.ENGINE_FUSION));
        getComponents().add(engineMount);
        cockpitMount = new CockpitMount(this, ComponentKeys.COCKPIT_STANDARD_MEK);
        getComponents().add(cockpitMount);
        Mount gyroMount = new Mount(this, ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.GYRO_STANDARD));
        getComponents().add(gyroMount);
        secondaryMotiveMount = new CompoundMount(this, ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.MEK_JJ));
        getComponents().add(secondaryMotiveMount);
        heatSinkMount = new CompoundMount(this, ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.HEAT_SINK_SINGLE));
        getComponents().add(heatSinkMount);
        myomerMount = new DistributedMount(this, ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.MYOMER_STANDARD));
        getComponents().add(myomerMount);
        armorMount = new ArmorMount(this, (Armor) ComponentLibrary.getInstance()
                .getComponent(getDefaultArmorName()));
        initCriticalSlots();
    }

    @Override
    public boolean isBiped() {
        return limbConfiguration.equals(LimbConfiguration.BIPED)
                || limbConfiguration.equals(LimbConfiguration.ARMLESS);
    }

    @Override
    public boolean isTripod() {
        return limbConfiguration.equals(LimbConfiguration.TRIPOD);
    }

    @Override
    public boolean isQuad() {
        return limbConfiguration.equals(LimbConfiguration.QUAD);
    }

    @Override
    public boolean usesKilogramStandard() {
        return false;
    }

    /**
     * Sets the number of critical slots for each location and removes entries for
     * non-existent limbs.
     */
    private void initCriticalSlots() {
        criticalSlots.put(UnitLocation.MEK_HEAD, 6);
        criticalSlots.put(UnitLocation.MEK_CTORSO, 12);
        criticalSlots.put(UnitLocation.MEK_LTORSO, 12);
        criticalSlots.put(UnitLocation.MEK_RTORSO, 12);
        criticalSlots.put(UnitLocation.MEK_LLEG, 6);
        criticalSlots.put(UnitLocation.MEK_RLEG, 6);
        switch (limbConfiguration) {
            case BIPED:
                criticalSlots.put(UnitLocation.MEK_LARM, 12);
                criticalSlots.put(UnitLocation.MEK_RARM, 12);
                criticalSlots.remove(UnitLocation.MEK_LFLEG);
                criticalSlots.remove(UnitLocation.MEK_RFLEG);
                criticalSlots.remove(UnitLocation.MEK_CLEG);
                break;
            case QUAD:
                criticalSlots.put(UnitLocation.MEK_LFLEG, 6);
                criticalSlots.put(UnitLocation.MEK_RFLEG, 6);
                criticalSlots.remove(UnitLocation.MEK_LARM);
                criticalSlots.remove(UnitLocation.MEK_RARM);
                criticalSlots.remove(UnitLocation.MEK_CLEG);
                break;
            case TRIPOD:
                criticalSlots.put(UnitLocation.MEK_LARM, 12);
                criticalSlots.put(UnitLocation.MEK_RARM, 12);
                criticalSlots.put(UnitLocation.MEK_CLEG, 6);
                criticalSlots.remove(UnitLocation.MEK_LFLEG);
                criticalSlots.remove(UnitLocation.MEK_RFLEG);
                break;
            case ARMLESS:
                criticalSlots.remove(UnitLocation.MEK_LARM);
                criticalSlots.remove(UnitLocation.MEK_RARM);
                criticalSlots.remove(UnitLocation.MEK_LFLEG);
                criticalSlots.remove(UnitLocation.MEK_RFLEG);
                criticalSlots.remove(UnitLocation.MEK_CLEG);
            default:
                break;

        }
    }

    public LimbConfiguration getLimbConfiguration() {
        return limbConfiguration;
    }

    /**
     * Changes limb configuration and adjusts critical spaces. Equipment mounted on a changed
     * limb is moved to the new corresponding limb, or unmounted if there is no corresponding limb
     * in the new configuration.
     *
     * @param newConfiguration The limb configuration to change to.
     */
    public void setLimbConfiguration(LimbConfiguration newConfiguration) {
        switch (newConfiguration) {
            case BIPED:
                getComponents().forEach(md -> {
                    md.changeLocation(UnitLocation.MEK_LFLEG, UnitLocation.MEK_LARM);
                    md.changeLocation(UnitLocation.MEK_RFLEG, UnitLocation.MEK_RARM);
                    md.changeLocation(UnitLocation.MEK_CLEG, UnitLocation.NO_LOCATION);
                });
                break;
            case QUAD:
                getComponents().forEach(md -> {
                    md.changeLocation(UnitLocation.MEK_LARM, UnitLocation.MEK_LFLEG);
                    md.changeLocation(UnitLocation.MEK_RARM, UnitLocation.MEK_RFLEG);
                    md.changeLocation(UnitLocation.MEK_CLEG, UnitLocation.NO_LOCATION);
                });
                break;
            case TRIPOD:
                getComponents().forEach(md -> {
                    md.changeLocation(UnitLocation.MEK_LFLEG, UnitLocation.MEK_LARM);
                    md.changeLocation(UnitLocation.MEK_RFLEG, UnitLocation.MEK_RARM);
                });
                break;
            case ARMLESS:
                getComponents().forEach(md -> {
                    md.changeLocation(UnitLocation.MEK_LARM, UnitLocation.NO_LOCATION);
                    md.changeLocation(UnitLocation.MEK_RARM, UnitLocation.NO_LOCATION);
                    md.changeLocation(UnitLocation.MEK_CLEG, UnitLocation.NO_LOCATION);
                });
                break;
        }
        limbConfiguration = newConfiguration;
        initCriticalSlots();
    }

    @Override
    public Set<UnitLocation> getLocationSet() {
        return Collections.unmodifiableSet(criticalSlots.keySet());
    }

    /**
     * @param loc A location on the unit
     * @return    The total number of slots in the location. If the location does not exist on the unit, returns 0.
     */
    public int slotsInLocation(UnitLocation loc) {
        return criticalSlots.getOrDefault(loc, 0);
    }

    @Override
    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    @Override
    public UnitWeightClass getWeightClass() {
        if (tonnage > 100) {
            return UnitWeightClass.SUPERHEAVY;
        } else if (tonnage >= 80) {
            return UnitWeightClass.ASSAULT;
        } else if (tonnage >= 60) {
            return UnitWeightClass.HEAVY;
        } else if (tonnage >= 40) {
            return UnitWeightClass.MEDIUM;
        } else if (tonnage >= 20) {
            return UnitWeightClass.LIGHT;
        } else {
            return UnitWeightClass.ULTRALIGHT;
        }
    }

    public MekInternalStructure getStructureMount() {
        return internalStructure;
    }

    public Component getStructureType() {
        return internalStructure.getComponent();
    }

    public void setStructureType(Component structure) { internalStructure.setComponent(structure);}

    public void setStructureTypeByName(String structureType) {
        internalStructure.setComponent(structureType);
    }

    @Override
    public double getStructureTonnage() {
        return internalStructure.getComponentWeight();
    }

    @Override
    public MekEngineMount getEngine() {
        return engineMount;
    }

    @Override
    public int getBaseWalkMP() {
        return (int)(engineMount.getEngineRating() / getTonnage());
    }

    @Override
    public int getWalkMP() {
        return getBaseWalkMP();
    }

    public int getBaseJumpMP() {
        if (((SecondaryMotiveSystem) secondaryMotiveMount.getComponent()).getMode()
                .equals(MotiveType.JUMP)) {
            return secondaryMotiveMount.getCount();
        } else {
            return 0;
        }
    }

    public int getBaseUWMP() {
        if (((SecondaryMotiveSystem) secondaryMotiveMount.getComponent()).getMode()
                .equals(MotiveType.SUBMERSIBLE)) {
            return secondaryMotiveMount.getCount();
        } else {
            return 0;
        }
    }

    public Cockpit getCockpitType() {
        return cockpitMount.getCockpit();
    }

    public void setCockpitType(String cockpitType) {
        cockpitMount.setCockpitType(cockpitType);
    }

    @Override
    public boolean isCockpitLocation(UnitLocation loc) {
        return cockpitMount.getCockpitSlots(loc) > 0;
    }

    public Component getGyro() {
        return gyroMount.getComponent();
    }

    public void setGyroType(String gyroType) {
        Component gyro = ComponentLibrary.getInstance().getComponent(gyroType);
        if (null != gyro && gyro.getType().equals(ComponentType.GYRO)) {
            gyroMount.setComponent(gyro);
        } else {
            System.err.println("Could not find gyro to match " + gyroType);
        }
    }

    public Component getMyomerType() {
        return myomerMount.getComponent();
    }

    public void setMyomerType(String myomerType) {
        Component myomer = ComponentLibrary.getInstance().getComponent(myomerType);
        if (null != myomerType && myomer.getType().equals(ComponentType.MYOMER)) {
            myomerMount.setComponent(myomer);
        } else {
            System.err.println("Could not find gyro to match " + myomerType);
        }
    }

    @Override
    public double getArmorTonnage() {
        return armorMount.getComponentWeight();
    }

    @Override
    public double getTotalArmorPoints() {
        return armorMount.getTotalArmorPoints();
    }

    @Override
    public int getMaxArmorPoints(UnitLocation loc) {
        if (loc.equals(UnitLocation.MEK_HEAD)) {
            return internalStructure.getPoints(loc) * 3;
        } else {
            return internalStructure.getPoints(loc) * 2;
        }
    }

    @Override
    public String getDefaultArmorName() {
        return ComponentKeys.ARMOR_STANDARD;
    }
}
