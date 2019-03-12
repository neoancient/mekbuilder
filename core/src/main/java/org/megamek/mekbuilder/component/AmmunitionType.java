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
package org.megamek.mekbuilder.component;

/**
 * Ammunition according to weapon type. This does not cover alternate munition types or various sizes of
 * the same weapon.
 */
public enum AmmunitionType {
    NONE,
    VEHICLE_FLAMER, HEAVY_FLAMER, CHEMICAL_LASER, VGL,
    MG, MG_HEAVY, MG_LIGHT,
    GAUSS, GAUSS_LIGHT, GAUSS_HEAVY, APGAUSS, MAGSHOT, HAG, SBGAUSS, IGAUSS_HEAVY,
    AC, AC_LBX, AC_ULTRA, AC_ROTARY, LAC, HYPER_VELOCITY, ACI, PAC,
    LRM, LRM_TORPEDO, LRM_TORPEDO_COMBO, LRM_STREAK, EXLRM, PXLRM, NLRM,
    TBOLT_5, TBOLT_10, TBOLT_15, TBOLT_20,
    SRM, SRM_TORPEDO, SRM_STREAK, SRM_ADVANCED, HSRM,
    MRM, MRM_STREAK, NARC, INARC, AMS, ATM, MML, IATM, APDS,
    ARROW_IV, LONG_TOM, SNIPER, THUMPER, CRUISE_MISSILE, BA_TUBE,
    SNIPER_CANNON, THUMPER_CANNON, LONG_TOM_CANNON,
    MINE, ROCKET_LAUNCHER, PLASMA, RAIL_GUN, MEK_MORTAR, TASER, RIFLE,
    NAC, LIGHT_NGAUSS, MED_NGAUSS, HEAVY_NGAUSS, AR10, LMASS, MMASS, HMASS, SCC,
    KILLER_WHALE, WHITE_SHARK, BARRACUDA, KRAKEN_T, MANTA_RAY, SWORDFISH, STINGRAY, PIRANHA, KRAKENM,
    SCREEN_LAUNCHER, FLUID_GUN, NAIL_RIVET_GUN, C3_REMOTE_SENSOR,
    ALAMO, BOMB, BA_MICRO_BOMB, AAA_MISSILE, AS_MISSILE, ASEW_MISSILE, LAA_MISSILE, RL_BOMB, ARROW_IV_BOMB,
    AC_PRIMITIVE, LRM_PRIMITIVE, SRM_PRIMITIVE, LONG_TOM_PRIM, AC_IMP, GAUSS_IMP, SRM_IMP, LRM_IMP;
}
