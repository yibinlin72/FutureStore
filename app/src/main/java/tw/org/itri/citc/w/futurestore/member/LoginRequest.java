package tw.org.itri.citc.w.futurestore.member;


import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("acc")
    private String account;

    @SerializedName("pwd")
    private String password;

    @SerializedName("fcmtoken")
    private String fcmToken;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}

