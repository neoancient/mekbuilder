package org.megamek.mekbuilder.javafx.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Spinner
import javafx.scene.layout.AnchorPane
import org.megamek.mekbuilder.component.Cockpit
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.MVFEngine
import tornadofx.*

/**
 *
 */
class MekChassis: View(), InvalidationListener {
    override val root: AnchorPane by fxml()
    val techFilter: BasicInfo by inject()

    val spnTonnage: Spinner<Int> by fxid()
    val chkOmni: CheckBox by fxid()
    val cbBaseType: ComboBox<String> by fxid()
    val cbMotiveType: ComboBox<String> by fxid()
    val cbStructure: ComboBox<Component> by fxid()
    val cbEngine: ComboBox<MVFEngine> by fxid()
    val cbGyro: ComboBox<Component> by fxid()
    val cbCockpit: ComboBox<Cockpit> by fxid()

    init {

    }

    override fun invalidated(observable: Observable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}