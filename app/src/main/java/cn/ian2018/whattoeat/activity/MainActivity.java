package cn.ian2018.whattoeat.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.PhoneInfo;
import cn.ian2018.whattoeat.bean.UpdateFile;
import cn.ian2018.whattoeat.fragment.MainFragment;
import cn.ian2018.whattoeat.utils.Constant;
import cn.ian2018.whattoeat.utils.HttpUtil;
import cn.ian2018.whattoeat.utils.Logs;
import cn.ian2018.whattoeat.utils.PhoneInfoUtil;
import cn.ian2018.whattoeat.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MainFragment mainFragment;
    private File mFile;
    private BmobFile mBmobFile;
    private ProgressDialog progressDialog;
    private String imageUrl;
    private ImageView headImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        initView();

        // 初始化Fragment
        initFragment(savedInstanceState);

        checkVersionCode();

        sendPhoneInfo();
    }

    private void sendPhoneInfo() {
        PhoneInfo phone = PhoneInfoUtil.getPhoneInfo();
        HttpUtil.sendPhoneInfo(phone);
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


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // 设置头部图片
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        headImageView = (ImageView) view.findViewById(R.id.header_image);

        // 设置侧滑菜单点击事件
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

    /**
     * 检查更新
     */
    private void checkVersionCode() {
        BmobQuery<UpdateFile> bmobQuery = new BmobQuery<>();
        // 从bmob的数据表中查询数据
        bmobQuery.getObject(Constant.UPDATE_OBJECT_ID, new QueryListener<UpdateFile>() {
            @Override
            public void done(UpdateFile updateFile, BmobException e) {
                if (e == null) {
                    // 获取图片地址
                    imageUrl = updateFile.getHeadImage();
                    Glide.with(getApplicationContext()).load(imageUrl).into(headImageView);

                    // 如果bmob中的版本号大于本地版本号
                    if (updateFile.getVersion() > getVersionCode()) {
                        // 初始化下载文件目录
                        String path = getExternalFilesDir("apk").getPath() + "/WhatToEat.apk";
                        mFile = new File(path);
                        mBmobFile = updateFile.getApkFile();
                        if (mFile != null) {
                            // 显示更新对话框
                            showUpdateDialog(updateFile.getDescription());
                        }
                    }
                } else {
                    Logs.d("查询更新信息失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 显示更新对话框
     * @param description 更新描述
     */
    private void showUpdateDialog(String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("发现新版本");
        builder.setMessage(description);
        builder.setCancelable(false);
        // 设置积极按钮
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 下载apk
                downLoadApk();
                // 显示进度对话框
                showProgressDialog();
            }
        });
        // 设置消极按钮
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 下载apk
     */
    private void downLoadApk() {
        if (mBmobFile != null) {
            // 调用bmob的下载文件api
            mBmobFile.download(mFile, new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.showShort("下载成功，保存路径为：" + mFile.getPath());
                        closeProgessDialog();
                        // 安装应用
                        installApk();
                    }
                }

                @Override
                public void onProgress(Integer integer, long l) {
                    // 更新进度对话框进度
                    progressDialog.setProgress(integer);
                }
            });
        }
    }

    /**
     * 安装应用
     */
    private void installApk() {
        if (mFile != null) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            //文件作为数据源
            intent.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.mipmap.ic_launcher);
            progressDialog.setTitle("下载安装包中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgessDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 获取应用版本号
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            // 返回应用版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
