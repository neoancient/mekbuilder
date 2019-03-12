package org.megamek.mekbuilder.tech;

/**
 * Allows a class to implement {@link ITechProgression} by delegating the implementation to another object.
 * The implementing class only needs to override {@link #techDelegate} to return an instance of {@link ITechProgression}
 * and the remaining default methods will handle passing the method calls through to the delegate.
 *
 */
public interface ITechDelegator extends ITechProgression {

    /**
     * Provides pass-through implementation of {@link ITechProgression} by passing the implementation through
     * to a delegate object.
     *
     * @return The delegate that provides the actual implementation of {@link ITechProgression}
     */
    ITechProgression techDelegate();


    @Override
    default boolean clanTech() {
        return techDelegate().clanTech();
    }

    @Override
    default boolean mixedTech() {
        return techDelegate().mixedTech();
    }

    @Override
    default TechBase techBase() {
        return techDelegate().techBase();
    }

    @Override
    default Rating techRating() {
        return techDelegate().techRating();
    }

    @Override
    default Rating baseAvailability(int era) {
        return techDelegate().baseAvailability(era);
    }

    @Override
    default TechLevel staticTechLevel() {
        return techDelegate().staticTechLevel();
    }

    @Override
    default Integer getDate(TechStage techStage, boolean clan, Faction faction) {
        return techDelegate().getDate(techStage, clan, faction);
    }

}
