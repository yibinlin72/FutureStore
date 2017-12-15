package tw.org.itri.citc.w.futurestore.shoppingcart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import tw.org.itri.citc.w.futurestore.R;


public class ShoppingCartFragment extends Fragment{
    private static final String TAG = "ShoppingCartFragment";

    private SharedPreferences user_settings;
    private String uuid;

    OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private List<Product> products;
    private ShoppingCartAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        user_settings = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        uuid = user_settings.getString("UUID", "");

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        products = new ArrayList<Product>();
        adapter = new ShoppingCartAdapter(getActivity(), products);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);

        ListView shoppingCart = view.findViewById(R.id.shopping_cart);
        shoppingCart.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
        //TODO: fetch shopping cart while user pick or put product from shelf (i.e., while app receive push notification )
        fetchCart();

    }
/*
    private void generateTestData() {

        Product product;

        product = new Product();
        product.setName("水潤餅");
        product.setPrice(45);
        product.setQuantity(1);
        products.add(product);

        product = new Product();
        product.setName("馬祖老酒麵線");
        product.setPrice(45);
        product.setQuantity(2);
        products.add(product);

        product = new Product();
        product.setName("特美香布丁蛋糕");
        product.setPrice(80);
        product.setQuantity(1);
        products.add(product);

        product = new Product();
        product.setName("豆酥朋");
        product.setPrice(15);
        product.setQuantity(3);
        products.add(product);

        product = new Product();
        product.setName("福源花生醬");
        product.setPrice(125);
        product.setQuantity(1);
        products.add(product);

        product = new Product();
        product.setName("東海鮮奶");
        product.setPrice(99);
        product.setQuantity(2);
        products.add(product);

        product = new Product();
        product.setName("麻花捲");
        product.setPrice(55);
        product.setQuantity(1);
        products.add(product);

        adapter = new ShoppingCartAdapter(getActivity(), products);
    }
*/
    private void fetchCart() {
        Log.d(TAG, "Fetch shopping cart");

        final Gson gson = new Gson();

        // GET method
//        Request request = new Request.Builder()
//                .url("http://140.96.178.1:8081/app/viewcart/?uuid=010395")
//                .build();

        // POST method (x)
//        RequestBody formBody = new FormBody.Builder()
//                .add("uuid", "010395")
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://140.96.178.1:8081/app/viewcart/")
//                .post(formBody)
//                .build();

        // POST method
        String url = "http://140.96.178.1:8081/app/viewcart/";
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUuid(uuid);
        String jsonRequest = gson.toJson(cartRequest);
//        String jsonRequest = "{\"uuid\":\"010395\"}";
        RequestBody body = RequestBody.create(JSON, jsonRequest);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResponse = response.body().string();
                Log.d(TAG, "length of response=" + jsonResponse.length());
                Log.d(TAG, "Response = " + jsonResponse);
                Gson gson = new Gson();
                products  = gson.fromJson(jsonResponse, new TypeToken<ArrayList<Product>>(){}.getType());
                for (Product item: products) {
                    Log.d(TAG, "id= " + item.getId());
                    Log.d(TAG, "name= " + item.getName());
                    Log.d(TAG, "price= " + item.getPrice());
                    Log.d(TAG, "quantity= " + item.getQuantity());
                    Log.d(TAG, "subTotal= " + item.getSubTotal());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refreshCart(products);
                    }
                });
            }
            @Override
            public void onFailure(Call call, final IOException e) {
                //告知使用者連線失敗
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, e.getMessage());
            }
        });
    }

}
