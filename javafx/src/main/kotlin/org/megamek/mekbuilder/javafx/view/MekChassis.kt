package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.Cockpit
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.MVFEngine
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.SpinnerDoubleStringConverter
import tornadofx.*

/**
 *
 */
class MekChassis: View(), InvalidationListener {
    override val root: AnchorPane by fxml()
    val techFilter: BasicInfo by inject()
    val model: UnitViewModel by inject()

    val spnTonnage: Spinner<Double> by fxid()
    val lblWeightClass: Label by fxid()
    val cbStructure: ComboBox<Component> by fxid()
    val cbEngine: ComboBox<MVFEngine> by fxid()
    val cbGyro: ComboBox<Component> by fxid()
    val cbCockpit: ComboBox<Cockpit> by fxid()

    init {
        val tonnageFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(
                model.baseOption.value.minWeight, model.baseOption.value.maxWeight,
                model.tonnage.value.toDouble(),
                model.weightIncrement.value)
        model.baseOption.onChange {
            if (it != null) {
                tonnageFactory.max = it.maxWeight
                tonnageFactory.min = it.minWeight
                if (tonnageFactory.value < it.minWeight) {
                    tonnageFactory.value = it.minWeight
                }
                if (tonnageFactory.value > it.maxWeight) {
                    tonnageFactory.value = it.maxWeight
                }
            }
        }
        tonnageFactory.amountToStepByProperty().bind(model.weightIncrement)
        tonnageFactory.valueProperty().bindBidirectional(model.tonnage)
        tonnageFactory.converter = SpinnerDoubleStringConverter(spnTonnage.editor, tonnageFactory)
        spnTonnage.valueFactory = tonnageFactory
        // Force update on lost focus (e.g. tab)
        spnTonnage.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnTonnage.increment(0)
        }
        lblWeightClass.text = messages["lblWeightClass.${model.unitModel.unit.weightClass}"]
        model.tonnage.onChange {
            lblWeightClass.text = messages["lblWeightClass.${model.unitModel.unit.weightClass}"]
        }

        techFilter.addListener(this)
    }

    override fun invalidated(observable: Observable) {
    }
}