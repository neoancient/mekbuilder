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

/**
 * A collection of String constants for component keys, both as a handy reference
 * and a guard against typo bugs.
 */
public interface ComponentKeys {
    String ENGINE_FUSION = "FusionEngine";
    String ENGINE_COMPACT = "CompactFusion";
    String ENGINE_ICE = "ICE";
    String ENGINE_XXL_IS = "ISXXLEngine";

    String SECONDARY_MOTIVE_NONE = "NoSecondaryMotive";
    String MEK_JJ = "Jump Jet";
    String MEK_IJJ = "Improved Jump Jet";
    String MEK_UMU = "UMU";
    String PM_JJ = "ProtoMek Jump Jet";

    String HEAT_SINK_SINGLE = "Heat Sink";
    String HEAT_SINK_DOUBLE_IS = "ISDoubleHeatSink";
    String HEAT_SINK_DOUBLE_CLAN = "CLDoubleHeatSink";

    String ARMOR_STANDARD = "Standard Armor";
    String ARMOR_INDUSTRIAL = "Industrial Armor";
    String ARMOR_PROTOMEK_STANDARD = "ProtoMek Armor";
    String ARMOR_BA_IS_STANDARD = "IS BA Standard Armor (Basic)";
    String ARMOR_BA_CLAN_STANDARD = "CLAN BA Standard Armor (Basic)";

    String MEK_STRUCTURE_STANDARD = "Standard Structure";
    String MEK_STRUCTURE_IS_ENDO_STEEL = "IS Endo Steel";
    String MEK_STRUCTURE_CLAN_ENDO_STEEL = "Clan Endo Steel";

    String COCKPIT_STANDARD_MEK = "Standard Cockpit";
    String COCKPIT_MEK_SMALL = "Small Cockpit";
    String COCKPIT_INDUSTRIAL = "Industrial Cockpit";
    String COCKPIT_STANDARD_ASF = "ASF Cockpit";
    String COCKPIT_STANDARD_CF = "CF Cockpit";
    String COCKPIT_STANDARD_CV = "CV Cockpit";

    String GYRO_STANDARD = "Standard Gyro";
    String GYRO_HEAVY_DUTY = "Heavy Duty Gyro";

    String MYOMER_STANDARD = "Standard Myomer";
    String MYOMER_TSM = "TSM";

    String MASC_IS = "ISMASC";
    String MASC_CLAN = "CLMASC";
    String MEK_JUMP_BOOSTER = "MechanicalJumpBooster";

    String MEDIUM_LASER = "Medium Laser";
    String LRM10 = "ISLRM 10";
    String STREAK_SRM2_IS = "ISStreakSRM2";
    String PPC = "PPC";
    String UAC5_IS = "ISUltraAC5";
    String RAC5_IS = "ISRotaryAC5";

    String ARTEMIS_IV_IS = "ISArtemisIV";
    String PPC_CAPACITOR = "PPC Capacitor";
    String LASER_PULSE_MODULE = "ISRISCLaserPulseModule";
    String LASER_INSULATOR = "ISLaserInsulator";
    String ONE_SHOT = "OneShot";
}
