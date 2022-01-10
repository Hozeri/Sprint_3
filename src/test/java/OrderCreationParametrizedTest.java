import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class OrderCreationParametrizedTest {

    private final List<ScooterColor> color;
    public OrderClient orderClient;
    public String strTrackId;

    public OrderCreationParametrizedTest(List<ScooterColor> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentialsForCourier() {
        return new Object[][]{
                {List.of(ScooterColor.BlACK, ScooterColor.GRAY)},
                {List.of(ScooterColor.BlACK)},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        orderClient.cancel(strTrackId);
    }

    @Test
    public void testOrderWithPartialDataCodeCreated() {
        Faker faker = new Faker();
        Order order = new Order.Builder()
                .withFirstName(faker.name().firstName())
                .withLastName(faker.name().lastName())
                .withAddress(faker.address().streetAddress())
                .withMetroStation("Seventh Avenue")
                .withPhone(faker.phoneNumber().phoneNumber())
                .withRentTime(10)
                .withDeliveryDate("2023-01-01")
                .withComment(faker.rickAndMorty().character())
                .withColor(color)
                .build();
        int trackId = orderClient.create(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .extract()
                .path("track");
        strTrackId = Integer.toString(trackId);
        assertFalse("TrackId is empty", strTrackId.isEmpty());
    }

}
