package org.megamek.mekbuilder.unit;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MekConfigurationTest {

    @Test
    void testOneConfigurationForEachMechSubtype() {
        final Map<MekConfiguration.SubType, Integer> map = new EnumMap<>(MekConfiguration.SubType.class);

        for (MekConfiguration c : MekConfiguration.getConfigurations(UnitType.BATTLE_MEK)) {
            map.merge(c.getSubType(), 1, Integer::sum);
        }

        assertTrue(Arrays.stream(MekConfiguration.SubType.values()).allMatch(st -> map.get(st) == 1));
    }


    @Test
    void testOneConfigurationForEachIndustrialMechSubtype() {
        final Map<MekConfiguration.SubType, Integer> map = new EnumMap<>(MekConfiguration.SubType.class);
        /*  The battlemek list will give all subtypes available; filter out LAM and QV to get the
            list of subtypes that should be present for industrialmeks.
         */
        List<MekConfiguration.SubType> imekSubtypes = MekConfiguration.getConfigurations(UnitType.BATTLE_MEK)
                .stream().filter(c -> !c.getBaseType().equals(MekConfiguration.BaseType.LAM)
                    && !c.getBaseType().equals(MekConfiguration.BaseType.QUADVEE))
                .map(c -> c.getSubType()).collect(Collectors.toList());

        for (MekConfiguration c : MekConfiguration.getConfigurations(UnitType.INDUSTRIAL_MEK)) {
            map.merge(c.getSubType(), 1, Integer::sum);
        }

        assertTrue(imekSubtypes.stream().allMatch(st -> map.get(st) == 1));
    }
}