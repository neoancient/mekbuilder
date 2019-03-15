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
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.tools.Borders;
import org.megamek.mekbuilder.javafx.RootLayout;
import org.megamek.mekbuilder.javafx.view.BasicInfo;
import org.megamek.mekbuilder.javafx.view.MekChassis;
import org.megamek.mekbuilder.unit.UnitType;

import java.io.IOException;

/**
 * UI Controller for the most typical layout
 */
public class StandardLayout {

    private static StandardLayout instance;
    private Node root;

    public static StandardLayout getInstance() {
        if (null == instance) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(StandardLayout.class.getResource("StandardLayout.fxml"));
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
    private VBox structureLeft;
    @FXML
    private VBox structureMid;
    @FXML
    private VBox structureRight;

    @FXML
    private void initialize() {
        refreshLayout();
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

    private Node addBorder(Node node, String title) {
        return Borders.wrap(node).lineBorder().title(title).buildAll();
    }

    private void refreshLayout() {
        /*
        UnitType type = RootLayout.getInstance().getUnit().getUnitType();
        switch (type) {
            case BATTLE_MEK:
            case INDUSTRIAL_MEK:
            */
        structureLeft.getChildren().setAll(
                addBorder(BasicInfo.getInstance().getRoot(), "Basic Info"),
                addBorder(MekChassis.getInstance().getRoot(), "Chassis")
        );
//            default:
//        }
    }
}
