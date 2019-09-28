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
import org.megamek.mekbuilder.tech.ITechFilter;
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
    }

    private final Map<UnitLocation, Integer> criticalSlots;
    private MekConfiguration configuration;
    private double tonnage = 20;
    private boolean omni = false;

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
        configuration = MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_BIPED);
        internalStructure = (MekInternalStructure) createMount(getDefaultStructure());
        getComponents().add(internalStructure);
        engineMount = (MekEngineMount) createMount(getDefaultEngine());
        engineMount.setRating(20);
        getComponents().add(engineMount);
        cockpitMount = (CockpitMount) createMount(getDefaultCockpit());
        getComponents().add(cockpitMount);
        gyroMount = createMount(getDefaultGyro());
        getComponents().add(gyroMount);
        secondaryMotiveMount = (CompoundMount) createMount(getDefaultSecondaryMotiveType());
        getComponents().add(secondaryMotiveMount);
        heatSinkMount = (CompoundMount) createMount(getDefaultHeatSinkType());
        heatSinkMount.setCount(0);
        getComponents().add(heatSinkMount);
        myomerMount = (DistributedMount) createMount(ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.MYOMER_STANDARD));
        getComponents().add(myomerMount);
        armorMount = (ArmorMount) createMount(ComponentLibrary.getInstance()
                .getComponent(getDefaultArmorName()));
        initCriticalSlots();
    }

    @Override
    public boolean isBiped() {
        return configuration.getLimbConfiguration().equals(LimbConfiguration.BIPED)
                || configuration.getLimbConfiguration().equals(LimbConfiguration.ARMLESS);
    }

    @Override
    public boolean isTripod() {
        return configuration.getLimbConfiguration().equals(LimbConfiguration.TRIPOD);
    }

    @Override
    public boolean isQuad() {
        return configuration.getLimbConfiguration().equals(LimbConfiguration.QUAD);
    }

    public boolean isPrimitive() {
        return configuration.getBaseType().equals(MekConfiguration.BaseType.PRIMITIVE);
    }

    public boolean isQuadVee() {
        return configuration.getBaseType().equals(MekConfiguration.BaseType.QUADVEE);
    }

    public boolean isLAM() {
        return configuration.getBaseType().equals(MekConfiguration.BaseType.LAM);
    }

    public boolean isSuperheavy() {
        return getWeightClass().equals(UnitWeightClass.SUPERHEAVY);
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
        switch (getLimbConfiguration()) {
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
        return configuration.getLimbConfiguration();
    }

    @Override
    public void setBaseConstructionOption(UnitConstructionOption option) {
        if (!configuration.getConstructionOptions().contains(option)) {
            throw new IllegalArgumentException("Mismatch between configuration option "
                    + configuration.getSubType() + " and constructionOption " + option.getKey());
        }
        super.setBaseConstructionOption(option);
    }

    /**
     * @return The specific mech configuration
     */
    public MekConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the specific mech configuration. The construction option will be changed to the one most closely
     * matching the current tonnage.
     *
     * @param configuration      The configuration to set
     */
    public void setConfiguration(MekConfiguration configuration) {
        LimbConfiguration oldLimbs = getLimbConfiguration();
        this.configuration = configuration;
        if (oldLimbs != getLimbConfiguration()) {
            resetLimbConfiguration();
        }
        if (isOmni() && !configuration.isOmniAllowed()) {
            setOmni(false);
        }
        // Assign the first one that we find with upper limit >= designated tonnage
        for (UnitConstructionOption option : configuration.getConstructionOptions()) {
            if (getTonnage() <= option.getMaxWeight()) {
                setBaseConstructionOption(option);
                return;
            }
        }
        // If we can't find a match, pick the last one
        setBaseConstructionOption(configuration.getConstructionOptions()
                .get(configuration.getConstructionOptions().size() - 1));
    }

    /**
     * Changes limb configuration and adjusts critical spaces. Equipment mounted on a changed
     * limb is moved to the new corresponding limb, or unmounted if there is no corresponding limb
     * in the new configuration.
     */
    private void resetLimbConfiguration() {
        switch (getLimbConfiguration()) {
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
        int walk = getBaseWalkMP();
        this.tonnage = tonnage;
        setBaseWalkMP(walk);
    }

    @Override
    public boolean isOmni() {
        return omni;
    }

    @Override
    public void setOmni(boolean omni) {
        this.omni = omni && configuration.isOmniAllowed();
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

    public CockpitMount getCockpitMount() {
        return cockpitMount;
    }

    public CompoundMount getHeatSinkMount() {
        return heatSinkMount;
    }

    public Mount getGyroMount() {
        return gyroMount;
    }

    public DistributedMount getMyomerMount() {
        return myomerMount;
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

    public MVFEngine getEngineType() {
        return (MVFEngine) engineMount.getComponent();
    }

    public void setEngineType(MVFEngine engine) { engineMount.setComponent(engine);}

    public int getEngineRating() {
        return engineMount.getEngineRating();
    }

    public void setEngineRating(int rating) {
        engineMount.setRating(rating);
    }

    @Override
    public void setBaseWalkMP(int walk) {
        if (walk > maxWalkMP(null)) {
            walk = maxWalkMP(null);
        }
        double rating = walk * getTonnage();
        if (isPrimitive()) {
            rating *= 1.2;
        }
        rating = Math.max(10.0, Math.ceil(rating * getEngineType().variableStepSize())
                / getEngineType().variableStepSize());
        if (rating > getEngineType().variableSizeMax()) {
            setEngineType(getEngineType().largeSize());
        } else if (rating < getEngineType().variableSizeMin()) {
            setEngineType(getEngineType().standardSize());
        }
        setEngineRating((int) rating);
    }

    @Override
    public int getBaseWalkMP() {
        int rating = engineMount.getEngineRating();
        if (isPrimitive()) {
            rating = (rating * 5) / 6;
        }
        return (int) (rating / getTonnage());
    }

    @Override
    public int getWalkMP() {
        return getBaseWalkMP();
    }

    @Override
    public String formattedRunMP() {
        int maxRun = getRunMP();
        long boosts = getComponents().stream()
                .filter(m -> m.getComponent().getType().equals(ComponentType.MOVE_ENHANCEMENT)
                        && m.getComponent().hasFlag(ComponentSwitch.SPEED_BOOST)
                        && ((MoveEnhancement) m.getComponent()).getMode() == MotiveType.GROUND).count();
        if (boosts > 1) {
            // It shouldn't be possible to have more than two (MASC + Supercharger),
            // but we'll make sure we're capped at x2.5 anyway
            maxRun *= 2.5;
        } else if (boosts == 1) {
            maxRun *= 2;
        } else if (getComponents().stream().anyMatch(m -> m.getComponent().hasFlag(ComponentSwitch.TSM))) {
            maxRun++;
        }
        if (maxRun > getRunMP()) {
            return String.format("%d [%d]", getRunMP(), maxRun);
        } else {
            return Integer.toString(maxRun);
        }
    }

    @Override
    public int minWalkMP() {
        // LAMs have a minimum jump MP of 3, which necessarily implies a minimum walk MP.
        if (!isLAM()) {
            return 1;
        } else if (getSecondaryMotiveType() != null && getSecondaryMotiveType().isImproved()) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public int maxWalkMP(ITechFilter filter) {
        MVFEngine engine = getEngineType();
        if ((engine.largeSize() != null)
            && (filter == null || filter.isLegal(engine.largeSize()))) {
            engine = engine.largeSize();
        }
        double maxRating = engine.variableSizeMax();
        if (isPrimitive()) {
            // Drop max walk for primitives to make sure walk * tonnage * 1.2 will be <= max rating
            // and round down to the step size
            maxRating = Math.floor((maxRating / 1.2) * engine.variableStepSize()) / engine.variableStepSize();
        }
        return (int) (maxRating / tonnage);
    }

    @Override
    public SecondaryMotiveSystem getSecondaryMotiveType() {
        return (SecondaryMotiveSystem) secondaryMotiveMount.getComponent();
    }

    @Override
    public void setSecondaryMotiveType(SecondaryMotiveSystem secondary) {
        if (secondary.getMode().equals(MotiveType.GROUND)) {
            secondaryMotiveMount.setCount(0);
        }
        secondaryMotiveMount.setComponent(secondary);
    }

    public int minSecondaryMP() {
        if (isLAM()) {
            return 3;
        } else if (getSecondaryMotiveType().getMode().equals(MotiveType.GROUND)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int maxSecondaryMP() {
        if (getSecondaryMotiveType().getMode().equals(MotiveType.GROUND)) {
            return 0;
        } else if (getSecondaryMotiveType().isImproved()) {
            return getBaseRunMP();
        } else {
            return getBaseWalkMP();
        }
    }

    @Override
    public int getBaseSecondaryMP() {
        return secondaryMotiveMount.getCount();
    }

    @Override
    public void setSecondaryMP(int mp) {
        secondaryMotiveMount.setCount(mp);
    }

    @Override
    public int getSecondaryMP() {
        int mp = getBaseSecondaryMP();
        if ((mp > 0) && getComponents().stream()
                .anyMatch(m -> m.getComponent().hasFlag(ComponentSwitch.PARTIAL_WING))) {
            mp++;
            if (getWeightClass().value < UnitWeightClass.HEAVY.value) {
                mp++;
            }
        }
        return mp;
    }

    /**
     * @return The type of cockpit installed on the unit
     */
    public Cockpit getCockpitType() {
        return cockpitMount.getCockpit();
    }

    /**
     * Sets the type of cockpit on the unit. A {@code null} cockpit is ignored.
     *
     * @param cockpitType The cockpit type.
     */
    public void setCockpitType(Cockpit cockpitType) {
        cockpitMount.setCockpitType(cockpitType);
    }

    @Override
    public boolean isCockpitLocation(UnitLocation loc) {
        return cockpitMount.getCockpitSlots(loc) > 0;
    }

    /**
     * @return The type of gyro installed
     */
    public Component getGyroType() {
        return gyroMount.getComponent();
    }

    /**
     * Sets the type of gyro. A {@code null} gyro is ignored.
     * @param gyro The gyro type
     */
    public void setGyroType(Component gyro) {
        if (null != gyro) {
            if (gyro.getType().equals(ComponentType.GYRO)) {
                gyroMount.setComponent(gyro);
            } else {
                System.err.println("Invalid gyro type: " + gyro.getInternalName());
            }
        }
    }

    /**
     * @return The type of myomer installed on the unit.
     */
    public Component getMyomerType() {
        return myomerMount.getComponent();
    }

    /**
     * Sets the type of myomer. A {@code null} myomer is ignored.
     * @param myomer The myomer type
     */
    public void setMyomerType(Component myomer) {
        if (null != myomer) {
            if (myomer.getType().equals(ComponentType.MYOMER)) {
                myomerMount.setComponent(myomer);
            } else {
                System.err.println("Invalid myomer type: " + myomer.getInternalName());
            }
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

    public Component getDefaultEngine() {
        return ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_FUSION);
    }

    public Component getDefaultStructure() {
        return ComponentLibrary.getInstance().getAllComponents().stream()
                .filter(c -> c.getType().equals(ComponentType.MEK_STRUCTURE)
                && c.isDefault() && allowed(c)).findFirst()
                .orElse(ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_STRUCTURE_STANDARD));
    }

    public Cockpit getDefaultCockpit() {
        return (Cockpit) ComponentLibrary.getInstance().getComponent(
                getUnitType().equals(UnitType.INDUSTRIAL_MEK) ?
                        ComponentKeys.COCKPIT_INDUSTRIAL : ComponentKeys.COCKPIT_STANDARD_MEK);

    }

    public Component getDefaultGyro() {
        return ComponentLibrary.getInstance().getAllComponents().stream()
                .filter(c -> c.getType().equals(ComponentType.GYRO)
                        && c.isDefault() && allowed(c)).findFirst()
                .orElse(ComponentLibrary.getInstance().getComponent(ComponentKeys.GYRO_STANDARD));
    }

    @Override
    public SecondaryMotiveSystem getDefaultSecondaryMotiveType() {
        return (SecondaryMotiveSystem) ComponentLibrary.getInstance().getAllComponents().stream()
                .filter(c -> c.getType().equals(ComponentType.SECONDARY_MOTIVE_SYSTEM)
                        && c.isDefault() && allowed(c)).findFirst()
                .orElse(super.getDefaultSecondaryMotiveType());
    }

    @Override
    public HeatSink getDefaultHeatSinkType() {
        return (HeatSink) ComponentLibrary.getInstance().getAllComponents().stream()
                .filter(c -> c.getType().equals(ComponentType.HEAT_SINK)
                        && c.isDefault() && allowed(c)).findFirst()
                .orElse(super.getDefaultHeatSinkType());
    }

    @Override
    public String getDefaultArmorName() {
        return ComponentKeys.ARMOR_STANDARD;
    }


    @Override
    public boolean allowed(Component component) {
        return super.allowed(component)
                && component.flagMatches(ComponentSwitch.PRIMITIVE, isPrimitive())
                && component.flagMatches(ComponentSwitch.SUPERHEAVY_MEK, isSuperheavy())
                && component.flagMatches(ComponentSwitch.QUAD, isQuad())
                && component.flagMatches(ComponentSwitch.TRIPOD, isTripod())
                && component.flagMatches(ComponentSwitch.LAM, isLAM())
                && component.flagMatches(ComponentSwitch.QUADVEE, isQuadVee())
                && !(component.spreadable() && isLAM());
    }

    @Override
    public Mount createMount(Component c) {
        switch (c.getType()) {
            case ENGINE:
                return new MekEngineMount(this, (MVFEngine) c);
            case MEK_STRUCTURE:
                return new MekInternalStructure(this, c);
            case MYOMER:
                return new DistributedMount(this, c);
            default:
                return super.createMount(c);
        }
    }
    /**
     * Computes the maximum heat generated by any secondary motive system.
     * @return maximum jump/UMU heat
     */
    int secondaryMotiveHeat() {
        int jumpHeat = 0;
        if (getSecondaryMotiveType().getMode().equals(MotiveType.JUMP)) {
            if (getSecondaryMotiveType().isImproved()) {
                jumpHeat = Math.max((getBaseSecondaryMP() + 1) / 2, 3);
            } else {
                jumpHeat = Math.max(getBaseSecondaryMP(), 3);
            }
            if (getEngineType().hasFlag(ComponentSwitch.XXL)) {
                jumpHeat *= 2;
            }
        } else if (getSecondaryMotiveType().getMode().equals(MotiveType.SUBMERSIBLE)) {
            jumpHeat = 1;
        }
        return jumpHeat;
    }

    @Override
    public int movementHeat() {
        return super.movementHeat() + Math.max(engineMount.runHeat(), secondaryMotiveHeat());
    }
}
