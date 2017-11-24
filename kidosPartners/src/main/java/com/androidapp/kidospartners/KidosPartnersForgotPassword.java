package com.androidapp.kidospartners;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class KidosPartnersForgotPassword extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper {

    private String forgotPasswordURI=KidosPartnersConstants.FORGOT_PASSWORD_URI;
    private String successMsg = "One time password is sent to registered emailID and mobile phone";
    private String errorMsg = "Account not found. Please enter correct registered Mobile number or Email ID ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidos_partners_forgot_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(KidosPartnersUtil.setTitleText(this, KidosPartnersConstants.FORGOT_PASSWORD_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

    }

    @Override
    public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
        KidosPartnersRestClient.sendRequest(context,args,method,url);
    }

    @Override
    public void restCallBack(String restOutput, String requestUrl) {

        System.out.println("In Rest Call back method of KidosPartnersForgotPassword::requestUrl="+requestUrl);

        Gson gson = new Gson();
        if(restOutput!=null && !"".equals(restOutput)) {
            KidosPartnersUtil.createDialog(KidosPartnersForgotPassword.this, KidosPartnersUtil.TITLE_SUCCESS, successMsg, KidosPartnersUtil.NEUTRAL_DIALOG, dialogClickListener);
            }
        else {
            KidosPartnersUtil.createDialog(KidosPartnersForgotPassword.this, KidosPartnersUtil.TITLE_ERROR, errorMsg, KidosPartnersUtil.NONE_DIALOG, null);
        }

    }

    public void forgotPassword(View v)
    {
        if(validateForm())
        {
            EditText phNoEmail = (EditText) findViewById(R.id.txt_login_phnoemail);
            Map<String, Object> passwordMap = new HashMap<String,Object>();
            passwordMap.put("phnoemail",phNoEmail.getText().toString());
            restRequest(KidosPartnersForgotPassword.this, passwordMap, KidosPartnersConstants.POSTJSON, forgotPasswordURI);
        }
    }

    private boolean validateForm() {
        boolean validForm = false;
        EditText phNoEmail = (EditText) findViewById(R.id.txt_login_phnoemail);

        if (phNoEmail.getText().toString().length() == 0) {
            phNoEmail.setError("Phone number or EmailID is required!");
            return validForm;
        }
        validForm=true;
        return validForm;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_NEUTRAL:
                    Intent resetPassIntent =new Intent(KidosPartnersForgotPassword.this,KidosPartnersResetPassword.class);
                    resetPassIntent.putExtra("phNoEmail",((EditText)findViewById(R.id.txt_login_phnoemail)).getText().toString());

                    startActivity(resetPassIntent);
                    KidosPartnersForgotPassword.this.finish();

                    break;


            }
        }
    };

}
