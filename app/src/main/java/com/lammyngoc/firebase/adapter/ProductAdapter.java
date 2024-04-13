package com.lammyngoc.firebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lammyngoc.firebase.R;
import com.lammyngoc.firebase.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        TextView txtProductId = convertView.findViewById(R.id.txtProductId);
        TextView txtProductName = convertView.findViewById(R.id.txtProductName);
        TextView txtUnitPrice = convertView.findViewById(R.id.txtUnitPrice);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        txtProductId.setText(String.format(Locale.getDefault(), "ID: %d", product.getProductId()));
        txtProductName.setText(product.getProductName());

        // Format price in Vietnamese Dong
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtUnitPrice.setText(currencyFormat.format(product.getUnitPrice()));

        Picasso.get().load(product.getImgLink()).placeholder(R.mipmap.ic_no_img).into(imageView);

        return convertView;
    }
}