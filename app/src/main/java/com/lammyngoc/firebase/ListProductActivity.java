package com.lammyngoc.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lammyngoc.firebase.adapter.ProductAdapter;
import com.lammyngoc.firebase.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    ListView lvProduct;
    ProductAdapter adapter;
    List<Product> productList;
    DatabaseReference databaseProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        addViews();
        loadProducts();
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
    }

    private void loadProducts() {
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void addViews() {
        lvProduct = findViewById(R.id.lvProduct);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        lvProduct.setAdapter(adapter);
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseProducts != null) {
            databaseProducts.removeEventListener((ValueEventListener) this);
        }
    }
}