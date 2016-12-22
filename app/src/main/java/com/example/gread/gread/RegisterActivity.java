package com.example.gread.gread;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends Activity {


    EditText firstName, eMail, passWord, rePassword, lastName;
    TextView logo, caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        logo=(TextView)findViewById(R.id.logo_gread);
        caption=(TextView)findViewById(R.id.caption_text);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");
        logo.setTypeface(face);

        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");
        caption.setTypeface(face1);
    }


    public void setRegisterButton(View v)
    {
        firstName = (EditText) findViewById(R.id.firstname);
        eMail = (EditText) findViewById(R.id.email);
        passWord = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.repassword);
        lastName = (EditText) findViewById(R.id.lastname);
        String pass = passWord.getText().toString();
        String repass = rePassword.getText().toString();
        String lname = lastName.getText().toString();
        String email = eMail.getText().toString();
        String fname = firstName.getText().toString();
        if(pass.equals(repass))
        {
                    }
    }


}
