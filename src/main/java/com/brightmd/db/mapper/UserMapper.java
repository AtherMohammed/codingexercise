package com.brightmd.db.mapper;

import com.brightmd.db.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new User( r.getInt(  "id"), r.getString("firstName"), r.getString("lastName"), r.getString("zipcode"),  r.getString("emailaddress"));
    }
}
