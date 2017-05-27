package com.betterda.btpay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.BtPay;
import com.betterda.javabean.BtPayResult;
import com.betterda.callback.BtPayCallBack;
import com.betterda.callback.BtResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.utils.KeyGenerator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnUnionPay,mBtnUnionWapPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnUnionPay = (Button) findViewById(R.id.btn_unionpay);
        mBtnUnionWapPay = (Button) findViewById(R.id.btn_unionwappay);

        mBtnUnionPay.setOnClickListener(this);
        mBtnUnionWapPay.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unionpay:
                PayCloudReqModel payCloudReqModel = new PayCloudReqModel();

                BtPay.getInstance(MainActivity.this).requestPay(payCloudReqModel,new BtPayCallBack() {
                    @Override
                    public void done(BtResult result) {
                        Toast.makeText(MainActivity.this,((BtPayResult)result).getResult(),0).show();
                    }
                });
                break;
            case R.id.btn_unionwappay:
                break;
        }
    }
}
