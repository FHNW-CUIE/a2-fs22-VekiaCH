package cuie.timecontrol;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeControlTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {
            //nothing else to do
        });
        Platform.setImplicitExit(false);
    }

    private TimeControl tc;

    @BeforeEach
    void setup() {
        tc = new TimeControl(SkinType.DEFAULT_TYPE);
    }

    @Test
    void testSomething() {
        //given

        //when

        //then
    }

}