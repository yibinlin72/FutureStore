package tw.org.itri.citc.w.futurestore.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import tw.org.itri.citc.w.futurestore.R;


public class ShoppingCartAdapter extends BaseAdapter {
    private List<Product> products;
    private Context context;

    public ShoppingCartAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(products != null) {
            ret = products.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if (convertView != null) {
            v = convertView;
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        ViewHolder holder = (ViewHolder) v.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.item_product_name = (TextView) v.findViewById(R.id.product_name);
            holder.item_product_price = (TextView) v.findViewById(R.id.product_price);
            holder.item_product_quantity = (TextView) v.findViewById(R.id.product_quantity);
            holder.item_subtotal = (TextView) v.findViewById(R.id.subtotal);
        }

        holder.item_product_name.setText(products.get(position).getName());
        holder.item_product_price.setText("" + products.get(position).getPrice());
        holder.item_product_quantity.setText("" + products.get(position).getQuantity());
        holder.item_subtotal.setText("NT$ " + products.get(position).getSubTotal());

        v.setTag(holder);
        return v;
    }

    public void refreshCart(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        private TextView item_product_name;
        private TextView item_product_price;
        private TextView item_product_quantity;
        private TextView item_subtotal;
    }

}

