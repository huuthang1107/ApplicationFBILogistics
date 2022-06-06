package com.example.demoapp.view.activity.dom;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivityDomBinding;
import com.example.demoapp.view.activity.LoginActivity;
import com.example.demoapp.view.activity.chat.DashboardActivity;
import com.example.demoapp.view.fragment.dom.DomColdFragment;
import com.example.demoapp.view.fragment.dom.DomCyFragment;
import com.example.demoapp.view.fragment.dom.DomCySeaFragment;
import com.example.demoapp.view.fragment.dom.DomDoorFragment;
import com.example.demoapp.view.fragment.dom.DomDoorSeaFragment;
import com.example.demoapp.view.fragment.dom.DomDryFragment;
import com.example.demoapp.view.fragment.dom.DomExportFragment;
import com.example.demoapp.view.fragment.dom.DomImportFragment;
import com.example.demoapp.view.fragment.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DomActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ActivityDomBinding binding;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_DOM_EXPORT = 1;
    private static final int FRAGMENT_DOM_IMPORT = 2;
    private static final int FRAGMENT_DOM_DRY = 3;
    private static final int FRAGMENT_DOM_COLD = 4;
    private static final int FRAGMENT_DOM_CY = 5;
    private static final int FRAGMENT_DOM_DOOR= 6;
    private static final int FRAGMENT_DOM_CY_SEA = 7;
    private static final int FRAGMENT_DOM_DOOR_SEA = 8;
    private static final int ACTIVITY_MESSAGE = 9;
    private static final int LOG_OUT = 10;

    private FirebaseAuth mAuth;

    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    public void initView() {
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        replaceFragment(new HomeFragment());
        binding.navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (mCurrentFragment != FRAGMENT_HOME) {
                    replaceFragment(new HomeFragment());
                    mCurrentFragment = FRAGMENT_HOME;
                    binding.toolbar.setTitle("Home");
                }
                break;
            case R.id.nav_dom_export:
                if (mCurrentFragment != FRAGMENT_DOM_EXPORT) {
                    replaceFragment(new DomExportFragment());
                    mCurrentFragment = FRAGMENT_DOM_EXPORT;
                    binding.toolbar.setTitle("DOM EXPORT");
                }
                break;
            case R.id.nav_dom_import:
                if (mCurrentFragment != FRAGMENT_DOM_IMPORT) {
                    replaceFragment(new DomImportFragment());
                    mCurrentFragment = FRAGMENT_DOM_IMPORT;
                    binding.toolbar.setTitle("DOM IMPORT");
                }
                break;
            case R.id.nav_dom_dry:
                if (mCurrentFragment != FRAGMENT_DOM_DRY) {
                    replaceFragment(new DomDryFragment());
                    mCurrentFragment = FRAGMENT_DOM_DRY;
                    binding.toolbar.setTitle("DOM DRY");
                }
                break;
            case R.id.nav_dom_cold:
                if (mCurrentFragment != FRAGMENT_DOM_COLD) {
                    replaceFragment(new DomColdFragment());
                    mCurrentFragment = FRAGMENT_DOM_COLD;
                    binding.toolbar.setTitle("DOM COLD");
                }
                break;
            case R.id.nav_dom_cy:
                if (mCurrentFragment != FRAGMENT_DOM_CY) {
                    replaceFragment(new DomCyFragment());
                    mCurrentFragment = FRAGMENT_DOM_CY;
                    binding.toolbar.setTitle("DOM CY");
                }
                break;
            case R.id.nav_dom_door:
                if (mCurrentFragment != FRAGMENT_DOM_DOOR) {
                    replaceFragment(new DomDoorFragment());
                    mCurrentFragment = FRAGMENT_DOM_DOOR;
                    binding.toolbar.setTitle("DOM DOOR");
                }
                break;
            case R.id.nav_dom_cy_sea:
                if (mCurrentFragment != FRAGMENT_DOM_CY_SEA) {
                    replaceFragment(new DomCySeaFragment());
                    mCurrentFragment = FRAGMENT_DOM_CY_SEA;
                    binding.toolbar.setTitle("DOM CY SEA");
                }
                break;
            case R.id.nav_dom_door_sea:
                if (mCurrentFragment != FRAGMENT_DOM_DOOR_SEA) {
                    replaceFragment(new DomDoorSeaFragment());
                    mCurrentFragment = FRAGMENT_DOM_DOOR_SEA;
                    binding.toolbar.setTitle("DOM DOOR SEA");
                }
                break;
            case R.id.tab_chat_fcl:
                if (mCurrentFragment != ACTIVITY_MESSAGE) {
                    Intent intent = new Intent(this, DashboardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tab_logout_fcl:
                if (mCurrentFragment != LOG_OUT) {
                    mAuth.signOut();
                    checkUserStatus();
                }
                finish();
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkUserStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(DomActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}