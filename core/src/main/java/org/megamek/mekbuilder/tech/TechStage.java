package org.megamek.mekbuilder.tech;

import megamek.common.annotations.Nullable;

/**
 * Various stages of development of a piece of technology
 *
 * @author: Neoancient
 */
public enum TechStage {
    PROTOTYPE,
    PRODUCTION,
    COMMON,
    EXTINCTION,
    REINTRODUCTION;

    /**
     * @return the next stage in the production cycle. COMMON and REINTRODUCTION are considered
     *         end points each followed by null
     */
    public @Nullable
    TechStage isFollowedBy() {
        switch (this) {
            case PROTOTYPE:
                return PRODUCTION;
            case PRODUCTION:
                return COMMON;
            case EXTINCTION:
                return REINTRODUCTION;
            default:
                return null;
        }
    }
};
