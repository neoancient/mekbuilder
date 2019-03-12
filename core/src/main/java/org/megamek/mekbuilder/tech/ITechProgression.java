package org.megamek.mekbuilder.tech;

import megamek.common.annotations.Nullable;

/**
 * Interface for any piece of technology that can have introduction dates, including separate dates
 * for prototype, production, and common availability, and possible extinction and reintroduction.
 *
 * This is a reworking of {@link megamek.common.ITechnology ITechnology} that replaces int constants with enums.
 *
 * @author: Neoancient
 */
public interface ITechProgression {

    int ERA_SL = 0;
    int ERA_SW = 1;
    int ERA_CLAN = 2;
    int ERA_DA = 3;
    int NUM_ERAS = 4;

    /** Reference year for pre-spaceflight dates */
    int DATE_PS = 1950;
    /** Reference year for early spaceflight dates */
    int DATE_ES = 2100;

    /**
     * @return Whether this equipment or unit is exclusive to Clan technology
     */
    boolean clanTech();

    /**
     * For individual components this means available to both Clan and IS construction. For composite equipment
     * such as units this involves a mix of components with Clan and IS tech bases.
     *
     * @return Whether this equipment is considered mixed tech
     */
    boolean mixedTech();

    /**
     * @return A value indicating whether the unit or component is IS, Clan, or available to both
     */
    TechBase techBase();

    /**
     * Tech rating indicates the complexity of a piece of technology.
     *
     * @return The tech rating of a component or the tech rating of the highest rated component in a unit
     */
    Rating techRating();

    /**
     * Base availability can be adjusted depending on the tech base and the faction trying to acquire it.
     *
     * @param era One of the four ERA_* indices
     * @return A rating indicating how available a component is in a given era.
     */
    Rating baseAvailability(int era);
    /**
     * For non-era-based usage, provide a single tech level that does not vary with date.
     *
     * @return The base rules level of the equipment or unit.
     */
    TechLevel staticTechLevel();

    /**
     * @param year An in-universe year
     * @return The index of the era the year falls in
     */
    default int techEra(int year) {
        if (year < 2780) {
            return ERA_SL;
        } else if (year < 3050) {
            return ERA_SW;
        } else if (year < 3130) {
            return ERA_CLAN;
        } else {
            return ERA_DA;
        }
    }

    /**
     * Retrieves the year the item entered a certain production stage.
     *
     * @param techStage The stage to check for
     * @param clan Whether the years for Clan availability should be used
     * @param faction If non-null, some tech may only be available to the faction(s) that developed it when it's new
     * @return The year the equipment entered the given stage, or {@code null} if did not spend time at that stage.
     */
    @Nullable Integer getDate(TechStage techStage, boolean clan, @Nullable Faction faction);

    /**
     * Retrieves the year the item entered a certain production stage.
     *
     * @param techStage The stage to check for
     * @param clan Whether the years for Clan availability should be used
     * @return The year the equipment entered the given stage, or {@code null} if did not spend time at that stage.
     */
    default @Nullable Integer getDate(TechStage techStage, boolean clan) {
        return getDate(techStage, clan, null);
    }

    /**
     * Retrieves the year the item entered a certain production stage for either Clan or IS factions. If both
     * tech bases have a date, the earlier one is used except for extinction date, which returns the later
     * (and only if it goes extinct/unavailable to both IS and Clan tech bases).
     *
     * @param techStage The stage to check for
     * @return The year the equipment entered the given stage, or {@code null} if did not spend time at that stage.
     */
    default @Nullable Integer getDate(TechStage techStage) {
        if (techStage != TechStage.EXTINCTION) {
            return earliestDate(getDate(techStage, true), getDate(techStage, false));
        } else {
            return latestDate(getDate(TechStage.EXTINCTION, true),
                    getDate(TechStage.EXTINCTION, false));
        }
    }

