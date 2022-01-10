import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;

public class CourierLoginTests {

    public CourierClient courierClient;
    public Courier courier;
    public CourierCredentials courierCredentials;
    private ArrayList<Integer> courierIds;

    @Before
    public void setUp() {
        courierIds = new ArrayList<>();
        courier = Courier.getRandomCourier();
        courierCredentials = CourierCredentials.getCourierCredentials(courier);
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (!(courierIds.isEmpty())) {
            for (Integer courierId: courierIds) {
                courierClient.delete(courierId);
            }
        }
    }

    @Test
    public void testCourierWithRequiredCredentialsCodeOk() {
        courierClient.create(courier).statusCode(SC_CREATED);
        int courierId = courierClient.login(courierCredentials)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .path("id");
        getCourierIdToDelete(courier);
        String strCourierId = Integer.toString(courierId);
        assertFalse("\"id\" is empty", strCourierId.isEmpty());
    }

    @Test
    public void testCourierIsNotExistCodeNotFound() {
        courierClient.login(courierCredentials)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void testCourierWithIncorrectLoginCodeNotFound() {
        courierClient.create(courier).statusCode(SC_CREATED);
        String courierLogin = courierCredentials.getLogin();
        courierCredentials.setLogin(courierLogin + "1");
        courierClient.login(courierCredentials)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        getCourierIdToDelete(courier);
    }

    @Test
    public void testCourierWithIncorrectPasswordCodeNotFound() {
        courierClient.create(courier).statusCode(SC_CREATED);
        String courierPassword = courierCredentials.getPassword();
        courierCredentials.setPassword(courierPassword + "1");
        courierClient.login(courierCredentials)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        getCourierIdToDelete(courier);
    }

    public void getCourierIdToDelete(Courier courier) {
        Integer courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierIds.add(courierId);
    }

}
