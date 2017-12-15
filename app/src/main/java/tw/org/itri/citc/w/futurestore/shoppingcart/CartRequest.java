package tw.org.itri.citc.w.futurestore.shoppingcart;

import com.google.gson.annotations.SerializedName;

public class CartRequest {
    @SerializedName("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
