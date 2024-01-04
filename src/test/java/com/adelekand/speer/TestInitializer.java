package com.adelekand.speer;

import com.adelekand.speer.helpers.DataBuilder;
import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import com.adelekand.speer.repository.NoteRepository;
import com.adelekand.speer.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.lang.String.format;


@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestInitializer.Initializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestInitializer {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    DataBuilder dataBuilder;

    @AfterEach
    void reset() {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(Note.class);
    }

    @Container
    public static MongoDBContainer mongoContainer = new MongoDBContainer("mongo:latest");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    format("spring.data.mongodb.uri=mongodb://%s:%s/speer_test_db", mongoContainer.getHost(), mongoContainer.getMappedPort(27017)));
        }
    }
}