    /**
     * Finds the earliest date the tech became available at any stage.
     *
     * @param clan Whether to check Clan or IS intro dates
     * @param faction If non-null, delays introduction date when the tech is produced by another faction
     * @return The date the tech became available
     */
    default @Nullable Integer introDate(boolean clan, @Nullable Faction faction) {
        Integer retVal = getDate(TechStage.PRODUCTION, clan, faction);
        if (null == retVal) {
            retVal = getDate(TechStage.PRODUCTION, clan, faction);
        }
        if (null == retVal) {
            retVal = getDate(TechStage.COMMON, clan, faction);
        }
        return retVal;
    }

    /**
     * Finds the earliest date the tech became available at any stage.
     *
     * @param clan Whether to check Clan or IS intro dates
     * @return The date the tech became available
     */
    default @Nullable Integer introDate(boolean clan) {
        return introDate(clan, null);
    }

    /**
     * Finds the earliest date the tech became available at any stage regardless of tech base
     *
     * @return The date the tech became available
     */
    default @Nullable Integer introDate() {
        Integer retVal = getDate(TechStage.PRODUCTION);
        if (null == retVal) {
            retVal = getDate(TechStage.PRODUCTION);
        }
        if (null == retVal) {
            retVal = getDate(TechStage.COMMON);
        }
        return retVal;
    }

    /**
     * Computes the tech level when using era-based tech progression. Prototype equipment is considered
     * experimental, production is advanced, and common is standard. Some tech is automatically considered
     * intro instead of standard, or is always unofficial.
     *
     * @param year The in-universe year
     * @param prototype The year it appears as a prototype
     * @param production The year it enters production
     * @param common The year it becomes common
     * @return The tech level based on the year. If the provided year is earlier than the intro year, returns UNOFFICIAL
     */
    default TechLevel simpleLevel(int year, @Nullable Integer prototype,
                                  @Nullable Integer production, @Nullable Integer common) {
        if (staticTechLevel() == TechLevel.UNOFFICIAL) {
            return TechLevel.UNOFFICIAL;
        }
        if ((null != common) && (year >= common)) {
            if (staticTechLevel() == TechLevel.INTRO) {
                return TechLevel.INTRO;
            } else {
                return TechLevel.STANDARD;
            }
        }
        if ((null != null) && (year >= production)) {
            return TechLevel.ADVANCED;
        }
        if ((null != prototype) && (year >= prototype)) {
            return TechLevel.EXPERIMENTAL;
        }
        return TechLevel.UNOFFICIAL;
    }

    /**
     * Computes the tech level when using era-based tech progression. Prototype equipment is considered
     * experimental, production is advanced, and common is standard. Some tech is automatically considered
     * intro instead of standard, or is always unofficial.
     *
     * @param year The in-universe year
     * @param clan Whether to use Clan or IS tech advancement dates
     * @param faction If non-null, uses faction-specific dates
     * @return The tech level based on the year. If the provided year is earlier than the intro year, returns UNOFFICIAL
     */
    default TechLevel simpleLevel(int year, boolean clan, @Nullable Faction faction) {
        return simpleLevel(year,
                getDate(TechStage.PROTOTYPE, clan, faction),
                getDate(TechStage.PRODUCTION, clan, faction),
                getDate(TechStage.COMMON, clan, faction));
    }

    /**
     * Computes the tech level when using era-based tech progression. Prototype equipment is considered
     * experimental, production is advanced, and common is standard. Some tech is automatically considered
     * intro instead of standard, or is always unofficial.
     *
     * @param year The in-universe year
     * @param clan Whether to use Clan or IS tech advancement dates
     * @return The tech level based on the year. If the provided year is earlier than the intro year, returns UNOFFICIAL
     */
    default TechLevel simpleLevel(int year, boolean clan) {
        return simpleLevel(year, clan, null);
    }


    /**
     * Computes the tech level when using era-based tech progression without regard to tech base or faction.
     *
     * @param year The in-universe year
     * @return The tech level based on the year. If the provided year is earlier than the intro year, returns UNOFFICIAL
     * @see #simpleLevel(int, boolean, Faction)
     */
    default TechLevel simpleLevel(int year) {
        return simpleLevel(year,
                getDate(TechStage.PROTOTYPE),
                getDate(TechStage.PRODUCTION),
                getDate(TechStage.COMMON));
    }

