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
package org.megamek.mekbuilder.javafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.megamek.mekbuilder.javafx.unitlayout.StandardLayout;
import org.megamek.mekbuilder.unit.MekBuild;
import org.megamek.mekbuilder.unit.UnitBuild;

import java.io.IOException;

/**
 *
 */
public class RootLayout {

    private static RootLayout instance;
    private Parent root;

    public static RootLayout getInstance() {
        if (null == instance) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RootLayout.class.getResource("RootLayout.fxml"));
                Parent node = loader.load();
                instance = loader.getController();
                instance.root = node;
            } catch (IOException ex) {

            }
        }
        return instance;
    }

    public Parent getRoot() {
        return root;
    }

    private final ObjectProperty<UnitBuild> unitProperty = new SimpleObjectProperty();

    @FXML
    private MenuBar menuBar;
    @FXML
    private HBox statusBar;
    @FXML
    private AnchorPane panCenter;

    @FXML
    private void initialize() {
        unitProperty.set(new MekBuild());
        Node root = StandardLayout.create();
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        panCenter.getChildren().setAll(root);
    }

    public UnitBuild getUnit() {
        return unitProperty.get();
    }
}
