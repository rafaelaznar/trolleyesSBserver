package net.ausiasmarch.trolleyesSBserver;

import net.ausiasmarch.trolleyesSBserver.api.AppController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TrolleyesSBserverApplicationTests {

    @Test
    public void testAppController() {
        AppController homeController = new AppController();
        String result = homeController.info().getBody();
        assertEquals(result, "Welcome to TROLLEYES Server");
    }

}