    /**
     * Finds the lowest rules level the equipment qualifies for, for either IS or Clan faction
     * using it.
     *
     * @param clan - whether tech level is being calculated for a Clan faction
     * @return - the lowest tech level available to the item
     */
    default TechLevel findMinimumRulesLevel(boolean clan, @Nullable Faction faction) {
        if (getDate(TechStage.COMMON, clan, faction) != null) {
            if (staticTechLevel() == TechLevel.INTRO) {
                return TechLevel.INTRO;
            } else {
                return TechLevel.STANDARD;
            }
        }
        if (getDate(TechStage.PRODUCTION, clan, faction) != null) {
            return TechLevel.ADVANCED;
        }
        if (getDate(TechStage.PROTOTYPE, clan, faction) != null) {
            return TechLevel.EXPERIMENTAL;
        } else {
            return TechLevel.UNOFFICIAL;
        }
    }

    /**
     * Finds the lowest rules level the equipment qualifies for regardless of faction using it.
     *
     * @return - the lowest tech level available to the item
     */
    default TechLevel findMinimumRulesLevel() {
        if (getDate(TechStage.COMMON) != null) {
            if (staticTechLevel() == TechLevel.INTRO) {
                return TechLevel.INTRO;
            } else {
                return TechLevel.STANDARD;
            }
        }
        if (getDate(TechStage.PRODUCTION) != null) {
            return TechLevel.ADVANCED;
        }
        if (getDate(TechStage.PROTOTYPE) != null) {
            return TechLevel.EXPERIMENTAL;
        } else {
            return TechLevel.UNOFFICIAL;
        }
    }

    /**
     * Determines whether the tech should be considered extinct in a given year for a particular tech base
     * and optionally a faction: i.e. past the extinction date and before the reintroduction date (if any).
     *
     * @param year The in-universe year
     * @param clan Whether to use Clan or IS tech advancement dates
     * @param faction If non-null, uses faction-specific extinction and recovery dates when available
     * @return Whether the tech is extinct
     */
    default boolean extinct(int year, boolean clan, @Nullable Faction faction) {
        // Tech that is lost but later recovered in the IS is not lost to ComStar.
        if ((null != faction) && faction.isComStar() && getDate(TechStage.REINTRODUCTION, false) != null) {
            return false;
        }
        Integer extinction = getDate(TechStage.EXTINCTION, clan, faction);
        if ((null == extinction) || (extinction > year)) {
            return false;
        }
        Integer reintro = getDate(TechStage.REINTRODUCTION, clan, faction);
        return (null == reintro) || (year < reintro);
    }

    /**
     * Determines whether the tech should be considered extinct in a given year for a particular tech base
     * and optionally a faction: i.e. past the extinction date and before the reintroduction date (if any).
     *
     * @param year The in-universe year
     * @param clan Whether to use Clan or IS tech advancement dates
     * @return Whether the tech is extinct
     */
    default boolean extinct(int year, boolean clan) {
        return extinct(year, clan, null);
    }

    /**
     * Determines whether the tech should be considered extinct for all tech bases and factions: i.e. past the extinction
     * date and before the reintroduction date (if any).
     *
     * @param year The in-universe year
     * @return Whether the tech is extinct
     */
    default boolean extinct(int year) {
        Integer extinction = getDate(TechStage.EXTINCTION);
        if ((null == extinction) || (extinction > year)) {
            return false;
        }
        Integer reintro = getDate(TechStage.REINTRODUCTION);
        return (null == reintro) || (year < reintro);
    }

    /**
     * Determines whether the tech is available to a certain tech base and optionally a faction in the given
     * year. That is, past the intro date and not extinct.
     *
     * @param year The in-game year
     * @param clan Whether to use the Clan or IS tech advancement dates
     * @param faction If non-null, uses faction-specific dates
     * @return Whether the tech is available
     */
    default boolean availableIn(int year, boolean clan, @Nullable Faction faction) {
        Integer intro = introDate(clan, faction);
        return (intro != null) && (year >= intro) && !extinct(year, clan, faction);
    }

