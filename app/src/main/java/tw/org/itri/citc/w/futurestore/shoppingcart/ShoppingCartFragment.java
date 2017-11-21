package tw.org.itri.citc.w.futurestore.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tw.org.itri.citc.w.futurestore.R;


public class ShoppingCartFragment extends Fragment{
    private static final String TAG = "ShoppingCartFragment";

    private List<Product> products;
    private ShoppingCartAdapter adapter;

    private ListView shoppingCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        products = new ArrayList<Product>();
        Product product = null;

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

        adapter = new ShoppingCartAdapter(products, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);

        shoppingCart = view.findViewById(R.id.shopping_cart);
        shoppingCart.setAdapter(adapter);

        return view;
    }
}
