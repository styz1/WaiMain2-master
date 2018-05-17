package linchange.example.com.waimain.bean;

import com.google.gson.annotations.SerializedName;


public class PaymentPlatform {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("icon")
    String iconUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
