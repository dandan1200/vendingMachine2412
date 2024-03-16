import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestOwnerSession {
    @Test
    public void testMenu() {
        assertEquals(OwnerSession.getMenu(), "Owner functions: Seller functions, Cashier functions, Edit roles, User report, Cancelled transactions report");
    }

    @Test
    public void testInvalidFunctions(){
        assertFalse(OwnerSession.isValidFunction(null));
        assertFalse(OwnerSession.isValidFunction(""));
        assertFalse(OwnerSession.isValidFunction("Non function"));
    }

    @Test
    public void testValidFunctions(){
        assertTrue(OwnerSession.isValidFunction("Seller functions"));
        assertTrue(OwnerSession.isValidFunction("Cashier functions"));
        assertTrue(OwnerSession.isValidFunction("Edit roles"));
        assertTrue(OwnerSession.isValidFunction("User report"));
        assertTrue(OwnerSession.isValidFunction("Cancelled transactions report"));
    }
    @Test
    public void testEditRoles() {
        User u = new User("x","x",Role.CASHIER);
        OwnerSession.editRoles(u,Role.OWNER);
        assertEquals(u.getRole(),Role.OWNER);
    }

    @Test
    public void testUserReport() {
        User u1 = new User("x1","x1",Role.CASHIER);
        User u2 = new User("x2","x2",Role.OWNER);
        User u3 = new User("x3","x3",Role.SELLER);

        List<User> users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);
        users.add(u3);

        String ret = OwnerSession.userReport(users);
        assertEquals(ret, "x1 : CASHIER\nx2 : OWNER\nx3 : SELLER\n");

    }

    @Test
    public void testTransactionReport() {
        Product p1 = new Product("p1", 1.0, 1, ProductType.CHOCOLATE);
        Product p2 = new Product("p2", 2.0, 2, ProductType.CANDIES);
        Product p3 = new Product("p3", 3.0, 3, ProductType.CHIPS);
        Product p4 = new Product("p4", 4.0, 4, ProductType.DRINK);

        List<Product> pList1 = new ArrayList<Product>();
        pList1.add(p1);
        pList1.add(p2);

        List<Product> pList2 = new ArrayList<Product>();
        pList2.add(p2);
        pList2.add(p3);
        pList2.add(p4);

        List<Product> pList3 = new ArrayList<Product>();
        pList3.add(p4);

        List<Product> pList4 = new ArrayList<Product>();

        List<Transaction> transactions = new ArrayList<>();

        //Test no cancelled
        Transaction t1 = new Transaction("u1", Timestamp.from(Instant.now()).toString(), 0,pList1,false,"none");
        transactions.add(t1);
        assertEquals(OwnerSession.cancelledTransactionReport(transactions),"No cancelled transactions...\n");

        //test 1 cancelled
        Timestamp ts2 = Timestamp.from(Instant.now());
        Transaction t2 = new Transaction("u2", ts2.toString(), 0,pList2,true,"timeout");
        transactions.add(t2);
        assertEquals(OwnerSession.cancelledTransactionReport(transactions),ts2.toString() + ": " + "u2" + " cancelled due to: timeout\n");

        //test 2 cancelled
        Timestamp ts3 = Timestamp.from(Instant.now());
        Transaction t3 = new Transaction("u3", ts3.toString(), 0,pList3,true,"user cancelled");
        transactions.add(t3);
        assertEquals(OwnerSession.cancelledTransactionReport(transactions),ts2.toString() + ": " + "u2" + " cancelled due to: timeout\n" + ts3.toString() + ": " + "u3" + " cancelled due to: user cancelled\n");


    }
}
