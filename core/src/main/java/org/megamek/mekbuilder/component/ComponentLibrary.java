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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

/**
 * Singleton class responsible for loading all components and providing a lookup service.
 */
public class ComponentLibrary {
    private static ComponentLibrary instance = null;

    private final static String DATA_DIR = "data/components";

    private final Map<String, Component> allComponents = new HashMap<>();

    private ComponentLibrary() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP));
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        for (ComponentType type : ComponentType.values()) {
            final InputStream is = getClass().getResourceAsStream(type.name().toLowerCase() + ".json");
            if (null != is) {
                try {
                    TypeReference<?> tr;
                    switch (type) {
                        case AMMUNITION:
                            tr = new TypeReference<List<Ammunition>>(){};
                            break;
                        case ARMOR:
                            tr = new TypeReference<List<Armor>>(){};
                            break;
                        case COCKPIT:
                            tr = new TypeReference<List<Cockpit>>(){};
                            break;
                        case HEAT_SINK:
                            tr = new TypeReference<List<HeatSink>>(){};
                            break;
                        case HEAVY_WEAPON:
                        case CAPITAL_WEAPON:
                            tr = new TypeReference<List<HeavyWeapon>>(){};
                            break;
                        case INF_ARMOR:
                            tr = new TypeReference<List<InfantryArmorKit>>(){};
                            break;
                        case INF_WEAPON:
                            tr = new TypeReference<List<InfantryWeapon>>(){};
                            break;
                        case MOVE_ENHANCEMENT:
                            tr = new TypeReference<List<MoveEnhancement>>(){};
                            break;
                        case ENGINE:
                            tr = new TypeReference<List<MVFEngine>>(){};
                            break;
                        case PHYSICAL_WEAPON:
                            tr = new TypeReference<List<PhysicalWeapon>>(){};
                            break;
                        case SECONDARY_MOTIVE_SYSTEM:
                            tr = new TypeReference<List<SecondaryMotiveSystem>>(){};
                            break;
                        default:
                            tr = new TypeReference<List<Component>>(){};
                            break;
                    }
                    List<? extends Component> list = mapper.readValue(is, tr);
                    list.forEach(c -> allComponents.put(c.getInternalName(), c));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ComponentLibrary getInstance() {
        if (null == instance) {
            instance = new ComponentLibrary();
        }
        return instance;
    }

    public Component getComponent(String key) {
        return allComponents.get(key);
    }

    public Collection<Component> getAllComponents() {
        return Collections.unmodifiableCollection(allComponents.values());
    }

    protected void writeComponents() {
        writeComponents(allComponents.values(), DATA_DIR);
    }

    protected void writeComponents(Collection<Component> components, String dir) {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<ComponentType, List<String>> json = new HashMap<>();
        for (Component c : components) {
            json.putIfAbsent(c.getType(), new ArrayList<>());
            try {
                json.get(c.getType()).add(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        for (ComponentType type : json.keySet()) {
            final File f = new File(dir, type.toString().toLowerCase() + ".json");
            OutputStream os;
            try {
                os = new FileOutputStream(f);
                final PrintWriter pw = new PrintWriter(os);
                pw.println(json.get(type));
                pw.flush();
                pw.close();
                os.flush();
                os.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
