/**
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
 * Values used for faction-specific intro dates.
 *
 * @author: Neoancient
 */
public enum Faction {
    IS ("Inner Sphere", Faction.GROUP_IS),
    CC ("Capellan Confederation", Faction.GROUP_IS),
    CF ("Circinus Federation", Faction.GROUP_PERIPHERY),
    CP ("Calderon Protectorate", Faction.GROUP_PERIPHERY),
    CS ("ComStar", Faction.GROUP_CS),
    DC ("Draconis Combine", Faction.GROUP_IS),
    EI ("Escorpión Império", Faction.GROUP_PERIPHERY),
    FC ("Federated Commonwealth", Faction.GROUP_IS),
    FR ("Fronc Reaches", Faction.GROUP_PERIPHERY),
    FS ("Fderated Suns", Faction.GROUP_IS),
    FW ("Free Worlds League", Faction.GROUP_IS),
    LC ("Lyran Commonwealth", Faction.GROUP_IS),
    MC ("Magistracy of Canopus", Faction.GROUP_PERIPHERY),
    MH ("Marian Hegemony", Faction.GROUP_PERIPHERY),
    OA ("Outworlds Alliance", Faction.GROUP_PERIPHERY),
    TA ("Terran Alliance", Faction.GROUP_IS),
    TC ("Taurian Concordat", Faction.GROUP_PERIPHERY),
    TH ("Terran Hegemony", Faction.GROUP_PERIPHERY),
    RD ("Rasalhague Dominion", Faction.GROUP_CLAN),
    RS ("Republic of the Sphere", Faction.GROUP_IS),
    RA ("Raven Alliance", Faction.GROUP_CLAN),
    RW ("Rim Worlds Republic", Faction.GROUP_PERIPHERY),
    WB ("Word of Blake", Faction.GROUP_CS),
    MERC ("Mercenary", Faction.GROUP_IS),
    PER ("Periphery", Faction.GROUP_PERIPHERY),
    CLAN ("Clan", Faction.GROUP_CLAN),
    CBR ("Clan Burrock", Faction.GROUP_CLAN),
    CBS ("Clan Blood Spirit", Faction.GROUP_CLAN),
    CCY ("Clan Coyote", Faction.GROUP_CLAN),
    CCC ("Clan Cloud Cobra", Faction.GROUP_CLAN),
    CFM ("Clan Fire Mandrill", Faction.GROUP_CLAN),
    CGB ("Clan Ghost Bear", Faction.GROUP_CLAN),
    CGS ("Clan Goliath Scorpion", Faction.GROUP_CLAN),
    CHH ("Clan Hell's Horses", Faction.GROUP_CLAN),
    CIH ("Clan Ice Hellion", Faction.GROUP_CLAN),
    CJF ("Clan Jade Falcon", Faction.GROUP_CLAN),
    CMN ("Clan Mongoose", Faction.GROUP_CLAN),
    CNC ("Clan Nova Cat", Faction.GROUP_CLAN),
    CSF ("Clan Sea Fox", Faction.GROUP_CLAN),
    CSJ ("Clan Smoke Jaguar", Faction.GROUP_CLAN),
    CSR ("Clan Snow Raven", Faction.GROUP_CLAN),
    CSV ("Clan Steel Viper", Faction.GROUP_CLAN),
    CSA ("Clan Star Adder", Faction.GROUP_CLAN),
    CWM ("Clan Widowmaker", Faction.GROUP_CLAN),
    CWF ("Clan Wolf", Faction.GROUP_CLAN),
    CWX ("Clan Wolf-in-Exile", Faction.GROUP_CLAN),
    CWV ("Clan Wolverine", Faction.GROUP_CLAN);

    static final int GROUP_IS = 0;
    static final int GROUP_PERIPHERY = 1;
    static final int GROUP_CLAN = 2;
    static final int GROUP_CS = 3;

    public final String displayName;
    private final int group;

    Faction(String displayName, int group) {
        this.displayName = displayName;
        this.group = group;
    }

    public boolean isIS() {
        return group == Faction.GROUP_IS || group == Faction.GROUP_CS;
    }

    public boolean isClan() {
        return group == Faction.GROUP_CLAN;
    }

    public boolean isPeriphery() {
        return group == Faction.GROUP_PERIPHERY;
    }

    public boolean isComStar() {
        return group == Faction.GROUP_CS;
    }
}


