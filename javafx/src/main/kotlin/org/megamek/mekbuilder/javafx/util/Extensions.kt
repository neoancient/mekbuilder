package org.megamek.mekbuilder.javafx.util

import org.megamek.mekbuilder.component.Component
import org.megamek.mekbuilder.component.ComponentLibrary

/**
 *
 */

fun String.component(): Component? = ComponentLibrary.getInstance().getComponent(this)