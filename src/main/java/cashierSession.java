import java.util.List;

public class cashierSession {
    private LoadData data;
    private Cash cash;

    public cashierSession(LoadData data, Cash cash) {
        this.data = data;
        this.cash = cash;
    }

    public static String getMenu() {
        return "Cashier functions: \n" +
        "Enter 'edit change' to Edit Available Change, \n" +
        "Enter 'change report' to View Change Report, \n" +
        "Enter 'sales summary' to View Sales Summary";
    }

    public static boolean isValidFunction(String input) {
        if (input.equals("edit change") || input.equals("change report") || input.equals("sales summary")){
            return true;
        } else {
            return false;
        }
    }

    public static void editChange(Cash oldCash, Cash newCash) {

         oldCash.setFifty(newCash.getFifty());
         oldCash.setTwenty(newCash.getTwenty());
         oldCash.setTen(newCash.getTen());
         oldCash.setFive(newCash.getFive());
         oldCash.setTwo(newCash.getTwo());
         oldCash.setOne(newCash.getOne());
         oldCash.setFiftyCents(newCash.getFiftyCents());
         oldCash.setTwentyCents(newCash.getTwentyCents());
         oldCash.setTenCents(newCash.getTenCents());
         oldCash.setFiveCents(newCash.getFiveCents());

        System.out.println("Change has been updated");
    }

    public static String getReport(Cash c){
        return c.toString();
    }



    
}
