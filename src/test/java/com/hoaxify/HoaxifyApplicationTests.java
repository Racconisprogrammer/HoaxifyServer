package com.hoaxify;

import com.hoaxify.domain.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HoaxifyApplicationTests {

    public static final String API_USER_URL = "/api/1.0/users";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void postUser_whenUserIsValid_receiveOk() {
        User user = getUser();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USER_URL, user, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private static User getUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("password");
        return user;
    }

    @Test
    void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = getUser();
        testRestTemplate.postForEntity(API_USER_URL, user, Object.class);
        assertEquals(userRepository().count(), 1);
    }

}
