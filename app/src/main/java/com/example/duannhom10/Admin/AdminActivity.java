package com.example.duannhom10.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duannhom10.Account.LoginActivity;
import com.example.duannhom10.Product.Product_Activity;
import com.example.duannhom10.R;


public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btn_khachHang = findViewById(R.id.btn_KhachHang);
        Button btn_sanPham = findViewById(R.id.btn_Sanpham);
        Button btn_voucher = findViewById(R.id.btn_Voucher);
        Button LogOut = findViewById(R.id.adminThoat);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.btn_KhachHang) {
                    startActivity(new Intent(getApplicationContext(), Admin_User_Activity.class));
                } else if (id == R.id.btn_Sanpham) {
                    startActivity(new Intent(getApplicationContext(), Product_Activity.class));
                } else if (id == R.id.btn_Voucher) {
                    startActivity(new Intent(getApplicationContext(), AddVoucherActivity.class));
                } else if (id == R.id.adminThoat) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        };

        btn_khachHang.setOnClickListener(clickListener);
        btn_sanPham.setOnClickListener(clickListener);
        btn_voucher.setOnClickListener(clickListener);
        LogOut.setOnClickListener(clickListener);
    }
}
