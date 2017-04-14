package cn.ian2018.whattoeat.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.utils.Logs;
import cn.ian2018.whattoeat.utils.ToastUtil;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView versionText;
    private TextView contactWeText;
    private TextView joinQQ;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();

        setClick();
    }

    private void setClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contactWeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将文字复制到系统粘贴板
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("text","chenshuai@ian2018.cn");
                cm.setPrimaryClip(data);
                ToastUtil.showShort("已经将邮箱复制到粘贴板上");
            }
        });

        joinQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将文字复制到系统粘贴板
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("text","626180781");
                cm.setPrimaryClip(data);
                ToastUtil.showShort("已经将群号复制到粘贴板上");
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMWeb web = new UMWeb("http://fir.im/whattoeat");
                UMImage image = new UMImage(AboutActivity.this, R.drawable.icon);
                web.setTitle("我在使用吃什么，快来一起使用吧");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("吃什么——为你解决不知道吃什么的问题");//描述
                // 友盟分享
                new ShareAction(AboutActivity.this).withMedia(web)
                        // 添加微信分享
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                ToastUtil.showShort("分享成功");
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Logs.e("分享失败" + throwable.toString());
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Logs.d("取消分享");
                            }
                        }).open();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.about_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionText = (TextView) findViewById(R.id.version_text);
        contactWeText = (TextView) findViewById(R.id.tv_contact_we);
        joinQQ = (TextView) findViewById(R.id.tv_feedback_qq);

        shareButton = (Button) findViewById(R.id.bt_share);

        // 拿到包管理者
        PackageManager pm = getPackageManager();
        // 获取包的信息(Info)
        try {
            // flags：为0是获取基本信息
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            // 设置版本号
            versionText.setText("版本号："+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
