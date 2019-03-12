package org.megamek.mekbuilder.tech;

/**
 * Rating system used for tech rating and availability
 *
 * @author: Neoancient
 */
public enum Rating {
    RATING_A ("A"),
    RATING_B ("B"),
    RATING_C ("C"),
    RATING_D ("D"),
    RATING_E ("E"),
    RATING_F ("F"),
    RATING_FSTAR ("F*"),
    RATING_X ("X");

    public final String displayName;

    Rating(String displayName) {
        this.displayName = displayName;
    }

    public Rating plus(int addend) {
        return values()[Math.max(0, Math.min(ordinal() + addend, values().length - 1))];
    }
}
