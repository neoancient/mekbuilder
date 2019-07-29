package org.megamek.mekbuilder.javafx

import org.megamek.mekbuilder.javafx.unitlayout.StandardLayout
import tornadofx.*

/**
 *
 */
class MekBuilder: App(MainUI::class)

class MainUI: View() {
    val menu: MainMenu by inject()
    val standardLayout: StandardLayout by inject()

    override val root = borderpane {
        top = menu.root
        bottom = hbox()
        center = standardLayout.root
    }
}