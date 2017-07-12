package com.betterda.btpay;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.betterda.BtPay;
import com.betterda.javabean.BtPayResult;
import com.betterda.callback.BtPayCallBack;
import com.betterda.callback.BtResult;
import com.betterda.javabean.PayCloudReqModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnUnionPay,mBtnUnionWapPay;

    public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqQ8mG2VN2rRi5pF4drOi9pB2kdIAiO6YR7LQGDWQkP2DkAI19apajGxDt3q1m2kmWdytX5dmI8AhxEgK+Ak+qoaf7qNv/6NRQUesnJ8kB7sACzEG79CNwxeZy0jLP2E0RC69r/vyyqcD5PwkIuaMNc5KIJhapl0pPmsMZ+F85QIDAQAB";
    public static final String APP_ID = "47cb95e8badd4521b3bd17da1516d5db";
  // public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEyg29Z8uHvIY1xQhJnKzg2dO5o/W/LYZ0jUQAyZy7y/Tbaz0N6/1jf5w2sFf7oDh5JlkO+UZgqnV9bwY6pHV2ZOJC81PObn6cq4kHxNhut066Fwr2x6RlayPIG3elVyHxGcIjSWeJriuv7ODnRg4+RmHjcjK7zvtlGdTCUP3JGwIDAQAB";
   // public static final String APP_ID = "9cdca84409714992b4edeabb9a1ae1bc";

    public static final String SERVICE_URL = "http://www.laidouzhen.cn/paycloud-openapi/api/unionpay/app/ctrl/getform/%s/%s";


    //public static final String SERVICE_URL = "http://120.42.7.225:8999/paycloud-openapi/api/unionpay/app/ctrl/getform/%s/%s";

    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnUnionPay = (Button) findViewById(R.id.btn_unionpay);
        mBtnUnionWapPay = (Button) findViewById(R.id.btn_unionwappay);

        mBtnUnionPay.setOnClickListener(this);
        mBtnUnionWapPay.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mAlertDialog = builder.setTitle("ddd").create();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unionpay:
                //时间格式:yyyyMMddHHmmss
                String orderId = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
                String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String txtAmt = "10";
                PayCloudReqModel payCloudReqModel = new PayCloudReqModel();
                payCloudReqModel.setAppid(APP_ID);
                payCloudReqModel.setPublicKey(PUB_KEY);
                payCloudReqModel.setBackUrl("http://www.baidu.com");
                payCloudReqModel.setOrderId(orderId);
                payCloudReqModel.setTxnTime(txnTime);
                payCloudReqModel.setTxnAmt(txtAmt);
                payCloudReqModel.setServiceUrl(SERVICE_URL);
                payCloudReqModel.setAccNo("6212261904000175994");
                mBtnUnionPay.setEnabled(false);

                mAlertDialog.show();

                BtPay.getInstance(MainActivity.this).requestPay(payCloudReqModel,new BtPayCallBack() {
                    @Override
                    public void done(BtResult result) {
                        if (mBtnUnionPay != null) {
                            mBtnUnionPay.setEnabled(true);
                        }
                        mAlertDialog.dismiss();
                        Toast.makeText(MainActivity.this,((BtPayResult)result).getResult(),0).show();
                    }
                });

                break;
            case R.id.btn_unionwappay:
                String orderId2 = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
                String txnTime2 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String txtAmt2 = "10";
                PayCloudReqModel payCloudReqModel2 = new PayCloudReqModel();
                payCloudReqModel2.setAppid(APP_ID);
                payCloudReqModel2.setPublicKey(PUB_KEY);
                payCloudReqModel2.setBackUrl("http://www.baidu.com");
                payCloudReqModel2.setOrderId(orderId2);
                payCloudReqModel2.setTxnTime(txnTime2);
                payCloudReqModel2.setTxnAmt(txtAmt2);
                payCloudReqModel2.setServiceUrl(SERVICE_URL);

                mBtnUnionPay.setEnabled(false);

                mAlertDialog.show();

                BtPay.getInstance(MainActivity.this).requestPay(payCloudReqModel2,new BtPayCallBack() {
                    @Override
                    public void done(BtResult result) {
                        if (mBtnUnionPay != null) {
                            mBtnUnionPay.setEnabled(true);
                        }
                        mAlertDialog.dismiss();
                        Toast.makeText(MainActivity.this,((BtPayResult)result).getResult(),0).show();
                    }
                });
                break;
        }
    }
}
