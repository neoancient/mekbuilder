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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.istack.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Keys that provide fixed values for accessing construction options
 */
public enum ConstructionOptionKey {
    OMNI (ConstructionOptionKey.TYPE_BASIC),
    OMNI_VEHICLE (ConstructionOptionKey.TYPE_BASIC),
    PATCHWORK_ARMOR (ConstructionOptionKey.TYPE_BASIC),
    MIXED_TECH (ConstructionOptionKey.TYPE_BASIC),

    MEK_PRIMITIVE (ConstructionOptionKey.TYPE_UNIT),
    MEK_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    MEK_BIMODAL_LAM (ConstructionOptionKey.TYPE_UNIT),
    MEK_STANDARD_LAM (ConstructionOptionKey.TYPE_UNIT),
    MEK_QUADVEE (ConstructionOptionKey.TYPE_UNIT),
    MEK_TRIPOD (ConstructionOptionKey.TYPE_UNIT),
    MEK_SUPERHEAVY (ConstructionOptionKey.TYPE_UNIT),
    MEK_SUPERHEAVY_TRIPOD (ConstructionOptionKey.TYPE_UNIT),
    MEK_ULTRALIGHT (ConstructionOptionKey.TYPE_UNIT),

    IMEK_PRIMITIVE (ConstructionOptionKey.TYPE_UNIT),
    IMEK_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    IMEK_SUPERHEAVY (ConstructionOptionKey.TYPE_UNIT),
    IMEK_TRIPOD (ConstructionOptionKey.TYPE_UNIT),
    IMEK_SUPERHEAVY_TRIPOD (ConstructionOptionKey.TYPE_UNIT),

    PROTOMEK_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    PROTOMEK_ULTRAHEAVY (ConstructionOptionKey.TYPE_UNIT),
    PROTOMEK_QUAD (ConstructionOptionKey.TYPE_UNIT),
    PROTOMEK_GLIDER (ConstructionOptionKey.TYPE_UNIT),

