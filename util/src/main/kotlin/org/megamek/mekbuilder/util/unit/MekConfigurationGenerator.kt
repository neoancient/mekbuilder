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
package org.megamek.mekbuilder.util.unit

import com.fasterxml.jackson.databind.ObjectMapper
import org.megamek.mekbuilder.tech.ConstructionOptionKey
import org.megamek.mekbuilder.tech.UnitConstructionOption
import org.megamek.mekbuilder.unit.MekBuild
import org.megamek.mekbuilder.unit.MekConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import kotlin.streams.toList

/**
 * DSL to generate the mech configuration options
 */

class MekConfigurationBuilder {
    var baseType: MekConfiguration.BaseType? = null
    var subType: MekConfiguration.SubType? = null
    var limbConfiguration: MekBuild.LimbConfiguration? = null
    private var constructionOptions = ArrayList<UnitConstructionOption>()
    fun option(key: ConstructionOptionKey) {
        constructionOptions.add(key.get() as UnitConstructionOption)
    }
    var omni = true;

    fun build(): MekConfiguration = MekConfiguration(baseType, subType, limbConfiguration,
            constructionOptions.stream().sorted{o1, o2 -> o1.minWeight.compareTo(o2.minWeight)}.toList(),
            omni)
}

fun mekConfiguration(block: MekConfigurationBuilder.() -> Unit) = MekConfigurationBuilder().apply(block).build()

val mekList = listOf(
        mekConfiguration {
            baseType = MekConfiguration.BaseType.STANDARD
            subType = MekConfiguration.SubType.STANDARD_BIPED
            limbConfiguration = MekBuild.LimbConfiguration.BIPED
            option(ConstructionOptionKey.MEK_ULTRALIGHT)
            option(ConstructionOptionKey.MEK_STANDARD)
            option(ConstructionOptionKey.MEK_SUPERHEAVY)
            omni = true
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.STANDARD
            subType = MekConfiguration.SubType.STANDARD_QUAD
            limbConfiguration = MekBuild.LimbConfiguration.QUAD
            option(ConstructionOptionKey.MEK_ULTRALIGHT)
            option(ConstructionOptionKey.MEK_STANDARD)
            option(ConstructionOptionKey.MEK_SUPERHEAVY)
            omni = true
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.STANDARD
            subType = MekConfiguration.SubType.STANDARD_TRIPOD
            limbConfiguration = MekBuild.LimbConfiguration.TRIPOD
            option(ConstructionOptionKey.MEK_TRIPOD)
            option(ConstructionOptionKey.MEK_SUPERHEAVY_TRIPOD)
            omni = true
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.PRIMITIVE
            subType = MekConfiguration.SubType.PRIMITIVE_BIPED
            limbConfiguration = MekBuild.LimbConfiguration.BIPED
            option(ConstructionOptionKey.MEK_PRIMITIVE)
            omni = false
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.PRIMITIVE
            subType = MekConfiguration.SubType.PRIMITIVE_QUAD
            limbConfiguration = MekBuild.LimbConfiguration.QUAD
            option(ConstructionOptionKey.MEK_PRIMITIVE)
            omni = false
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.LAM
            subType = MekConfiguration.SubType.LAM_STANDARD
            limbConfiguration = MekBuild.LimbConfiguration.BIPED
            option(ConstructionOptionKey.MEK_STANDARD_LAM)
            omni = false
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.LAM
            subType = MekConfiguration.SubType.LAM_BIMODAL
            limbConfiguration = MekBuild.LimbConfiguration.BIPED
            option(ConstructionOptionKey.MEK_BIMODAL_LAM)
            omni = false
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.QUADVEE
            subType = MekConfiguration.SubType.QUADVEE_TRACKED
            limbConfiguration = MekBuild.LimbConfiguration.QUAD
            option(ConstructionOptionKey.MEK_QUADVEE_TRACKED)
            omni = true
        },
        mekConfiguration {
            baseType = MekConfiguration.BaseType.QUADVEE
            subType = MekConfiguration.SubType.QUADVEE_WHEELED
            limbConfiguration = MekBuild.LimbConfiguration.QUAD
            option(ConstructionOptionKey.MEK_QUADVEE_WHEELED)
            omni = true
        }
)

val imekList = listOf(
    mekConfiguration {
        baseType = MekConfiguration.BaseType.STANDARD
        subType = MekConfiguration.SubType.STANDARD_BIPED
        limbConfiguration = MekBuild.LimbConfiguration.BIPED
        option(ConstructionOptionKey.IMEK_STANDARD)
        option(ConstructionOptionKey.IMEK_SUPERHEAVY)
        omni = false
    },
    mekConfiguration {
        baseType = MekConfiguration.BaseType.STANDARD
        subType = MekConfiguration.SubType.STANDARD_QUAD
        limbConfiguration = MekBuild.LimbConfiguration.QUAD
        option(ConstructionOptionKey.IMEK_STANDARD)
        option(ConstructionOptionKey.IMEK_SUPERHEAVY)
        omni = false
    },
    mekConfiguration {
        baseType = MekConfiguration.BaseType.STANDARD
        subType = MekConfiguration.SubType.STANDARD_TRIPOD
        limbConfiguration = MekBuild.LimbConfiguration.TRIPOD
        option(ConstructionOptionKey.IMEK_TRIPOD)
        option(ConstructionOptionKey.IMEK_SUPERHEAVY_TRIPOD)
        omni = false
    },
    mekConfiguration {
        baseType = MekConfiguration.BaseType.PRIMITIVE
        subType = MekConfiguration.SubType.PRIMITIVE_BIPED
        limbConfiguration = MekBuild.LimbConfiguration.BIPED
        option(ConstructionOptionKey.IMEK_PRIMITIVE)
        omni = false
    },
    mekConfiguration {
        baseType = MekConfiguration.BaseType.PRIMITIVE
        subType = MekConfiguration.SubType.PRIMITIVE_QUAD
        limbConfiguration = MekBuild.LimbConfiguration.QUAD
        option(ConstructionOptionKey.IMEK_PRIMITIVE)
        omni = false
    }
)

fun write(file: File, output: String) {
    val os = FileOutputStream(file)
    val pw = PrintWriter(os)
    pw.println(output)
    pw.flush()
    pw.close()
    os.flush()
    os.close()
}

fun main() {
    val mapper = ObjectMapper()
    write(File("util/data", "mekConfigurations.json"),
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mekList))
    write(File("util/data", "imekConfigurations.json"),
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(imekList))
}