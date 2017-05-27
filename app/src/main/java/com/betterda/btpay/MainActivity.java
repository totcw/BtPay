package com.betterda.btpay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.BtPay;
import com.betterda.javabean.BtPayResult;
import com.betterda.callback.BtPayCallBack;
import com.betterda.callback.BtResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.utils.KeyGenerator;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayCloudReqModel payCloudReqModel = new PayCloudReqModel();

                BtPay.getInstance(MainActivity.this).requestPay(payCloudReqModel,new BtPayCallBack() {
                    @Override
                    public void done(BtResult result) {
                        Toast.makeText(MainActivity.this,((BtPayResult)result).getResult(),0).show();
                    }
                });

            }
        });


    }


}
