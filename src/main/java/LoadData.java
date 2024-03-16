import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadData {
    private String filepath;
    private JSONObject file;

    private List<Product> products = new ArrayList<Product>();

    private List<Transaction> transactions = new ArrayList<Transaction>();

    private List<User> users = new ArrayList<User>();

    private List<CreditCard> creditCards = new ArrayList<CreditCard>();

    private Cash cash;

    public LoadData(String filepath){
        this.filepath = filepath;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filepath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            this.file = (JSONObject) obj;
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        {
            JSONArray productsJSON = (JSONArray) file.get("products");
            for (int i = 0; i < productsJSON.size(); i++) {
                JSONObject product = (JSONObject) productsJSON.get(i);
                products.add(new Product((String) product.get("name"), Double.parseDouble((String) product.get("price")), Integer.parseInt((String) product.get("quantity")), ProductType.valueOf((String) product.get("type"))));
            }
        }



        //Load Products
//        {
//            JSONArray drinksJSON = (JSONArray) ((JSONObject) file.get("products")).get("drinks");
//
//            for (int i = 0; i < drinksJSON.size(); i++) {
//                JSONObject drink = (JSONObject) drinksJSON.get(i);
//                products.add(new Product((String) drink.get("name"), Double.parseDouble((String) drink.get("price")), Integer.parseInt((String) drink.get("quantity")), ProductType.DRINK));
//            }
//
//            JSONArray chocolatesJSON = (JSONArray) ((JSONObject) file.get("products")).get("chocolates");
//
//            for (int i = 0; i < chocolatesJSON.size(); i++) {
//                JSONObject chocolate = (JSONObject) chocolatesJSON.get(i);
//                products.add(new Product((String) chocolate.get("name"), Double.parseDouble((String) chocolate.get("price")), Integer.parseInt((String) chocolate.get("quantity")), ProductType.CHOCOLATE));
//            }
//
//            JSONArray chipsJSON = (JSONArray) ((JSONObject) file.get("products")).get("chips");
//
//            for (int i = 0; i < chipsJSON.size(); i++) {
//                JSONObject chip = (JSONObject) chipsJSON.get(i);
//                products.add(new Product((String) chip.get("name"), Double.parseDouble((String) chip.get("price")), Integer.parseInt((String) chip.get("quantity")), ProductType.CHIPS));
//            }
//
//            JSONArray candiesJSON = (JSONArray) ((JSONObject) file.get("products")).get("candies");
//
//            for (int i = 0; i < candiesJSON.size(); i++) {
//                JSONObject candy = (JSONObject) candiesJSON.get(i);
//                products.add(new Product((String) candy.get("name"), Double.parseDouble((String) candy.get("price")), Integer.parseInt((String) candy.get("quantity")), ProductType.CANDIES));
//            }
//        }

        //Load Transactions
        {
            JSONArray transactionsJSON = (JSONArray) file.get("transactions");
            for (int i = 0; i < transactionsJSON.size(); i++) {
                JSONObject tsxn = (JSONObject) transactionsJSON.get(i);

                this.transactions.add(new Transaction((String) tsxn.get("username"), (String) tsxn.get("timestamp"), Double.parseDouble((String) tsxn.get("total")), (JSONArray) tsxn.get("products"), ((String) tsxn.get("isCancelled")).equals("T"), (String) tsxn.get("cancellationReason"), true));
            }
        }

        //Load users
        {
            JSONArray usersJSON = (JSONArray) file.get("users");
            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject user = (JSONObject) usersJSON.get(i);
                Role r = null;
                if (((String) user.get("role")).equals("OWNER")) {
                    r = Role.OWNER;
                } else if (((String) user.get("role")).equals("CUSTOMER")) {
                    r = Role.CUSTOMER;
                } else if (((String) user.get("role")).equals("CASHIER")) {
                    r = Role.CASHIER;
                } else if (((String) user.get("role")).equals("SELLER")) {
                    r = Role.SELLER;
                }

                this.users.add(new User((String) user.get("username"), (String) user.get("password"), r));
            }
        }

        //Load Credit cards
        {
            JSONArray cardsJSON = (JSONArray) file.get("saved-credit-cards");
            for (int i = 0; i < cardsJSON.size(); i++) {
                JSONObject card = (JSONObject) cardsJSON.get(i);
                for (int j = 0; j < this.users.size(); j++) {
                    if (((String) card.get("username")).equals(users.get(j))) {
                        users.get(j).setCard(new CreditCard((String) card.get("cardName"), (String) card.get("cardNum"), users.get(j).getName()));
                    }
                }
            }
        }

        //Load cash
        {
            JSONObject cashJSON = (JSONObject) file.get("cash");
            int _50 = Integer.parseInt((String) cashJSON.get("50"));
            int _20 = Integer.parseInt((String) cashJSON.get("20"));
            int _10 = Integer.parseInt((String) cashJSON.get("10"));
            int _5 = Integer.parseInt((String) cashJSON.get("5"));
            int _2 = Integer.parseInt((String) cashJSON.get("2"));
            int _1 = Integer.parseInt((String) cashJSON.get("1"));
            int _05 = Integer.parseInt((String) cashJSON.get("0.5"));
            int _02 = Integer.parseInt((String) cashJSON.get("0.2"));
            int _01 = Integer.parseInt((String) cashJSON.get("0.1"));
            int _005 = Integer.parseInt((String) cashJSON.get("0.05"));

            //Create cash object
            this.cash = new Cash(_50,_20,_10,_5,_2,_1,_05,_02,_01,_005);
        }


    }

    public void cashUpdated() {
        this.file.remove("cash");
        this.file.put("cash",this.cash.savetoJson());
        this.savetoFile();
    }

    public Cash getCash() {
        return this.cash;
    }


    public void addCreditCard(CreditCard cc) {
        this.creditCards.add(cc);
        ((JSONArray) file.get("saved-credit-cards")).add(cc.toJSONString());
        savetoFile();
    }

    public List<CreditCard> getCreditCards() { return this.creditCards; }

    public List<Product> getProducts() {
        return this.products;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }
    public List<User> getUsers() { return this.users; }

    public void addUser(User newUser) {
        this.users.add(newUser);
        ((JSONArray) this.file.get("users")).add(newUser.toJSONString());
        this.savetoFile();

    }
    public void usersUpdated() {
        file.remove("users");
        JSONArray usersArray = new JSONArray();
        for (User u : this.users) {
            usersArray.add(u.toJSONString());
        }
        file.put("users", usersArray);
        this.savetoFile();
    }
    public void addTransaction(Transaction newTransaction) {
        this.transactions.add(newTransaction);
        ((JSONArray) this.file.get("transactions")).add(newTransaction.toJSONString());
        this.savetoFile();
    }

//    public void transactionsUpdated() {
//        file.remove("transactions");
//        JSONArray transactionsArray = new JSONArray();
//        for (Transaction t : this.transactions) {
//            transactionsArray.add(t.toJSONString());
//        }
//        file.put("transactions", transactionsArray);
//        this.savetoFile();
//    }

    public void productsUpdated() {
        file.remove("products");
        JSONArray productsArray = new JSONArray();
        for (Product p : this.products) {
            productsArray.add(p.toJSONString());
        }
        file.put("products", productsArray);
        this.savetoFile();
    }

    private boolean savetoFile(){
        try {
            FileWriter fw = new FileWriter(this.filepath);
            fw.write(this.file.toJSONString());
            fw.close();
            return true;
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
