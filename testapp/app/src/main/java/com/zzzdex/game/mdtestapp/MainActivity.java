package com.zzzdex.game.mdtestapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.fastjson.FastjsonMsgConvertor;
import com.zzzdex.game.mdtestapp.pay.AuthResult;
import com.zzzdex.game.mdtestapp.pay.PayResult;
import com.zzzdex.game.mdtestapp.util.DesUtil;
import org.json.JSONException;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    HTTP http = HTTP.builder()
            .baseUrl("http://172.20.10.3:8080")
            .addMsgConvertor(new FastjsonMsgConvertor())
            .build();
    private String token = "";
    private final String key = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getpwd = findViewById(R.id.getpwd);
        TextView email = findViewById(R.id.getpwdemail);
        Button login = findViewById(R.id.login);
        TextView emaill = findViewById(R.id.email);
        TextView pwdl = findViewById(R.id.pwd);
        Button getinfo = findViewById(R.id.getinfo);

        TextView good = findViewById(R.id.good);
        TextView price = findViewById(R.id.price);

        Button pay = findViewById(R.id.pay);

        getpwd.setOnClickListener(v -> {
            http.async("/api/user/getpwd")
                    .addBodyPara("email", email.getText())
                    .addBodyPara("t", DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis() / 1000))))
                    .setOnResponse(httpResult -> {
                        Looper.prepare();
                        Toast.makeText(this, httpResult.getBody().toString(), Toast.LENGTH_SHORT).show();

                        Looper.loop();

                    })
                    .post();
        });
//    /user/user_info
        login.setOnClickListener(v -> {
            http.async("/api/user/login")
                    .addBodyPara("email", emaill.getText())
                    .addBodyPara("pwd", pwdl.getText())
                    .addBodyPara("t", DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis() / 1000))))
                    .setOnResponse(httpResult -> {
                        Looper.prepare();
                        JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody().toString());
                        token = jsonObject.getString("data");
                        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    })
                    .post();
        });
        getinfo.setOnClickListener(v -> {
            http.async("/api/user/user_info")
                    .addHeader("Authorization", token)
                    .addUrlPara("t", DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis() / 1000))))
                    .setOnResponse(httpResult -> {
                        Looper.prepare();

                        Toast.makeText(this, httpResult.getBody().toString(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    })
                    .get();
        });
        pay.setOnClickListener(v -> {
            initBil((String) good.getText().toString(), (String) price.getText().toString());

        });

    }


    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    public void payV2(String orderInfo) {

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MainActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

        Intent intoDetail = new Intent(MainActivity.this, PayActivity.class);
        /**
         intoDetail.putExtra("out_trade_no",out_trade_no);
         intoDetail.putExtra("total_amount",total_amount);
         intoDetail.putExtra("time_stamp",time_stamp);
         */

        startActivity(intoDetail);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    /**
                     * 付完成时，客户端返回的参数；在resultInfo中，同服务端一样，以map的方式存储；
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(PurchaseActivity.this, getString(R.string.pay_success) + payResult);
                        String pauResultData = payResult.getResult();
                        System.out.println("--------------->同步返回交易信息。" + pauResultData);
                        parseJSONPayResulting(pauResultData);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(MainActivity.this, "失败" + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(MainActivity.this, "OK" + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(MainActivity.this, "FAILED" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void initBil(String name, String price) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResult post = http.sync("/api/user/provide/pay")
                            .addBodyPara("payType", 1)
                            .addUrlPara("t", DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis() / 1000))))
                            .addBodyPara("provideName", name)
                            .addHeader("Authorization", token)
                            .addBodyPara("provideAmount", price).post();

                    String data = JSONObject.parseObject(post.getBody().toString()).getString("data");
                    System.out.println("-------------->回调订单SDK信息" + data);
                    Log.d("DATA", data);

                    runOnUiThread(() -> payV2(data));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    String out_trade_no = null;
    String total_amount = null;
    String time_stamp = null;

    private void parseJSONPayResulting(String payResultData) {

        try {
            org.json.JSONObject jsonObjectOne = new org.json.JSONObject(payResultData);

            for (int i = 0; i < jsonObjectOne.length(); i++) {
                org.json.JSONObject jsonObject = (org.json.JSONObject) jsonObjectOne.get("alipay_trade_app_pay_response");

                out_trade_no = jsonObject.getString("out_trade_no");
                total_amount = jsonObject.getString("total_amount");
                time_stamp = jsonObject.getString("timestamp");


                System.out.println("------------->回调订单信息" + out_trade_no + total_amount + time_stamp);

                SharedPreferences.Editor editor = getSharedPreferences("bill", MODE_PRIVATE).edit();
                editor.putString("out_trade_no", out_trade_no);
                editor.putString("total_amount", total_amount);
                editor.putString("time_stamp", time_stamp);
                editor.commit();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}