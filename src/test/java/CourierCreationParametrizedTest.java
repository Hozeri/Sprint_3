import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;


@RunWith(Parameterized.class)
public class CourierCreationParametrizedTest {

    private final Courier courier;

    public CourierCreationParametrizedTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getParamsForCourier() {
        return new Object[][]{
                {new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10))},
                {new Courier(RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10))}
        };
    }

    @Test
    public void testCourierWithPartialDataCodeBadRequest() {
        CourierClient courierClient = new CourierClient();
        courierClient.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