    /**
     * Determines whether the tech is available to a certain tech base and optionally a faction in the given
     * year. That is, past the intro date and not extinct.
     *
     * @param year The in-game year
     * @param clan Whether to use the Clan or IS tech advancement dates
     * @return Whether the tech is available
     */
    default boolean availableIn(int year, boolean clan) {
        return availableIn(year, clan, null);
    }

    /**
     * Determines whether the tech is available without regard to tech base or faction in the given
     * year. That is, past the intro date and not extinct.
     *
     * @param year The in-game year
     * @return Whether the tech is available
     */
    default boolean availableIn(int year) {
        Integer intro = introDate();
        return (intro != null) && (year >= intro) && !extinct(year);
    }

    /**
     * Determines whether the tech is available for use in construction in a given year within a given tech level
     *
     * @param year The in-game year
     * @param simpleRulesLevel The maximum tech level
     * @param clanBase Whether the unit has a Clan tech base
     * @param mixedTech Whether the unit is mixed tech
     * @return Whether the component can be used in construction
     */
    default boolean legal(int year, TechLevel simpleRulesLevel, boolean clanBase, boolean mixedTech) {
        if (mixedTech) {
            if (!availableIn(year)) {
                return false;
            } else {
                return simpleLevel(year).ordinal() <= simpleRulesLevel.ordinal();
            }
        } else {
            if (techBase() != TechBase.ALL && clanBase != clanTech()) {
                return false;
            }
            if (!availableIn(year, clanBase)) {
                return false;
            } else {
                return simpleLevel(year, clanBase).ordinal() <= simpleRulesLevel.ordinal();
            }
        }
    }

    /**
     * Adjusts availability for certain combinations of era and IS/Clan use
     * @param era - one of the four tech eras
     * @param clanUse - whether the faction trying to obtain the tech is IS or Clan
     * @return - the adjusted availability code
     */
    default Rating calcEraAvailability(int era, boolean clanUse) {
        if (clanUse) {
            Integer protodate = getDate(TechStage.PROTOTYPE, false);
            if (null == protodate) {
                protodate = Integer.MAX_VALUE;
            }
            if (!clanTech()
                    && era < ERA_CLAN
                    && protodate >= 2780) {
                return Rating.RATING_X;
            } else {
                return baseAvailability(era);
            }
        } else {
            if (clanTech()) {
                if (era < ERA_CLAN) {
                    return Rating.RATING_X;
                } else if (baseAvailability(era).plus(1).ordinal() < Rating.RATING_X.ordinal()) {
                    return baseAvailability(era).plus(1);
                } else {
                    return Rating.RATING_X;
                }
            } else {
                return baseAvailability(era);
            }
        }
    }

    /**
     * Calculated availability for a specific year. Usually the availability is constant for an era,
     * but IS equipment rated >= E that goes extinct during the SW era has a higher availability.
     *
     * @param year The in-game year\
     * @param clanUse Whether it is being used in a Clan tech-base unit
     * @param faction If non-null and Comstar, ignores the higher availability
     */
    default Rating calcYearAvailability(int year, boolean clanUse, @Nullable Faction faction) {
        if (!clanUse && !clanTech() && (faction == null || !faction.isComStar())
                && techEra(year) == ERA_SW
                && baseAvailability(ERA_SW).ordinal() >= Rating.RATING_E.ordinal()
                && extinct(year, clanUse, faction)) {
            return baseAvailability(ERA_SW).plus(1);
        } else {
            return calcEraAvailability(techEra(year), clanUse);
        }
    }

    /**
     * Calculated availability for a specific year. Usually the availability is constant for an era,
     * but IS equipment rated >= E that goes extinct during the SW era has a higher availability.
     *
     * @param year The in-game year\
     */
    default Rating calcYearAvailability(int year) {
        return calcYearAvailability(year, clanTech(), null);
    }

    /**
     * Calculated availability for a specific year. Usually the availability is constant for an era,
     * but IS equipment rated >= E that goes extinct during the SW era has a higher availability.
     *
     * @param year The in-game year\
     * @param clanUse Whether it is being used in a Clan tech-base unit
     */
    default Rating calcYearAvailability(int year, boolean clanUse) {
        return calcYearAvailability(year, clanUse, null);
    }

