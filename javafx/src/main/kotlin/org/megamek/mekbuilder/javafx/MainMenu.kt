package org.megamek.mekbuilder.javafx

import tornadofx.*

/**
 *
 */
class MainMenu: View() {
    override val root = menubar {
        menu("File")
        menu("Edit")
        menu("Help") {
            item("About...")
        }
    }
}