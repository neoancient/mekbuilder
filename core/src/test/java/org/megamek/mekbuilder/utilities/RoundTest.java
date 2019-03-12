package org.megamek.mekbuilder.utilities;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class RoundTest {

    private static final double DELTA = 0.0001;

    @Test
    void roundToTon() {
        assertAll(
                () -> assertEquals(Round.roundToTon(5.1), 6, DELTA),
                () -> assertEquals(Round.roundToTon(5.0), 5.0, DELTA)
        );
    }

    @Test
    void roundToHalfTon() {
        assertAll(
                () -> assertEquals(Round.roundToHalfTon(5.0), 5.0, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.1), 5.5, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.5), 5.5, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.6), 6.0, DELTA)
        );
    }

    @Test
    void roundToKilo() {
        assertAll(
                () -> assertEquals(Round.roundToKilo(5.0), 5.0, DELTA),
                () -> assertEquals(Round.roundToKilo(5.001), 5.001, DELTA),
                () -> assertEquals(Round.roundToKilo(5.0001), 5.0, DELTA),
                () -> assertEquals(Round.roundToKilo(5.0005), 5.001, DELTA)
        );
    }

    @Test
    void round() {
        UnitBuild tonStandard = Mockito.mock(UnitBuild.class);
        Mockito.when(tonStandard.usesKilogramStandard()).thenReturn(false);
        UnitBuild kiloStandard = Mockito.mock(UnitBuild.class);
        Mockito.when(kiloStandard.usesKilogramStandard()).thenReturn(true);

        assertAll(
                () -> assertEquals(Round.round(5.0, tonStandard), 5.0, DELTA),
                () -> assertEquals(Round.round(5.0, kiloStandard), 5.0, DELTA),
                () -> assertEquals(Round.round(5.1, tonStandard), 5.5, DELTA),
                () -> assertEquals(Round.round(5.1, kiloStandard), 5.1, DELTA)
        );
    }
}