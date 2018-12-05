package com.brightmd.db.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import static io.dropwizard.testing.FixtureHelpers.*;

import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

public class UserTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void convertToJson() throws Exception {
        final User user = new User(1,"AtherHussain", "mohammed", "97078", "hussainmoha@gmail.com");

        final String expected = MAPPER.writeValueAsString(
            MAPPER.readValue(fixture("fixtures/user.json"), User.class));

        AssertionsForClassTypes.assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }


    @Test
    public void JsonToUser() throws Exception {
        final User person = new User(1,"AtherHussain", "mohammed", "97078", "hussainmoha@gmail.com");
        AssertionsForClassTypes.assertThat(MAPPER.readValue(fixture("fixtures/user.json"), User.class)).isEqualTo(person);
    }
}
