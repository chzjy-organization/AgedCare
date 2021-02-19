package com.punuo.sys.app.agedcare.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.punuo.sys.app.agedcare.R;
import com.punuo.sys.app.agedcare.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EatActivity extends HindebarActivity {
    @BindView(R2.id.eat_cancel)
    Button eat_cancel;
    @BindView(R2.id.eat_call)
    Button eat_call;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        ButterKnife.bind(this);
        eat_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rejectCall();
                finish();
            }
        });
        SharedPreferences preferences=getSharedPreferences("data",MODE_PRIVATE);
         item=preferences.getString("订餐电话","");
//       call();
        eat_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("EATActivity",item+"");
                call();
            }
        });
    }
    private void call()
    {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + item);
        intent.setData(data);
        startActivity(intent);
    }

}