package com.example.duannhom10.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.R;
import com.example.duannhom10.ThankYouActivity;
import com.example.duannhom10.model.GioHang;
import com.example.duannhom10.model.Voucher;
import com.example.duannhom10.ui.DBHelper;
import com.example.duannhom10.ui.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ThanhToanActivity extends AppCompatActivity implements ThanhToanAdapter.ThanhToanCallBack {
    RecyclerView rvListCode;
    ArrayList<GioHang> lstPro;
    static TextView tongtienTT;
    EditText voucher;
    Button apply, confirm;
    ImageButton back;
    ThanhToanAdapter productAdapter;
    EditText addre;
    DBHelper dbHelper = new DBHelper(ThanhToanActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        rvListCode = findViewById(R.id.rvList3);
        tongtienTT = findViewById(R.id.payment_total_amount);
        voucher = findViewById(R.id.payment_discount_code_input);
        apply = findViewById(R.id.payment_apply_coupon_button);
        confirm = findViewById(R.id.payment_confirm_button);
        back = findViewById(R.id.backtt);
        addre = findViewById(R.id.edt_Address);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(ThanhToanActivity.this, GioHangActivity.class);
            startActivity(intent);
        });

        confirm.setOnClickListener(view -> {
            // Thực hiện thanh toán
            String username = getUsernameFromSharedPreferences();
            String address = addre.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                return;
            }
            long orderId = dbHelper.addOrderWithDetails(username, MainActivity.manggiohang, address);

            if (orderId != -1) {
                Toast.makeText(ThanhToanActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                MainActivity.manggiohang.clear();
                productAdapter.notifyDataSetChanged();
                EventUtil();
                // Chuyển sang màn hình ThankYouActivity
                Intent i = new Intent(ThanhToanActivity.this, ThankYouActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(ThanhToanActivity.this, "Lỗi trong quá trình thanh toán", Toast.LENGTH_SHORT).show();
            }
        });

        apply.setOnClickListener(v -> applyVoucher());

        EventUtil();
        Intent intent = getIntent();
        ArrayList<GioHang> gioHangList = (ArrayList<GioHang>) intent.getSerializableExtra("giohang");
        productAdapter = new ThanhToanAdapter(this, gioHangList, this);
        rvListCode.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProCallBack(this);
        rvListCode.setAdapter(productAdapter);
    }

    private void applyVoucher() {
        String voucherCode = voucher.getText().toString().trim();

        // Kiểm tra mã giảm giá
        Voucher voucherObj = dbHelper.getVoucherByCode(voucherCode);
        if (voucherObj == null) {
            Toast.makeText(this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra voucher có áp dụng cho sản phẩm trong giỏ hàng không
        boolean isApplicable = false;
        for (GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == voucherObj.getVoucherProductId()) {
                isApplicable = true;
                break;
            }
        }

        if (!isApplicable) {
            Toast.makeText(this, "Mã giảm giá không áp dụng cho sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Áp dụng giảm giá
        int discount = voucherObj.getDiscount();
        long tongtien = 0;
        for (GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == voucherObj.getVoucherProductId() && !gioHang.isDiscountApplied()) {
                long discountedPrice = gioHang.getGiasp() - discount;
                if (discountedPrice < 0) {
                    discountedPrice = 0;
                }
                gioHang.setGiasp(discountedPrice);
                gioHang.setDiscountApplied(true);
            }
            tongtien += gioHang.getGiasp() * gioHang.getSoluong();
        }

        // Cập nhật UI
        EventUtil();
    }

    public static void EventUtil() {
        double tongTien = 0;
        for (GioHang gioHang : MainActivity.manggiohang) {
            tongTien += gioHang.getGiasp() * gioHang.getSoluong();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtienTT.setText(decimalFormat.format(tongTien) + " VND");
    }

    public String getUsernameFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }


    public void onProductClick(GioHang gioHang) {
        // Handle product click if needed
    }

    @Override
    public void onItemLongClicked(GioHang gioHang) {
        // Handle long-click event
        Toast.makeText(ThanhToanActivity.this, "Long click detected for: " + gioHang.getTensp(), Toast.LENGTH_SHORT).show();
    }
}
