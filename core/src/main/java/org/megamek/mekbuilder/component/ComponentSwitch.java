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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.megamek.mekbuilder.unit.MotiveType;
import org.megamek.mekbuilder.unit.UnitWeightClass;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class DataParsers {
    static final BiFunction<String, String, Object> intParser = (val, sep) -> Integer.valueOf(val);
    static final BiFunction<String, String, Object> booleanParser = (val, sep) -> Boolean.valueOf(val);
    static final BiFunction<String, String, Object> doubleArrayParser = (val, sep) -> Arrays.stream(val.split(sep))
            .filter(s -> !s.isEmpty())
            .map(Double::valueOf).collect(Collectors.toList());
}

/**
 * A set of switches for miscellaneous information about equipment. These are similar to the BigInteger
 * flags used by MegaMek, but can be mapped to data to provide more information.
 */
public enum ComponentSwitch {
    /** Can be split among various locations */
    SPREADABLE,
    /** Explodes when hit, like most ammo */
    EXPLOSIVE,
    /** Can be hit by critical (specific to Meks) */
    HITTABLE,
    /** Can use a modular mount, such as omni pod or BA modular equipment */
    MODULE,
    /** The number of this component allowed in a single location (e.g. CASE or harjel) */
    LOCATION_EXCLUSIVE (1, DataParsers.intParser),
    /** The number of this component allowed on the same unit. */
    UNIT_EXCLUSIVE (1, DataParsers.intParser),
    /** Components that have a variable size,
     * such as cargo bays or mechanical jump boosters. First value is the step size.
     * If a second or third value is provided they are the minimum and maximum size values, respectively. */
    VARIABLE (1.0, DataParsers.doubleArrayParser),
    /** Can be linked to another component that has one of the given switches (Artemis, PPC Capacitor, etc) */
    LINKABLE (null, (String val, String sep) -> Arrays.stream(val.split(sep))
            .filter(s -> !s.isEmpty())
            .map(l -> ComponentSwitch.valueOf(l.toUpperCase())).collect(Collectors.toSet())),

    // Flags specific to construction options. If true, can only be used on the particular unit type.
    // if false, cannot be used on the particular unit type.

    /** If true, can only be used on primitive and retrotech units; if false, cannot be used on primitives. */
    PRIMITIVE (null, DataParsers.booleanParser),
    /** If true, can only be used on superheavy mechs; if false, cannot be used on superheavies. */
    SUPERHEAVY_MEK (null, DataParsers.booleanParser),
    /** If true, can only be used on tripod mechs; if false, cannot be used on tripods */
    TRIPOD (null, DataParsers.booleanParser),
    /** If true, can only be used on quad mechs; if false, cannot be used on quads. */
    QUAD (null, DataParsers.booleanParser),
    /** If true, can only be used on QuadVees; if false, cannot be used on QuadVees. */
    QUADVEE (null, DataParsers.booleanParser),
    /** If true, can only be used on LandAirMechs; if false, cannot be used on LAMs. */
    LAM (null, DataParsers.booleanParser),

    /** Can only be installed on vehicles with a motive type contained in the set. */
    VEE_MOTIVE (EnumSet.allOf(MotiveType.class),
            (String val, String sep) -> Arrays.stream(val.split(sep))
                    .filter(s -> !s.isEmpty())
                    .map(MotiveType::valueOf).collect(Collectors.toSet())),
    /** Can only be installed on unit with a weight class in the set. */
    WEIGHT_CLASS (EnumSet.allOf(UnitWeightClass.class),
            (String val, String sep) -> Arrays.stream(val.split(sep))
                    .filter(s -> !s.isEmpty())
                    .map(UnitWeightClass::valueOf).collect(Collectors.toSet())),

    /** For physical weapons or industrial equipment, actuators that must be present or removed to mount
     * (true for required, false for must be removed). For other weapons, actuators that must be removed
     * when mounting in an arm on an OmniMech. If true, implies lower arm actuator. */
    HAND_ACTUATOR (null, DataParsers.booleanParser),
     /** For physical weapons or industrial equipment, actuators that must be present or removed to mount
     * (true for required, false for must be removed). For other weapons, actuators that must be removed
     * when mounting in an arm on an OmniMech. If false, implies missing actuator. */
    LOWER_ARM_ACTUATOR (null, DataParsers.booleanParser),

    /** Indicates a fusion engine */
    FUSION,
    /** Engine rating > 400 */
    LARGE_ENGINE,
    /** Normal sized engines that have a large engine version */
    HAS_LARGE_ENGINE,
    /** Any engine that requires fuel (ICE, fuel cell) */
    FUEL_ENGINE,
    /** Adds to movement heat and doubles jump heat. */
    XXL,
    /** Must have one of the named engine types to mount component */
    ENGINE_TYPE (Collections.emptySet(),
            (String val, String sep) -> Arrays.stream(val.split(sep)).collect(Collectors.toSet())),

