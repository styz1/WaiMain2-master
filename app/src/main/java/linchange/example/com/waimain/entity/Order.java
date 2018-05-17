package linchange.example.com.waimain.entity;

/**
 * Created by Administrator on 2018/4/17.
 */

public class Order {

    private Integer id;
    private Integer businessId;
    private Integer userId;
    private String status;
    private String totalPrice;
    private String address;
    private String businessName;
    private String businessIcon;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    public String getBusinessIcon() {
        return businessIcon;
    }

    public void setBusinessIcon(String businessIcon) {
        this.businessIcon = businessIcon;
    }





    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
