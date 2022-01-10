import com.github.javafaker.Faker;

import java.util.List;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<ScooterColor> color;

    public static class Builder {

        private final Order newOrder;

        public Builder() {
            newOrder = new Order();
        }

        public Builder withFirstName(String firstName) {
            newOrder.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            newOrder.lastName = lastName;
            return this;
        }

        public Builder withAddress(String address) {
            newOrder.address = address;
            return this;
        }

        public Builder withMetroStation(String metroStation) {
            newOrder.metroStation = metroStation;
            return this;
        }

        public Builder withPhone(String phone) {
            newOrder.phone = phone;
            return this;
        }

        public Builder withRentTime(int rentTime) {
            newOrder.rentTime = rentTime;
            return this;
        }

        public Builder withDeliveryDate(String deliveryDate) {
            newOrder.deliveryDate = deliveryDate;
            return this;
        }

        public Builder withComment(String comment) {
            newOrder.comment = comment;
            return this;
        }

        public Builder withColor(List<ScooterColor> color) {
            newOrder.color = color;
            return this;
        }

        public Order build(){
            return newOrder;
        }

    }

    public void setColor(List<ScooterColor> color) {
        this.color = color;
    }

}
