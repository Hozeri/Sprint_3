import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierLoginParametrizedTest {

    public CourierCredentials courierCredentials;

    public CourierLoginParametrizedTest(CourierCredentials courierCredentials) {
        this.courierCredentials = courierCredentials;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentialsForCourier() {
        return new Object[][]{
                {new CourierCredentials(RandomStringUtils.randomAlphabetic(10), null)},
                {new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10))}
        };
    }

    @Test
    public void testCourierWithPartialCredentialsCodeBadRequest() {
        CourierClient courierClient = new CourierClient();
        courierClient.login(courierCredentials)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

}
