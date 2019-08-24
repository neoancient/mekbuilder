package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.DoubleProperty
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.Cockpit
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.MVFEngine
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.tech.UnitConstructionOption
import tornadofx.*

/**
 *
 */
class MekChassis: View(), InvalidationListener {
    override val root: AnchorPane by fxml()
    val techFilter: BasicInfo by inject()
    val model: UnitViewModel by inject()

    val spnTonnage: Spinner<Double> by fxid()
    val chkOmni: CheckBox by fxid()
    val cbStructure: ComboBox<Component> by fxid()
    val cbEngine: ComboBox<MVFEngine> by fxid()
    val cbGyro: ComboBox<Component> by fxid()
    val cbCockpit: ComboBox<Cockpit> by fxid()

    init {
        val tonnageFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(
                model.baseOption.value.minWeight, model.baseOption.value.maxWeight,
                model.declaredTonnage.value.toDouble(),
                model.weightIncrement.value)
        model.baseOption.onChange {
            if (it != null) {
                tonnageFactory.min = it.minWeight
                tonnageFactory.max = it.maxWeight
                if (tonnageFactory.value < it.minWeight) {
                    tonnageFactory.value = it.minWeight
                }
                if (tonnageFactory.value > it.maxWeight) {
                    tonnageFactory.value = it.maxWeight
                }
            }
        }
        tonnageFactory.amountToStepByProperty().bind(model.weightIncrement)
        // Bind ObjectProperty<Double> to DoubleProperty that can be bound to model property
        val tonnageValueProperty = DoubleProperty.doubleProperty(tonnageFactory.valueProperty())
        tonnageValueProperty.bindBidirectional(model.declaredTonnage)
        spnTonnage.valueFactory = tonnageFactory

        techFilter.addListener(this)
    }

    override fun invalidated(observable: Observable) {
    }
}