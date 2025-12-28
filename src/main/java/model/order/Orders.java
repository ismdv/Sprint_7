package model.order;

public class Orders {
    private OrderChecked[] orders;
    private PageInfo pageInfo;
    private Station[] availableStation;

    public Orders(OrderChecked[] orders, PageInfo pageInfo, Station[] availableStation) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStation = availableStation;
    }

    public OrderChecked[] getOrders() {
        return orders;
    }

    public void setOrders(OrderChecked[] orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Station[] getAvailableStation() {
        return availableStation;
    }

    public void setAvailableStation(Station[] availableStation) {
        this.availableStation = availableStation;
    }
}
