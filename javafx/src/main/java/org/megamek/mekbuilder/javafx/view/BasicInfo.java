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

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.megamek.mekbuilder.javafx.MekBuilder;
import org.megamek.mekbuilder.javafx.util.SimpleComboBoxCellFactory;
import org.megamek.mekbuilder.tech.Faction;
import org.megamek.mekbuilder.tech.TechBase;
import org.megamek.mekbuilder.tech.TechLevel;
import org.megamek.mekbuilder.tech.TechProgression;
import org.megamek.mekbuilder.unit.UnitBuild;

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
                ex.printStackTrace();
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

    @FXML
    private void initialize() {
        // Only allow up to four digits, but not initial zero.
        txtYear.setTextFormatter(new TextFormatter<>(new YearFieldConverter()));
        cbTechBase.setItems(FXCollections.observableArrayList(TechBase.values()));
        SimpleComboBoxCellFactory.setConverter(cbTechBase, tb -> tb.unitDisplayName);
        cbTechLevel.setItems(FXCollections.observableArrayList(TechLevel.values()));
        SimpleComboBoxCellFactory.setConverter(cbTechLevel, tl -> tl.displayName);
        cbFaction.setItems(FXCollections.observableArrayList(Faction.values()));
        SimpleComboBoxCellFactory.setConverter(cbFaction, f -> f.displayName);
        rebind(null, MekBuilder.getUnit());
        MekBuilder.unitProperty().addListener((obs, ov, nv) -> rebind(ov, nv));
    }

    private void rebind(UnitBuild oldV, UnitBuild newV) {
        if (null != oldV) {
            txtChassis.textProperty().unbindBidirectional(oldV.chassisProperty());
            txtModel.textProperty().unbindBidirectional(oldV.modelProperty());
            txtSource.textProperty().unbindBidirectional(oldV.sourceProperty());
            txtYear.textProperty().unbindBidirectional(oldV.yearProperty());
            oldV.techBaseProperty().unbind();
            oldV.techLevelProperty().unbind();
            oldV.factionProperty().unbind();
        }
        if (null != newV) {
            txtChassis.textProperty().bindBidirectional(newV.chassisProperty());
            txtModel.textProperty().bindBidirectional(newV.modelProperty());
            txtSource.textProperty().bindBidirectional(newV.sourceProperty());
            Bindings.bindBidirectional(txtYear.textProperty(), newV.yearProperty(), new YearFieldConverter());
            cbTechBase.getSelectionModel().select(newV.getTechBase());
            newV.techBaseProperty().bind(cbTechBase.getSelectionModel().selectedItemProperty());
            cbTechLevel.getSelectionModel().select(newV.getTechLevel());
            newV.techLevelProperty().bind(cbTechLevel.getSelectionModel().selectedItemProperty());
            cbFaction.getSelectionModel().select(newV.getFaction());
            newV.factionProperty().bind(cbFaction.getSelectionModel().selectedItemProperty());
        }
    }

    private static class YearFieldConverter extends StringConverter<Number> {
        private static final int MIN_YEAR = TechProgression.DATE_PS;
        private static final int MAX_YEAR = 3200;
        private static final int DEFAULT_YEAR = 3067;

        @Override
        public String toString(Number object) {
            if (null == object) {
                return "";
            } else {
                return object.toString();
            }
        }

        @Override
        public Integer fromString(String string) {
            string = string.replaceAll("[^\\d]", "");
            if (!string.isEmpty()) {
                return Math.min(Math.max(Integer.valueOf(string), MIN_YEAR), MAX_YEAR);
            } else if (MekBuilder.getUnit() != null){
                return MekBuilder.getUnit().getYear();
            } else {
                return DEFAULT_YEAR;
            }
        }
    }
}
