import org.json.simple.JSONObject;

enum Role {
    CUSTOMER,
    SELLER,
    CASHIER,
    OWNER,
    ANONYMOUS;
}

public class User {

    private String userName;
    private String password;
    private Role role;

    private CreditCard creditCard = null;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    public void setName(String name) { this.userName = userName; }

    public void setPassword(String password ) { this.password = password; }

    public void setRole(Role role ) { this.role = role; }

    public String getName() { return this.userName; }

    public String getPassword() { return this.password; }

    public Role getRole() { return this.role; }

    public JSONObject toJSONString() {
        JSONObject userObj = new JSONObject();
        userObj.put("username", this.getName());
        userObj.put("password", this.getPassword());
        userObj.put("role", this.getRole().toString());
        return userObj;
    }

    public void setCard(CreditCard cc) {
        this.creditCard = cc;
    }
    public CreditCard getCard() {
        return this.creditCard;
    }

}