package com.lgd.znyj_player.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lgd.znyj_player.Adapter.MDRvAdapter;
import com.lgd.znyj_player.Adapter.MDRvAdapterFile;
import com.lgd.znyj_player.R;
import com.lgd.znyj_player.bean.YJBean;
import com.lgd.znyj_player.utils.NetworkUtils;
import com.lgd.znyj_player.utils.OverallUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import freemarker.template.utility.Constants;

import static com.lgd.znyj_player.utils.Constans.FINALPATH;

public class FileActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MDRvAdapterFile mAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayout mLinearView, mLinearViewEmpty, mLinearViewChcnel;
    private TextView mTextviewDismis;
    private List<String> mListPath;
    private FloatingActionButton mFabBtn;
    private PopupWindow mPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();
    }

    private void initView() {

        mLinearViewEmpty = findViewById(R.id.containerViewEmpty);
        mLinearViewChcnel = findViewById(R.id.containerViewChancl);
        mLinearView = findViewById(R.id.containerView);
        initData();
//        mFabBtn = (FloatingActionButton) findViewById(R.id.fab_change_comment);
//        mFabBtn.setOnClickListener(fab_chick);
        //下来舒心
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.file_manger_view_refresh);
        //设置下拉刷新图标
        //setColorSchemeResources()可以改变加载图标的颜色。
        mRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mRefreshLayout.setOnRefreshListener(refreshlistion);
        mRecyclerView = (RecyclerView) findViewById(R.id.file_manger_recycler);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
//        mRecyclerView.addItemDecoration(new MDLinearRvDividerDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mLayoutManager = new GridLayoutManager(this, 3);
        mAdapter = new MDRvAdapterFile(getVideoFileName(), mListPath);

        mAdapter.setOnItemClickListener(new MDRvAdapterFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openFile(new File(mListPath.get(position)));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mAdapter.updateData(getVideoFileName(), true);
                showPopupWindow();
            }

            @Override
            public void onChildItemChick(View view, int position) {
                boolean isDel = OverallUtils.deleteFile(mListPath.get(position));
                if (isDel) {
                    Toast.makeText(FileActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    mAdapter.updateData(getVideoFileName(), true);
                } else {
                    Toast.makeText(FileActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private SwipeRefreshLayout.OnRefreshListener refreshlistion = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mAdapter.updateData(getVideoFileName());

            Toast.makeText(FileActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
            mRefreshLayout.setRefreshing(false);
        }
    };

    // 获取当前目录下所有的mp4文件  
    public List<String> getVideoFileName() {

        Vector<String> vecFile = new Vector<String>();
        File file = new File(FINALPATH);
        File[] subFile = file.listFiles();
        if (subFile == null||subFile.length==0) {
            mLinearViewEmpty.setVisibility(View.VISIBLE);
            if (mLinearViewChcnel.getVisibility()==0){
                dismisPopupWindow();
            }
        } else {
            mLinearViewEmpty.setVisibility(View.GONE);
            if (mListPath == null) {
                mListPath = new ArrayList<>();
            }
            mListPath.clear();
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    String filenamepath = subFile[iFileLength].getPath();
                    // 判断是否为MP4结尾
                    if (filename.trim().toLowerCase().endsWith(".mp4")) {
                        vecFile.add(filename);
                        mListPath.add(filenamepath);
                    }
                }
            }
        }
        return vecFile;
    }

    public void backOnChick_devicesx(View view) {
        finish();
    }

    /**
     * @Params: open video file
     * @Author: liuguodong
     * @Date: 2018/2/9 16:13
     * @return：
     */
    private void openFile(File f) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        String type = getMIMEType(f);
        intent.setDataAndType(Uri.fromFile(f), type);
        startActivity(intent);
    }

    public void dismisSeleAll(View view) {
        dismisPopupWindow();
    }

    /**
     * @Params: open video file mine type
     * @Author: liuguodong
     * @Date: 2018/2/9 16:13
     * @return：
     */


    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else {
            type = "*";
        }
        type += "/*";
        return type;
    }

    private void showPopupWindow() {
        mLinearViewChcnel.setVisibility(View.VISIBLE);
    }
    private void dismisPopupWindow() {
        mLinearViewChcnel.setVisibility(View.GONE);
        mAdapter.updateData(getVideoFileName(), false);
    }
    @Override
    public void onBackPressed() {
        if (mLinearViewChcnel.getVisibility()==0){
            dismisPopupWindow();
            return;
        }
        super.onBackPressed();
    }


    private View.OnClickListener fab_chick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(FileActivity.this, "DADAD", Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "dddddd", Snackbar.LENGTH_SHORT).setAction("sas", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FileActivity.this, "sasa", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    };
}
