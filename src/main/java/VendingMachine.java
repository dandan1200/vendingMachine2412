import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Writer;
import java.io.FileWriter;

public class VendingMachine {
    private static final String configPath = "src/main/resources/VendingMachineConfig.json";

    private User loggedInUser = new User("anon", "anon", Role.ANONYMOUS);

    private LoadData fileData;
    private Cash cash;
    private Scanner scan;

    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        vm.setFileData(new LoadData(configPath));
        vm.start();
    }


    public void start() {
        //App entry point
        LoadData data = new LoadData(configPath);
        this.fileData = data;
        // this.cash = data.getCash();  

        System.out.println("Welcome to the Vending Machine Program!");
        System.out.println("Below is a list of available products: ");

        System.out.println("Chocolates: ");
        for (Product p : data.getProducts()) {
            if (p.getCategory() == ProductType.CHOCOLATE) {
                System.out.println("    - " + p.getName() + ".");
            }

        }
        System.out.println("Drinks: ");
        for (Product p : data.getProducts()) {
            if (p.getCategory() == ProductType.DRINK) {
                System.out.println("    - " + p.getName() + ".");
            }
        }
        System.out.println("Chips: ");
        for (Product p : data.getProducts()) {
            if (p.getCategory() == ProductType.CHIPS) {
                System.out.println("    - " + p.getName() + ".");
            }
        }
        System.out.println("Candies: ");
        for (Product p : data.getProducts()) {
            if (p.getCategory() == ProductType.CANDIES) {
                System.out.println("    - " + p.getName() + ".");
            }
        }

        this.scan = new Scanner(System.in);
        boolean loggedIn = false;
        while (loggedIn == false) {
            System.out.println("Do you want to continue as an anonymous user? (Y/N)");
            String userInput = scan.nextLine();

            if (userInput.equalsIgnoreCase("N")) {
                while (true) {
                    System.out.println("Do you want to register (r) or login (l)?");
                    userInput = scan.nextLine();

                    if (userInput.equals("r")) {
                        //Register
                        this.register(scan);
                        loggedIn = true;
                        break;
                    } else if (userInput.equals("l")) {
                        //Login
                        this.login(scan);
                        loggedIn = true;
                        break;
                    }
                }
            } else if (userInput.equalsIgnoreCase("Y")){
                break;
            }
        }

        //User has logged at this point


        goToHome();

    }

    private void goToHome() {
        switch (this.loggedInUser.getRole()) {
            case OWNER :
                this.ownerHome();
                break;
            case CASHIER :
                this.cashierHome();
                break;
            case CUSTOMER :
                this.customerHome();
                break;
            case ANONYMOUS :
                this.anonHome();
                break;
            case SELLER :
                this.sellerHome();
                break;
        }
    }

    private void ownerHome() {
        System.out.println("Enter 'q' to quit.");
        System.out.println(OwnerSession.getMenu());
        String input = scan.nextLine();

        while (!OwnerSession.isValidFunction(input)) {
            if (input.equals("q")) {
                break;
            }
            System.out.println("Error, invalid input!");
            System.out.println(OwnerSession.getMenu());
            input = scan.nextLine();
        }

        if (input.equals("Seller functions")) {
            this.sellerHome();
        } else if (input.equals("Cashier functions")) {
            this.cashierHome();
        } else if (input.equals("Edit roles")) {
            User user = null;
            Role role = null;

            boolean validUsername = false;
            while (validUsername == false) {
                System.out.println("Enter the username for the user you would like to edit:");
                String username = scan.nextLine();
                for (User u : this.fileData.getUsers()) {
                    if (u.getName().equals(username) && u.equals(this.loggedInUser) == false) {
                        validUsername = true;
                        user = u;
                        break;
                    }
                }
                if (validUsername == false) {
                    System.out.println("Invalid username, reenter a valid username.");
                }

            }
            boolean validRole = false;
            while (validRole == false) {
                System.out.println("Enter the role to give the user (Cashier, Seller, Owner, Customer) :");
                String roleString = scan.nextLine();
                try {
                    role = Role.valueOf(roleString.toUpperCase());
                    validRole = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Role, try again.");
                }
            }

            OwnerSession.editRoles(user, role);
            System.out.println("Role updated!\n");
            this.fileData.usersUpdated();


        } else if (input.equals("User report")) {
            System.out.print(OwnerSession.userReport(this.fileData.getUsers()));
            System.out.println();
        } else if (input.equals("Cancelled transactions report")) {
            System.out.print(OwnerSession.cancelledTransactionReport(this.fileData.getTransactions()));
            System.out.println();
        } else if (input.equals("q")) {
            return;
        }
        this.ownerHome();
    }

    private void cashierHome() {
        System.out.println("Enter 'q' to quit.");
        System.out.println(cashierSession.getMenu());
        String input = scan.nextLine();

        while (cashierSession.isValidFunction(input) == false) {
            if (input.equals('q') == false) {
                break;
            }
            System.out.println("Error, invalid input!");
            System.out.println(cashierSession.getMenu());
            input = scan.nextLine();
        }

        if (input.equals("edit change")){
            System.out.println("Enter 50 dollar bills: ");
            int fifty = scan.nextInt();
            System.out.println("Enter 20 dollar bills: ");
            int twenty = scan.nextInt();
            System.out.println("Enter 10 dollar bills: ");
            int ten = scan.nextInt();
            System.out.println("Enter 5 dollar bills: ");
            int five = scan.nextInt();
            System.out.println("Enter 2 dollar coins: ");
            int two = scan.nextInt();
            System.out.println("Enter 1 dollar coins: ");
            int one = scan.nextInt();
            System.out.println("Enter 50 cent coins: ");
            int fiftyCents = scan.nextInt();
            System.out.println("Enter 20 cent coins: ");
            int twentyCents = scan.nextInt();
            System.out.println("Enter 10 cent coins: ");
            int tenCents = scan.nextInt();
            System.out.println("Enter 5 cent coins: ");
            int fiveCents = scan.nextInt();
            Cash cash = new Cash(fifty, twenty, ten, five, two, one, fiftyCents, twentyCents, tenCents, fiveCents);
            cashierSession.editChange(fileData.getCash(),cash);
            fileData.cashUpdated();



        } else if (input.equals("q")) {
            return;
        } else if (input.equals("change report")) {
            System.out.println(cashierSession.getReport(fileData.getCash()));
        }


        
        this.cashierHome();
    }
    private void customerHome() {
        String input = "";
        CustomerSession cs = new CustomerSession(this.fileData.getProducts());

        // Loop of interaction with customers
        while (!input.equals("q")) {
            System.out.println("Hi, " + this.loggedInUser.getName() + " Welcome. What would you like for today?");
            System.out.println(cs.getCommands());
            input = scan.nextLine();

            // Check the correctness of user input
            while (!cs.isValidCommand(input)) {
                if (input.equals("q")) break;
                System.out.println("Command does not exist. Try again");
                System.out.println(cs.getCommands());
                input = scan.nextLine();
            }

            // Now Start customers' operations
            // Customers want to see all available products
            if (input.equals("menu")) {
                this.viewAllProducts();
            }

            // Customer starts a purchase
            else if (input.equals("buy")) {
                List<Product> productsToBuy = new ArrayList<Product>();
                boolean isCancelled = false;
                boolean isCompleted = false;

                // The loop for adding items into the purchase
                while (!isCancelled && !isCompleted) {  // The purchase finishes when it is cancelled or completed
                    System.out.println("During your purchase, enter 'cancel' to cancel the entire purchase, \n" +
                            "'complete' to finish adding items. ");
                    System.out.print("Please enter product name: ");

                    String productName;
                    input = scan.nextLine();
                    productName = input;

                    // Customer wants to complete the purchase
                    if (input.equals("complete")) {
                        if (productsToBuy.size() != 0) {
                            System.out.println("Your products are ready, as follows: ");
                            for (int i = 0; i < productsToBuy.size(); i++) {
                                System.out.println(productsToBuy.get(i).getName() + ": " + productsToBuy.get(i).getQuantity() +
                                        ", $" + productsToBuy.get(i).getQuantity()*productsToBuy.get(i).getPrice());
                            }
                            double total = cs.totalPrice(productsToBuy);
                            System.out.println("Total: $" + total);

                            // Proceed to Payment
                            boolean paymentComplete = false;
                            while(!paymentComplete) {
                                System.out.println("Would you like to pay on Credit card or Cash? \n" +
                                        "Enter 'credit' or 'cash' to pay. 'cancel' to regret. ");
                                input = scan.nextLine();

                                // User wants to pay in credit card
                                if (input.equals("credit")) {
                                    List<CreditCard> creditCards = this.fileData.getCreditCards();

                                    // Check if there is registered credit card for this user.
                                    boolean creditCardRegistered = false;
                                    for (CreditCard c : creditCards) {
                                        if (c.getUsername().equals(this.loggedInUser.getName())) {
                                            System.out.println("$" + total + " is successfully paid with registered credit card. \n" +
                                                    "Now purchase is complete.\n");
                                            isCompleted = true;
                                            creditCardRegistered = true;
                                            paymentComplete = true;
                                            break;
                                        }
                                    }

                                    // Ask if user wants to register one if there isn't one yet.
                                    if (!creditCardRegistered) {
                                        System.out.println("You have no registered credit card. Would you like to register one? \n" +
                                                "'y' to register credit card, anything else to reselect payment method.");

                                        // User says yes.
                                        if (scan.nextLine().equals("y")) {
                                            System.out.println("Enter card holder's name: ");
                                            String cardName = scan.nextLine();
                                            System.out.println("Enter card number: ");
                                            String cardNum = scan.nextLine();

                                            System.out.println("Credit Card successfully registered. Redirect to payment.\n");
                                            // Register the new credit card
                                            this.fileData.addCreditCard(new CreditCard(cardName, cardNum, this.loggedInUser.getName()));
                                        }
                                        // Otherwise just redirect to payment selection
                                    }

                                    // User wants to pay in Cash
                                } else if (input.equals("cash")) {
                                    double totalPayment = 0;
                                    while (true) {
                                        System.out.println("Enter payment amount: ");
                                        String amount = scan.nextLine();
                                        totalPayment += Double.parseDouble(amount);
                                        double change = totalPayment - total;
                                        if (change >= 0) {
                                            System.out.println("Thank you for your purchase in cash. Your Change: $" + change + "\n");
                                            break;
                                        }
                                        else {
                                            System.out.println("total is $" + total + ". $" + totalPayment + " entered. $" +
                                                    change*-1 + " more needed");
                                        }
                                    }

                                    isCompleted = true;
                                    paymentComplete = true;

                                } else if (input.equals("cancel")) {
                                    System.out.println("Purchase canceled. You are now on our black list.");
                                    isCancelled = true;
                                    break;
                                } else {
                                    System.out.println("Invalid command. Try again.");
                                }
                            }
                        }
                        else {
                            System.out.println("Your shopping cart is empty. Purchase cancelled.\n");
                            isCancelled = true;
                        }
                    }

                    else if (input.equals("cancel")) {
                        System.out.println("Purchase canceled");
                        isCancelled = true;
                    }

                    else if (input.equals("menu")){
                        this.viewAllProducts();
                    }

                    // product is available
                    else if (cs.productIsAvailable(productName)) {
                        // Loop for getting a correct quantity
                        while (true) {
                            System.out.print("Please enter quantity: ");
                            String quantity;
                            input = scan.nextLine();
                            quantity = input;
                            int quantityInt;

//                          // Check quantity input are numeric
                            try {
                                quantityInt = Integer.parseInt(quantity);
                            }
                            catch (NumberFormatException e){
                                System.out.println("Please enter positive integer for quantity only. Lets try again :)");
                                continue;
                            }
                            // Check quantity input are positive integers
                            if (quantityInt <= 0) {
                                System.out.println("Please enter positive integer for quantity only. Lets try again :)");
                                continue;
                            }

                            // Check if there are enough number of products in the vending machine
                            if (cs.checkQuantityIsEnough(productName, quantityInt)) {
                                productsToBuy.add(new Product(productName, cs.getProductPrice(productName), quantityInt, cs.getProductCategory(productName)));
                                System.out.println(quantity + " " + productName + " are successfully added to your cart.");
                                break;
                            }
                            else {
                                System.out.println("There are not " + quantity + " " + productName + " in the vending machine. \n" +
                                        "There are only " + cs.checkMaxQuantity(productName) + " right now. \n" +
                                        "You should consider eating less rubbish food.\n");
                            }
                        }
                    }

                    // Input is neither a command nor a product
                    else {
                        System.out.println(input + " is not an available product in our vending machine. \n" +
                                "Enter 'menu' to check all available products.");
                    }

                    // Here, one product entry is done. Either adding more or cancel or complete purchase.
                }

                // Purchase finished, make transaction
                Transaction transaction = cs.makeTransaction(this.loggedInUser.getName(), productsToBuy, isCancelled);
                this.fileData.addTransaction(transaction);
            }
        }
    }
    private void anonHome() {
        this.customerHome();
    }

    private void sellerHome() {

        System.out.println("To obtain reports, enter 'y'. Press any other key to continue");
        String modifier = scan.nextLine();

        if (modifier.equals("y")) {

            System.out.println("What type of report are you after? (item report/sold item report)");
            String _modifier = scan.nextLine();
            if (_modifier.equals("item report")) {
                List<String> toWrite = new ArrayList<String>();

                for (Product p: this.fileData.getProducts()) {
                    String currProduct = p.getName() + ": " + p.getQuantity() + "@ " + p.getPrice();
                    toWrite.add(currProduct);
                }

                try {
                    Writer fileWriter = new FileWriter("src/main/resources/out.txt", false);
                    for (String s: toWrite) {
                        fileWriter.write(s + "\n");
                    }
                    fileWriter.close();
                } catch (Exception e) {
                    System.out.println("Failed to generate report, try again");
                }
            } if (_modifier.equals("sold item report")) {
                HashMap<String, Integer> soldProducts = new HashMap<String, Integer>();
                for (Transaction t : this.fileData.getTransactions()) {
                    if (!t.isCancelled()){
                        for (Product _p : t.getProducts()) {
                            soldProducts.merge(_p.getName(), 1, Integer::sum);
                        }
                    }
                }
                for (String s : soldProducts.keySet()) {
                }
            }


        } else {
            boolean validProduct = false;
            Product productObject = null;

            while (!validProduct) {
                this.viewAllProducts();
                System.out.println("Please select an item");
                String itemName = scan.nextLine();
                for (Product p : this.fileData.getProducts()) {
                    if (p.getName().equals(itemName)) {
                        productObject = p;
                        validProduct = true;
                    }
                } if (!validProduct) {System.out.println("Invalid item, try again.");}
            }

            System.out.println("What would you like to modify? (name, code, category, quantity, price)");
            String toModify = scan.nextLine();
            while (true) {
                if (toModify.equals("name")) {
                    System.out.println("Enter new item name: ");
                    String newName = scan.nextLine();
                    productObject.editName(newName);
                    break;

                } if (toModify.equals("category")) {
                    System.out.println("Enter new item category (drinks, candies, chips, chocolates): ");
                    String newCategory = scan.nextLine();
                    if ((newCategory.equals("drinks")) || (newCategory.equals("candies")) || (newCategory.equals("chips")) || (newCategory.equals("chocolates"))) {
                        productObject.editCategory(ProductType.valueOf(newCategory.toUpperCase()));
                    }
                    break;

                } if (toModify.equals("quantity")) {
                    System.out.println("Enter new item quantity:");
                    int newQuantity = Integer.parseInt(scan.nextLine());
                    if (newQuantity + productObject.getQuantity() <= 15) {
                        productObject.editQuantity(newQuantity);
                    } else {System.out.println("quantity exceeds limit (15), filling to limit.");}
                    break;

                } if (toModify.equals("price")) {
                    System.out.println("Enter new item price:");
                    Double newPrice = Double.parseDouble(scan.nextLine());
                    productObject.editPrice(newPrice);
                    break;

                } else {
                    System.out.println("Invalid input -> try (name, code, category, quantity, price)");
                    toModify = scan.nextLine();
                }

            }
            fileData.productsUpdated();
        }


        // Code to generate report

    }

    private void login(Scanner scan) {

        while (true) {
            System.out.println("Enter username: ");
            String username = scan.nextLine();
            //Add java.io.readpassword
            System.out.println("Enter password: ");
            String password = scan.nextLine();

            for (User u : this.fileData.getUsers()) {
                if (u.getName().equals(username) && u.getPassword().equals(password)) {
                    System.out.println("Success!");
                    this.loggedInUser = u;
                    return;
                }
            }
            System.out.println("Invalid login, try again.");
        }

    }

    private void register(Scanner scan) {
        boolean validUsername = false;
        String usernameIn = null;
        while (validUsername == false) {
            System.out.println("Enter a username");
            usernameIn = scan.nextLine();

            for (User u : this.fileData.getUsers()) {
                if (u.getName().equals(usernameIn)) {
                    System.out.println("That username is already taken! Choose a new username.");
                    validUsername = true;
                    break;
                }
            }
            validUsername = true;
        }
        //Add java.io.readpassword
        System.out.println("Enter a password");
        String password = scan.nextLine();
        this.loggedInUser = new User(usernameIn,password, Role.CUSTOMER);
        this.fileData.addUser(loggedInUser);
    }

    private void setFileData(LoadData data) { this.fileData = data; }

    private void viewAllProducts() {
        System.out.println("Chocolates: ");
        for (Product p : this.fileData.getProducts()) {
            if (p.getCategory() == ProductType.CHOCOLATE) {
                System.out.println("    - " + p.getName() + ": " + p.getPrice() + ", " + p.getQuantity());
            }

        }
        System.out.println("Drinks: ");
        for (Product p : this.fileData.getProducts()) {
            if (p.getCategory() == ProductType.DRINK) {
                System.out.println("    - " + p.getName() + ": " + p.getPrice() + ", " + p.getQuantity());
            }
        }
        System.out.println("Chips: ");
        for (Product p : this.fileData.getProducts()) {
            if (p.getCategory() == ProductType.CHIPS) {
                System.out.println("    - " + p.getName() + ": " + p.getPrice() + ", " + p.getQuantity());
            }
        }
        System.out.println("Candies: ");
        for (Product p : this.fileData.getProducts()) {
            if (p.getCategory() == ProductType.CANDIES) {
                System.out.println("    - " + p.getName() + ": " + p.getPrice() + ", " + p.getQuantity());
            }
        }
    }
}
