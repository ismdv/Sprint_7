package model.order;

public class OrderModel {
    private String firstName;
    private String lastName;
    private String adress;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrderModel(String firstName,
                      String lastName,
                      String adress,
                      String metroStation,
                      String phone,
                      int rentTime,
                      String deliveryDate,
                      String comment,
                      String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
