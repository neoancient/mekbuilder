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
package org.megamek.mekbuilder.javafx.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import org.megamek.mekbuilder.component.Cockpit;
import org.megamek.mekbuilder.component.Component;
import org.megamek.mekbuilder.component.MVFEngine;

import java.io.IOException;

/**
 * UI controller for mech chassis options.
 */
public class MekChassis {

    private static MekChassis instance;
    private Node root;

    public static MekChassis getInstance() {
        if (null == instance) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MekChassis.class.getResource("MekChassis.fxml"));
                Node root = loader.load();
                instance = loader.getController();
                instance.root = root;
            } catch (IOException ex) {

            }
        }
        return instance;
    }

    public Node getRoot() {
        return root;
    }

    @FXML
    private Spinner<Integer> spnTonnage;
    @FXML
    private CheckBox chkOmni;
    @FXML
    private ComboBox<String> cbBaseType;
    @FXML
    private ComboBox<String> cbMotiveType;
    @FXML
    private ComboBox<Component> cbStructure;
    @FXML
    private ComboBox<MVFEngine> cbEngine;
    @FXML
    private ComboBox<Component> cbGryo;
    @FXML
    private ComboBox<Cockpit> cbCockpit;


}
