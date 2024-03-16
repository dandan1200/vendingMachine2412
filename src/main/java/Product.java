import org.json.simple.JSONObject;

enum ProductType {
    CANDIES,
    CHIPS,
    CHOCOLATE,
    DRINK
}

public class Product {
    protected String name;
    protected double price;
    protected int quantity;

    protected ProductType category;

    public Product(String name, double price, int quantity, ProductType category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;

    }

    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public ProductType getCategory() {
        return this.category;
    }

    public JSONObject toJSONString() {
        JSONObject product = new JSONObject();
        product.put("quantity", String.valueOf(this.quantity));
        product.put("price", String.valueOf(this.price));
        product.put("name", this.name);
        product.put("type", this.category.toString());
        return product;
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public void editPrice(double newPrice) {
        this.price = newPrice;
    }

    public void editQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void editCategory(ProductType newCategory) {
        this.category = newCategory;
    }
}
