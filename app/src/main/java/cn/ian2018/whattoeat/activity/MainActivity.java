package cn.ian2018.whattoeat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        initView();

        // 初始化Fragment
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        // 创建Fragment实例
        if (savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
        } else {
            mainFragment = new MainFragment();
        }

        // 添加Fragment
        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }

        // 显示Fragment
        showMainFragment();
    }

    private void showMainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 设置侧滑菜单点击事件
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);

                int id = item.getItemId();
                switch (id) {
                    case R.id.nac_feedback:
                        startActivity(new Intent(getApplicationContext(),FeedBackActivity.class));
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存Fragment
        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
    }
}
