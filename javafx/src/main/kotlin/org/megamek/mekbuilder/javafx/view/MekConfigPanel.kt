/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2019 The MegaMek Team
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
package org.megamek.mekbuilder.javafx.view

import javafx.beans.binding.Bindings.createObjectBinding
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.SimpleComboBoxCellFactory
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.UnitConstructionOption
import tornadofx.*
import org.megamek.mekbuilder.unit.MekConfiguration
import java.util.concurrent.Callable

/**
 * Panel that shows basic configuration options for BattleMeks and IndustrialMeks
 */
class MekConfigPanel: View() {
    internal val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    internal val cbBaseType: ComboBox<MekConfiguration.BaseType> by fxid()
    internal val cbSubType: ComboBox<MekConfiguration> by fxid()
    internal val cbConstructionOption: ComboBox<UnitConstructionOption> by fxid()
    internal val chkOmni: CheckBox by fxid()
    internal val lblConstructionOption: Label by fxid()

    init {
        // List property that updates when the unit type changes to include all base types
        val baseTypeProperty = SimpleListProperty<MekConfiguration.BaseType>()
        baseTypeProperty.bind(objectBinding(model.unitTypeProperty) {
            MekConfiguration.getConfigurations(model.unitType).map{it.baseType}.toSet().toList().observable()
        })

        cbBaseType.items = baseTypeProperty
        SimpleComboBoxCellFactory.setConverter(cbBaseType) {
            messages["baseType.$it"]
        }
        cbBaseType.selectionModel.select(model.mekConfiguration.baseType)

        // List property bound to the available configurations for the base type.
        // This is invalidated when the base type selection changes.
        val configurationsList = SimpleListProperty<MekConfiguration>()
        val subTypeBinding = createObjectBinding (Callable {
            MekConfiguration.getConfigurations(model.unitType, cbBaseType.selectedItem).observable()
        })
        configurationsList.bind(subTypeBinding)
        cbBaseType.selectionModel.selectedItemProperty().onChange {
            subTypeBinding.invalidate()
            if (!configurationsList.contains(model.mekConfiguration)) {
                cbSubType.selectionModel.select(configurationsList.first())
            }
        }
        cbSubType.items = configurationsList
        SimpleComboBoxCellFactory.setConverter(cbSubType) {
            messages["subType.${it.subType}"]
        }
        cbSubType.selectionModel.selectedItemProperty().onChange {
            if (it != null) {
                model.mekConfiguration = it
            }
        }
        model.mekConfigurationProperty.onChange {
            if (it != null) {
                cbBaseType.selectionModel.select(it.baseType)
                cbSubType.selectionModel.select(it)
            }
        }

        // List property bound to the available construction options for the configuration.
        // Invalidates when the configuration changes.
        val optionsList = SimpleListProperty<UnitConstructionOption>()
        optionsList.bind(objectBinding(model.mekConfigurationProperty) {
            model.mekConfiguration.constructionOptions.observable()
        })
        cbConstructionOption.items = optionsList
        // We can't do a simple double binding here because the value can go to null
        // while refreshing the options and we need to be able to ignore it.
        cbConstructionOption.selectionModel.select(model.baseOption)
        model.baseOptionProperty.onChange {
            cbConstructionOption.selectionModel.select(it)
        }
        cbConstructionOption.selectionModel.selectedItemProperty().onChange {
            if (it != null) {
                model.baseOption = it
                setMinTechLevel()
            }
        }
        cbSubType.selectionModel.selectedItemProperty().onChange {
            if (it != null) {
                optionsList.invalidate()
                if (model.baseOption !in optionsList) {
                    // Try to keep the same weight class, otherwise pick the first in the list
                    model.baseOption = optionsList
                            .filter{model.tonnage >= it.minWeight && model.tonnage <= it.maxWeight}
                            .firstOrNull() ?: optionsList.first()
                }
            }
        }
        SimpleComboBoxCellFactory.setConverter(cbConstructionOption) {
            when {
                it.maxWeight < 20 -> messages["option.Ultralight"]
                it.minWeight > 100 -> messages["option.Superheavy"]
                else -> messages["option.Standard"]
            }
        }
        // Observable boolean that determines whether the construction option should
        // be dsiplayed (determined by whether there are multiple options)
        val showOptions = optionsList.booleanBinding {
            it?.size ?: 0 > 1
        }
        lblConstructionOption.visibleWhen(showOptions)
        cbConstructionOption.visibleWhen(showOptions)

        chkOmni.bind(model.omniProperty)
        chkOmni.visibleWhen(model.mekConfigurationProperty.booleanBinding {
            it?.isOmniAllowed ?: false
        })
        chkOmni.selectedProperty().onChange {
            setMinTechLevel()
        }
    }

    private fun setMinTechLevel() {
        var tl = model.baseOption.staticTechLevel()
        if (model.omni && tl < ConstructionOptionKey.OMNI.get().staticTechLevel()) {
            tl = ConstructionOptionKey.OMNI.get().staticTechLevel()
        }
        model.minTechLevel = tl
    }
}