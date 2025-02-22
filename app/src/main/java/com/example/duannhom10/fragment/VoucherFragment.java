package com.example.duannhom10.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Admin.VoucherAdapter;
import com.example.duannhom10.R;
import com.example.duannhom10.model.Voucher;
import com.example.duannhom10.ui.DBHelper;
import com.example.duannhom10.ui.Ultils;

import java.util.ArrayList;
import java.util.List;


public class VoucherFragment extends Fragment {
    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<Voucher> voucherList;
    private DBHelper dbHelper;
    private Button back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity()); // Use getActivity() to get the context
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);
        voucherList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view_vouchers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Use getActivity() here
        adapter = new VoucherAdapter(voucherList);
        recyclerView.setAdapter(adapter);
        loadVoucherList();
        return view; // Return the inflated view
    }
    private void loadVoucherList() {
        voucherList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Ultils.VOUCHER_ID,
                Ultils.VOUCHER_CODE,
                Ultils.VOUCHER_DISCOUNT,
                Ultils.VOUCHER_START_DATE,
                Ultils.VOUCHER_END_DATE,
                Ultils.VOUCHER_PRODUCT_ID
        };
        Cursor cursor = db.query(
                Ultils.TABLE_VOUCHER,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String voucherId = cursor.getString(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_ID));
            String voucherCode = cursor.getString(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_CODE));
            int voucherProductId = cursor.getInt(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_PRODUCT_ID));
            int voucherDiscount = cursor.getInt(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_DISCOUNT));
            long voucherStart = cursor.getLong(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_START_DATE));
            long voucherEnd = cursor.getLong(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_END_DATE));
            Voucher voucher = new Voucher(voucherId, voucherCode,voucherProductId,voucherDiscount, voucherStart, voucherEnd);
            voucherList.add(voucher);
        }
        cursor.close();
        VoucherAdapter adapter = new VoucherAdapter(voucherList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }




}
