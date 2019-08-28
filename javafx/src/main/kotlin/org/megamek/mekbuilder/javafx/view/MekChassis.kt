package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.*
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.ComponentComboBoxCellFactory
import org.megamek.mekbuilder.javafx.util.SpinnerDoubleStringConverter
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*

/**
 *
 */

/**
 * Creates an {@link ObservableList} of all components that match the criteria, sorted alphabetically
 * by shortName with default value(s) moved to the top.
 */
fun createComponentList(op: (Component) -> Boolean) =
        ComponentLibrary.getInstance().allComponents
                .filter{op(it)}.sortedBy{it.shortName}
                .sortedBy{!it.isDefault}.toList().observable()

class MekChassis: View(), InvalidationListener {
    override val root: AnchorPane by fxml()
    private val techFilter: BasicInfo by inject()
    private val model: UnitViewModel by inject()

    private val spnTonnage: Spinner<Double> by fxid()
    private val lblWeightClass: Label by fxid()
    private val cbStructure: ComboBox<Component> by fxid()
    private val cbEngine: ComboBox<MVFEngine> by fxid()
    private val cbGyro: ComboBox<Component> by fxid()
    private val cbCockpit: ComboBox<Cockpit> by fxid()

    val allStructures = createComponentList { it.type == ComponentType.MEK_STRUCTURE }
    val allEngines = createComponentList { it.type == ComponentType.ENGINE }

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
        lblWeightClass.text = messages["lblWeightClass.${model.unit.weightClass}"]
        model.tonnage.onChange {
            lblWeightClass.text = messages["lblWeightClass.${model.unit.weightClass}"]
            allStructures.invalidate()
        }

        val structureList = SimpleListProperty<Component>()
        structureList.bind(objectBinding(allStructures) {
            filter { techFilter.isLegal(it) && model.unit.allowed(it)}.toList().observable()
        })
        cbStructure.items = structureList
        cbStructure.bind(model.internalStructure)
        ComponentComboBoxCellFactory.setConverter(cbStructure)

        val engineList = SimpleListProperty<MVFEngine>()
        engineList.bind(objectBinding(allEngines) {
            filter { techFilter.isLegal(it)
                    && model.unit.allowed(it)
                    && model.engineRating.value >= it.variableSizeMin()
                    && model.engineRating.value <= it.variableSizeMax()
                    && (it.hasFlag(ComponentSwitch.FUSION)
                        || model.unit.unitType == UnitType.INDUSTRIAL_MEK
                        || (model.unit as MekBuild).isPrimitive()
                        || techFilter.isLegal(ConstructionOptionKey.NON_FUSION_BATTLEMEK.get()))}
                    .map{it as MVFEngine}.toList().observable()
        })
        cbEngine.items = engineList
        cbEngine.bind(model.engineType)
        ComponentComboBoxCellFactory.setConverter(cbEngine)

        techFilter.addListener(this)
    }

    override fun invalidated(observable: Observable) {
        allStructures.invalidate()
        allEngines.invalidate()
        if (model.internalStructure.value !in cbStructure.items
                && cbStructure.items.isNotEmpty()) {
            cbStructure.selectionModel.select(cbStructure.items.first())
        }
    }
}