import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLoadData {
    LoadData data = new LoadData("src/test/resources/VendingMachineConfig.json");

    @Test
    public void testUsers() {
        boolean foundTest = false;
        for (int i = 0; i < data.getUsers().size(); i++) {
            if (data.getUsers().get(i).getName().equals("daniel")) {
                foundTest = true;
            }
        }
        assertTrue(foundTest);
    }

    @Test
    public void testProducts() {
        boolean foundTest = false;
        for (int i = 0; i < data.getProducts().size(); i++) {
            if (data.getProducts().get(i).getName().equals("Pepsi")) {
                foundTest = true;
            }
        }
        assertTrue(foundTest);
    }

    @Test
    public void testTransactions() {
        boolean foundTest = false;
        for (int i = 0; i < data.getTransactions().size(); i++) {
            if (data.getTransactions().get(i).getTimestamp().equals(Timestamp.valueOf("2022-10-28 12:27:35.79596"))) {
                foundTest = true;
            }
        }
        assertTrue(foundTest);
    }

    @Test
    public void addUser() {
        User u = new User("test","test", Role.CUSTOMER);
        data.addUser(u);
        boolean foundTest = false;
        for (int i = 0; i < data.getUsers().size(); i++) {
            if (data.getUsers().get(i).getName().equals("test")) {
                foundTest = true;
            }
        }
        assertTrue(foundTest);
    }

    @Test
    public void addTransaction() {
        Timestamp curTime = Timestamp.from(Instant.now());
        Transaction t = new Transaction("test", curTime.toString(), 5.0, new ArrayList<Product>(), false, "None");
        data.addTransaction(t);
        boolean foundTest = false;
        for (int i = 0; i < data.getTransactions().size(); i++) {
            if (data.getTransactions().get(i).getTimestamp().toString().equals(curTime.toString())) {
                foundTest = true;
            }
        }
        assertTrue(foundTest);
    }

    @Test
    public void editRole() {
        OwnerSession o = new OwnerSession();
        o.editRoles(data.getUsers().get(0),Role.OWNER);
        data.usersUpdated();
        assertEquals(data.getUsers().get(0).getRole(),Role.OWNER);
    }

    @Test
    public void editProduct(){
        data.getProducts().get(0).editQuantity(1);
        data.productsUpdated();
        assertEquals(data.getProducts().get(0).getQuantity(), 1);
    }

    @AfterAll
    static void resetFile() {
        try {
            FileReader fr = new FileReader("src/test/resources/VendingMachineConfigBACKUP.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(fr);
            fr.close();

            FileWriter fw = new FileWriter("src/test/resources/VendingMachineConfig.json");
            fw.write(obj.toJSONString());
            fw.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
