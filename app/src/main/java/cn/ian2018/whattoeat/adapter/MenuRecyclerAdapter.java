package cn.ian2018.whattoeat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.ShopMenu;

/**
 * Created by Administrator on 2017/1/23/023.
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    private List<ShopMenu> mShopMenuLists;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView shopNameText;
        TextView shopPhoneText;

        public ViewHolder(View itemView) {
            super(itemView);
            shopNameText = (TextView) itemView.findViewById(R.id.name);
            shopPhoneText = (TextView) itemView.findViewById(R.id.price);
        }
    }


    public MenuRecyclerAdapter(List<ShopMenu> shopMenuList) {
        mShopMenuLists = shopMenuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shop_menu, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShopMenu menu = mShopMenuLists.get(position);
        holder.shopNameText.setText(menu.getName());
        holder.shopPhoneText.setText(menu.getPrice() + "元");
    }

    @Override
    public int getItemCount() {
        return mShopMenuLists.size();
    }
}