    // Flags specific to weapons
    /** Capable of indirect fire; used in Alpha Strike calculations */
    INDIRECT_FIRE,
    /** DF weapon that can be linked to a targeting computer */
    TC_LINKABLE,
    /** Enhancement available to some missile systems */
    ARTEMIS,
    /** Enhancement available to MRM systems */
    APOLLO,
    /** Enhancement available to PPCs */
    PPC_CAPACITOR,
    /** Enhancement available to lasers */
    LASER_INSULATOR,
    /** Enhancement available to non-pulse lasers */
    PULSE_MODULE,
    /** Enhancement available to missile launchers */
    ONE_SHOT,
    /** Enhancement requires all eligible components to be linked if any are (Artemis, Apollo). */
    LINK_ALL,
    /** Machine gun array, value is ammo type of eligible machine guns in same location */
    MGA (null, (String s1, String s2) -> AmmunitionType.valueOf(s1)),
    /** Special facing rules for Mek side torso. */
    VGL,

    /** Defensive bonus supplied by items such as stealth armor. */
    BV_TARGET_BONUS (0, DataParsers.intParser),
    /** Value added to movement heat when calculating BV. */
    MOVEMENT_HEAT (0, DataParsers.intParser),

    // Flags to identify certain types of equipment
    /** Virtual armor component indicating each location can have a different armor type */
    PATCHWORK_ARMOR,
    /** Qualifies for stealth armor requirement */
    ECM,
    /** MASC is incompatible with non-standard myomer */
    MASC,
    /** Triple-Strength Myomer; provides a boost to top speed */
    TSM,
    /** Enables running at walk x 2 (MASC, supercharger, etc) */
    SPEED_BOOST,
    /** Mechanical jump boosters */
    MECH_JUMP,
    /** Confers both jump and heat dissipation bonus */
    PARTIAL_WING,
    /** Standard C3 system, incompatible with C3i */
    C3,
    /** C3i system, incompatible with standard */
    C3I,
    /** Reduces gunner crew needs */
    BASIC_FIRECON,
    /** Advanced fire control; needed by IM and SV for some equipment. */
    ADV_FIRECON,
    /** Cannot mount both coolant pod and radical heat sink system. */
    COOLANT_POD,
    /** Reduced movement */
    SHIELD,
    /** Can only mount one component with this flag in a location. */
    CONSTRUCTION,
    /** Available as an enhancement for cargo, must have direction indicated. */
    DUMPER,
    /** Incompatible with IM ejection seat. */
    COCKPIT_COMMAND,
    /** Incompatible with cockpit command console. */
    TORSO_COCKPIT,
    /** Required by some components, possibly a particular type */
    CARGO (Collections.emptySet(),
            (String val, String sep) -> Arrays.stream(val.split(sep)).collect(Collectors.toSet())),
    /** Incompatible with harjel_iii and certain armor type */
    HARJEL_II,
    /** Incompatible with harjel_ii and certain armor type */
    HARJET_III,
    /** Null-signature system, incompatible with C3, TC, stealth armor, and VSS. */
    NSS,
    /** Requires fusion or fission engine; torso mount restriction removed for superheavy. */
    HGAUSS;

    private final Object defaultVal;
    private final BiFunction<String, String, Object> dataParser;

    ComponentSwitch(Object defaultVal, BiFunction<String, String, Object> dataParser) {
        this.defaultVal = defaultVal;
        this.dataParser = dataParser;
    }

    ComponentSwitch() {
        this(null, (s1, s2) -> s1);
    }

    public static String serializeValue(Object val) {
        if (val == null) {
            return "null";
        } else if (val instanceof Collection<?>) {
            return ((Collection<?>) val).stream().map(Object::toString).collect(Collectors.joining(","));
        } else if (val instanceof Object[]) {
            return Arrays.stream((Object[]) val).map(Object::toString).collect(Collectors.joining(","));
        } else {
            return val.toString();
        }
    }

    public Object deserializeValue(String val) {
        if (null == val || "null".equals(val)) {
            return defaultVal;
        } else {
            return dataParser.apply(val, ",");
        }
    }


    public static class SwitchMapSerializer extends StdSerializer<Map<ComponentSwitch, Object>> {
        SwitchMapSerializer() {
            super(Map.class, true);
        }

        @Override
        public void serialize(Map<ComponentSwitch, Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            for (Map.Entry<ComponentSwitch, Object> e : value.entrySet()) {
                gen.writeObjectField(e.getKey().toString(), ComponentSwitch.serializeValue(e.getValue()));
            }
            gen.writeEndObject();
        }
    }

    public static class SwitchMapDeserializer extends StdDeserializer<Map<ComponentSwitch, Object>> {
        SwitchMapDeserializer() {
            super(Map.class);
        }

        public Map<ComponentSwitch, Object> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
            final Map<ComponentSwitch, Object> map = new EnumMap<>(ComponentSwitch.class);
            final JsonNode node = parser.readValueAsTree();
            node.fields().forEachRemaining(n -> {
                final ComponentSwitch cs = ComponentSwitch.valueOf(n.getKey());
                map.put(cs, cs.deserializeValue(n.getValue().asText()));
            });
            return map;
        }
    }

}
