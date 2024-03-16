import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class Cash {
    private int fifty;
    private int twenty;
    private int ten;
    private int five;
    private int two;
    private int one;
    private int fiftyCents;
    private int twentyCents;
    private int tenCents;
    private int fiveCents;

    public Cash(int fifty, int twenty, int ten, int five, int two, int one, int fiftyCents, int twentyCents, int tenCents, int fiveCents) {
        this.fifty = fifty;
        this.twenty = twenty;
        this.ten = ten;
        this.five = five;
        this.two = two;
        this.one = one;
        this.fiftyCents = fiftyCents;
        this.twentyCents = twentyCents;
        this.tenCents = tenCents;
        this.fiveCents = fiveCents;
    }

    public int getFifty() {
        return this.fifty;
    }

    public int getTwenty() {
        return this.twenty;
    }

    public int getTen() {
        return this.ten;
    }

    public int getFive() {
        return this.five;
    }   

    public int getTwo() {
        return this.two;
    }

    public int getOne() {
        return this.one;
    }

    public int getFiftyCents() {
        return this.fiftyCents;
    }

    public int getTwentyCents() {
        return this.twentyCents;
    }

    public int getTenCents() {
        return this.tenCents;
    }

    public int getFiveCents() {
        return this.fiveCents;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    public void setTwenty(int twenty) {
        this.twenty = twenty;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public void setFiftyCents(int fiftyCents) {
        this.fiftyCents = fiftyCents;
    }

    public void setTwentyCents(int twentyCents) {
        this.twentyCents = twentyCents;
    }

    public void setTenCents(int tenCents) {
        this.tenCents = tenCents;
    }

    public void setFiveCents(int fiveCents) {
        this.fiveCents = fiveCents;
    }

    public String toString() {
        return "Cash [fifty=" + this.fifty + ", fiftyCents=" + this.fiftyCents + ", five=" + this.five + ", fiveCents="
                + this.fiveCents + ", one=" + this.one + ", ten=" + this.ten + ", tenCents=" + this.tenCents + ", twenty="
                + this.twenty + ", twentyCents=" + this.twentyCents + ", two=" + this.two + "]";
    }

    public JSONObject savetoJson() {
        //NEED HELP WITH THIS
        JSONObject cash = new JSONObject();
        cash.put("50", String.valueOf(this.fifty));
        cash.put("20", String.valueOf(this.twenty));
        cash.put("10", String.valueOf(this.ten));
        cash.put("5", String.valueOf(this.five));
        cash.put("2", String.valueOf(this.two));
        cash.put("1", String.valueOf(this.one));
        cash.put("0.5", String.valueOf(this.fiftyCents));
        cash.put("0.2", String.valueOf(this.twentyCents));
        cash.put("0.1", String.valueOf(this.tenCents));
        cash.put("0.05", String.valueOf(this.fiveCents));
        return cash;

    }
}
