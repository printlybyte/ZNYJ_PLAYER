package com.lgd.znyj_player.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.SoundPool;
import android.nfc.tech.NfcA;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgd.znyj_player.R;
import com.lgd.znyj_player.contrl.RtspSurfaceRender;
import com.lgd.znyj_player.utils.Constans;
import com.lgd.znyj_player.utils.NetworkUtils;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lgd.znyj_player.utils.OverallUtils.getSystemTime;

public class PlayerActivity extends Activity {

//    public static final String URL = "rtsp://admin:admin123@10.31.11.79:554/cam/realmonitor?channel=1@subtype=0";

    //    public static final String URL = "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";
//    public static final String URL = "rtsp://218.246.35.198:554/123456.sdp";
    public static final String URL = "rtsp://192.168.10.200:8554/recovderLive";

//    public static final String URL = "rtsp://10.31.0.61:8554/test.mkv";

    private GLSurfaceView mSurfaceView;
    private RtspSurfaceRender mRender;

    private Button mButton, mButtoncau;
    private boolean mRecording;
    private String mName;
    private String mUlr;
    private String mIp;
    private TextView mShowInfo;
    private Thread mThread;
    private boolean isp = true;
    private StringBuilder stringBuilder = new StringBuilder();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    singleThreadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            isp = NetworkUtils.isAvailableByPing(mIp);
                        }
                    });
                    if (isp) {
                    } else {
                        stringBuilder.append(getSystemTime() + " 智能眼镜连接异常,请检查..." + '\n');
                    }
                    mShowInfo.setText(stringBuilder);
                    mHandle.sendEmptyMessageDelayed(1, 10000);
                    Log.i("qweqwe", "" + mIp + isp + mUlr);
                    break;
                case 2:
                    if (!mRecording) {
                        mRender.startRecording(mName);
                        mRecording = !mRecording;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        mName = intent.getStringExtra(Constans.PUTEXTART_NAME);
        mUlr = intent.getStringExtra(Constans.PUTEXTART_URL);
        mIp = intent.getStringExtra(Constans.PUTEXTART_IP);
        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mUlr) || TextUtils.isEmpty(mIp)) {
            Toast.makeText(this, "程序异常,即将推出", Toast.LENGTH_SHORT).show();
            finish();
        }
        mSurfaceView = findViewById(R.id.surface);
        mSurfaceView.setEGLContextClientVersion(3);
        mShowInfo = findViewById(R.id.showinfo_txt);
        mRender = new RtspSurfaceRender(mSurfaceView);
        mRender.setRtspUrl(mUlr);

        mButton = findViewById(R.id.btn);
        mButtoncau = findViewById(R.id.btn_cau);
        mButtoncau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mRecording) {
                    mButton.setText("start");
                    mRender.stopRecording();
                } else {
                    mButton.setText("stop");
                    mRender.startRecording(mName);
                }
                mRecording = !mRecording;
            }
        });

        mSurfaceView.setRenderer(mRender);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mHandle.sendEmptyMessage(1);
        stringBuilder.append(getSystemTime() + "智能眼镜连接中..." + '\n');
        mShowInfo.setText(stringBuilder);
        mHandle.sendEmptyMessageDelayed(2, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        mRender.onSurfaceDestoryed();
        if (mHandle.hasMessages(1)) {
            mHandle.removeMessages(1);
        }
        if (mHandle.hasMessages(2)) {
            mHandle.removeMessages(2);
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (mRecording) {
            mRender.stopRecording();
            mRecording = !mRecording;
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (mRecording) {
            mRender.stopRecording();
            mRecording = !mRecording;
        }
        finish();
        super.onBackPressed();
    }

    public void backOnChick_devicesx(View view) {
        if (mRecording) {
            mRender.stopRecording();
            mRecording = !mRecording;
        }
        finish();
    }
}
