package model.order;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class PageInfo {
    private int page;
    private int total;
    private int limit;
}
