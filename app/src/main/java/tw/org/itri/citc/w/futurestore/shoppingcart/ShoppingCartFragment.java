package tw.org.itri.citc.w.futurestore.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.org.itri.citc.w.futurestore.R;

/**
 * Created on 2017/11/12.
 */

public class ShoppingCartFragment extends Fragment{
    private static final String TAG = "ShoppingCartFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);

        return view;
    }
}
