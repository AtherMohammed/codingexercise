package com.brightmd.resources;


import com.brightmd.db.dao.UserDao;
import com.brightmd.db.model.User;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;

@Path(UserResource.RESOURCEURL)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserResource.class);
    protected static final String RESOURCEURL = "/profiles/users";
    private final MetricRegistry metrics = new MetricRegistry();
    private final Meter requests = metrics.meter("requests");

    private final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @Timed(name = "get-requests-timed")
    @Metered(name = "get-requests-metered")
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {
        LOGGER.info("User details requested for user with id : {}", id);
        LOGGER.info("Retrieving user with id : {}", id);
        User user = userDao.getUserById(id);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user ).build();
    }

    @POST
    public Response createUser(User user) {
        try {
            LOGGER.info("Add user request initiated..");
            int userId = user.getId();
            if(userDao.getUserById(userId) == null ) {
                LOGGER.info("Creating user ..");
                userId = userDao.createUser(user);
                LOGGER.info("User creation successfull with id : {}", userId);
                return Response.created(new  URI(RESOURCEURL+"/"+String.valueOf(userId))).build();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LOGGER.info("User already exists with id : {}",user.getId());
        return Response.ok(null).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        LOGGER.info("Update user request initiated for user with id : {}", user.getId());
        userDao.updateUser(user);
        LOGGER.info("User updated with id : {}", user.getId());
        return Response.ok(user.toString()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        LOGGER.info("Delete user requested with id : {}", id);
        userDao.deleteUser(id);
        LOGGER.info("User deleted with id : {}", id);
        return Response.noContent().build();
    }

}
