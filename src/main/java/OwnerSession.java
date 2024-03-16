import java.util.List;

public class OwnerSession {

    public static String getMenu() {
        return "Owner functions: Seller functions, Cashier functions, Edit roles, User report, Cancelled transactions report";
    }

    public static boolean isValidFunction(String input) {
        if (input == null) {
            return false;
        }

        if (input.equals("Seller functions") || input.equals("Cashier functions") || input.equals("Edit roles") || input.equals("User report") || input.equals("Cancelled transactions report")){
            return true;
        } else {
            return false;
        }
    }

    public static void editRoles(User u, Role r) {
        u.setRole(r);
    }

    public static String userReport(List<User> users) {
        String ret = "";
        for (User u : users) {
            ret = ret + u.getName() + " : " + u.getRole().toString() + "\n";
        }
        return ret;
    }

    public static String cancelledTransactionReport(List<Transaction> transactions) {
        boolean anyCancelled = false;
        String ret = "";
        for (Transaction t : transactions) {
            if (t.isCancelled()) {
                ret = ret + t.getTimestamp().toString() + ": " + t.getUsername() + " cancelled due to: " + t.getCancellationReason() + "\n";
                //System.out.println(t.getTimestamp().toString() + ": " + t.getUsername() + " cancelled due to: " + t.getCancellationReason());
                anyCancelled = true;
            }
        }
        if (anyCancelled == false) {
            ret = "No cancelled transactions...\n";
            //System.out.println("No cancelled transactions...");
        }
        return ret;
    }
}
