public abstract class ShipmentOrder implements SummaryPrintable {

    protected String orderNumber;
    protected String customerName;
    protected double distanceKm;
    protected double baseFee;
    protected boolean insured;
    protected double lastCalculatedPrice;

    public ShipmentOrder(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
    }

    public String getOrderNumber() { return orderNumber; }
    public String getCustomerName() { return customerName; }
    public double getDistanceKm() { return distanceKm; }
    public double getBaseFee() { return baseFee; }
    public boolean isInsured() { return insured; }
    public double getLastCalculatedPrice() { return lastCalculatedPrice; }

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();
        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = price;
        printProcessingResult();
    }

    private void validateOrder() {
        if (orderNumber == null || orderNumber.isBlank()) {
            throw new IllegalArgumentException("Numer zamówienia nie może być pusty.");
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Odległość musi być większa od 0.");
        }
    }

    protected void validateSpecificRules() {
    }

    private double applyInsurance(double price) {
        if (insured) {
            price += price * 0.07;
        }
        return price;
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    private void printProcessingResult() {
        System.out.printf("Zamówienie %s przetworzone. Cena końcowa: %.2f PLN%n", orderNumber, lastCalculatedPrice);
    }

    @Override
    public String buildSummaryLine() {
        return String.format("[%s] Klient: %s | Typ: %s | Cena: %.2f PLN",
                orderNumber, customerName, getShipmentType(), lastCalculatedPrice);
    }

    protected abstract double calculateBasePrice();
    protected abstract double calculateAdditionalFee();
    public abstract String getShipmentType();
}