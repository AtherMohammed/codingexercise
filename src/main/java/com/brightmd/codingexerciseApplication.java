package com.brightmd;

import com.brightmd.db.dao.UserDao;
import com.brightmd.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class codingexerciseApplication extends Application<codingexerciseConfiguration> {

    private static final Logger LOGGER =  LoggerFactory.getLogger(codingexerciseApplication.class);

    public static void main(final String[] args) throws Exception {
        new codingexerciseApplication().run(args);
    }

    @Override
    public String getName() {
        return "codingexercise";
    }

    @Override
    public void initialize(final Bootstrap<codingexerciseConfiguration> bootstrap) {
        // TODO: application initialization

    }

    @Override
    public void run(codingexerciseConfiguration configuration, Environment environment) {
        LOGGER.info("Bootstrap process started for the service :: " + getName());
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "hsqldb");
        UserDao userDao = jdbi.onDemand(UserDao.class);
        LOGGER.info("Database initialization process started...");
        userDao.dropUserTable();
        userDao.createUserTable();
        LOGGER.info("Database initialised. User Table created.");
        environment.jersey().register(new UserResource(userDao));
        LOGGER.info("UserResource initialised.");
    }

}
