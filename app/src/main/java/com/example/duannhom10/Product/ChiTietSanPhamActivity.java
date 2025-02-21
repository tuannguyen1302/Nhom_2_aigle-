package com.example.duannhom10.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.R;
import com.example.duannhom10.User.GioHangActivity;
import com.example.duannhom10.model.GioHang;
import com.example.duannhom10.model.Product;
import com.example.duannhom10.ui.MainActivity;
import com.example.duannhom10.ui.MainAdapter;
import com.example.duannhom10.ui.Ultils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity implements MainAdapter.MainCallBack {
    // Views
    ImageView imgchitietsp;
    TextView txttensp, txtgiasp, txtmota;
    Spinner spinner;
    Button btnthemgohang;
    Toolbar toolbarchitiet;

    // Product details
    int Id = 0;
    String Tenchitiet = "";
    int Giachitiet = 0;
    String Hinhanhchitiet = "";
    String Motachitiet = "";
    int Idsanpham = 0;
    ArrayList<Product> lstPro;
    RecyclerView rvProductList;
    ArrayList<Product> lstProOriginal;

    // Adapter for RecyclerView
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        // Initialize views
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();

        // Initialize RecyclerView and Adapter
        rvProductList = findViewById(R.id.rcv_Phone);
        lstProOriginal = ProDataQuery.getALL(this);
        lstPro = ProDataQuery.getALL(this);

        mainAdapter = new MainAdapter(lstPro);
        mainAdapter.setMainCallBack(this);  // Set MainCallBack listener here
        mainAdapter = new MainAdapter(this, lstPro, new MainAdapter.ItemClickListener() {
            @Override
            public void onItemCick(Product product) {
                onClickrecyclerview(product);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvProductList.setAdapter(mainAdapter);
        rvProductList.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menugiohang) {
            Intent intent = new Intent(this, GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickrecyclerview(Product product){
        Intent intent=new Intent(this, ChiTietSanPhamActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("thongtinsanpham", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void EventButton() {
        btnthemgohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                boolean exists = false;

                for (GioHang gioHang : MainActivity.manggiohang) {
                    if (gioHang.getIdsp() == Id) {
                        // Update quantity and price
                        int newQuantity = gioHang.getSoluong() + quantity;
                        if (newQuantity > 10) {
                            gioHang.setSoluong(10);
                        } else {
                            gioHang.setSoluong(newQuantity);
                        }
                        gioHang.setGiasp(gioHang.getSoluong() * Giachitiet);
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    // Add new item to the cart
                    int totalPrice = quantity * Giachitiet;
                    MainActivity.manggiohang.add(new GioHang(Id, Tenchitiet, totalPrice, Hinhanhchitiet, quantity));
                }

                // Navigate to Cart Activity
                Intent intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] quantityOptions = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, quantityOptions);
        spinner.setAdapter(quantityAdapter);
    }

    private void GetInformation() {
        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        Id = product.getProID();
        Tenchitiet = product.getProName();
        Giachitiet = product.getProPrice();
        Hinhanhchitiet = product.getProImage();
        imgchitietsp.setImageBitmap(Ultils.convertToBitmapFromAssets(this, product.getProImage()));
        Motachitiet = product.getProDes();
        txttensp.setText(Tenchitiet);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiasp.setText("Giá: " + decimalFormat.format(Giachitiet) + " Đ");
        txtmota.setText(Motachitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        imgchitietsp = findViewById(R.id.imgchitietsp);
        txttensp = findViewById(R.id.txttensp);
        txtgiasp = findViewById(R.id.txtgiachitietsp);
        txtmota = findViewById(R.id.txtmotachitietsp);
        spinner = findViewById(R.id.spinnerchitietsp);
        btnthemgohang = findViewById(R.id.btnthemgiohang);
        toolbarchitiet = findViewById(R.id.toolbarchitietsp);
    }

    @Override
    public void onItemClick(int id) {
        // Handle item click here
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update spinner based on cart quantity
        for (GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == Id) {
                int index = gioHang.getSoluong() - 1; // Since array starts at 0
                spinner.setSelection(index);
                break;
            }
        }
    }
}
