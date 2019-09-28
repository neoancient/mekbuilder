package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.MekBuild;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class WeaponMountTest {

    @Test
    void weightAndSlotsIncludesEnhancement() {
        MekBuild mek = new MekBuild();
        HeavyWeapon lrm10 = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.LRM10);
        Component artemis = ComponentLibrary.getInstance().getComponent(ComponentKeys.ARTEMIS_IV_IS);

        WeaponMount m = (WeaponMount) mek.createMount(lrm10);
        m.setEnhancement(artemis);

        assertAll(
                () -> assertTrue(m.getComponentWeight() > lrm10.calcWeight(mek)),
                () -> assertTrue(m.getComponentSlots() > lrm10.calcSlots(mek))
        );
    }

    @Test
    void ppcCapacitorIncreasesHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon ppc = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.PPC);
        Component cap = ComponentLibrary.getInstance().getComponent(ComponentKeys.PPC_CAPACITOR);

        WeaponMount m = (WeaponMount) mek.createMount(ppc);
        m.setEnhancement(cap);

        assertEquals(ppc.getHeat() + 5, m.modifiedWeaponHeat());
    }

    @Test
    void pulseModuleIncreasesHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon laser = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEDIUM_LASER);
        Component module = ComponentLibrary.getInstance().getComponent(ComponentKeys.LASER_PULSE_MODULE);

        WeaponMount m = (WeaponMount) mek.createMount(laser);
        m.setEnhancement(module);

        assertEquals(laser.getHeat() + 2, m.modifiedWeaponHeat());
    }

    @Test
    void laserInsulatorDecreasesHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon laser = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEDIUM_LASER);
        Component module = ComponentLibrary.getInstance().getComponent(ComponentKeys.LASER_INSULATOR);

        WeaponMount m = (WeaponMount) mek.createMount(laser);
        m.setEnhancement(module);

        assertEquals(laser.getHeat() - 1, m.modifiedWeaponHeat());
    }

    @Test
    void oneShotDecreasesHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon lrm = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.LRM10);
        Component os = ComponentLibrary.getInstance().getComponent(ComponentKeys.ONE_SHOT);

        WeaponMount m = (WeaponMount) mek.createMount(lrm);
        m.setEnhancement(os);

        assertEquals(lrm.getHeat() * 0.25, m.modifiedWeaponHeat(), 0.001);
    }

    @Test
    void streakWeaponDecreasesHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon srm = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.STREAK_SRM2_IS);

        WeaponMount m = (WeaponMount) mek.createMount(srm);

        assertEquals(srm.getHeat() * 0.5, m.modifiedWeaponHeat(), 0.001);
    }

    @Test
    void uacDoublesMaxHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon uac = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.UAC5_IS);

        Mount m = mek.createMount(uac);

        assertEquals(uac.getHeat() * 2, m.modifiedWeaponHeat());
    }

    @Test
    void uacSextuplesMaxHeat() {
        MekBuild mek = new MekBuild();
        HeavyWeapon rac = (HeavyWeapon) ComponentLibrary.getInstance().getComponent(ComponentKeys.RAC5_IS);

        Mount m = mek.createMount(rac);

        assertEquals(rac.getHeat() * 6, m.modifiedWeaponHeat(), 0.001);
    }

}