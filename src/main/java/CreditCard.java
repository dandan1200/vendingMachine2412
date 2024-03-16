import org.json.simple.JSONObject;

public class CreditCard {
    private String cardName;
    private String cardNum;
    private String username;

    public String getUsername() {
        return this.username;
    }
    public String getCardName() {
        return this.cardName;
    }
    public String getCardNum() {
        return this.cardNum;
    }
    public CreditCard(String name, String number, String username) {
        this.cardName = name;
        this.cardNum = number;
        this.username = username;
    }

    public JSONObject toJSONString() {
        JSONObject newCC = new JSONObject();
        newCC.put("cardName", cardName);
        newCC.put("cardNum", cardNum);
        newCC.put("username", username);
        return newCC;
    }
}
