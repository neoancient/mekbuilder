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
package org.megamek.mekbuilder.tech;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import megamek.common.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Primary implementation of ITechProgression. This is a reworking of {@link megamek.common.TechAdvancement TechAdvancement}
 * to be immutable and use enums instead of int constants.
 */
@JsonSerialize(using = TechProgression.Serializer.class)
@JsonDeserialize(using = TechProgression.Deserializer.class)
public class TechProgression implements ITechProgression {

    /** If a date is indicated to be approximate, this is the number of years before the date that it becomes
     * available (or after the extinction date that it becomes unavailable.
     */
    private static final int APPROXIMATE_LENGTH = 5;

    private final TechBase techBase;
    private final Map<TechStage, Integer> isProgression = new EnumMap<>(TechStage.class);
    private final Map<TechStage, Integer> clanProgression = new EnumMap<>(TechStage.class);
    private final Set<TechStage> isApproximate = EnumSet.noneOf(TechStage.class);
    private final Set<TechStage> clanApproximate = EnumSet.noneOf(TechStage.class);
    private final Map<TechStage, Set<Faction>> factions = new EnumMap<>(TechStage.class);
    private final TechLevel staticLevel;
    private final Rating rating;
    private final Rating[] availability = new Rating[NUM_ERAS];

    TechProgression(TechBase techBase, Rating rating, Rating[] availability,
                           Map<TechStage, Integer> isProgression,
                           Map<TechStage, Integer> clanProgression,
                           Set<TechStage> isApproximate,
                           Set<TechStage> clanApproximate,
                           Map<TechStage, Set<Faction>> factions,
                           TechLevel staticLevel) {
        this.techBase = techBase;
        this.rating = rating;
        System.arraycopy(availability, 0, this.availability, 0, NUM_ERAS);
        this.isProgression.putAll(isProgression);
        this.clanProgression.putAll(clanProgression);
        this.isApproximate.addAll(isApproximate);
        this.clanApproximate.addAll(clanApproximate);
        for (Map.Entry<TechStage, Set<Faction>> entry : factions.entrySet()) {
            Set<Faction> set = EnumSet.noneOf(Faction.class);
            set.addAll(entry.getValue());
            this.factions.put(entry.getKey(), set);
        }
        this.staticLevel = staticLevel;
    }

    /**
     * Default constructor that fills in the least restrictive values.
     */
    public TechProgression() {
        this.techBase = TechBase.ALL;
        this.rating = Rating.RATING_A;
        Arrays.fill(this.availability, Rating.RATING_A);
        for (TechStage stage : TechStage.values()) {
            isProgression.put(stage, DATE_PS);
            clanProgression.put(stage, DATE_PS);
            factions.put(stage, EnumSet.noneOf(Faction.class));
        }
        this.staticLevel = TechLevel.STANDARD;
    }

    static class Parser {
        private static final Pattern regex = Pattern.compile("(~?)(\\d+)(\\(.*\\))?");

        private final String string;

        Parser(String str) {
            this.string = str;
        }

        TechProgression parse() {
            String[] fields = string.split("\\|");
            TechBase base = TechBase.valueOf(fields[0]);
            Rating rating = Rating.valueOf("RATING_" + fields[1]);
            Rating[] availability = new Rating[4];
            for (int i = 0; i < NUM_ERAS; i++) {
                availability[i] = Rating.valueOf("RATING_" + fields[2].substring(i, i + 1));
            }
            Map<TechStage, Integer> isProg = new EnumMap<>(TechStage.class);
            Map<TechStage, Integer> clanProg = new EnumMap<>(TechStage.class);
            Set<TechStage> isApprox = EnumSet.noneOf(TechStage.class);
            Set<TechStage> clanApprox = EnumSet.noneOf(TechStage.class);
            Map<TechStage, Set<Faction>> factions = new EnumMap<>(TechStage.class);
            parseProgression(fields[3], isProg, isApprox, factions);
            parseProgression(fields[4], clanProg, clanApprox, factions);
            TechLevel staticTL = (fields.length > 5) ? TechLevel.valueOf(fields[5]) : TechLevel.UNOFFICIAL;

            return new TechProgression(base, rating, availability,
                    isProg, clanProg, isApprox, clanApprox,
                    factions, staticTL);
        }

