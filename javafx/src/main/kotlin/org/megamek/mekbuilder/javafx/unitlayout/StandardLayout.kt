package org.megamek.mekbuilder.javafx.unitlayout

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import org.megamek.mekbuilder.javafx.view.BasicInfo
import org.megamek.mekbuilder.javafx.view.MekChassis
import org.megamek.mekbuilder.javafx.view.MekConfigPanel
import tornadofx.*

/**
 *
 */
class StandardLayout: View() {
    val mekConfiguration: MekConfigPanel by inject()
    val basicInfo: BasicInfo by inject()
    val mekChassis: MekChassis by inject()

    override val root: TabPane by fxml()

    val structureTab: Tab by fxid()
    val armorTab: Tab by fxid()
    val equipmentTab: Tab by fxid()
    val buildTab: Tab by fxid()
    val previewTab: Tab by fxid()

    val structureLeft: VBox by fxid()
    val structureMid: VBox by fxid()
    val structureRight: VBox by fxid()

    init {
        refreshLayout()
    }

    private fun wrapAnchor(node: Node): Node {
        val anchor = AnchorPane()
        anchor.children.setAll(node)
        AnchorPane.setLeftAnchor(node, 0.0)
        AnchorPane.setRightAnchor(node, 0.0)
        return anchor
    }

    private fun refreshLayout() {
        /*
        UnitType type = RootLayout.getInstance().getUnit().getUnitType();
        switch (type) {
            case BATTLE_MEK:
            case INDUSTRIAL_MEK:
            */

        structureLeft.children.setAll(
                wrapAnchor(mekConfiguration.root),
                wrapAnchor(basicInfo.root),
                wrapAnchor(mekChassis.root)
        )
        //            default:
        //        }
    }
}