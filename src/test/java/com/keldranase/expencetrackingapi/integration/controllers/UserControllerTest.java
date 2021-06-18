package com.keldranase.expencetrackingapi.integration.controllers;

import com.keldranase.expencetrackingapi.controllers.UserController;
import com.keldranase.expencetrackingapi.services.IUserService;
import com.keldranase.expencetrackingapi.services.SimpleUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.beans.Expression;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

//    @Autowired
//    private MockMvc mockMvc;


    static IUserService userServiceMock;
//    UserController userControllerTestee = new UserController(userServiceMock);

    @BeforeAll
    public static void before() {
        userServiceMock = new SimpleUserService(null);
    }

    @Test
    public void userLoginTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/api/users/login");

        //MvcResult result = mockMvc.perform(request).andReturn();
        //Assertions.assertEquals(result.getResponse(), 5);
        //Assertions.assertThrows(RuntimeException.class, () -> { int a = 5 / 0;});

    }

}
