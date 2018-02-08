package com.lgd.znyj_player.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lgd.lgdmyutilsx.loadingbutton.ActionProcessButton;
import com.lgd.lgdmyutilsx.loadingbutton.ProgressGenerator;
import com.lgd.znyj_player.Applacationx;
import com.lgd.znyj_player.R;
import com.lgd.znyj_player.bean.YJBean;
import com.lgd.znyj_player.greendao.gen.DaoUtils;
import com.lgd.znyj_player.greendao.gen.YJBeanDao;
import com.lgd.znyj_player.utils.OverallUtils;
import com.lgd.znyj_player.utils.XEditText;

import java.util.List;

public class UrlActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {

    /**
     * 请输入您的IP
     */
    private XEditText mXeditIp;
    /**
     * 请输入您的设备编号
     */
    private XEditText mXeditDevicesid;
    private ActionProcessButton mBtnSave;
    private ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        OverallUtils.setTranslucentStatus(this, true);
        initView();
        YJBean bean = new YJBean();
        bean.setMYJdevice("sdfsdfsdfs1111111");
        bean.setMYJName("dgdfdfgddgdfgdfg");
        bean.setId(OverallUtils.longPressLong("2222"));
        Applacationx.getDaoInstant().getYJBeanDao().update(bean);
    }

    private void initView() {
        mXeditIp = (XEditText) findViewById(R.id.xedit_ip);
        mXeditDevicesid = (XEditText) findViewById(R.id.xedit_devicesid);
        mBtnSave = (ActionProcessButton) findViewById(R.id.btnSave);
        mBtnSave.setOnClickListener(mBtnSaveChick);
        progressGenerator = new ProgressGenerator(this);
        mBtnSave.setMode(ActionProcessButton.Mode.ENDLESS);//tumblr加载样式
//            mBtnSave.setMode(ActionProcessButton.Mode.PROGRESS);//直线加载样式
    }

    View.OnClickListener mBtnSaveChick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dvc = mXeditDevicesid.getTrimmedString();
            String ipc = mXeditIp.getTrimmedString();
            if (TextUtils.isEmpty(dvc)) {
                Toast.makeText(UrlActivity.this, "请输入设备名称", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(ipc)) {
                Toast.makeText(UrlActivity.this, "请输入IP地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (DaoUtils.isQueryDeviceExist(dvc)) {
                Toast.makeText(UrlActivity.this, "已经添加此名称了", Toast.LENGTH_SHORT).show();
            } else {
                String url = "";
                url = "rtsp://" + ipc + ":8554/" + dvc;
                progressGenerator.start(mBtnSave);
                mBtnSave.setEnabled(false);
                mXeditIp.setEnabled(false);
                mXeditDevicesid.setEnabled(false);
                YJBean bean = new YJBean();
                bean.setId(OverallUtils.longPressLong(dvc));
                bean.setMYJdevice(dvc);
                bean.setMYJUrl(url);
                bean.setMYJip(ipc);
                Applacationx.getDaoInstant().getYJBeanDao().insert(bean);
            }


//          String result=  DaoUtils.queryDeviceId("123");

            Log.i("QWEQWE", " == ");

        }
    };

    /**
     * @Params: loading compail
     * @Author: liuguodong
     * @Date: 2018/2/7 15:08
     * @return：
     */
    @Override
    public void onComplete() {
        Toast.makeText(this, R.string.savecomplete, Toast.LENGTH_SHORT).show();
        finish();

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void backOnChick_devicesx(View view) {
        finish();
    }
}
