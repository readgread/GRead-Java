package com.example.gread.gread;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {


    private String[] readerList;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence title;

    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        title = getTitle();
        readerList = getResources().getStringArray(R.array.page_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, readerList));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

       // getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){

//            public void onDrawerClosed(View drawerView) {
//                getActionBar().setTitle(title);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(title);
//                invalidateOptionsMenu();
//            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if(savedInstanceState==null){
            selectItem(0);
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

        }
    }

    private void selectItem(int position){
        Fragment commasFragment = CommasFragment.newInstance("Hello", "World!");

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, commasFragment)
                .commit();

        drawerList.setItemChecked(position, true);
        //setTitle(readerList[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }
}





