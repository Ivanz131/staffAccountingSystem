package net.ivanz.staffAccountingSystem.controller;

import net.ivanz.staffAccountingSystem.AbstractTest;
import net.ivanz.staffAccountingSystem.integration.UserRoles;
import net.ivanz.staffAccountingSystem.models.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getAllUsers() throws Exception {
        String uri = "/api/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List userList = super.mapFromJson(content, List.class);
        assertTrue(userList.size() > 0);
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void createUser() throws Exception{
        String uri = "/api/users";
        User user = new User("1","user1", "password1", new ArrayList<>(Arrays.asList(UserRoles.ADMIN)),"mail","ivan","famili");
        String inputJson = super.mapToJson(user);
        // TODO cant convert to json, find another way to do it
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
//        String content = mvcResult.getResponse().getContentAsString();
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {

    }
}