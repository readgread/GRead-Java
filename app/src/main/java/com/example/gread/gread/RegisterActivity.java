package com.example.gread.gread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    TextView logoGread,caption;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        logoGread = (TextView) findViewById(R.id.logo_gread);
        Typeface logo_font = Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");
        logoGread.setTypeface(logo_font);

        caption = (TextView) findViewById(R.id.caption_text);
        Typeface caption_font = Typeface.createFromAsset(getAssets(), "fonts/Meltix.otf");
        caption.setTypeface(caption_font);

        registerButton = (Button) findViewById(R.id.register_button);
        Typeface button_font = Typeface.createFromAsset(getAssets(), "fonts/Port.ttf");
        registerButton.setTypeface(button_font);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent sub = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(sub);
                System.out.println("Done");
                // Perform action on click
            }
        });



    }


}
