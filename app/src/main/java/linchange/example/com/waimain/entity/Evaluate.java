package linchange.example.com.waimain.entity;

/**
 * Created by Administrator on 2018/5/20.
 */

public class Evaluate {
    private Integer id;
    //用户id;
    private Integer userId;
    //订单id;
    public  Integer getId() {
        return id;
    }public void    setId(Integer id) {
        this.id = id;
    }public Integer getUserId() {
        return userId;
    }public void    setUserId(Integer userId) {
        this.userId = userId;
    }public Integer getOrderId() {
        return orderId;
    }public void    setOrderId(Integer orderId) {
        this.orderId = orderId;
    }public Integer getBusinessId() {
        return businessId;
    }public void    setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }public String  getDetail() {
        return detail;
    }public void    setDetail(String detail) {
        this.detail = detail;
    }private Integer orderId;
    //店铺id;
    private Integer businessId;
    //评价内容
    private String detail;
}
