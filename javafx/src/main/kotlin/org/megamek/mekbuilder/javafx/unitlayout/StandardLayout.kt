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
package org.megamek.mekbuilder.javafx.unitlayout

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import org.megamek.mekbuilder.javafx.view.*
import tornadofx.*

/**
 * The main window used for most unit types
 */
class StandardLayout: View() {
    val mekConfiguration: MekConfigPanel by inject()
    val basicInfo: BasicInfo by inject()
    val mekChassis: MekChassis by inject()
    val movement: MovementView by inject()
    val summary: UnitSummary by inject()

    override val root: AnchorPane by fxml()

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
        structureMid.children.setAll(
                wrapAnchor(movement.root)
        )
        structureRight.children.setAll(
                wrapAnchor(summary.root)
        )
        //            default:
        //        }
    }
}