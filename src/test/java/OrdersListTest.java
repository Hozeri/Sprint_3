import org.junit.Test;

import java.util.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

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
        int expectedListSize = 30;
        int expectedCountOfIds = 30;
        int actualCountOfIds = 0;
        System.out.println(ordersList);
        for (Object object : ordersList) {
            if (object.toString().contains("id")) {
                actualCountOfIds++;
            }
        }
        assertEquals("There are no orders in the list", expectedListSize, ordersList.size());
        assertEquals("There are no id for order/orders", expectedCountOfIds, actualCountOfIds);
    }
}
