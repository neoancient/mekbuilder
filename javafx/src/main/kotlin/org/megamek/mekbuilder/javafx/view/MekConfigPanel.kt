package org.megamek.mekbuilder.javafx.view

import javafx.beans.binding.Bindings.createObjectBinding
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.ComboBox
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.SimpleComboBoxCellFactory
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

    init {
        val baseTypeProperty = SimpleListProperty<MekConfiguration.BaseType>()
        baseTypeProperty.bind(objectBinding(model.unitType) {
            MekConfiguration.getConfigurations(model.unitType.value).map{it.baseType}.toSet().toList().observable()
        })

        cbBaseType.items = baseTypeProperty
        SimpleComboBoxCellFactory.setConverter(cbBaseType) {
            messages["baseType.$it"]
        }
        cbBaseType.selectionModel.select(model.mekConfiguration.value.baseType)

        val configurationsList = SimpleListProperty<MekConfiguration>()
        val subTypeBinding = createObjectBinding (Callable {
            MekConfiguration.getConfigurations(model.unitType.value, cbBaseType.selectedItem).observable()
        })
        configurationsList.bind(subTypeBinding)
        cbBaseType.selectionModel.selectedItemProperty().onChange {
            subTypeBinding.invalidate()
            if (!configurationsList.contains(model.mekConfiguration.value)) {
                cbSubType.selectionModel.select(configurationsList.firstOrNull())
            }
        }
        cbSubType.items = configurationsList
        cbSubType.selectionModel.select(model.mekConfiguration.value)
        SimpleComboBoxCellFactory.setConverter(cbSubType) {
            messages["subType.${it.subType}"]
        }
        model.mekConfiguration.bind(cbSubType.selectionModel.selectedItemProperty())
    }
}