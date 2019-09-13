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
package org.megamek.mekbuilder.javafx.models

import javafx.beans.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.util.Callback
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.Mount
import tornadofx.*
import javax.swing.text.StyleConstants.setComponent

/**
 *
 */
class MountModel(m: Mount) {
    val mountProperty = SimpleObjectProperty(m)
    var mount by mountProperty

    val componentProperty = pojoProperty<Mount, Component>(m, Mount::getComponent, Mount::setComponent)
    var component by componentProperty
    val sizeProperty = pojoProperty(m, Mount::getSize, Mount::setSize)
    var size by sizeProperty
    val locationProperty = pojoProperty(m, Mount::getLocation, Mount::setLocation)
    var location by locationProperty
    val moduleProperty = pojoProperty(m, Mount::getModuleType, Mount::setModuleType)
    var moduleType by moduleProperty
    val rearFacingProperty = pojoProperty(m, Mount::isRearFacing, Mount::setRearFacing)
    var rearFacing by rearFacingProperty
    val armoredProperty = pojoProperty(m, Mount::isArmored, Mount::setArmored)
    var armored by armoredProperty

    val weight = doubleBinding(componentProperty, sizeProperty, armoredProperty) {
        mount.componentWeight
    }
    val slots = integerBinding(componentProperty, sizeProperty) {
        mount.componentSlots
    }
    val cost = doubleBinding(componentProperty, sizeProperty, moduleProperty, armoredProperty) {
        mount.componentCost
    }
}