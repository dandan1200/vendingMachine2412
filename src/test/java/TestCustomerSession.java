import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomerSession {

    @Test
    public void testGetCommands() {
        CustomerSession cs = new CustomerSession(null);
        assertEquals(cs.getCommands(), "Available Commands: 'q' to quit, 'menu' to view all products, 'buy' to purchase products. \n" +
                "When buying stuff, 'cancel' to cancel the entire purchase and 'complete' to finish purchase \n" +
                "After completion of cancellation of a purchase, you will be redirected to the home page.");
    }

    @Test
    public void testConstructor() {
        assertNotNull(new CustomerSession(null));
    }

    @Test
    public void testProductIsAvailable() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cola", 2.0, 10, ProductType.DRINK));
        CustomerSession cs = new CustomerSession(products);

        assertTrue(cs.productIsAvailable("Cola"));
        assertFalse(cs.productIsAvailable("Pepsi"));
    }

    @Test
    public void testQuantityIsAvailable() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cola", 2.0, 10, ProductType.DRINK));
        CustomerSession cs = new CustomerSession(products);

        assertTrue(cs.checkQuantityIsEnough("Cola", 5));
        assertFalse(cs.checkQuantityIsEnough("Cola", 20));
    }

    @Test
    public void testProductPrice() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cola", 2.0, 10, ProductType.DRINK));
        CustomerSession cs = new CustomerSession(products);

        assertEquals(cs.getProductPrice("Cola"), 2.0);
        assertEquals(cs.getProductPrice("pepsi"), 0.0);
    }
}
