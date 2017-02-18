package com.example.gread.gread;import android.content.Intent;import android.content.SharedPreferences;import android.graphics.Typeface;import android.os.Bundle;import android.os.StrictMode;import android.support.v4.app.FragmentActivity;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.widget.EditText;import android.widget.TextView;import com.facebook.drawee.backends.pipeline.Fresco;import com.google.android.gms.auth.api.Auth;import com.google.android.gms.auth.api.signin.GoogleSignInAccount;import com.google.android.gms.auth.api.signin.GoogleSignInOptions;import com.google.android.gms.auth.api.signin.GoogleSignInResult;import com.google.android.gms.common.SignInButton;import com.google.android.gms.common.api.GoogleApiClient;public class MainActivity extends FragmentActivity{    TextView logo, caption;    EditText userName, password;    private static final int RC_SIGN_IN = 9001;    public static GoogleSignInAccount account;    public static final String USER_PREFS_NAME = "User Information";    protected void onCreate(Bundle savedInstanceState)    {        super.onCreate(savedInstanceState);        Fresco.initialize(this);        requestWindowFeature(Window.FEATURE_NO_TITLE);        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();        StrictMode.setThreadPolicy(policy);        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                WindowManager.LayoutParams.FLAG_FULLSCREEN);        setContentView(R.layout.activity_main);//        Button button = (Button)findViewById(R.id.test);//        button.setOnClickListener(new View.OnClickListener() {//            @Override//            public void onClick(View v) {//                Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);//                startActivity(loggedin);////            }//        });        SharedPreferences preferences = getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE);        String storedID = preferences.getString("ID", null);        if(storedID != null){            Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);            startActivity(loggedin);        }        else{            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)                    //      .requestIdToken(this.getResources().getString(R.string.server_client_id))                    .requestEmail()                    .build();            final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)                    .build();            SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);            signInButton.setSize(SignInButton.SIZE_WIDE);            signInButton.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View v) {                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);                    startActivityForResult(signInIntent, RC_SIGN_IN);                }            });            logo=(TextView)findViewById(R.id.logo_gread);            caption=(TextView)findViewById(R.id.caption_text);            Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");            logo.setTypeface(face);            Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");            caption.setTypeface(face1);        }}    @Override    public void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if(requestCode == RC_SIGN_IN)        {            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);            handleSignInResult(result);        }    }    private void handleSignInResult(GoogleSignInResult result){        //Log.d(TAG, "handleSignInResult" + result.isSuccess());        if(result.isSuccess()){            account = result.getSignInAccount();            SharedPreferences.Editor userInfo = getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE).edit();            userInfo.putString("DisplayName", account.getDisplayName());            userInfo.putString("ID", account.getId());            userInfo.putString("Email", account.getEmail());            userInfo.putString("PhotoURL", String.valueOf(account.getPhotoUrl()));            userInfo.commit();            Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);            startActivity(loggedin);        }    }}