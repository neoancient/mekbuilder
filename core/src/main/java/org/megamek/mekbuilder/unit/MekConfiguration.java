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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.Nullable;
import org.megamek.mekbuilder.tech.ConstructionOption;
import org.megamek.mekbuilder.tech.UnitConstructionOption;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Categorizes variations of BattleMechs and IndustrialMechs according to base type and subtype.
 * Each configuration designates the base type and specific subtypes, and the limb configuration
 * for that variation. The {@link ConstructionOption}(s) available for the configuration should
 * either be unique, or a series of weight classes sorted from lightest to heaviest.
 */
public class MekConfiguration {

    private static final ConfigurationList configList = new ConfigurationList();

    /**
     * Fetches all available configurations for the unit type
     *
     * @param unitType Either {@link UnitType::BATTLE_MEK} or {@link UnitType::INDUSTRIAL_MEK}
     * @return         The configurations for the Mek type, or an empty list for any other unit type.
     */
    public static List<MekConfiguration> getConfigurations(UnitType unitType) {
        return configList.getConfigurations(unitType);
    }

    /**
     * Fetches all available configurations for the unit type matching a base type
     *
     * @param unitType Either {@link UnitType::BATTLE_MEK} or {@link UnitType::INDUSTRIAL_MEK}
     * @param baseType The base unit type
     * @return         All subtypes matching the unit type and base type.
     */
    public static List<MekConfiguration> getConfigurations(UnitType unitType, BaseType baseType) {
        return getConfigurations(unitType).stream().filter(c -> c.baseType == baseType)
                .collect(Collectors.toList());
    }

    /**
     * Fetches the configuration belonging to a specific unit type and subtype. There should not be
     * more than one configuration for a given pair of unit type and subtype. If there is, the first
     * one found is returned. Returns {@code null} if none is found (such as trying to fetch an
     * industrial LAM).
     *
     * @param unitType Either {@link UnitType::BATTLE_MEK} or {@link UnitType::INDUSTRIAL_MEK}
     * @param subType  The specific configuration subtype
     * @return         The configuration data for the requested subtype.
     */
    public static @Nullable
    MekConfiguration getConfigurations(UnitType unitType, SubType subType) {
        return getConfigurations(unitType).stream().filter(c -> c.subType == subType).findFirst().orElse(null);
    }

    /**
     * General categories of battlemeks and industrialmeks
     */
    public enum BaseType {
        STANDARD, PRIMITIVE, LAM, QUADVEE
    }

    /**
     * Specific types of meks. Each of these belongs to one base type and has specific construction rules.
     */
    public enum SubType {
        STANDARD_BIPED, STANDARD_QUAD, STANDARD_TRIPOD,
        PRIMITIVE_BIPED, PRIMITIVE_QUAD,
        LAM_BIMODAL, LAM_STANDARD,
        QUADVEE_TRACKED, QUADVEE_WHEELED
    }

    private final BaseType baseType;
    private final SubType subType;
    private final MekBuild.LimbConfiguration limbConfiguration;
    @JsonSerialize(contentUsing=ConstructionOption.Serializer.class)
    @JsonDeserialize(contentUsing=ConstructionOption.Deserializer.class)
    private final List<UnitConstructionOption> constructionOptions;

    @JsonCreator
    @SuppressWarnings("unused")
    MekConfiguration() {
        this(BaseType.STANDARD, SubType.STANDARD_BIPED, MekBuild.LimbConfiguration.BIPED, new ArrayList<>());
    }

    public MekConfiguration(BaseType baseType,
                            SubType subType,
                            MekBuild.LimbConfiguration limbConfiguration,
                            List<UnitConstructionOption> constructionOptions) {
        this.baseType = baseType;
        this.subType = subType;
        this.limbConfiguration = limbConfiguration;
        this.constructionOptions = Collections.unmodifiableList(new ArrayList<>(constructionOptions));
    }

    /**
     * @return The basic category of Mek
     */
    public BaseType getBaseType() {
        return baseType;
    }

    /**
     * @return A specific type of Mek configuration
     */
    public SubType getSubType() {
        return subType;
    }

    /**
     * @return The limb configuration for the configuration.
     */
    public MekBuild.LimbConfiguration getLimbConfiguration() {
        return limbConfiguration;
    }

    /**
     * @return The {@link UnitConstructionOption}(s) available for the configuration. If there is more than
     * one, they should be a series of weight categories (e.g. ultra-light, standard, superheavy) sorted
     * from lightest to heaviest.
     */
    public List<UnitConstructionOption> getConstructionOptions() {
        return constructionOptions;
    }

    private static class ConfigurationList {
        private final List<MekConfiguration> mekList = new ArrayList<>();
        private final List<MekConfiguration> iMekList = new ArrayList<>();

        List<MekConfiguration> getConfigurations(UnitType unitType) {
            if (unitType == UnitType.BATTLE_MEK) {
                return mekList;
            } else if (unitType == UnitType.INDUSTRIAL_MEK) {
                return iMekList;
            } else {
                return Collections.emptyList();
            }
        }

        ConfigurationList() {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP));
            mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
            try {
                mekList.addAll(readList(mapper, "mekConfigurations.json"));
                iMekList.addAll(readList(mapper, "imekConfigurations.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private List<MekConfiguration> readList(ObjectMapper mapper, String resourceName) throws IOException {
            final InputStream is = MekConfiguration.class.getResourceAsStream(resourceName);
            if (null != is) {
                return mapper.readValue(is, new TypeReference<List<MekConfiguration>>(){});
            }
            return Collections.emptyList();
        }
    }
}
