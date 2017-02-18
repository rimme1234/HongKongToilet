package hk.edu.ouhk.ronnie.hongkongtoilet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import layout.SettingFragment;
import layout.ToiletListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager;
    ToiletListFragment toiletListFragment;
    SettingFragment settingFragment;
    ActionBarDrawerToggle toggle;
    ToiletApplication application;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application=(ToiletApplication)this.getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setSubtitle(getString(R.string.action_search));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_search).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        toiletListFragment = new ToiletListFragment();
        settingFragment = new SettingFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                toiletListFragment,
                toiletListFragment.getTag()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment displayedFragment = getSupportFragmentManager().findFragmentByTag("detail");
            if(displayedFragment!=null && displayedFragment.isVisible())
            {
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.content_main,
                        toiletListFragment,
                        toiletListFragment.getTag()).commit();
            }
            else {
            super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            toiletListFragment.getLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    toiletListFragment,
                    toiletListFragment.getTag()).commit();
        } else if (id == R.id.nav_setting) {
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    settingFragment,
                    settingFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
