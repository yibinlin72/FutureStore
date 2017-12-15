package tw.org.itri.citc.w.futurestore.shoppingcart;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("gid")
    private String id;

    @SerializedName("gname")
    private String name;

    @SerializedName("imgstr")
    private String imageEncodedString;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private int price;

    @SerializedName("amount")
    private int subTotal;

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

    public String getImageUrl() {
        return imageEncodedString;
    }

    public void setImageUrl(String imageUrl) {
        this.imageEncodedString = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
}
