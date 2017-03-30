package cn.ian2018.whattoeat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.ian2018.whattoeat.MyApplication;
import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.ShopMenu;

/**
 * Created by Administrator on 2017/3/30/030.
 */

public class ListViewAdapter extends BaseAdapter {
    private List<ShopMenu> shopMenuList;

    public ListViewAdapter (List<ShopMenu> shopMenuList) {
        this.shopMenuList = shopMenuList;
    }
    @Override
    public int getCount() {
        return shopMenuList.size();
    }

    @Override
    public ShopMenu getItem(int position) {
        return shopMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(), R.layout.list_item_shop_menu, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(getItem(position).getName());
        viewHolder.price.setText(getItem(position).getPrice() + "元");
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView price;
    }
}


