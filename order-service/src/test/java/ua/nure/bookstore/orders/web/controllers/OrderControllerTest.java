package ua.nure.bookstore.orders.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ua.nure.bookstore.orders.AbstractIntegrationTest;
import ua.nure.bookstore.orders.testdata.TestDataFactory;

public class OrderControllerTest extends AbstractIntegrationTest {
    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
            var payload =
                    """
                    {
                        "customer" : {
                            "name": "John Doe",
                            "email": "johnD@gmail.com",
                            "phone": "666666666"
                        },
                        "deliveryAddress" : {
                            "addressLine1": "UTH 33",
                            "addressLine2": "Park Str",
                            "city": "Salt Lake City",
                            "state": "Utah",
                            "zipCode": "84044",
                            "country": "USA"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "Product 1",
                                "price": 25.50,
                                "quantity": 1
                            }
                        ]
                    }
                    """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
