package cn.ian2018.whattoeat.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.activity.ShopActivity;
import cn.ian2018.whattoeat.adapter.RecyclerAdapter;
import cn.ian2018.whattoeat.bean.Shop;
import cn.ian2018.whattoeat.utils.HttpUtil;
import cn.ian2018.whattoeat.utils.ToastUtil;

/**
 * Created by Administrator on 2017/3/27/027.
 * 到店吃
 */

public class ShopToEatFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Shop> shopList = new ArrayList<>();
    private RecyclerAdapter adapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initView(view);

        // 设置交互事件
        setClick();

        initData();

        showData();

        return view;
    }

    public void showDialog() {
        // 产生一个随机数
        int number = (int) (Math.random() * 100) + 1;
        int position = number % shopList.size();
        final Shop shop = shopList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.create();
        View view = View.inflate(getContext(), R.layout.dialog_random, null);
        dialog.setView(view, 0, 0, 0, 0);

        ImageView shopImage = (ImageView) view.findViewById(R.id.image_view);
        TextView shopName = (TextView) view.findViewById(R.id.name);
        Button button = (Button) view.findViewById(R.id.bt_to);

        Glide.with(getContext()).load(shop.getImageUrl()).placeholder(R.drawable.ic_placeholder).into(shopImage);
        shopName.setText(shop.getName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("shop",shop);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void initData() {
        // 加载数据
        showProgressDialog();
        HttpUtil.getData(2, shopList, new HttpUtil.OnResult() {
            @Override
            public void success() {
                closeProgressDialog();
                showData();
            }

            @Override
            public void error() {
                closeProgressDialog();
                ToastUtil.showShort("获取数据失败");
            }
        });
    }

    private void showData() {
        if (adapter == null) {
            adapter = new RecyclerAdapter(shopList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        // 设置RecyclerView条目点击事件
        adapter.setItemClickListener(new RecyclerAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("shop",shopList.get(position));
                startActivity(intent);
            }
        });
    }

    private void setClick() {
        // 设置RecyclerView滑动监听事件
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        // TODO 加载更多
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
                // 隐藏或者显示fab
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
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
