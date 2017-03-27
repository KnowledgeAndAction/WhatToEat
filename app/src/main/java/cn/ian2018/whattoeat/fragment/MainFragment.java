package cn.ian2018.whattoeat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ian2018.whattoeat.R;

/**
 * Created by Administrator on 2017/3/27/027.
 */

public class MainFragment extends android.support.v4.app.Fragment {
    private Context context;
    private CalledTakeawayFragment calledTakeawayFragment;
    private ShopToEatFragment shopToEatFragment;
    private TabLayout tabLayout;
    private String[] titles;
    private PagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            calledTakeawayFragment = (CalledTakeawayFragment) manager.getFragment(savedInstanceState, "call");
            shopToEatFragment = (ShopToEatFragment) manager.getFragment(savedInstanceState, "shop");
        } else {
            calledTakeawayFragment = new CalledTakeawayFragment();
            shopToEatFragment = new ShopToEatFragment();
        }

        titles = new String[]{
                context.getResources().getString(R.string.called_takeaway),
                context.getResources().getString(R.string.shop_to_eat),
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        adapter = new PagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "call", calledTakeawayFragment);
        manager.putFragment(outState, "shop", shopToEatFragment);
    }

    // 适配器
    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return shopToEatFragment;
            }
            return calledTakeawayFragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
