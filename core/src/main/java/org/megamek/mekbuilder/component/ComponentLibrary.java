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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.*;

/**
 *
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
            final File file = new File(DATA_DIR, type.name().toLowerCase() + ".json");
            if (file.exists()) {
                try {
                    InputStream is = new FileInputStream(file);
                    TypeReference<?> tr;
                    switch (type) {
                        case AMMUNITION:
                            tr = new TypeReference<List<Ammunition>>(){};
                            break;
                        default:
                            tr = new TypeReference<List<Component>>(){};
                            break;
                    }
                    List<? extends Component> list = mapper.readValue(is, tr);
                    list.forEach(c -> allComponents.put(c.getInternalName(), c));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
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
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
