package com.example.gread.gread;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends FragmentActivity
{
    CallbackManager callbackManager;
    private View view;
    Button button;
    LoginButton loginButton;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    TextView logoGread,caption;
    EditText userName, password;





    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        logoGread = (TextView) findViewById(R.id.logo_gread);
        Typeface logo_font = Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");
        logoGread.setTypeface(logo_font);

        caption = (TextView)findViewById(R.id.caption_text);
        Typeface captionFont = Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");
        caption.setTypeface(captionFont);

        Button cue = (Button) findViewById(R.id.sign_button_login);
        Typeface cue_font= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");
        cue.setTypeface(cue_font);
        cue.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent sub = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(sub);
                System.out.println("Done");
                // Perform action on click
            }
        });

        Button register = (Button) findViewById(R.id.register_button_login);
        Typeface register_font= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");
        register.setTypeface(register_font);

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent sub = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(sub);
                System.out.println("Done");
                // Perform action on click
            }
        });


        //LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        /*LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);
                        System.out.println("Done");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/





}
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        //loginButton = (LoginButton) view.findViewById(R.id.button);
        //loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(i);
//                System.out.println("Done");
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
        return view;
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }


}

