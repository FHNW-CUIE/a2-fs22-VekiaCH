package cuie.timecontrol;

import java.util.function.Function;

import javafx.scene.control.SkinBase;

public enum SkinType {
    DEFAULT_TYPE(TimeSkin::new);

    private final Function<TimeControl, SkinBase<TimeControl>> factory;

    SkinType(Function<TimeControl, SkinBase<TimeControl>> factory) {
        this.factory = factory;
    }

    public Function<TimeControl, SkinBase<TimeControl>> getFactory() {
        return factory;
    }

}