        private void parseProgression(String str, Map<TechStage, Integer> prog,
                                             Set<TechStage> approx, Map<TechStage, Set<Faction>> factionMap) {
            final String[] fields = str.split(",");
            for (int i = 0; i < fields.length; i++) {
                TechStage stage = TechStage.values()[i];
                factionMap.putIfAbsent(stage, EnumSet.noneOf(Faction.class));
                Matcher matcher = regex.matcher(fields[i]);
                if (matcher.matches()) {
                    if (!matcher.group(1).isEmpty()) {
                        approx.add(stage);
                    }
                    prog.put(stage, Integer.parseInt(matcher.group(2)));
                    if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                        String[] fnames = matcher.group(3).replaceAll("[()]", "").split("/");
                        for (String name : fnames) {
                            factionMap.get(stage).add(Faction.valueOf(name));
                        }
                    }
                } else {
                    prog.put(stage, null);
                }
            }
        }
    }

    @Override
    public boolean clanTech() {
        return techBase == TechBase.CLAN;
    }

    @Override
    public boolean mixedTech() {
        return techBase == TechBase.ALL;
    }

    @Override
    public TechBase techBase() {
        return techBase;
    }

    @Override
    public Rating techRating() {
        return rating;
    }

    @Override
    public Rating baseAvailability(int era) {
        return availability[era];
    }

    @Override
    public TechLevel staticTechLevel() {
        return staticLevel;
    }

    private boolean setContainsFactionGroup(Set<Faction> fSet, Faction faction) {
        if (faction.isClan()) {
            return fSet.contains(Faction.CLAN);
        } else if (faction.isPeriphery()) {
            return fSet.contains(Faction.PER);
        } else {
            return fSet.contains(Faction.IS);
        }
    }

    /**
     * When a prototype, production, or reintroduction date is attributed to one or more factions,
     * other factions eventually get the tech after the elapse of several years.
     *
     * @param techStage The tech development stage
     * @param faction   The faction attempting to access the tech
     * @return          Whether the faction is delayed at the given stage
     */
    public boolean factionDelay(TechStage techStage, @Nullable Faction faction) {
        if (null == faction) {
            return false;
        }
        Set<Faction> fList = factions.get(techStage);
        return (fList != null)
                && !fList.isEmpty()
                && !fList.contains(faction)
                && !setContainsFactionGroup(fList, faction);
    }

    @Override
    public @Nullable Integer getDate(TechStage techStage, boolean clan) {
        Integer date = clan ? clanProgression.get(techStage) : isProgression.get(techStage);
        if (null == date) {
            return null;
        }
        boolean approximate = clan ? clanApproximate.contains(techStage) : isApproximate.contains(techStage);
        if (approximate) {
            return date + (techStage == TechStage.EXTINCTION ? APPROXIMATE_LENGTH : -APPROXIMATE_LENGTH);
        } else {
            return date;
        }
    }

    @Override
    public @Nullable Integer getDate(TechStage techStage, boolean clan, @Nullable Faction faction) {
        switch (techStage) {
            // All except the common date require consideration of faction variations, and each
            // stage handles it differently.
            case PROTOTYPE : return getPrototypeDate(clan, faction);
            case PRODUCTION: return getProductionDate(clan, faction);
            case COMMON: return getDate(techStage, clan);
            case EXTINCTION: return getExtinctionDate(clan, faction);
            case REINTRODUCTION: return getReintroductionDate(clan, faction);
            default: return null;
        }
    }

    private @Nullable Integer getPrototypeDate(boolean clan, @Nullable Faction faction) {
        Integer baseDate = getDate(TechStage.PROTOTYPE, clan);
        if (null == baseDate) {
            return null;
        }
        if (null == faction) {
            return baseDate;
        }
        Set<Faction> fSet = factions.get(TechStage.PROTOTYPE);
        if (null == fSet
                || fSet.isEmpty()
                || fSet.contains(faction)
                || setContainsFactionGroup(fSet, faction)) {
            return baseDate;
        }
        // Per IO p. 34, tech with only a prototype date becomes available to
        // other factions after 3d6+5 years if it hasn't gone extinct by then.
        // Using the minimum value here.
        if (getDate(TechStage.PRODUCTION, clan) == null
                && getDate(TechStage.COMMON, clan) == null
                && !extinct(baseDate + 8, clan)) {
            return baseDate + 8;
        } else {
            return null;
        }
    }

    private @Nullable Integer getProductionDate(boolean clan, @Nullable Faction faction) {
        Integer baseDate = getDate(TechStage.PRODUCTION, clan);
        if (null == baseDate) {
            return null;
        }
        if (null == faction) {
            return baseDate;
        }
        Set<Faction> fSet = factions.get(TechStage.PROTOTYPE);
        if (null == fSet
                || fSet.isEmpty()
                || fSet.contains(faction)
                || setContainsFactionGroup(fSet, faction)) {
            return baseDate;
        }
        // Per IO p. 34, tech with no common date becomes available to
        // other factions after 10 years if it hasn't gone extinct by then.
        if (getDate(TechStage.COMMON, clan) == null
                && !extinct(baseDate + 10, clan)) {
            return baseDate + 10;
        } else {
            return null;
        }
    }

    private @Nullable Integer getReintroductionDate(boolean clan, @Nullable Faction faction) {
        Integer baseDate = getDate(TechStage.REINTRODUCTION, clan);
        if (null == baseDate) {
            return null;
        }
        if (null == faction) {
            return baseDate;
        }
        Set<Faction> fSet = factions.get(TechStage.PROTOTYPE);
        if (null == fSet
                || fSet.isEmpty()
                || fSet.contains(faction)
                || setContainsFactionGroup(fSet, faction)) {
            return baseDate;
        }
        // If the production or common date is later than the reintroduction date, that is
        // when it becomes available to other factions. Otherwise we use reintro + 10 as with
        // production date.
        if (!fSet.isEmpty()) {
            Integer production = getProductionDate(clan, faction);
            Integer common = getDate(TechStage.COMMON, clan);
            if (production != null && production > baseDate) {
                return production;
            } else if (common != null && common > baseDate) {
                return common;
            } else {
                return baseDate + 10;
            }

        }
        return baseDate;
    }

    private @Nullable Integer getExtinctionDate(boolean clan, @Nullable Faction faction) {
        Integer baseDate = getDate(TechStage.EXTINCTION, clan);
        if (null == baseDate) {
            return null;
        }
        if (null == faction) {
            return baseDate;
        }
        // Need to check whether there are extinction factions. If so and the faction is not among them,
        // return null
        Set<Faction> fSet = factions.get(TechStage.PROTOTYPE);
        if (fSet == null
                || fSet.isEmpty() || fSet.contains(faction)
                || setContainsFactionGroup(fSet, faction)) {
            return null;
        } else {
            return baseDate;
        }
    }

    private String formatProg(Map<TechStage,Integer> prog, Set<TechStage> approx, boolean clan) {
        StringJoiner sj = new StringJoiner(",");
        for (TechStage stage : TechStage.values()) {
            StringBuilder sb = new StringBuilder();
            if (approx.contains(stage)) {
                sb.append("~");
            }
            sb.append(prog.get(stage) == null ? "-" : prog.get(stage));
            Set<Faction> fList = factions.get(stage);
            String str = fList.stream().filter(f -> f.isClan() == clan)
                    .map(Faction::toString).collect(Collectors.joining("/"));
            if (!str.isEmpty()) {
                sb.append("(");
                sb.append(str);
                sb.append(")");
            }
            sj.add(sb.toString());
        }
        return sj.toString();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|");
        sj.add(techBase().toString());
        sj.add(techRating().toString().replace("RATING_", ""));
        sj.add(Arrays.stream(availability).map(a -> a.toString().replace("RATING_", ""))
            .collect(Collectors.joining("")));
        sj.add(formatProg(isProgression, isApproximate, false));
        sj.add(formatProg(clanProgression, clanApproximate, true));
        sj.add(staticLevel.toString());

        return sj.toString();
    }

    public static class Serializer extends StdSerializer<TechProgression> {
        @JsonCreator
        private Serializer() {
            super(TechProgression.class);
        }

        @Override
        public void serialize(TechProgression value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends StdDeserializer<TechProgression> {
        @JsonCreator
        private Deserializer() {
            super(TechProgression.class);
        }

        @Override
        public TechProgression deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
            return new Parser(parser.getText()).parse();
        }
    }

}
