package linchange.example.com.waimain.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/17.
 */

public class Order implements Serializable {

    private Integer id;
    private Integer businessId;
    private Integer userId;
    private String status;
    private String totalPrice;
    //订单接收人
    private String consignee;
    //订单联系电话
    private String phone;
    //订单支付方式
    private Integer payMethod;
    //是否已经评价
    private String isEvaluate;
    //评价id;
    private Integer EvaluateId;
    //订单详情
    private String detail;
    private String address;
    private String businessName;
    private String businessIcon;

    public String getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(String isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public Integer getEvaluateId() {
        return EvaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        EvaluateId = evaluateId;
    }


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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
