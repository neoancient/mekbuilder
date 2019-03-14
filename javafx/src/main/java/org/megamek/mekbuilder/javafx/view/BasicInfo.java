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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.megamek.mekbuilder.tech.Faction;
import org.megamek.mekbuilder.tech.TechBase;
import org.megamek.mekbuilder.tech.TechLevel;

import java.io.IOException;

/**
 * Controller for basic info view.
 */
public class BasicInfo {

    private static BasicInfo instance;
    private Node root;

    public static BasicInfo getInstance() {
        if (null == instance) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(BasicInfo.class.getResource("BasicInfo.fxml"));
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
    private TextField txtChassis;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtSource;
    @FXML
    private ComboBox<TechBase> cbTechBase;
    @FXML
    private ComboBox<TechLevel> cbTechLevel;
    @FXML
    private ComboBox<Faction> cbFaction;
}
