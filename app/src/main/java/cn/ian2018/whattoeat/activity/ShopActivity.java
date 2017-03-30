package cn.ian2018.whattoeat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.adapter.MenuRecyclerAdapter;
import cn.ian2018.whattoeat.bean.Shop;
import cn.ian2018.whattoeat.bean.ShopMenu;
import cn.ian2018.whattoeat.utils.HttpUtil;
import cn.ian2018.whattoeat.utils.ToastUtil;
import cn.ian2018.whattoeat.view.FullyLinearLayoutManager;

/**
 * 商铺详情
 */
public class ShopActivity extends BaseActivity {

    private ImageView imageView;
    private CollapsingToolbarLayout toolbarLayout;
    private Shop shop;
    private int type;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<ShopMenu> shopMenuList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type",0);
            shop = (Shop) intent.getSerializableExtra("shop");
        }

        initView();

        setClick();

        getMenuData(shop.getId());
    }

    private void getMenuData(int shopId) {
        showProgressDialog();
        HttpUtil.getMenuData(shopId, shopMenuList, new HttpUtil.OnResult() {
            @Override
            public void success() {
                MenuRecyclerAdapter adapter = new MenuRecyclerAdapter(shopMenuList);
                recyclerView.setAdapter(adapter);
                closeProgressDialog();
            }

            @Override
            public void error() {
                closeProgressDialog();
                ToastUtil.showShort("加载失败");
            }
        });
    }

    private void setClick() {
        // 设置滑动监听
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });

        // 设置fab的点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shop.getPhone()));
                startActivity(intent);
            }
        });

        // 返回按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnabled(false);

        imageView = (ImageView) findViewById(R.id.image_view);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        nestedScrollView = (NestedScrollView) findViewById(R.id.scrollView);

        // 设置标题和背景图
        toolbarLayout.setTitle(shop.getName());
        Glide.with(this)
                .load(shop.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .into(imageView);

        TextView location = (TextView) findViewById(R.id.location);
        TextView phone = (TextView) findViewById(R.id.phone);
        location.setText("店铺位置：" + shop.getLocation());
        phone.setText("电话：" + shop.getPhone());
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
