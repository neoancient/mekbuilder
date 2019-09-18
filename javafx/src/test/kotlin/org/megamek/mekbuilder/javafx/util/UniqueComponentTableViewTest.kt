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
package org.megamek.mekbuilder.javafx.util

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.TableView
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.megamek.mekbuilder.component.ComponentKeys
import org.megamek.mekbuilder.component.ComponentLibrary
import org.megamek.mekbuilder.component.ComponentSwitch
import org.megamek.mekbuilder.component.ComponentType
import org.megamek.mekbuilder.javafx.models.MekModel
import org.megamek.mekbuilder.javafx.models.UnitViewModel
import org.megamek.mekbuilder.javafx.view.BasicInfo
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.unit.MekBuild
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.framework.junit5.Start
import tornadofx.*

/**
 *
 */
internal class UniqueComponentTableViewTest: ApplicationTest() {
    private lateinit var table: TableView<UniqueComponentTableView.RowItem>
    private lateinit var model: UnitViewModel
    private lateinit var basicInfo: BasicInfo

    @Start
    override fun start(stage: Stage) {
        val view = UniqueComponentTableView { it.type == ComponentType.MOVE_ENHANCEMENT }
        table = view.root
        model = view.model
        basicInfo = view.techFilter
        stage.scene = Scene(view.root)
        stage.show()
    }

    @Test
    fun setInitialInstallation() {
        Platform.runLater {
            val masc = ComponentLibrary.getInstance().getComponent(ComponentKeys.MASC_IS)
            val mek = MekBuild()
            mek.addMount(mek.createMount(masc))
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertTrue(table.items.first { it.component == masc }.installedProperty.value)
        }
    }

    @Test
    fun externalConflictDisablesRow() {
        Platform.runLater {
            val mek = MekBuild()
            mek.myomerType = ComponentLibrary.getInstance().getComponent(ComponentKeys.MYOMER_TSM)
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertFalse(table.items.first { it.component.hasFlag(ComponentSwitch.MASC) }.allowedProperty.value)
        }
    }

    @Test
    fun internalConflictDisablesRow() {
        Platform.runLater {
            val masc = ComponentLibrary.getInstance().getComponent(ComponentKeys.MASC_IS)
            val mek = MekBuild()
            mek.techBase = TechBase.ALL
            mek.addMount(mek.createMount(masc))
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertFalse(table.items.first { it.component.internalName == ComponentKeys.MASC_CLAN }.allowedProperty.value)
        }
    }

    @Test
    fun setInitialSize() {
        Platform.runLater {
            val booster = ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JUMP_BOOSTER)
            val mek = MekBuild()
            val mount = mek.createMount(booster)
            mount.size = 3.0
            mek.addMount(mount)
            mek.year = booster.introDate() + 5
            basicInfo.cbTechLevel.selectionModel.select(booster.staticTechLevel())
            model.rebind {
                unitModel = MekModel(mek)
            }

            assertEquals(3.0, table.items.first { it.component == booster }.sizeProperty.value, 0.001)
        }
    }
}