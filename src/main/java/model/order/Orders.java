package model.order;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class Orders {
    private OrderChecked[] orders;
    private PageInfo pageInfo;
    private Station[] availableStation;


}
