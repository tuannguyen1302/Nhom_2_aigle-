package com.example.duannhom10.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duannhom10.Admin.AdminActivity;
import com.example.duannhom10.R;
import com.example.duannhom10.model.user;
import com.example.duannhom10.ui.DBHelper;
import com.example.duannhom10.ui.MainActivity;


public class LoginActivity extends AppCompatActivity {
    Button btLoginC;
    TextView btRegister;

    EditText edUserNameC,edPasswordC;

    DBHelper db = new DBHelper(LoginActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserNameC=findViewById(R.id.edUserName);
        edPasswordC=findViewById(R.id.edPass);
        btLoginC=findViewById(R.id.btLogin);
        btRegister=findViewById(R.id.btregisterss);
        btLoginC.setOnClickListener(NhanvaoLogin());
        btRegister.setOnClickListener(NhanvaoRegister());
    }
    @NonNull
    private View.OnClickListener NhanvaoRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        };
    }
    @NonNull
    private View.OnClickListener NhanvaoLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserNameC.getText().toString().trim();
                String password = edPasswordC.getText().toString().trim();
                LoginValidation loginValidation = db.CheckCustomer(username, password);
                boolean isValid = (checkUserName(username) && checkPassword(password));
                if (isValid) {
                    if (loginValidation.isCorrect()) {
                        user currentUser = loginValidation.getCurUser();
                        int userId = currentUser.getUserID();
                        saveLoginStatus(userId, username);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (username.equals("admin") && password.equals("admin123")) {
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }
    boolean checkUserName(String username){
        if(username.isEmpty()){
            edUserNameC.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }
        return true;
    }
    boolean checkPassword(String password){
        if(password.isEmpty()){
            edPasswordC.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        return true;
    }
    public void saveLoginStatus(int userId, String username) {
        SharedPreferences preferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userId", userId);
        editor.putString("username", username);
        editor.apply();
    }
}
