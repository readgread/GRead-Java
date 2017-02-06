package com.example.gread.gread;import android.content.Intent;import android.graphics.Typeface;import android.os.Bundle;import android.os.StrictMode;import android.support.v4.app.FragmentActivity;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.widget.EditText;import android.widget.TextView;import com.facebook.drawee.backends.pipeline.Fresco;import com.google.android.gms.auth.api.Auth;import com.google.android.gms.auth.api.signin.GoogleSignInAccount;import com.google.android.gms.auth.api.signin.GoogleSignInOptions;import com.google.android.gms.auth.api.signin.GoogleSignInResult;import com.google.android.gms.common.SignInButton;import com.google.android.gms.common.api.GoogleApiClient;import com.google.android.gms.common.api.OptionalPendingResult;public class MainActivity extends FragmentActivity{    TextView logo, caption;    EditText userName, password;    private static final int RC_SIGN_IN = 9001;    public static GoogleSignInAccount account;    protected void onCreate(Bundle savedInstanceState)    {        super.onCreate(savedInstanceState);        Fresco.initialize(this);        requestWindowFeature(Window.FEATURE_NO_TITLE);        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();        StrictMode.setThreadPolicy(policy);        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                WindowManager.LayoutParams.FLAG_FULLSCREEN);        setContentView(R.layout.activity_main);//        Button button = (Button)findViewById(R.id.test);//        button.setOnClickListener(new View.OnClickListener() {//            @Override//            public void onClick(View v) {//                Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);//                startActivity(loggedin);////            }//        });        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)          //      .requestIdToken(this.getResources().getString(R.string.server_client_id))                .requestEmail()                .build();        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)                .build();        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);        if(pendingResult.isDone())        {            account = pendingResult.get().getSignInAccount();            Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);            startActivity(loggedin);        }        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);        signInButton.setSize(SignInButton.SIZE_WIDE);        signInButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);                startActivityForResult(signInIntent, RC_SIGN_IN);            }        });        logo=(TextView)findViewById(R.id.logo_gread);        caption=(TextView)findViewById(R.id.caption_text);        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");        logo.setTypeface(face);        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");        caption.setTypeface(face1);//        Button register = (Button) findViewById(R.id.register_button_login);//        Typeface register_font= Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");//        register.setTypeface(register_font);////        register.setOnClickListener(new View.OnClickListener()//        {//            public void onClick(View v)//            {//                Intent sub = new Intent(MainActivity.this, RegisterActivity.class);//                startActivity(sub);//                System.out.println("Done");//                // Perform action on click//            }//        });}    @Override    public void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if(requestCode == RC_SIGN_IN)        {            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);            handleSignInResult(result);        }    }    private void handleSignInResult(GoogleSignInResult result){        //Log.d(TAG, "handleSignInResult" + result.isSuccess());        if(result.isSuccess()){            account = result.getSignInAccount();            Intent loggedin = new Intent(MainActivity.this, HomeActivity.class);            startActivity(loggedin);        }    }}