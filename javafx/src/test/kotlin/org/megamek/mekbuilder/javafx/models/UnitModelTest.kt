package org.megamek.mekbuilder.javafx.models

import javafx.embed.swing.JFXPanel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.megamek.mekbuilder.tech.ITechFilter
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.unit.MekBuild
import tornadofx.*

/**
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UnitModelTest {

    @BeforeAll
    fun init() {
        JFXPanel()
    }

    private fun createTechFilter() = object : ITechFilter {
        var _year = 3025
        var _techBase = TechBase.ALL
        var _techLevel = TechLevel.STANDARD

        override fun getYear() = _year
        override fun getTechBase() = _techBase
        override fun getTechLevel() = _techLevel
        override fun getFaction() = null
        override fun eraBasedProgression() = false
        override fun hideExtinct() = true
    }

    @Test
    fun testTechFilterOnModelChange() {
        val filter = createTechFilter()
        val viewModel = UnitViewModel()

        viewModel.techFilter = filter

        assertEquals(viewModel.unitModel.techFilter.getYear(), 3025)
        filter._year = 3030
        assertEquals(viewModel.unitModel.techFilter.getYear(), 3030)
        viewModel.rebind {
            unitModel = MekModel(MekBuild())
        }
        assertEquals(viewModel.unitModel.techFilter.getYear(), 3030)
    }
}