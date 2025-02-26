package com.example.duannhom10.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Product.ChiTietSanPhamActivity;
import com.example.duannhom10.R;
import com.example.duannhom10.model.Product;
import com.example.duannhom10.ui.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private RecyclerView rvSearchResults;
    private MainAdapter mainAdapter;
    private ArrayList<Product> searchResultsList;
    private ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        rvSearchResults = findViewById(R.id.rv_search_results);
        back = findViewById(R.id.backtt);

        searchResultsList = new ArrayList<>();
        mainAdapter = new MainAdapter(this, searchResultsList, new MainAdapter.ItemClickListener() {
            @Override
            public void onItemCick(Product product) {
                openProductDetails(product);
            }
        });

        rvSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        rvSearchResults.setAdapter(mainAdapter);

        String query = getIntent().getStringExtra("SEARCH_QUERY");
        if (query != null && !query.trim().isEmpty()) {
            performSearch(query);
        }

        back.setOnClickListener(v -> finish());
    }

    private void performSearch(String query) {
        DBHelper dbHelper = new DBHelper(this);
        List<Product> results = dbHelper.searchProducts(query);
        searchResultsList.clear();
        searchResultsList.addAll(results);
        mainAdapter.notifyDataSetChanged();
    }

    private void openProductDetails(Product product) {
        Intent intent = new Intent(SearchResults.this, ChiTietSanPhamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtinsanpham", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