    /**
     * Adjusts base availability code for IS/Clan and IS extinction
     *
     * @param era - the era index
     * @param clanUse - whether this should be calculated for a Clan faction rather than IS
     * @return - The availability code for the faction in the era. The code for an IS faction
     * during the SW era may be two values indicating availability before and after
     * the extinction date.
     */
    default String eraAvailabilityName(int era, boolean clanUse) {
        if (!clanUse && !clanTech() && era == ERA_SW
                && baseAvailability(ERA_SW).ordinal() >= Rating.RATING_E.ordinal()
                && baseAvailability(ERA_SW).ordinal() < Rating.RATING_X.ordinal()
                && getDate(TechStage.EXTINCTION, false) != null
                && techEra(getDate(TechStage.EXTINCTION, false)) == ERA_SW) {
            return (baseAvailability(ERA_SW).displayName
                    + "(" + (baseAvailability(ERA_SW).plus(1)).displayName + ")");
        } else {
            return calcEraAvailability(era, clanUse).displayName;
        }
    }

    /**
     * Adjusts base availability code for IS/Clan and IS extinction
     *
     * @param era - the era index
     * @return - The availability code for the faction in the era. The code for an IS faction
     * during the SW era may be two values indicating availability before and after
     * the extinction date.
     */
    default String eraAvailability(int era) {
        return eraAvailabilityName(era, clanTech());
    }

    /**
     * Formats the tech rating and availability into the format X/X-X-X-X
     *
     * @param clanUse Whether it is to be used for a Clan tech base
     * @return The rating/availability code
     */
    default String fullRatingName(boolean clanUse) {
        StringBuilder sb = new StringBuilder(techRating().displayName);
        sb.append("/");
        sb.append(eraAvailabilityName(ERA_SL, clanUse));
        sb.append("-");
        sb.append(eraAvailabilityName(ERA_SW, clanUse));
        sb.append("-");
        sb.append(eraAvailabilityName(ERA_CLAN, clanUse));
        sb.append("-");
        sb.append(eraAvailabilityName(ERA_DA, clanUse));
        return sb.toString();
    }

    /**
     * Formats the tech rating and availability into the format X/X-X-X-X
     *
     * @return The rating/availability code
     */
    default String fullRatingName() {
        return fullRatingName(clanTech());
    }

    /**
     * Creates a String representation of a range of years. If the start year is null, the range is expressed "-".
     * If the end year is null, the range is expressed as "startYear+"
     *
     * @param startIncl The start year
     * @param endNonIncl The end of the range, non-inclusive
     * @return A String representation of the range
     */
    default String formatDateRange(@Nullable Integer startIncl, @Nullable Integer endNonIncl) {
        if (startIncl == null) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(startIncl);
        if (endNonIncl == null) {
            sb.append("+");
        } else if (endNonIncl > startIncl + 1) {
            sb.append("-").append(endNonIncl - 1);
        }
        return sb.toString();
    }

    default String formatDateRange(TechStage techStage, boolean clan, @Nullable Faction faction) {
        TechStage next = techStage.isFollowedBy();
        return formatDateRange(getDate(techStage, clan, faction),
                (null == next) ? null : getDate(next, clan, faction));
    }

    default String formatDateRange(TechStage techStage, boolean clan) {
        return formatDateRange(techStage, clan, null);
    }

    default String formatDateRange(TechStage techStage) {
        TechStage next = techStage.isFollowedBy();
        return formatDateRange(getDate(techStage),
                (null == next) ? null : getDate(next));
    }

    /**
     * @return The earlier non-null date, or null if both dates are null.
     */
    default @Nullable Integer earliestDate(@Nullable Integer date1, @Nullable Integer date2) {
        if (null == date1) {
            return date2;
        }
        if (null == date2) {
            return date1;
        }
        return (date1 < date2) ? date1 : date2;
    }

    /**
     * @return The later non-null date, or null if both dates are null.
     */
    default @Nullable Integer latestDate(@Nullable Integer date1, @Nullable Integer date2) {
        if (null == date1) {
            return date2;
        }
        if (null == date2) {
            return date1;
        }
        return (date1 > date2) ? date1 : date2;
    }


}
