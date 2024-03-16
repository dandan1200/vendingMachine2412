import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCash {
    // test to add cash to the machine
    @Test
    public void testNewCash(){
        Cash cash = new Cash(50, 20, 10, 5, 2, 1, 50, 20, 10, 5);
        cash.setFifty(10);
        cash.setTwenty(10);
        cash.setTen(10);
        cash.setFive(10);
        cash.setTwo(10);
        cash.setOne(10);
        cash.setFiftyCents(10);
        cash.setTwentyCents(10);
        cash.setTenCents(10);
        cash.setFiveCents(10);
        assertEquals(10, cash.getFifty());
        assertEquals(10, cash.getTwenty());
        assertEquals(10, cash.getTen());
        assertEquals(10, cash.getFive());
        assertEquals(10, cash.getTwo());
        assertEquals(10, cash.getOne());
        assertEquals(10, cash.getFiftyCents());
        assertEquals(10, cash.getTwentyCents());
        assertEquals(10, cash.getTenCents());
        assertEquals(10, cash.getFiveCents());
    }

    @Test
    public void testToString(){
        Cash cash = new Cash(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals("Cash [fifty=10, fiftyCents=10, five=10, fiveCents=10, one=10, ten=10, tenCents=10, twenty=10, twentyCents=10, two=10]", cash.toString());
    }
}
