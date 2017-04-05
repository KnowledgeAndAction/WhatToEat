package cn.ian2018.whattoeat.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.ian2018.whattoeat.R;
import cn.ian2018.whattoeat.bean.Feedback;
import cn.ian2018.whattoeat.bean.PhoneInfo;
import cn.ian2018.whattoeat.utils.Logs;
import cn.ian2018.whattoeat.utils.PhoneInfoUtil;
import cn.ian2018.whattoeat.utils.ToastUtil;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView groupText;
    private EditText feedbackEdit;
    private Button submitButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

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

        groupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将文字复制到系统粘贴板
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("text","626180781");
                cm.setPrimaryClip(data);
                ToastUtil.showShort("已将群号拷贝到粘贴板上");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = feedbackEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    showProgressDialog();

                    PhoneInfo phoneInfo = PhoneInfoUtil.getPhoneInfo();
                    Feedback feedback = new Feedback();
                    feedback.setInfo(message);
                    feedback.setPhoneBrand(phoneInfo.getPhoneBrand());
                    feedback.setPhoneBrandType(phoneInfo.getPhoneBrandType());
                    feedback.setAndroidVersion(phoneInfo.getAndroidVersion());

                    feedback.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                closeProgressDialog();
                                feedbackEdit.setText("");
                                ToastUtil.showShort("感谢您的反馈，我们会尽快处理");
                            } else {
                                closeProgressDialog();
                                ToastUtil.showShort("反馈失败，请您稍后重试");
                                Logs.d("反馈失败：" + e.getMessage());
                            }
                        }
                    });
                } else {
                    ToastUtil.showShort("反馈内容不能为空");
                }
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.feed_back_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        groupText = (TextView) findViewById(R.id.feedback_group_text);

        feedbackEdit = (EditText) findViewById(R.id.feedback_edit);

        submitButton = (Button) findViewById(R.id.submit_button);
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("发送反馈中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}
