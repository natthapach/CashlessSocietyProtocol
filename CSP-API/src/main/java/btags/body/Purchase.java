package btags.body;

public class Purchase {
    private String name;
    private int amt;
    private double price;

    public Purchase(String name, int amt, double price) {
        this.name = name;
        this.amt = amt;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getAmt() {
        return amt;
    }

    public double getPrice() {
        return price;
    }
}
