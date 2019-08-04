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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Class for tracking tech progression of various generic construction options. Base class for unit-specific
 * construction options.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConstructionOption implements ITechDelegator {
    private final ConstructionOptionKey key;
    @JsonSerialize(using = TechProgression.Serializer.class)
    @JsonDeserialize(using = TechProgression.Deserializer.class)
    private final TechProgression techProgression;

    @JsonCreator
    @SuppressWarnings("unused")
    ConstructionOption() {
        this(null, new TechProgression());
    }

    public ConstructionOption(ConstructionOptionKey key, TechProgression techProgression) {
        this.key = key;
        this.techProgression = techProgression;
    }

    public ConstructionOptionKey getKey() {
        return key;
    }

    @Override
    public ITechProgression techDelegate() {
        return techProgression;
    }

    /**
     * Serializes only the key value
     */
    public static class Serializer extends StdSerializer<ConstructionOption> {
        @JsonCreator
        public Serializer() {
            super(ConstructionOption.class);
        }

        @Override
        public void serialize(ConstructionOption value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.key.name());
        }
    }

    /**
     * Reads the key value and fetches the object that's already loaded
     */
    public static class Deserializer extends StdDeserializer<ConstructionOption> {
        @JsonCreator
        public Deserializer() {
            super(ConstructionOption.class);
        }

        @Override
        public ConstructionOption deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
            return ConstructionOptionKey.valueOf(parser.getText()).get();
        }
    }
}
