package model.order;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class OrderChecked {
    private int id;
    private String courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private int track;
    private String[] color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private int status;

}
