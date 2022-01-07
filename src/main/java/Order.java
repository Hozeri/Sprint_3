import java.util.List;

public class Order {

    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<ScooterColor> color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<ScooterColor> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getOrder() {
        return new Order("Иван", "Иванов","Москва, Тверская улица, дом 7", "Тверская", "+7 925 777 77 77", 5, "2022-01-01", "Комментарий для курьера", null);
    }

    public void setColor(List<ScooterColor> color) {
        this.color = color;
    }

}
