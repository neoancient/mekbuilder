package org.megamek.mekbuilder.javafx.models

import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val configurationProperty = observable(mekBuild, MekBuild::getConfiguration, MekBuild::setConfiguration)
    val internalStructureProperty = observable(mekBuild, MekBuild::getStructureType, MekBuild::setStructureType)

    init {
        structureTonnageProperty.bind(doubleBinding(
                internalStructureProperty, declaredTonnageProperty, configurationProperty)
            {mekBuild.structureTonnage})
    }
}