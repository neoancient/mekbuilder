package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.component.MoveEnhancement
import org.megamek.mekbuilder.component.SecondaryMotiveSystem
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.util.ComponentComboBoxCellFactory
import org.megamek.mekbuilder.javafx.util.ComponentListNameLookup
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitBuild
import org.megamek.mekbuilder.unit.UnitType
import tornadofx.*

/**
 * View for setting walk/cruise/thrust speed and additional movement modes (e.g. jump/underwater).
 */
class MovementView : View(), InvalidationListener {
    private val model: UnitViewModel by inject()
    private val techFilter: BasicInfo by inject()

    override val root: AnchorPane by fxml()

    private val lblWalkMP: Label by fxid()
    private val lblRunMP: Label by fxid()
    private val cbSecondaryMotive: ComboBox<SecondaryMotiveSystem> by fxid()
    private val spnWalkBase: Spinner<Int> by fxid()
    private val lblRunBase: Label by fxid()
    private val spnBaseSecondary: Spinner<Int> by fxid()
    private val lblWalkFinal: Label by fxid()
    private val lblRunFinal: Label by fxid()
    private val lblSecondaryFinal: Label by fxid()
    private val tblEnhancement: TableView<MoveEnhancement> by fxid()
    private val colEnhancementInstalled: TableColumn<MoveEnhancement, Boolean> by fxid()
    private val colEnhancementName: TableColumn<MoveEnhancement, String> by fxid()
    private val colEnhancementSize: TableColumn<MoveEnhancement, Double> by fxid()

    private val allSecondaryMotive = ComponentLibrary.getInstance().allComponents
            .filter {it.type == ComponentType.SECONDARY_MOTIVE_SYSTEM}
            .sortedBy{it.shortName}.sortedBy{!it.isDefault}
            .toList().observable()
    private val allEnhancements = SortedFilteredList(ComponentLibrary.getInstance().allComponents
            .filter {it.type == ComponentType.MOVE_ENHANCEMENT}
            .map {it as MoveEnhancement}
            .sortedBy{it.shortName}.toList().observable(), {
                model.unit.allowed(it) && techFilter.isLegal(it)
    })


    private fun isWalker(unit: UnitBuild) =
        unit.unitType.isMech || unit.unitType == UnitType.PROTOMEK || unit.unitType == UnitType.BATTLE_ARMOR

    private fun isAero(unit: UnitBuild) =
        //TODO: Add aero support vehicles
        unit.unitType.isFighter || unit.unitType.isLargeCraft || unit.unitType == UnitType.SMALL_CRAFT

    init {
        lblWalkMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblWalkMP.text"]
                isAero(value) -> messages["lblSafeThrust.text"]
                else -> messages["lblCruiseMP.text"]
            }
        })
        lblRunMP.textProperty().bind(stringBinding(model.unitProperty) {
            when {
                isWalker(value) -> messages["lblRunMP.text"]
                isAero(value) -> messages["lblMaxThrust.text"]
                else -> messages["lblFlankMP.text"]
            }
        })

        val baseWalkFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(model.minWalk, model.maxWalk)
        baseWalkFactory.minProperty().bind(model.minWalkProperty)
        baseWalkFactory.maxProperty().bind(model.maxWalkProperty)
        spnWalkBase.valueFactory = baseWalkFactory
        spnWalkBase.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnWalkBase.increment(0)
        }
        spnWalkBase.bind(model.baseWalkMPProperty)

        val secondaryMotiveList = SimpleListProperty<SecondaryMotiveSystem>()
        secondaryMotiveList.bind(objectBinding(allSecondaryMotive) {
            filter { techFilter.isLegal(it) && model.unit.allowed(it)}
                    .sortedBy{it != model.unit.defaultSecondaryMotiveType}
                    .map{it as SecondaryMotiveSystem}.toList().observable()
        })
        cbSecondaryMotive.items = secondaryMotiveList
        cbSecondaryMotive.bind(model.secondaryMotiveProperty)
        ComponentComboBoxCellFactory.setConverter(cbSecondaryMotive)

        spnBaseSecondary.enableWhen {booleanBinding(model.secondaryMotiveProperty) {value.mode != MotiveType.GROUND}}
        val baseSecondaryFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(model.minSecondaryMP, model.maxSecondaryMP)
        // It would be nice if we could just bind the min and max properties to these, but that could
        // result in an exception for trying to set a bound value when the factory tries to maintain min < max
        model.minSecondaryMPProperty.onChange {
            updateSecondaryMPFactory(baseSecondaryFactory)
        }
        model.maxSecondaryMPProperty.onChange {
            updateSecondaryMPFactory(baseSecondaryFactory)
        }
        spnBaseSecondary.valueFactory = baseSecondaryFactory
        spnBaseSecondary.focusedProperty().addListener {
            _, _, newValue -> if (!newValue) spnBaseSecondary.increment(0)
        }
        spnBaseSecondary.bind(model.baseSecondaryMPProperty)

        lblRunBase.textProperty().bind(stringBinding(model.baseRunMPProperty) {
            model.baseRunMP.toString()
        })
        lblWalkFinal.textProperty().bind(stringBinding(model.walkMPProperty) {model.walkMP.toString()})
        lblRunFinal.textProperty().bind(stringBinding(model.runMPProperty) {model.runMP.toString()})
        lblSecondaryFinal.textProperty().bind(stringBinding(model.secondaryMPProperty) {model.secondaryMP.toString()})

        val enhancementLookup = ComponentListNameLookup(allEnhancements)
        allEnhancements.bindTo(tblEnhancement)
        colEnhancementInstalled.cellValueFactory = Callback<TableColumn.CellDataFeatures<MoveEnhancement, Boolean>,
                ObservableValue<Boolean>> {
            model.componentListProperty.booleanBinding {
                list -> list?.any{m -> m.component.equals(it?.value)} ?: false
            }
        }
        colEnhancementName.cellValueFactory = Callback<TableColumn.CellDataFeatures<MoveEnhancement, String>,
                ObservableValue<String>> {
            SimpleStringProperty(enhancementLookup[it.value])
        }

        techFilter.addListener(this)
    }

    private fun updateSecondaryMPFactory(factory: SpinnerValueFactory.IntegerSpinnerValueFactory) {
        factory.max = model.maxSecondaryMP
        factory.min = model.minSecondaryMP
        if (factory.value < model.minSecondaryMP) {
            model.baseSecondaryMP = model.minSecondaryMP
        }
        if (factory.value > model.maxSecondaryMP) {
            model.baseSecondaryMP = model.maxSecondaryMP
        }
    }

    override fun invalidated(observable: Observable) {
        allSecondaryMotive.invalidate()
        if (model.secondaryMotiveType !in cbSecondaryMotive.items) {
            model.secondaryMotiveType = cbSecondaryMotive.items.first()
        } else {
            cbSecondaryMotive.selectionModel.select(model.secondaryMotiveType)
        }
    }
}