public class PickupPointShipment extends ShipmentOrder {

    private String lockerSize;
    private boolean fragile;

    public PickupPointShipment(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured, double packageWeightKg, boolean weekendDelivery) {
        super(orderNumber, customerName, distanceKm, baseFee, insured);
    this.lockerSize = lockerSize;
    this.fragile = fragile;
    }

    @Override
    public String getShipmentType() {
        return "Pickup point";
    }

    @Override
    protected double calculateBasePrice() {
        return baseFee + distanceKm * 0.75;
    }

    @Override
    protected double calculateAdditionalFee() {
        double fee = switch (lockerSize) {
            case "S" -> 5.0;
            case "M" -> 10.0;
            case "L" -> 18.0;
        }; if (fragile) {
            fee += 12.0;
        }
        return fee;
    }

    @Override
    protected void validateSpecificRules() {
    if (!"S".equals(lockerSize) && !"M".equals(lockerSize) && !"L".equals(lockerSize)) {
    throw new IllegalArgumentException("Invalid locker size");
    }
    }


}
