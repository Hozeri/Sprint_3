import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertFalse;

public class OrdersListTest {

    @Test
    public void testNoBodyDataCodeOk() {
        OrderClient orderClient = new OrderClient();
        List<String> ordersList = orderClient.getOrderList()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .path("orders");
        assertFalse("There are no orders in the list", ordersList.isEmpty());
    }
}
