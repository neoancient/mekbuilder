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
package org.megamek.mekbuilder.tech;

import megamek.common.annotations.Nullable;

/**
 * Implementing class provides criteria that can be used to filter component availability by year, tech base,
 * tech level, and faction
 */
public interface ITechFilter {

    /**
     * @return The game year the unit was first produced.
     */
    int getYear();

    /**
     * @return The unit's tech base (IS or Clan). {@link TechBase#ALL} is used for mixed tech units.
     */
    TechBase getTechBase();

    /**
     * @return The maximum tech/rules level for components
     */
    TechLevel getTechLevel();

    /**
     * Production faction can be used to modify tech introduction dates so that the faction that develops it has
     * access a few years before other factions. {@link Faction#IS} and {@link Faction#CLAN} can be used to apply
     * variable introduction dates without specifying a faction (by using the one appropriate to the tech base).
     * A value of {@code null} will ignore the faction data and use the base years.
     *
     * @return The production faction, or {@code null} if there is not a particular faction.
     */
    @Nullable
    Faction getFaction();

    /**
     * Era-based progression determines tech level based on production stage; experimental for prototype,
     * advanced for production, and standard for common.
     *
     * @return Whether to use era-based tech progression
     */
    boolean eraBasedProgression();

    /**
     * Extinction for the purposes of tech progression means no longer manufactured rather than non-existent. So
     * extinct components would not be used in large-scale production, but could be found in one-off refits or limited
     * production runs.
     *
     * @return Whether to include components even though they are extinct.
     */
    boolean showExtinct();

    default boolean isLegal(ITechProgression tech) {
        // Faction may be treated as TH for ComStar and early Clan tech
        Faction faction = getFaction();
        // IS tech may be treated as Clan in early Clan period
        boolean clanTech = tech.clanTech();

        Integer isIntroDate = tech.introDate(false);
        Integer clanIntroDate = tech.introDate(true);
        boolean introducedIS = (null != isIntroDate) && (isIntroDate <= getYear());
        boolean introducedClan = (null != clanIntroDate) && (clanIntroDate <= getYear());
        boolean extinctIS = tech.extinct(getYear(), false);
        boolean extinctClan = tech.extinct(getYear(), true);

        if ((faction.isComStar())
                && extinctIS && (null != isIntroDate)
                && (tech.baseAvailability(ITechProgression.techEra(getYear())).ordinal() < Rating.RATING_X.ordinal())
                && isIntroDate <= getYear()) {
            // ComStar has access to Star League tech that is otherwise extinct in the Inner Sphere as if TH,
            // unless it has an availability of X (which is SLDF Royal equipment).
            extinctIS = false;
            faction = Faction.TH;
        } else if (getTechBase().equals(TechBase.CLAN) && !introducedClan
                && tech.availableIn(2787, false, Faction.TH)
                && !extinctClan && !extinctIS
                && (tech.getDate(TechStage.EXTINCTION, false) != null)) {
            // Transitional period: Clans can treat IS tech as Clan if it was available to TH and
            // has an extinction date that it hasn't reached yet (using specific Clan date if given).
            faction = Faction.TH;
            clanTech = false;
        }
        if (getTechBase().equals(TechBase.ALL)) {
            if ((!introducedIS && !introducedClan)
                    || (!showExtinct()
                    && (tech.extinct(getYear())))) {
                return false;
            } else if (eraBasedProgression()) {
                // If using tech progression with mixed tech, we pass if either IS or Clan meets the required level
                return tech.simpleLevel(getYear(), true, faction).compareTo(getTechLevel()) <= 0
                        || tech.simpleLevel(getYear(), false, faction).compareTo(getTechLevel()) <= 0;
            }
        } else {
            if (!tech.techBase().equals(TechBase.ALL)
                    && clanTech != tech.clanTech()) {
                return false;
            } else if (clanTech && (!introducedClan || (!showExtinct() && extinctClan))) {
                return false;
            } else if (!clanTech && (!introducedIS || (!showExtinct() && extinctIS))) {
                return false;
            } else if (eraBasedProgression()) {
                return tech.simpleLevel(getYear(), clanTech, faction).compareTo(getTechLevel()) <= 0;
            }
        }
        // It's available in the year and we're not using tech progression, so just check the tech level.
        return tech.staticTechLevel().compareTo(getTechLevel()) <= 0;
    }

}
