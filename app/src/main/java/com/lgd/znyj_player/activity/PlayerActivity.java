package com.lgd.znyj_player.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.nfc.tech.NfcA;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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
    public static final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private networkChanges changes;

    private GLSurfaceView mSurfaceView;
    private RtspSurfaceRender mRender;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
    private Button mButton, mButtoncau;
    private boolean mRecording;
    private String mName;
    private String mUlr;
    private String mIp;
    private TextView mShowInfo;
    private Thread mThread;
    private boolean isp;
    private StringBuilder stringBuilder = new StringBuilder();
    private AudioManager audioManager;
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private boolean ismRecording = false;
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
                    if (isp) {
                        mRender.startRecording(mName);
                        ismRecording = true;
                        stringBuilder.append(getSystemTime() + "智能眼镜连接中..." + '\n');
                        mShowInfo.setText(stringBuilder);
                        Toast.makeText(PlayerActivity.this, "开始录制", Toast.LENGTH_SHORT).show();

                    } else {
                        ismRecording = false;
                        Toast.makeText(PlayerActivity.this, "连接异常,不支持录制", Toast.LENGTH_SHORT).show();
                        mHandle.sendEmptyMessageDelayed(2, 10000);
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
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        mName = intent.getStringExtra(Constans.PUTEXTART_NAME);
        mUlr = intent.getStringExtra(Constans.PUTEXTART_URL);
        mIp = intent.getStringExtra(Constans.PUTEXTART_IP);
        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mUlr) || TextUtils.isEmpty(mIp)) {
            Toast.makeText(this, "程序异常,即将推出", Toast.LENGTH_SHORT).show();
            finish();
        }
        //获取音频服务
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //设置声音模式
        audioManager.setMode(AudioManager.STREAM_MUSIC);
        //关闭麦克风
        audioManager.setMicrophoneMute(false);

        mSurfaceView = findViewById(R.id.surface);
        mSurfaceView.setEGLContextClientVersion(2);
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
        mHandle.sendEmptyMessageDelayed(2, 6000);
        registRecever();
    }

    /**
     * @Params: regist recever
     * @Author: liuguodong
     * @Date: 2018/2/11 10:47
     * @return：
     */

    private void registRecever() {
        IntentFilter filter = new IntentFilter(netACTION);
        changes = new networkChanges();
        registerReceiver(changes, filter);
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
        audioManager.setMicrophoneMute(true);
        audioManager.setSpeakerphoneOn(true);
        mRender.onSurfaceDestoryed();
        if (mHandle.hasMessages(1)) {
            mHandle.removeMessages(1);
        }
        if (mHandle.hasMessages(2)) {
            mHandle.removeMessages(2);
        }
        unregisterReceiver(changes);
        super.onDestroy();
    }

    @Override
    protected void onStop() {

        if (ismRecording) {
            mRender.stopRecording();
            ismRecording = false;
            Toast.makeText(this, "录制停止", Toast.LENGTH_SHORT).show();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        audioManager.setMicrophoneMute(true);
        if (ismRecording) {
            mRender.stopRecording();
            ismRecording = false;
            Toast.makeText(this, "录制停止", Toast.LENGTH_SHORT).show();
        }

        finish();
        super.onBackPressed();
    }

    public void backOnChick_devicesx(View view) {
        audioManager.setMicrophoneMute(true);
        if (ismRecording) {
            mRender.stopRecording();
            ismRecording = false;
            Toast.makeText(this, "录制停止", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    class networkChanges extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(netACTION)) {
                if (!NetworkUtils.isConnected()&&ismRecording) {
                        mRender.stopRecording();
                        ismRecording = false;
                        Toast.makeText(context, "录制停止", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
