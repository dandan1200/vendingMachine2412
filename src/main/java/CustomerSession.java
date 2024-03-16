import java.sql.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CustomerSession {

    private List<Product> products = new ArrayList<Product>();

    public CustomerSession(List<Product> products) {
        this.products = products;
    }
    public String getCommands() {
        return "Available Commands: 'q' to quit, 'menu' to view all products, 'buy' to purchase products. \n" +
                "When buying stuff, 'cancel' to cancel the entire purchase and 'complete' to finish purchase \n" +
                "After completion of cancellation of a purchase, you will be redirected to the home page."
                ;
    }

    public boolean isValidCommand(String input) {
        if (input.equals("q") || input.equals("menu") || input.equals("buy") || input.equals("cancel") || input.equals("complete")) {
            return true;
        }
        return false;
    }

    public Transaction makeTransaction(String userName, List<Product> productsToBuy, boolean isCancelled) {
        // Create the transaction
        Transaction transaction = null;
        Timestamp currentTime = Timestamp.from(Instant.now());
        String currentTimeStr = currentTime.toString();

        if (!isCancelled) {
            transaction = new Transaction(userName, currentTimeStr, this.totalPrice(productsToBuy), productsToBuy, isCancelled, "transaction completed");
        }
        else {
            transaction = new Transaction(userName, currentTimeStr, 0.0, productsToBuy, isCancelled, "Customer clicked cancel");
        }

        return transaction;
    }

    public boolean productIsAvailable(String productName) {

        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getName().equals(productName)) return true;
        }
        return false;
    }

    public boolean checkQuantityIsEnough(String productName, int quantity) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getName().equals(productName)) {
                if (this.products.get(i).getQuantity() >= quantity) return true;
                else return false;
            }
        }
        return false;
    }

    public double getProductPrice(String productName) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getName().equals(productName)) {
                return this.products.get(i).getPrice();
            }
        }
        return 0.0;
    }

    public ProductType getProductCategory(String productName) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getName().equals(productName)) {
                return this.products.get(i).getCategory();
            }
        }
        return null;
    }

    public double totalPrice(List<Product> productsToBuy) {
        double totalPrice = 0.0;
        for (int i = 0; i < productsToBuy.size(); i++) { totalPrice += (productsToBuy.get(i).getPrice()*productsToBuy.get(i).getQuantity()); }

        return totalPrice;
    }

    public int checkMaxQuantity(String productName) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getName().equals(productName)) {
                return this.products.get(i).getQuantity();
            }
        }
        return -1;
    }
}
