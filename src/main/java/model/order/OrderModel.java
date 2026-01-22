package model.order;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
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

}
