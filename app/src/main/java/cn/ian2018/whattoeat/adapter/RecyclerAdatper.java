package cn.ian2018.whattoeat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.ian2018.whattoeat.MyApplication;
import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.Shop;

/**
 * Created by Administrator on 2017/1/23/023.
 */

public class RecyclerAdatper extends RecyclerView.Adapter<RecyclerAdatper.ViewHolder> {

    private List<Shop> mShopList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View shopView;
        ImageView shopImage;
        TextView shopNameText;
        TextView shopPhoneText;
        TextView shopLocText;

        public ViewHolder(View itemView) {
            super(itemView);
            shopView = itemView;
            shopImage = (ImageView) itemView.findViewById(R.id.shop_image);
            shopNameText = (TextView) itemView.findViewById(R.id.shop_name);
            shopPhoneText = (TextView) itemView.findViewById(R.id.shop_phone);
            shopLocText = (TextView) itemView.findViewById(R.id.shop_loction);
        }
    }

    public RecyclerAdatper(List<Shop> fruitList) {
        mShopList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        // 设置点击事件
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posotion = holder.getAdapterPosition();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shop shop = mShopList.get(position);
        Glide.with(MyApplication.getContext()).load(shop.getImageUrl()).into(holder.shopImage);
        holder.shopNameText.setText(shop.getName());
        holder.shopPhoneText.setText("电话：" + shop.getPhone());
        holder.shopLocText.setText("位置：" + shop.getLocation());
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }
}
