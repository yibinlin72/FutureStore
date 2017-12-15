package tw.org.itri.citc.w.futurestore.member;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("msg")
    private String msg;

    @SerializedName("uuid")
    private String uuid;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
