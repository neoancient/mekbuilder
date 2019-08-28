package org.megamek.mekbuilder.javafx.models

import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.collections.ObservableList
import org.megamek.mekbuilder.tech.ITechProgression
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val configurationProperty = observable(mekBuild, MekBuild::getConfiguration, MekBuild::setConfiguration)
    val internalStructureProperty = observable(mekBuild, MekBuild::getStructureType, MekBuild::setStructureType)
    val engineTypeProperty = observable(mekBuild, MekBuild::getEngineType, MekBuild::setEngineType)
    val engineRatingProperty = observable(mekBuild, MekBuild::getEngineRating, MekBuild::setEngineRating)
    val cockpitTypeProperty = observable(mekBuild, MekBuild::getCockpitType, MekBuild::setCockpitType)
    val gyroTypeProperty = observable(mekBuild, MekBuild::getGyroType, MekBuild::setGyroType)
    val myomerTypeProperty = observable(mekBuild, MekBuild::getMyomerType, MekBuild::setMyomerType)

    init {
        structureTonnageProperty.bind(doubleBinding(
                internalStructureProperty, tonnageProperty, configurationProperty)
            {mekBuild.structureTonnage})
    }
}