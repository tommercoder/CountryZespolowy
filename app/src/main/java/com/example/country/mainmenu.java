package com.example.country;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.country.ActivitiesNav.election_act;
import com.example.country.ActivitiesNav.profile_act;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class mainmenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//phone up bar off


        setContentView(R.layout.mainmenu_act);
        ////
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar1 = findViewById(R.id.toolbar);
        ////
        //setSupportActionBar(toolbar1);
        ////
        navigationView.bringToFront();//setting drawer to fron of screen
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);//burger button
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//turning burger button off after drawer was opened

        navigationView.setNavigationItemSelectedListener(this);//clickable buttons

        navigationView.setCheckedItem(R.id.home_id);


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);//for icon of burger menu

    }

    @Override
    public void onBackPressed() {//android back button

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);//tapping close

        } else {




        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home_id:

                break;
            case R.id.election_id:

                startActivity(new Intent(mainmenu.this, election_act.class));
                break;
            //case R.id.Local_solutions_id:

                //break;
           // case R.id.referendum_id:

               // break;
            //case R.id.chat_id:

                //break;
            case R.id.logout_id:
                FirebaseAuth.getInstance().signOut();
                //Intent logout = new Intent(mainmenu.this, MainActivity.class);
                finish();
               // startActivity(logout);
                break;
            case R.id.phoneSup_id:

                break;
            case R.id.emailSup_id:

                break;
            case R.id.profile_id:
                startActivity(new Intent(mainmenu.this, profile_act.class));

                break;
            case R.id.settingsBtn:

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);//after pressing closing the drawer

        return false;//checking what was clicked
    }
}