    CV_WHEELED (ConstructionOptionKey.TYPE_VEHICLE),
    CV_TRACKED (ConstructionOptionKey.TYPE_VEHICLE),
    CV_HOVER (ConstructionOptionKey.TYPE_VEHICLE),
    CV_VTOL (ConstructionOptionKey.TYPE_VEHICLE),
    CV_NAVAL_DISPLACEMENT (ConstructionOptionKey.TYPE_VEHICLE),
    CV_NAVAL_HYDROFOIL (ConstructionOptionKey.TYPE_VEHICLE),
    CV_NAVAL_SUBMARINE (ConstructionOptionKey.TYPE_VEHICLE),
    CV_WIGE (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_WHEELED (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_TRACKED (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_HOVER (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_VTOL (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_NAVAL_DISPLACEMENT (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_NAVAL_SUBMARINE (ConstructionOptionKey.TYPE_VEHICLE),
    CV_SUPERHEAVY_WIGE (ConstructionOptionKey.TYPE_VEHICLE),

    SV_WHEELED_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_WHEELED_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_WHEELED_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_TRACKED_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_TRACKED_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_TRACKED_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_HOVER_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_HOVER_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_HOVER_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_VTOL_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_VTOL_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_VTOL_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_NAVAL_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_NAVAL_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_NAVAL_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_WIGE_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_WIGE_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_WIGE_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_FIXED_WING_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_FIXED_WING_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_FIXED_WING_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_AIRSHIP_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_AIRSHIP_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_AIRSHIP_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_RAIL_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_RAIL_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_RAIL_L (ConstructionOptionKey.TYPE_VEHICLE),
    SV_SATELLITE_S (ConstructionOptionKey.TYPE_VEHICLE),
    SV_SATELLITE_M (ConstructionOptionKey.TYPE_VEHICLE),
    SV_SATELLITE_L (ConstructionOptionKey.TYPE_VEHICLE),

    BA_EXOSKELETON (ConstructionOptionKey.TYPE_UNIT),
    BA_PAL (ConstructionOptionKey.TYPE_UNIT),
    BA_LIGHT (ConstructionOptionKey.TYPE_UNIT),
    BA_MEDIUM (ConstructionOptionKey.TYPE_UNIT),
    BA_HEAVY (ConstructionOptionKey.TYPE_UNIT),
    BA_ASSAULT (ConstructionOptionKey.TYPE_UNIT),

    ASF_PRIMITIVE (ConstructionOptionKey.TYPE_UNIT),
    ASF_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    CF_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    SC_PRIMITIVE (ConstructionOptionKey.TYPE_UNIT),
    SC_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    DS_PRIMITIVE_SPHEROID (ConstructionOptionKey.TYPE_UNIT),
    DS_PRIMITIVE_AERODYNE (ConstructionOptionKey.TYPE_UNIT),
    DS_STANDARD_SPHEROID (ConstructionOptionKey.TYPE_UNIT),
    DS_STANDARD_AERODYNE (ConstructionOptionKey.TYPE_UNIT),
    JS_PRIMITIVE (ConstructionOptionKey.TYPE_UNIT),
    JS_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    WARSHIP (ConstructionOptionKey.TYPE_UNIT),
    WARSHIP_SUBCOMPACT (ConstructionOptionKey.TYPE_UNIT),
    SPACE_STATION_STANDARD (ConstructionOptionKey.TYPE_UNIT),
    SPACE_STATION_MODULAR (ConstructionOptionKey.TYPE_UNIT);

    static final int TYPE_BASIC = 0;
    static final int TYPE_UNIT = 1;
    static final int TYPE_VEHICLE = 2;

    final int type;
    private OptionMap optionMap;

    ConstructionOptionKey(int type) {
        this.type = type;
    }

    public @Nullable ConstructionOption get() {
        if (null == optionMap) {
            optionMap = new OptionMap();
        }
        return optionMap.get(this);
    }

    private static class OptionMap {
        @JsonDeserialize(using = OptionMapDeserializer.class)
        private final Map<ConstructionOptionKey, ConstructionOption> optionMap = new HashMap<>();

        @Nullable
        ConstructionOption get(ConstructionOptionKey key) {
            return optionMap.get(key);
        }

        OptionMap() {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP));
            mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Map.class, new OptionMapDeserializer());
            mapper.registerModule(module);
            final InputStream is = ConstructionOption.class.getResourceAsStream("construction_options.json");
            if (null != is) {
                try {
                    optionMap.putAll(mapper.readValue(is,
                            new TypeReference<Map<ConstructionOptionKey, ConstructionOption>>(){}));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static class OptionMapDeserializer extends StdDeserializer<Map<ConstructionOptionKey, ConstructionOption>> {
            @JsonCreator
            private OptionMapDeserializer() {
                super(Map.class);
            }

            @Override
            public Map<ConstructionOptionKey, ConstructionOption> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
                final Map<ConstructionOptionKey, ConstructionOption> map = new HashMap<>();
                @SuppressWarnings("unchecked")
                final TypeReference<? extends ConstructionOption>[] typerefs = new TypeReference[]{
                        new TypeReference<ConstructionOption>(){},
                        new TypeReference<UnitConstructionOption>(){},
                        new TypeReference<VehicleConstructionOption>(){}
                };
                ObjectMapper mapper = (ObjectMapper) parser.getCodec();
                ObjectNode objNode = mapper.readTree(parser);
                for (Iterator<Map.Entry<String, JsonNode>> iter = objNode.fields(); iter.hasNext();) {
                    final Map.Entry<String, JsonNode> entry = iter.next();
                    ConstructionOptionKey key = ConstructionOptionKey.valueOf(entry.getKey());
                    ConstructionOption val = mapper.readValue(entry.getValue().toString(), typerefs[key.type]);
                    map.put(key, val);
                }
                return map;
            }
        }
    }
}
