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
package org.megamek.mekbuilder.util.tech

import org.megamek.mekbuilder.tech.*
import java.util.regex.Pattern

/**
 * Domain-specific language for creating TechProgression objects
 */

class TechProgressionBuilder {
    var techBase = TechBase.ALL
    var rating = Rating.RATING_A
    private var av = arrayOf(Rating.RATING_A, Rating.RATING_A, Rating.RATING_A, Rating.RATING_A)
    var availability: String = ""
        set(value) {
            for (i in 0..(value.length - 1)) {
                av[i] = Rating.valueOf("RATING_${value[i]}")
            }
            field = value
        }
    var isProg: MutableMap<TechStage, Int?> = HashMap(TechStage.values().map{Pair(it, null)}.toMap())
    var clanProg: MutableMap<TechStage, Int?> = HashMap(TechStage.values().map{Pair(it, null)}.toMap())
    var isApproximate = HashSet<TechStage>()
    var clanApproximate = HashSet<TechStage>()
    var factions: Map<TechStage, MutableSet<Faction>> =
            TechStage.values().map{Pair(it, HashSet<Faction>())}.toMap()
    var staticLevel = TechLevel.STANDARD

    fun isProgression(block: Progression.() -> Unit) {
        val p = Progression().apply(block)
        isProg.putAll(p.dates)
        isApproximate.addAll(p.approx)
        p.factions.forEach{factions[it.key]?.addAll(it.value)}
    }

    fun clanProgression(block: Progression.() -> Unit) {
        val p = Progression().apply(block)
        clanProg.putAll(p.dates)
        clanApproximate.addAll(p.approx)
        p.factions.forEach{factions[it.key]?.addAll(it.value)}
    }

    fun progression(block: Progression.() -> Unit) {
        val p = Progression().apply(block)
        isProg.putAll(p.dates)
        clanProg.putAll(p.dates)
        isApproximate.addAll(p.approx)
        clanApproximate.addAll(p.approx)
        p.factions.forEach{factions[it.key]?.addAll(it.value)}
    }

    fun build() = TechProgression(techBase, rating, av,
            isProg, clanProg, isApproximate, clanApproximate, factions, staticLevel)
}

fun techProgression(block: TechProgressionBuilder.() -> Unit): TechProgression =
        TechProgressionBuilder().apply(block).build()

val PROG_REGEX = Pattern.compile("(~?)(\\d+)(\\(.*\\))?")

class Progression {
    val dates = HashMap<TechStage, Int>()
    val approx = HashSet<TechStage>()
    val factions = HashMap<TechStage, Set<Faction>>()

    var prototype: String = ""
        set(value) {
            enterData(TechStage.PROTOTYPE, value)
            field = value
        }
    var production: String = ""
        set(value) {
            enterData(TechStage.PRODUCTION, value)
            field = value
        }
    var common: String = ""
        set(value) {
            enterData(TechStage.COMMON, value)
            field = value
        }
    var extinction: String = ""
        set(value) {
            enterData(TechStage.EXTINCTION, value)
            field = value
        }
    var reintroduction: String = ""
        set(value) {
            enterData(TechStage.REINTRODUCTION, value)
            field = value
        }

    private fun enterData(stage: TechStage, str: String) {
        val matcher = PROG_REGEX.matcher(str)
        if (matcher.matches()) {
            val fSet = HashSet<Faction>()
            if (!matcher.group(1).isEmpty()) {
                approx.add(stage)
            }
            dates.put(stage, Integer.parseInt(matcher.group(2)))
            if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                val fnames = matcher.group(3).replace("[()]".toRegex(), "").split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                for (name in fnames) {
                    fSet.add(Faction.valueOf(name))
                }
            }
            factions[stage] = fSet
        }
    }
}

fun progression(block: Progression.() -> Unit): Progression {
    val p = Progression()
    block(p)
    return p
}
