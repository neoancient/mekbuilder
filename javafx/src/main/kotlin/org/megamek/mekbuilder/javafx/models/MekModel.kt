package org.megamek.mekbuilder.javafx.models

import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val configurationProperty = mekBuild.observable(MekBuild::getConfiguration, MekBuild::setConfiguration)
    val internalStructureProperty = mekBuild.observable(MekBuild::getStructureType, MekBuild::setStructureType)

    init {
        configurationProperty.onChange {
            baseOptionProperty.refresh()
        }
        structureTonnageProperty.bind(doubleBinding(
                internalStructureProperty, declaredTonnageProperty, configurationProperty)
            {mekBuild.structureTonnage})
    }
}