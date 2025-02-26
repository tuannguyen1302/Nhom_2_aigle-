package com.example.duannhom10.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.duannhom10.Account.LoginValidation;
import com.example.duannhom10.model.GioHang;
import com.example.duannhom10.model.Order;
import com.example.duannhom10.model.Product;
import com.example.duannhom10.model.Voucher;
import com.example.duannhom10.model.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NBTKSHOP = Ultils.DATABASE_NAME;
    private static final int DATABASE_VERSION = 26;
    public DBHelper(Context context) {
        super(context, NBTKSHOP,null,26);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Ultils.TABLE_USER+ "("
                + Ultils.USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.USER_NAME + " TEXT,"
                + Ultils.USER_PASS + " TEXT,"
                + Ultils.USER_EMAIL + " TEXT,"
                + Ultils.USER_PHONE + " TEXT,"
                + Ultils.USER_AVATAR + " TEXT"
                +")";
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + Ultils.TABLE_ADMIN+ "("
                + Ultils.ADMIN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ADMIN_NAME + " TEXT,"
                + Ultils.ADMIN_PASS + " TEXT,"
                + Ultils.ADMIN_AVATAR + " TEXT"
                +")";
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + Ultils.TABLE_CATEGORY+ "("
                + Ultils.CATEGORY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.CATEGORY_NAME+ " TEXT"
                +")";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Ultils.TABLE_PRODUCT + "("
                + Ultils.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.PRODUCT_IMAGE + " TEXT, "
                + Ultils.PRODUCT_NAME + " TEXT, "
                + Ultils.PRODUCT_PRICE + " TEXT, "
                + Ultils.PRODUCT_DESCRIPTION + " TEXT, "
                + Ultils.CATEGORY_ID + " INTEGER, "
                + "FOREIGN KEY(" + Ultils.CATEGORY_ID + ") REFERENCES " + Ultils.TABLE_CATEGORY + "(" + Ultils.CATEGORY_ID + ")"
                + ")";
        String CREATE_ORDER_TABLE = "CREATE TABLE " + Ultils.TABLE_ORDER + " ("
                + Ultils.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ORDER_DATE + " TEXT, "
                + Ultils.ORDER_STATUS + " TEXT, "
                + Ultils.ORDER_TOTAL + " REAL, "
                + Ultils.ORDER_ADDRESS + " TEXT, "
                + Ultils.USER_NAME + " TEXT, "
                + "FOREIGN KEY(" + Ultils.USER_NAME + ") REFERENCES " + Ultils.TABLE_USER + "(" + Ultils.USER_NAME + ")"
                + ")";
        String CREATE_ORDER_DETAIL_TABLE = "CREATE TABLE " + Ultils.TABLE_ORDER_DETAIL + "("
                + Ultils.ORDER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ORDER_ID + " INTEGER, "
                + Ultils.PRODUCT_ID + " INTEGER, "
                + Ultils.QUANTITY + " INTEGER, "
                + Ultils.PRICE + " REAL, "
                + "FOREIGN KEY(" + Ultils.ORDER_ID + ") REFERENCES " + Ultils.TABLE_ORDER + "(" + Ultils.ORDER_ID + "),"
                + "FOREIGN KEY(" + Ultils.PRODUCT_ID + ") REFERENCES " + Ultils.TABLE_PRODUCT + "(" + Ultils.PRODUCT_ID + ")"
                + ")";
        String CREATE_VOUCHER_TABLE = "CREATE TABLE " + Ultils.TABLE_VOUCHER + "("
                + Ultils.VOUCHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.VOUCHER_CODE + " TEXT, "
                + Ultils.VOUCHER_PRODUCT_ID + " INTEGER, "
                + Ultils.VOUCHER_DISCOUNT + " INTEGER, "
                + Ultils.VOUCHER_START_DATE + " INTEGER, "
                + Ultils.VOUCHER_END_DATE + " INTEGER"  + ");";
        // Execute table creation statements
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ADMIN_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_DETAIL_TABLE);
        sqLiteDatabase.execSQL(CREATE_VOUCHER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);
        // Execute data insertion statements
        String insertUserData = "INSERT INTO " + Ultils.TABLE_USER + "("
                + Ultils.USER_NAME + ", "
                + Ultils.USER_PASS + ", "
                + Ultils.USER_EMAIL + ", "
                + Ultils.USER_PHONE + ", "
                + Ultils.USER_AVATAR + ") VALUES "
                + "('test', '0123456', 'test@gmail.com', '0123456789', 'meo1.png') ";
        String insertCategoryData = "INSERT INTO " + Ultils.TABLE_CATEGORY + "(" + Ultils.CATEGORY_NAME + ") VALUES "
                + "('Điện thoại'), "
                + "('Tai nghe'), "
                + "('Sạc điện thoại')";
        String insertProductData = "INSERT INTO " + Ultils.TABLE_PRODUCT + "(" + Ultils.PRODUCT_IMAGE + ", " + Ultils.PRODUCT_NAME + ", " + Ultils.PRODUCT_PRICE + ", " + Ultils.PRODUCT_DESCRIPTION + ", " + Ultils.CATEGORY_ID + ") VALUES "
                + "('h20.png', 'Ghế ODGER', '12000000', 'Sofa thiết kế hiện đại, với đệm mút cao cấp và vải bọc chống thấm. Chất liệu gỗ tự nhiên, bền bỉ, màu sắc trang nhã phù hợp với mọi không gian sống.', 1), "
                + "('h18.png', 'Sofa SÖDERHAMN', '10000000', 'Ghế ăn thiết kế đơn giản, chất liệu gỗ và nệm bọc da, dễ dàng vệ sinh. Phù hợp cho không gian phòng ăn của gia đình.', 1), "
                + "('h19.png', 'Đèn đa dụng', '8000000', 'Đèn gia dụng là một phần không thể thiếu trong mỗi gia đình, giúp chiếu sáng và tạo không gian ấm cúng. Có nhiều loại đèn gia dụng khác nhau, mỗi loại có thiết kế và chức năng riêng biệt:\n" +
                "\n" +
                "Đèn trần: Được gắn trên trần nhà, thường dùng để chiếu sáng toàn bộ phòng. Có thể là đèn chùm, đèn âm trần hoặc đèn LED.\n" +
                "\n" +
                "Đèn bàn: Thường được đặt trên bàn làm việc hoặc bàn học, cung cấp ánh sáng tập trung cho các hoạt động cần sự tập trung cao.\n" +
                "\n" +
                "Đèn sàn: Được đặt trên sàn nhà, thường có thiết kế cao và có thể di chuyển dễ dàng. Thích hợp cho việc đọc sách hoặc tạo điểm nhấn trong phòng.\n" +
                "\n" +
                "Đèn tường: Gắn trên tường, thường dùng để chiếu sáng hành lang hoặc tạo điểm nhấn cho bức tường.\n" +
                "\n" +
                "Đèn ngủ: Thường có ánh sáng dịu nhẹ, giúp tạo không gian thư giãn và dễ chịu cho giấc ngủ.', 1), "
                + "('h17.png', 'Sofa KLIPPAN', '5000000', 'Ghế thư giãn với thiết kế êm ái, đệm mút dày, có thể điều chỉnh độ nghiêng, thích hợp để nghỉ ngơi và thư giãn.', 1), "
                + "('h16.png', 'Tủ bếp DUKTIG', '3000000', 'Tủ bếp thiết kế tinh tế, mặt kính cường lực, khung thép chắc chắn. Phù hợp với các không gian phòng khách nhỏ gọn.', 2), "
                + "('h15.png', 'Đèn ngủ LAMPAN', '4500000', 'Đèn gia dụng là một phần không thể thiếu trong mỗi gia đình, giúp chiếu sáng và tạo không gian ấm cúng. Có nhiều loại đèn gia dụng khác nhau, mỗi loại có thiết kế và chức năng riêng biệt:\n" +
                "\n" +
                "Đèn trần: Được gắn trên trần nhà, thường dùng để chiếu sáng toàn bộ phòng. Có thể là đèn chùm, đèn âm trần hoặc đèn LED.\n" +
                "\n" +
                "Đèn bàn: Thường được đặt trên bàn làm việc hoặc bàn học, cung cấp ánh sáng tập trung cho các hoạt động cần sự tập trung cao.\n" +
                "\n" +
                "Đèn sàn: Được đặt trên sàn nhà, thường có thiết kế cao và có thể di chuyển dễ dàng. Thích hợp cho việc đọc sách hoặc tạo điểm nhấn trong phòng.\n" +
                "\n" +
                "Đèn tường: Gắn trên tường, thường dùng để chiếu sáng hành lang hoặc tạo điểm nhấn cho bức tường.\n" +
                "\n" +
                "Đèn ngủ: Thường có ánh sáng dịu nhẹ, giúp tạo không gian thư giãn và dễ chịu cho giấc ngủ.', 2), "
                + "('h14.png', 'Gối ngủ FAMNIG', '2000000', 'Gối ngủ thiết kế đơn giản, chất liệu gỗ và nệm bọc da, dễ dàng vệ sinh. Phù hợp cho không gian phòng ăn của gia đình.', 2), "
                + "('h13.png', 'Thảm STOCKHOLM', '3500000', 'Thảm đa năng với mặt bàn rộng rãi, nhiều ngăn kéo chứa đồ. Thiết kế gọn gàng, tiết kiệm không gian, phù hợp cho văn phòng hoặc phòng làm việc tại nhà.', 3), "
                + "('h12.png', 'Bình cắm hoa ', '6000000', 'Bình cắm hoa thiết kế đơn giản, có nhiều ngăn chứa giày dép, phù hợp với không gian nhỏ. Chất liệu gỗ công nghiệp cao cấp, dễ dàng vệ sinh.', 3),"
                + "('h24.png', 'Sofa SÖDERHAMN', '10000000', 'Ghế ăn thiết kế đơn giản, chất liệu gỗ và nệm bọc da, dễ dàng vệ sinh. Phù hợp cho không gian phòng ăn của gia đình.', 1), "
                + "('h23.png', 'Sofa SÖDERHAMN', '10000000', 'Ghế ăn thiết kế đơn giản, chất liệu gỗ và nệm bọc da, dễ dàng vệ sinh. Phù hợp cho không gian phòng ăn của gia đình.', 1), "
                + "('h25.png', 'Sofa SÖDERHAMN', '10000000', 'Ghế ăn thiết kế đơn giản, chất liệu gỗ và nệm bọc da, dễ dàng vệ sinh. Phù hợp cho không gian phòng ăn của gia đình.', 1), "
                + "('h11.png', 'Ghế FRÖSET  ', '6000000', 'Ghế thiết kế đơn giản, có nhiều ngăn chứa giày dép, phù hợp với không gian nhỏ. Chất liệu gỗ công nghiệp cao cấp, dễ dàng vệ sinh.', 3)";
        sqLiteDatabase.execSQL(insertProductData);

        sqLiteDatabase.execSQL(insertCategoryData);
        sqLiteDatabase.execSQL(insertProductData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ADMIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ORDER_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_VOUCHER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ORDER);
        onCreate(sqLiteDatabase);
    }
    //them moi 1 user(register)
    public boolean addOne(user newUser) {
        String username = newUser.getUserName();
        String pass = newUser.getPassword();
        String mail = newUser.getEmail();
        String phone = newUser.getPhoneNumber();
        if (username.length() > 0 && pass.length() > 0 && mail.length() > 0 && phone.length() > 0) {
            boolean check = CheckCondition(newUser);
            if(!check) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Ultils.USER_NAME, newUser.getUserName());
                cv.put(Ultils.USER_PASS, newUser.getPassword());
                cv.put(Ultils.USER_EMAIL, newUser.getEmail());
                cv.put(Ultils.USER_PHONE, newUser.getPhoneNumber());
                long insert = db.insert(Ultils.TABLE_USER, null, cv);
                db.close();
                return true;
            }
        }
        return false;
    }
    public boolean CheckCondition(user newUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String mail_convert = "'" + newUser.getEmail() + "'";
        String sdt_convert = "'" + newUser.getPhoneNumber() + "'";

        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_USER
                + " WHERE " + Ultils.USER_PHONE + " = " + sdt_convert
                + " OR " + Ultils.USER_EMAIL + " = " + mail_convert, null);
        if(cursor.moveToFirst()) {
            return true;
        }
        return false;
    }
    // Check login information
    public LoginValidation CheckCustomer(String name, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name_convert = "'" + name + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_USER + " WHERE " + Ultils.USER_NAME + "=" + name_convert, null);
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Ultils.USER_ID);
            int nameIndex = cursor.getColumnIndex(Ultils.USER_NAME);
            int passIndex = cursor.getColumnIndex(Ultils.USER_PASS);
            int mailIndex = cursor.getColumnIndex(Ultils.USER_EMAIL);
            int phoneIndex = cursor.getColumnIndex(Ultils.USER_PHONE);
            int avatarIndex = cursor.getColumnIndex(Ultils.USER_AVATAR);
            int userID = cursor.getInt(idIndex);
            String userName = cursor.getString(nameIndex);
            String userPass = cursor.getString(passIndex);
            String userMail = cursor.getString(mailIndex);
            String userPhone = cursor.getString(phoneIndex);
            int userAvatar = cursor.getInt(avatarIndex);
            if(pass.equals(userPass)) {
                user newUser = new user(userID, userName, userPass, userMail, userPhone, userAvatar);
                return new LoginValidation(newUser, true);
            }
        }
        cursor.close();
        db.close();
        return new LoginValidation(false);
    }
    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.CATEGORY_ID + " = ?", new String[] { String.valueOf(categoryId) });
        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                String productImage = cursor.getString(1);
                String productName = cursor.getString(2);
                int productPrice = cursor.getInt(3);
                String productDescription = cursor.getString(4);
                int productCategoryId = cursor.getInt(5);
                Product product = new Product(productId, productImage, productName, productPrice, productDescription, productCategoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productList;
    }
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.PRODUCT_ID + " = " + productId, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String image = cursor.getString(1);
        String name = cursor.getString(2);
        int price = cursor.getInt(3);
        String description = cursor.getString(4);
        cursor.close();
        db.close();
        return new Product(0, image, name, price, description);
    }
    public Voucher getVoucherByCode(String voucherCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(
                Ultils.TABLE_VOUCHER,
                new String[]{Ultils.VOUCHER_ID, Ultils.VOUCHER_CODE, Ultils.VOUCHER_PRODUCT_ID, Ultils.VOUCHER_DISCOUNT, Ultils.VOUCHER_START_DATE, Ultils.VOUCHER_END_DATE},
                Ultils.VOUCHER_CODE + " = ?",
                new String[]{voucherCode},
                null,
                null,
                null);
        Voucher voucher = null;
        if (cursor != null && cursor.moveToFirst()) {
            voucher = new Voucher(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getLong(4),
                    cursor.getLong(5)
            );
            cursor.close();
        }
        db.close();
        return voucher;
    }
    public List<Product> getHotProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Ultils.TABLE_PRODUCT + " ORDER BY " + Ultils.PRODUCT_PRICE + " DESC LIMIT 4";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int proIdIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
                int proImageIndex = cursor.getColumnIndex(Ultils.PRODUCT_IMAGE);
                int proNameIndex = cursor.getColumnIndex(Ultils.PRODUCT_NAME);
                int proPriceIndex = cursor.getColumnIndex(Ultils.PRODUCT_PRICE);
                int proDesIndex = cursor.getColumnIndex(Ultils.PRODUCT_DESCRIPTION);
                int categoryIdIndex = cursor.getColumnIndex(Ultils.CATEGORY_ID);
                if (proIdIndex != -1 && proImageIndex != -1 && proNameIndex != -1 && proPriceIndex != -1 && proDesIndex != -1 && categoryIdIndex != -1) {
                    Product product = new Product();
                    product.setProID(cursor.getInt(proIdIndex));
                    product.setProImage(cursor.getString(proImageIndex));
                    product.setProName(cursor.getString(proNameIndex));
                    product.setProPrice(cursor.getInt(proPriceIndex));
                    product.setProDes(cursor.getString(proDesIndex));
                    product.setCategoryID(cursor.getInt(categoryIdIndex));
                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
        // Phương thức để thêm đơn hàng mới và chi tiết đơn hàng vào cơ sở dữ liệu
        public long addOrderWithDetails(String username, ArrayList<GioHang> cartItems,String Address) {
            SQLiteDatabase db = this.getWritableDatabase();
            // Thêm dữ liệu vào bảng order
            ContentValues orderValues = new ContentValues();
            orderValues.put(Ultils.ORDER_DATE, getCurrentDateTime());
            orderValues.put(Ultils.ORDER_STATUS, "Thành công");
            orderValues.put(Ultils.ORDER_ADDRESS,Address );
            orderValues.put(Ultils.ORDER_TOTAL, calculateTotal(cartItems));
            orderValues.put(Ultils.USER_NAME, username);
            long orderId = db.insert(Ultils.TABLE_ORDER, null, orderValues);

            // Log thông tin đơn hàng sau khi thêm
            if (orderId != -1) {
                logOrderInfo(db, orderId);  // Gọi phương thức logOrderInfo

                for (GioHang item : cartItems) {
                    ContentValues detailValues = new ContentValues();
                    detailValues.put(Ultils.ORDER_ID, orderId);
                    detailValues.put(Ultils.PRODUCT_ID, item.getIdsp());
                    detailValues.put(Ultils.QUANTITY, item.getSoluong());
                    detailValues.put(Ultils.PRICE, item.getGiasp());
                    long detailId = db.insert(Ultils.TABLE_ORDER_DETAIL, null, detailValues);
                    // Log thông tin chi tiết đơn hàng sau khi thêm
                    if (detailId != -1) {
                        logOrderDetailInfo(db, orderId);  // Gọi phương thức logOrderDetailInfo
                    }
                }
            }
            db.close();
            return orderId; // Trả về ID của đơn hàng mới được thêm hoặc -1 nếu có lỗi
        }
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private double calculateTotal(ArrayList<GioHang> cartItems) {
        double total = 0;
        for (GioHang item : cartItems) {
            total += item.getGiasp() * item.getSoluong();
        }
        return total;
    }
    private void logOrderInfo(SQLiteDatabase db, long orderId) {
        Cursor cursor = db.query(Ultils.TABLE_ORDER, null, Ultils.ORDER_ID + " = ?",
                new String[] { String.valueOf(orderId) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Ultils.ORDER_ID);
            int dateIndex = cursor.getColumnIndex(Ultils.ORDER_DATE);
            int statusIndex = cursor.getColumnIndex(Ultils.ORDER_STATUS);
            int totalIndex = cursor.getColumnIndex(Ultils.ORDER_TOTAL);
            int userNameIndex = cursor.getColumnIndex(Ultils.USER_NAME); // Sử dụng USER_NAME thay vì USER_ID
//            int paymentMethodIndex = cursor.getColumnIndex(Ultils.PAYMENT_METHOD);
            // Kiểm tra xem các chỉ số có hợp lệ hay không
            if (idIndex != -1 && dateIndex != -1 && statusIndex != -1 &&
                    totalIndex != -1 && userNameIndex != -1 ) {
                String orderInfo = "Order ID: " + cursor.getInt(idIndex) +
                        ", Date: " + cursor.getString(dateIndex) +
                        ", Status: " + cursor.getString(statusIndex) +
                        ", Total: " + cursor.getDouble(totalIndex) +
                        ", Username: " + cursor.getString(userNameIndex)  // Hiển thị Username
                     ;
                Log.d("DBLog", orderInfo);
            }
            cursor.close();
        }
    }


    private void logOrderDetailInfo(SQLiteDatabase db, long orderId) {
        Cursor cursor = db.query(Ultils.TABLE_ORDER_DETAIL, null, Ultils.ORDER_ID + " = ?",
                new String[] { String.valueOf(orderId) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int detailIdIndex = cursor.getColumnIndex(Ultils.ORDER_DETAIL_ID);
            int productIdIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
            int quantityIndex = cursor.getColumnIndex(Ultils.QUANTITY);
            int priceIndex = cursor.getColumnIndex(Ultils.PRICE);

            // Kiểm tra xem các chỉ số có hợp lệ hay không
            if (detailIdIndex != -1 && productIdIndex != -1 &&
                    quantityIndex != -1 && priceIndex != -1) {
                do {
                    String detailInfo = "Order Detail ID: " + cursor.getInt(detailIdIndex) +
                            ", Order ID: " + orderId +
                            ", Product ID: " + cursor.getInt(productIdIndex) +
                            ", Quantity: " + cursor.getInt(quantityIndex) +
                            ", Price: " + cursor.getDouble(priceIndex);
                    Log.d("DBLog", detailInfo);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public ArrayList<Product> searchProducts(String query) {
        ArrayList<Product> productList = new ArrayList<>();
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // SQL query to perform the search
        String searchQuery = "SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.PRODUCT_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + query + "%"});
        // Log the query
        Log.d("Search Query", "Query: " + searchQuery);
        // Iterate over the cursor to create Product objects and add them to the list
        if (cursor.moveToFirst()) {
            do {
                int idColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
                int id = cursor.getInt(idColumnIndex);
                int imageColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_IMAGE);
                String image = cursor.getString(imageColumnIndex);
                int nameColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_NAME);
                String name = cursor.getString(nameColumnIndex);
                int priceColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_PRICE);
                int price = cursor.getInt(priceColumnIndex);
                int descriptionColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_DESCRIPTION);
                String description = cursor.getString(descriptionColumnIndex);
                int categoryIdColumnIndex = cursor.getColumnIndex(Ultils.CATEGORY_ID);
                int categoryId = cursor.getInt(categoryIdColumnIndex);
                // Log the retrieved data
                Log.d("Search Result", "ID: " + id + ", Image: " + image + ", Name: " + name + ", Price: " + price + ", Description: " + description + ", Category ID: " + categoryId);
                // Assuming you have a Product constructor like this
                Product product = new Product(id, image, name, price, description, categoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        // Close the cursor and database
        cursor.close();
        db.close();
        return productList;
    }
    // Phương thức để lấy danh sách đơn hàng dựa trên tên người dùng
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Ultils.TABLE_ORDER,
                new String[] {Ultils.ORDER_ID, Ultils.ORDER_DATE, Ultils.ORDER_TOTAL},
                Ultils.USER_NAME + "=?",
                new String[]{username},
                null, null, null, null);
        if (cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex(Ultils.ORDER_ID);
            int orderDateIndex = cursor.getColumnIndex(Ultils.ORDER_DATE);
            int orderTotalIndex = cursor.getColumnIndex(Ultils.ORDER_TOTAL);
            int orderAddress =cursor.getColumnIndex(Ultils.ORDER_ADDRESS);
            // Make sure all indices are valid
            if (orderIdIndex != -1 && orderDateIndex != -1 && orderTotalIndex != -1) {
                do {
                    Order order = new Order(
                            cursor.getInt(orderIdIndex),
                            cursor.getString(orderDateIndex),
                            cursor.getString(orderAddress),
                            cursor.getDouble(orderTotalIndex)
                    );
                    orders.add(order);
                } while (cursor.moveToNext());
            } else {
                // Handle the error or throw an exception
                Log.e("DBHelper", "Không tồn tại");
            }
        }
        cursor.close();
        db.close();
        return orders;
    }

}

