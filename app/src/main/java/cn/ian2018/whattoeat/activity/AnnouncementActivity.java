package cn.ian2018.whattoeat.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.Announcement;
import cn.ian2018.whattoeat.utils.Constant;
import cn.ian2018.whattoeat.utils.Logs;

public class AnnouncementActivity extends BaseActivity {

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private ImageView imageView;
    private TextView announcementText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        initView();

        setClick();

        getData();
    }

    private void getData() {
        showProgressDialog();
        BmobQuery<Announcement> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Constant.ANNOUNCEMENT_OBJECT_ID, new QueryListener<Announcement>() {
            @Override
            public void done(Announcement announcement, BmobException e) {
                if (e == null) {
                    Glide.with(AnnouncementActivity.this).load(announcement.getImageUrl()).placeholder(R.drawable.ic_placeholder).into(imageView);
                    announcementText.setText(announcement.getAnnouncement());
                    closeProgressDialog();
                } else {
                    Logs.e("获取公告失败" + e.toString());
                }
            }
        });
    }

    private void setClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.announcement_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.announcement_image);
        announcementText = (TextView) findViewById(R.id.announcement_text);
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}
