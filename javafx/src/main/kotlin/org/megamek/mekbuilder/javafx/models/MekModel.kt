package org.megamek.mekbuilder.javafx.models

import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
class MekModel(mekBuild: MekBuild): UnitModel(mekBuild) {
    val internalStructureProperty = observable(mekBuild, MekBuild::getStructureType, MekBuild::setStructureType)
    val limbConfigurationProperty = observable(mekBuild, MekBuild::getLimbConfiguration, MekBuild::setLimbConfiguration)

    init {
        structureTonnageProperty.bind(doubleBinding(
                internalStructureProperty, declaredTonnageProperty, limbConfigurationProperty)
            {mekBuild.structureTonnage})
    }
}