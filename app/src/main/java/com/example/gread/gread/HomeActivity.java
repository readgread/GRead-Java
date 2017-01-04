package com.example.gread.gread;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import static com.example.gread.gread.MainActivity.account;

public class HomeActivity extends AppCompatActivity {


    private String[] readerList;
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private CharSequence title;
    private Toolbar toolbar;
    public static Context appContext;


    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        appContext = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
        ab.setDisplayHomeAsUpEnabled(true);

        title = getTitle();
        //readerList = getResources().getStringArray(R.array.page_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawer = (NavigationView) findViewById(R.id.left_drawer);
        navDrawer.setNavigationItemSelectedListener(new Navigation());

        //drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, readerList));

        //drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

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
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if(savedInstanceState==null){

        }

    }

    public class Navigation implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager;
            item.setChecked(true);
            drawerLayout.closeDrawers();

            switch (item.getItemId()){

                case R.id.home:
                    Fragment homeFragment = HomeFragment.newInstance(account.getDisplayName(), account.getEmail());

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, homeFragment)
                            .commit();
                    break;

                case R.id.commas_drawer_item:
                    Fragment commasFragment = CommasFragment.newInstance(account.getDisplayName(), account.getEmail());

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, commasFragment)
                            .commit();
                    break;
            }
            return true;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

//    private void selectItem(int position){
//        if (position==0)
//        {
//            Fragment homeFragment = HomeFragment.newInstance(account.getDisplayName(), account.getEmail());
//
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, homeFragment)
//                    .commit();
//        }
//
//        if(position==1) {
//            Fragment commasFragment = CommasFragment.newInstance(account.getDisplayName(), account.getEmail());
//
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, commasFragment)
//                    .commit();
//        }
//        drawerList.setItemChecked(position, true);
//        setTitle(readerList[position]);
//        drawerLayout.closeDrawer(drawerList);
//    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void onClickCommasCard(View v) {
        Fragment commasFragment = CommasFragment.newInstance(account.getDisplayName(), account.getEmail());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, commasFragment)
                .commit();
    }

}
