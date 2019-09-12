package org.megamek.mekbuilder.javafx.util

import javafx.beans.property.SimpleListProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.tech.TechBase
import tornadofx.*

fun Component.shortNameWithTechBase() =
        if (techBase().equals(TechBase.ALL) || shortName.toUpperCase().startsWith(techBase().toString())) {
            shortName
        } else {
            "$shortName ${techBase().suffix()}"
        }

/**
 * Manages display names for a list of {@link Component}s to ensure each name is unique within the list.
 * The short name is the first choice. If there are multiple entries with the same short name, any collisions
 * have IS or Clan appended as appropriate. If there are still collisions, the full name is used and
 * no further attempts are made to resolve collisions, since this means that either an entry appears multiple
 * times in the list or there are multiple {@link Component}s with the same full name.
 */

class ComponentListNameLookup() {
    val listProperty = SimpleListProperty<Component>()
    private val nameMap = HashMap<Component, String>()

    constructor(list: ObservableList<out Component>) : this() {
        listProperty.bind(objectBinding(list) {
            toList().observable()
        })
    }

    /**
     * @param c  A component in the list
     * @return   A display name for this component which is unique within the collection, or {@code null}
     *           if the component is not in the list.
     *
     */
    operator fun get(c: Component): String? = nameMap[c]

    init {
        listProperty.addListener(ListChangeListener {
            val count = listProperty.groupingBy{it.shortName}.eachCount()
            if (count.filter{it.value > 1}.isEmpty()) {
                listProperty.forEach{nameMap[it] = it.shortName}
            } else {
                listProperty.forEach {
                    if (count[it.shortName]!! > 1) {
                        nameMap[it] = it.shortNameWithTechBase()
                    } else {
                        nameMap[it] = it.shortName
                    }
                }
            }
            if (listProperty.groupingBy{nameMap[it]}.eachCount().filter{it.value > 1}.isNotEmpty()) {
                listProperty.forEach{nameMap[it] = it.fullName}
            }
        })
    }
}