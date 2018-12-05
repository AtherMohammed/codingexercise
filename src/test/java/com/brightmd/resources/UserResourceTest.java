package com.brightmd.resources;

import com.brightmd.db.dao.UserDao;
import com.brightmd.db.model.User;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserResourceTest {

    private static final UserDao dao = mock(UserDao.class);
    private final User user = new User(1,"AtherHussain", "mohammed", "97078", "hussainmoha@gmail.com");

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new UserResource(dao)).build();

    @Before
    public void setup() {
        when(dao.getUserById(eq(1))).thenReturn(user);
    }

    @After
    public void tearDown(){
        // reset the class rules and mocks
        reset(dao);
    }

    @Test
    public void testGetUser() {
        assertThat(resources.target("/profiles/users/1").request().get(User.class)).isEqualTo(user);
        verify(dao).getUserById(1);
    }


    @Test
    public void testCreateUser()  {
        when(dao.getUserById(eq(1))).thenReturn(null);
        when(dao.createUser(user)).thenReturn(1);
        final Response response = resources.target("/profiles/users")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        verify(dao).createUser(user);
    }


    @Test
    public void testCreateUserForexistingUser()  {
        when(dao.getUserById(eq(1))).thenReturn(user);
        when(dao.createUser(user)).thenReturn(1);
        final Response response = resources.target("/profiles/users")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao,times(0)).createUser(user);
    }

    @Test
    public void testUpdateUser()  {
        final Response response = resources.target("/profiles/users/1")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).updateUser(user);
    }

    @Test
    public void testDeleteUser()  {
        final Response response = resources.target("/profiles/users/1")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);
        verify(dao).deleteUser(1);
    }
}
