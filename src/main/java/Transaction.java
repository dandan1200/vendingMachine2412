import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transaction {
    private String username;
    private Timestamp timestamp;
    private double total;
    private List<Product> products = new ArrayList<Product>();

    private boolean isCancelled;

    private String cancellationReason;

    public Transaction(String username, String timestamp, double total, JSONArray productsJSON, boolean isCancelled, String cancellationReason, boolean JSON) {
        this.username = username;
        this.total = total;
        this.timestamp = Timestamp.valueOf(timestamp);
        this.cancellationReason = cancellationReason;
        this.isCancelled = isCancelled;

        JSONArray chocolates = null;
        JSONArray drinks = null;
        JSONArray chips = null;
        JSONArray candies = null;

        for (int i = 0; i < productsJSON.size(); i++) {
            JSONObject product = (JSONObject) productsJSON.get(i);
            products.add(new Product((String) product.get("name"), Double.parseDouble((String) product.get("price")), Integer.parseInt((String) product.get("quantity")), ProductType.valueOf((String) product.get("type"))));
        }
        this.calculateTotal();
    }

    public Transaction(String username, String timestamp, double total, List<Product> products, boolean isCancelled, String cancellationReason) {
        this.username = username;
        this.total = total;
        this.products = products;
        this.timestamp = Timestamp.valueOf(timestamp);
        this.cancellationReason = cancellationReason;
        this.isCancelled = isCancelled;
        this.calculateTotal();
    }


    public String getUsername() {
        return this.username;
    }
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    public boolean isCancelled() {
        return this.isCancelled;
    }

    public double getTotal() {
        return this.total;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public String getCancellationReason() {
        if (this.isCancelled) {
            return this.cancellationReason;
        } else {
            return null;
        }
    }

    private void calculateTotal() {
        this.total = 0;

        for (int i = 0; i < this.products.size(); i++) {
            this.total += (this.products.get(i).getPrice() * this.products.get(i).getQuantity());
        }
    }

    public JSONObject toJSONString() {
        JSONObject newObj = new JSONObject();
        newObj.put("total", String.valueOf(this.total));
        if (this.isCancelled) {
            newObj.put("isCancelled", "T");
        } else {
            newObj.put("isCancelled", "F");
        }
        newObj.put("cancellationReason", this.cancellationReason.toString());
        newObj.put("username", this.username);
        newObj.put("timestamp", this.timestamp.toString());
        JSONArray products = new JSONArray();
        for (int i = 0; i < this.products.size(); i++){
            products.add(this.products.get(i).toJSONString());
        }
        newObj.put("products", products);
        return newObj;
    }
}
