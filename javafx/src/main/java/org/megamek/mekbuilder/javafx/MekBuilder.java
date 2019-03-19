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

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.megamek.mekbuilder.unit.MekBuild;
import org.megamek.mekbuilder.unit.UnitBuild;

import java.io.IOException;

/**
 *
 */

public class MekBuilder extends Application {

    private static ObjectProperty<UnitBuild> unitProperty = new SimpleObjectProperty<>();

    public static ObjectProperty<UnitBuild> unitProperty() {
        if (unitProperty.get() == null) {
            unitProperty.set(new MekBuild());
        }
        return unitProperty;
    }

    public static UnitBuild getUnit() {
        return unitProperty().get();
    }

    public static void setUnit(UnitBuild unit) {
        unitProperty().set(unit);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Mekbuilder");

        initRootLayout();
    }

    private void initRootLayout() {
        Scene scene = new Scene((Parent) RootLayout.getInstance().getRoot());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
