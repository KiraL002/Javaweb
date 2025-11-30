package com.mycompany.javaweb.context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VNPAYUtils {

    // Thông tin cấu hình Sandbox
    public static String vnp_TmnCode = "WYFE328E";    
    public static String vnp_HashSecret = "AA1Q63YNDDFXCDAKQ0R687YTWAKY3HOK";
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    // URL trả về của dự án bạn
    public static String vnp_ReturnUrl = "http://localhost:8080/javaweb/vnpay_return";

    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", calendar);
    }
    // Tạo thời gian hết hạn thanh toán, ví dụ 15 phút sau

    public static String getExpireDateTime(int minutes) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        calendar.add(Calendar.MINUTE, minutes);
        return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", calendar);
    }
    public static String hmacSHA512(String key, String data) throws Exception {
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        hmac512.init(secretKey);
        byte[] bytes = hmac512.doFinal(data.getBytes("UTF-8"));

        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }

    public static String createPaymentUrl(Map<String, String> params) throws Exception {

        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String name : fieldNames) {
            String value = params.get(name);
            if (value != null && !value.isEmpty()) {

                hashData.append(name).append('=')
                        .append(URLEncoder.encode(value, "UTF-8")).append('&');

                query.append(URLEncoder.encode(name, "UTF-8")).append('=')
                     .append(URLEncoder.encode(value, "UTF-8")).append('&');
            }
        }

        String queryUrl = query.substring(0, query.length() - 1);
        String rawHash = hashData.substring(0, hashData.length() - 1);

        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, rawHash);

        return vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
    }
}
