import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreationTests {
    public CourierClient courierClient;
    public Courier courier;
    private ArrayList<Integer> courierIds;

    @Before
    public void setUp() {
        courierIds = new ArrayList<>();
        courier = Courier.getRandomCourier();
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
    public void testCourierWithRequiredDataReturnsCodeCreated() {
        courierClient.create(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        getCourierIdToDelete(courier);
    }

    @Test
    public void testCourierWithTheSameDataCodeConflict() {
        courierClient.create(courier).assertThat().statusCode(SC_CREATED);
        getCourierIdToDelete(courier);
        courierClient.create(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void testCourierWithTheSameLoginCodeConflict() {
        courierClient.create(courier).assertThat().statusCode(SC_CREATED);
        String courierLogin = courier.getLogin();
        getCourierIdToDelete(courier);
        Courier courierWithTheSameLogin = Courier.getRandomCourier();
        courierWithTheSameLogin.setLogin(courierLogin);
        courierClient.create(courierWithTheSameLogin)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    public void getCourierIdToDelete(Courier courier) {
        Integer courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierIds.add(courierId);
    }

}
