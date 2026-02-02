package cn.iocoder.yudao.framework.common.util.http;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
public class CRMUtils {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CRMUtils.class);

    private static final String CRM_URL = "https://cle.legrandchina.cn/Ashx/GetSpecPrint.ashx";
    private static final String USER_ID = "BBFC8115-8EF5-42E1-B1CB-A1154291F9CD";
    private static final String TYPE = "GetSpecPrints";

    public static Map<String, String> getInfoByCRM(String orderId, String productSn,String type) {
        Map<String, String> result = new HashMap<>();

        try {
            // 第一次请求
            result = sendRequest(orderId, productSn, "AmmeterPrdNo");

            if (result.isEmpty()) {
                // 第二次请求
                result = sendRequest(orderId, productSn, "ProductNo");
            }

            if (result.isEmpty() && "pdu".equals(type)) {
                result.put("CUSTOMERNAME", "标准");
                result.put("MODELCODE", "无");
                result.put("QUANTITY", "0");
            }

            if (result.isEmpty() && "bus".equals(type)) {
                result.put("CUSTOMERNAME", null);
                result.put("MODELCODE", null);
                result.put("QUANTITY", "0");
            }

        } catch (Exception e) {
            log.error("获取CRM信息失败：", e);
        }

        return result;
    }


    private static Map<String, String> sendRequest(String orderId, String productSn, String productField) {
        Map<String, String> result = new HashMap<>();
        // 创建一个不验证证书的 SSLContext
        SSLContext sslContext = createTrustAllSSLContext();
        // 发送请求
        HttpResponse response = HttpRequest.get(CRM_URL)
                .form("Type", TYPE)
                .form("UserId", USER_ID)
                .form("OrderNo", orderId)
                .form(productField, productSn)
                .setSSLSocketFactory(sslContext.getSocketFactory()) // 设置 SSLSocketFactory
                .execute();
        String responseBody = response.body();
        System.out.println("Response Status: " + response.getStatus());
        System.out.println("Response Body: " + responseBody);
        JSONObject json = JSONObject.parseObject(responseBody);
        JSONArray rows = json.getJSONArray("rows");
        if (!rows.isEmpty()) {
            // 遍历 rows 列表，找到 AMMETERPRDNO 和 productSn 一致的数据
            for (int i = 0; i < rows.size(); i++) {
                JSONObject row = rows.getJSONObject(i);
                String ammeterPrdNo = row.getString("AMMETERPRDNO");
                if (productSn.equals(ammeterPrdNo)) {
                    result.put("CUSTOMERNAME", row.getString("CUSTOMERNAME"));
                    result.put("MODELCODE", row.getString("MODELCODE"));
                    result.put("QUANTITY", row.getString("QUANTITY"));
                    break; // 找到匹配的数据后退出循环
                }
            }
        }
        return result;
    }

    private static SSLContext createTrustAllSSLContext() {
        try {
            // 创建一个信任所有证书的 TrustManager
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // 初始化 SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create trust all SSLContext", e);
        }
    }

}
