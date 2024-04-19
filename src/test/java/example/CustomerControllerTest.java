package example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @TestFactory
    Stream<DynamicTest> customer_add_and_select() {
        Map<String, String> param = new HashMap<>();
        param.put("firstName", "from");
        param.put("lastName", "itive");

        return Stream.of(
                dynamicTest("고객의 데이터를 추가한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(param)
                            .when().post("/customers")
                            .then().log().all()
                            .statusCode(200);
                }),
                dynamicTest("고객의 데이터를 조회한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(param)
                            .when().get("/customers")
                            .then().log().all()
                            .statusCode(200).body("size()",is(1));
                })
        );
    }

}