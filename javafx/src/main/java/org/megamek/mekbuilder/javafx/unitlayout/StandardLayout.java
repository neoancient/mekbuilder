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
package org.megamek.mekbuilder.javafx.unitlayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import org.controlsfx.tools.Borders;

import java.io.IOException;

/**
 * UI Controller for the most typical layout
 */
public class StandardLayout {

    @FXML
    private Tab structureTab;
    @FXML
    private Tab armorTab;
    @FXML
    private Tab equipmentTab;
    @FXML
    private Tab buildTab;
    @FXML
    private Tab previewTab;

    @FXML
    private Pane panBasicInfo;
    @FXML
    private Pane panChassis;
    @FXML
    private Pane panMovement;
    @FXML
    private Pane panSummary;
    @FXML
    private Pane panEnhancements;

    @FXML
    private void initialize() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("Text"));
        Node wrappedPane = Borders.wrap(pane).lineBorder().title("Basic Info").buildAll();
        panBasicInfo.getChildren().add(wrappedPane);
    }

    public static Node create() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StandardLayout.class.getResource("StandardLayout.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
