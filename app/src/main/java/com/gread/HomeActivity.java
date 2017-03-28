package com.gread;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeActivity extends AppCompatActivity {


    private String[] readerList;
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private CharSequence title;
    private Toolbar toolbar;
    public static Context appContext;
    public boolean isHomeFragShown;
    public String storedDisplayName,storedPhotoURL,storedEmail;
    boolean doubleBackToExitPressedOnce = false;
    private ActionBarDrawerToggle drawerToggle;
    private FirebaseAnalytics mFireBaseAnalytics;

    private NetworkChangeReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        appContext = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
        ab.setDisplayHomeAsUpEnabled(true);
        mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        title = getTitle();
        //readerList = getResources().getStringArray(R.array.page_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawer = (NavigationView) findViewById(R.id.left_drawer);
        navDrawer.setNavigationItemSelectedListener(new Navigation());
        navDrawer.setItemIconTintList(null);

        //SharedPreferences preferences = getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE);
        storedDisplayName = getIntent().getStringExtra("DisplayName");
        storedPhotoURL = getIntent().getStringExtra("PhotoURL");
        storedEmail = getIntent().getStringExtra("Email");

        //drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, readerList));

        //drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        View nav_header = navDrawer.getHeaderView(0);
        TextView user_name = (TextView)nav_header.findViewById(R.id.user_display_name);
        user_name.setText(storedDisplayName);
        SimpleDraweeView user_image = (SimpleDraweeView) nav_header.findViewById(R.id.user_pic);
        user_image.setImageURI(storedPhotoURL);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerClosed(View drawerView) {
                //getSupportActionBar().setTitle(title);
                //invalidateOptionsMenu();
                Bundle params = new Bundle();
                mFireBaseAnalytics.logEvent("drawer_closed", params);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                ab.setTitle(title);
                Bundle params = new Bundle();
                mFireBaseAnalytics.logEvent("drawer_opened", params);
                //invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if(savedInstanceState==null){
            Navigation navigation = new Navigation();
            navigation.onNavigationItemSelected(navDrawer.getMenu().getItem(0));
        }

    }


    public class Navigation implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager;
            item.setChecked(true);
            Bundle params = new Bundle();
            //params.putString("imageURL", commas_recView.getAdapter().images.get(position).imageURL);
            mFireBaseAnalytics.logEvent("item_clicked", params);
            drawerLayout.closeDrawers();

            switch (item.getItemId()){


                case R.id.home:
                    Fragment homeFragment = HomeFragment.newInstance(storedDisplayName, storedEmail);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, homeFragment)
                            .commit();
                    isHomeFragShown = true;
                    setTitle("Gread");
                    Bundle parameters = new Bundle();
                    params.putString("username", storedDisplayName);
                    params.putString("emailId", storedEmail);
                    params.putString("item", item.toString());
                    mFireBaseAnalytics.logEvent("item_clicked", parameters);
                    break;

                case R.id.commas_drawer_item:
                    Fragment commasFragment = CommasFragment.newInstance(storedDisplayName, storedEmail);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, commasFragment)
                            .commit();
                    isHomeFragShown = false;
                    setTitle("Commas and Halfstrokes");
                    parameters = new Bundle();
                    params.putString("username", storedDisplayName);
                    params.putString("emailId", storedEmail);
                    params.putString("item", item.toString());
                    mFireBaseAnalytics.logEvent("item_clicked", parameters);
                    break;
                case R.id.readers_drawer_item:
                    Fragment readersFragment = ReaderFragment.newInstance(storedDisplayName, storedEmail);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, readersFragment)
                            .commit();
                    isHomeFragShown = false;
                    setTitle("Reader's Guild");
                    parameters = new Bundle();
                    params.putString("username", storedDisplayName);
                    params.putString("emailId", storedEmail);
                    params.putString("item", item.toString());
                    mFireBaseAnalytics.logEvent("item_clicked", parameters);
                    break;
                case R.id.scrawled_drawer_item:
                    Fragment scrawledFragment = ScrawledFragment.newInstance(storedDisplayName, storedEmail);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, scrawledFragment)
                            .commit();
                    isHomeFragShown = false;
                    setTitle("Scrawled Stories");
                    parameters = new Bundle();
                    params.putString("username", storedDisplayName);
                    params.putString("emailId", storedEmail);
                    params.putString("item", item.toString());
                    mFireBaseAnalytics.logEvent("item_clicked", parameters);
                    break;

            }
            return true;
        }
    }

    public class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isNetworkAvailable(context)){
                Toast.makeText(context, "You are Connected", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();

        }

        private boolean isNetworkAvailable(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager != null){
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if(info.getState()== NetworkInfo.State.CONNECTED)
                    return true;
                else
                    return false;
            }
            return false;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void onClickCommasCard(View v) {
        Fragment commasFragment = CommasFragment.newInstance(storedDisplayName, storedEmail);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, commasFragment)
                .commit();
        isHomeFragShown = false;
        setTitle("Commas and Halfstrokes");
        Bundle params = new Bundle();
        params.putString("card", "Commas and HalfStrokes");
        params.putString("username", storedDisplayName);
        params.putString("emailId", storedEmail);
        mFireBaseAnalytics.logEvent("card_clicked", params);
    }

    public void onClickReadersCard(View v){
        Fragment readersFragment = ReaderFragment.newInstance(storedDisplayName, storedEmail);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, readersFragment)
                .commit();
        isHomeFragShown = false;
        setTitle("Reader's Guild");
        Bundle params = new Bundle();
        params.putString("card", "Reader's Guild");
        params.putString("username", storedDisplayName);
        params.putString("emailId", storedEmail);
        mFireBaseAnalytics.logEvent("card_clicked", params);
    }

    public void onClickScrawledCard(View v){
        Fragment scrawledFragment = ScrawledFragment.newInstance(storedDisplayName, storedEmail);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, scrawledFragment)
                .commit();
        isHomeFragShown = false;
        setTitle("Scrawled Stories");
        Bundle params = new Bundle();
        params.putString("card", "Scrawled Stories");
        params.putString("username", storedDisplayName);
        params.putString("emailId", storedEmail);
        mFireBaseAnalytics.logEvent("card_clicked", params);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        Bundle params = new Bundle();
        //params.putString("imageURL", commas_recView.getAdapter().images.get(position).imageURL);
        mFireBaseAnalytics.logEvent("drawer_closed", params);}
        else{
            if(isHomeFragShown){
                if (doubleBackToExitPressedOnce) {
                    moveTaskToBack(true);
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }

            else {
                Navigation navigation = new Navigation();
                navigation.onNavigationItemSelected(navDrawer.getMenu().getItem(0));
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
