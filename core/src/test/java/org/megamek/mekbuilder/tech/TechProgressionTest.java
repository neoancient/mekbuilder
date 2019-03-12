package org.megamek.mekbuilder.tech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechProgressionTest {
    @Test
    void testProgression() {
        final TechProgression prog = new TechProgression.Parser("ALL|A|AAAA|2400,2500,3050,2810,3040|2400,2500,2820|STANDARD")
                .parse();

        assertAll(
                () -> assertEquals(prog.introDate().intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON).intValue(), 2820),
                () -> assertNull(prog.getDate(TechStage.EXTINCTION)),
                () -> assertEquals(prog.getDate(TechStage.REINTRODUCTION).intValue(), 3040),
                () -> assertEquals(prog.introDate(false).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE, false).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION, false).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON, false).intValue(), 3050),
                () -> assertEquals(prog.getDate(TechStage.EXTINCTION, false).intValue(), 2810),
                () -> assertEquals(prog.getDate(TechStage.REINTRODUCTION, false).intValue(), 3040),
                () -> assertEquals(prog.introDate(true).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE, true).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION, true).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON, true).intValue(), 2820),
                () -> assertNull(prog.getDate(TechStage.EXTINCTION, true)),
                () -> assertNull(prog.getDate(TechStage.REINTRODUCTION, true)),
                () -> assertFalse(prog.extinct(2900)),
                () -> assertTrue(prog.extinct(2900, false)),
                () -> assertFalse(prog.extinct(2900, true))
        );
    }
}