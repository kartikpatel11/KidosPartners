package com.example.kidospartners;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersUtil;

import java.util.Map;

public class KidosPartnersForgotPassword extends AppCompatActivity implements IKidosPartnersRestClientWrapper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidos_partners_forgot_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(KidosPartnersUtil.setTitleText(this, KidosPartnersConstants.FORGOT_PASSWORD_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

    }

    @Override
    public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {

    }

    @Override
    public void restCallBack(String restOutput, String requestUrl) {

    }
}
