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
    private val model: UnitViewModel by inject()

    override val root: AnchorPane by fxml()

    private val cbBaseType: ComboBox<MekConfiguration.BaseType> by fxid()
    private val cbSubType: ComboBox<MekConfiguration> by fxid()
    private val cbConstructionOption: ComboBox<UnitConstructionOption> by fxid()
    private val chkOmni: CheckBox by fxid()
    private val lblConstructionOption: Label by fxid()

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
        cbSubType.bind(model.mekConfigurationProperty)

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