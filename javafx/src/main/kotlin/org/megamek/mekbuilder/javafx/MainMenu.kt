package org.megamek.mekbuilder.javafx

import tornadofx.*

/**
 *
 */
class MainMenu: View() {
    override val root = menubar {
        menu(messages["menu.file"])
        menu(messages["menu.edit"])
        menu(messages["menu.help"]) {
            item(messages["menuItem.about"])
        }
    }
}