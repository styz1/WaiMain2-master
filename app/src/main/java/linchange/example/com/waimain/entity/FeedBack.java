package linchange.example.com.waimain.entity;

/**
 * Created by Administrator on 2018/5/18.
 */

public class FeedBack {
    private Integer id;
    private String  userId;
    private String detail;
    private String userContactWay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserContactWay() {
        return userContactWay;
    }

    public void setUserContactWay(String userContactWay) {
        this.userContactWay = userContactWay;
    }

}
