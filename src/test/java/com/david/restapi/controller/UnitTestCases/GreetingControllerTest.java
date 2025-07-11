package com.david.restapi.controller.UnitTestCases;

import com.david.restapi.controller.GreetingController;
import com.david.restapi.models.Greeting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GreetingControllerTest {

    @Test
    void testGreetings(){

        GreetingController greetingController = new GreetingController();
        Greeting greeting = greetingController.greetings("David");

        assertNotNull(greeting);
        assertEquals("Hey Hii David",greeting.getContent());
        assertTrue(greeting.getId()>0);

    }


}
