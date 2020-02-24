package com.marekhudyma.postgresql.json;

import com.testcompose.TestComposeExtension;
import com.testcompose.annotation.Await;
import com.testcompose.annotation.Compose;
import com.testcompose.annotation.Port;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(TestComposeExtension.class)
@ExtendWith(SpringExtension.class)
@Compose(
        id = "Postgresql",
        exportPorts = {
                @Port(container = "postgres", port = 5432, as = "postgres.port")
        },
        waitFor = {
                @Await(container = "postgres", message = "database system is ready to accept connections")
        }
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ActiveProfiles(profiles = {"api", "test"})
public @interface IntegrationTest {
}