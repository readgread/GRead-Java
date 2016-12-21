package com.example.gread.gread;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.RegisterParams;
import com.stormpath.sdk.models.StormpathError;

public class RegisterActivity extends Activity {


    EditText firstName, eMail, passWord, rePassword, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            RegisterParams registerParams = new RegisterParams(fname, lname, email, pass);

            Stormpath.register(registerParams, new StormpathCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Context context = getApplicationContext();
                    CharSequence charSequence = "Registration Successful";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, charSequence, duration).show();

                }

                @Override
                public void onFailure(StormpathError error) {
                    System.out.println(error);
                    Context context = getApplicationContext();
                    CharSequence charSequence = "Registration Unsuccessful";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, charSequence, duration).show();

                }
            });
        }
    }


}
