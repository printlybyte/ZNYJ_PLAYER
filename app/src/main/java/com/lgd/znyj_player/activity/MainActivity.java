//package com.lgd.znyj_player.activity;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.lgd.znyj_player.Adapter.MDRvAdapter;
//import com.lgd.znyj_player.R;
//import com.lgd.znyj_player.bean.YJBean;
//import com.lgd.znyj_player.contrl.MDLinearRvDividerDecoration;
//import com.lgd.znyj_player.greendao.gen.DaoUtils;
//import com.lgd.znyj_player.utils.OverallUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends Activity implements View.OnClickListener {
//    private TextView mAddItemBtn;
//
//    private TextView mDelItemBtn;
//
//    private RecyclerView mRecyclerView;
//
//    private MDRvAdapter mAdapter;
//
//    private RecyclerView.LayoutManager mLayoutManager;
//    private SwipeRefreshLayout mRefreshLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        OverallUtils.setTranslucentStatus(this, true);//与系统状态栏成半透明状态
//        //全透明状态
////        if (Build.VERSION.SDK_INT >= 21) {
////            View decorView = getWindow().getDecorView();
////            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
////            decorView.setSystemUiVisibility(option);
////            getWindow().setStatusBarColor(Color.TRANSPARENT); //也可以设置成灰色透明的，比较符合Material Design的风格
////        }else {
////        }
//
//
//        initData();
//        initView();
//        initAction();
//    }
//
//
//    private void initData() {
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mAdapter = new MDRvAdapter(getData());
//
//        mAdapter.setOnItemClickListener(new MDRvAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "click " + position + " item", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "long click " + position + " item", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void initView() {
//        //下来舒心
//        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_recycler_view_refresh);
//        //设置下拉刷新图标
//        //setColorSchemeResources()可以改变加载图标的颜色。
//        mRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
//        mRefreshLayout.setOnRefreshListener(refreshlistion);
//
//        mAddItemBtn = (TextView) findViewById(R.id.rv_add_item_btn);
//        mDelItemBtn = (TextView) findViewById(R.id.rv_del_item_btn);
//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        // 设置布局管理器
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        // 设置adapter
//        mRecyclerView.setAdapter(mAdapter);
//        // 设置Item添加和移除的动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        // 设置Item之间间隔样式
////        mRecyclerView.addItemDecoration(new MDLinearRvDividerDecoration(this, LinearLayoutManager.VERTICAL));
//
//    }
//
//    private void initAction() {
//        mAddItemBtn.setOnClickListener(this);
//        mDelItemBtn.setOnClickListener(this);
//    }
//
//    private List<YJBean> getData() {
//        List<YJBean> mList = DaoUtils.quryDateAll();
//        return mList;
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.rv_add_item_btn) {
//            mAdapter.addNewItem();
//            mLayoutManager.scrollToPosition(0);
//        } else if (id == R.id.rv_del_item_btn) {
//            mAdapter.deleteItem();
//            mLayoutManager.scrollToPosition(0);
//        }
//    }
//
//    /**
//     * @Params: add chick
//     * @Author: liuguodong
//     * @Date: 2018/2/7 13:03
//     * @return：
//     */
//    public void addOnChick_devices(View view) {
//        startActivity(new Intent(MainActivity.this, UrlActivity.class));
//    }
//
//    /**
//     * @Params: 下拉舒心监听
//     * @Author: liuguodong
//     * @Date: 2018/2/8 10:38
//     * @return：
//     */
//    private SwipeRefreshLayout.OnRefreshListener refreshlistion = new SwipeRefreshLayout.OnRefreshListener() {
//        @Override
//        public void onRefresh() {
//            mAdapter.updateData(getData());
//            Toast.makeText(MainActivity.this, "舒心完成", Toast.LENGTH_SHORT).show();
//            mRefreshLayout.setRefreshing(false);
//        }
//    };
//
//
//}


package com.lgd.znyj_player.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.lgd.regcode.RegUtil;
import com.lgd.znyj_player.Adapter.MDRvAdapter;
import com.lgd.znyj_player.R;
import com.lgd.znyj_player.bean.YJBean;
import com.lgd.znyj_player.greendao.gen.DaoUtils;
import com.lgd.znyj_player.utils.NetworkUtils;
import com.lgd.znyj_player.utils.OverallUtils;
import com.lgd.znyj_player.utils.RecycleItemTouchHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import static com.lgd.znyj_player.utils.Constans.PUTEXTART_IP;
import static com.lgd.znyj_player.utils.Constans.PUTEXTART_NAME;
import static com.lgd.znyj_player.utils.Constans.PUTEXTART_URL;
import static com.lgd.znyj_player.utils.Constans.REQUST_CODE_FINSHED;

public class MainActivity extends Activity implements View.OnClickListener {
    //    private SwipeMenuRecyclerView mRecyclerView;
    private RecyclerView mRecyclerView;
    private MDRvAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayout mLinearView, mLinearViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OverallUtils.setTranslucentStatus(this, true);//与系统状态栏成半透明状态
        //全透明状态
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT); //也可以设置成灰色透明的，比较符合Material Design的风格
//        }else {
//        }

        initData();
        initRegUtil();
        initView();
    }
    private void initRegUtil() {
        RegUtil regUtil = new RegUtil(this);
        regUtil.SetDialogCancelCallBack(new RegUtil.DialogCancelInterface() {
            @Override
            public void ToFinishActivity() {
                finish();
            }

            @Override
            public void ToFinishActivity_pwd() {
                finish();
            }
        });

    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MDRvAdapter(getData());

        mAdapter.setOnItemClickListener(new MDRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                YJBean bean = getData().get(position);
                String myJdevice = bean.getMYJdevice();
                String url = bean.getMYJUrl();
                String ip = bean.getMYJip();
                if (TextUtils.isEmpty(myJdevice) || TextUtils.isEmpty(url)) {
                    Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NetworkUtils.isConnected()) {
                    Toast.makeText(MainActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                    return;
                }

                luanchPlayerActivity(myJdevice, url,ip);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "long click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChangeNotifacion(int position) {
                if (position < 1) {
                    checkViewEmpty();
                }
            }
        });
    }

    private void initView() {
        mLinearView = findViewById(R.id.containerView);
        mLinearViewEmpty = findViewById(R.id.containerViewEmpty);
        //下来舒心
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_recycler_view_refresh);
        //设置下拉刷新图标
        //setColorSchemeResources()可以改变加载图标的颜色。
        mRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mRefreshLayout.setOnRefreshListener(refreshlistion);
        //第三方的recyclervierw滑动删除
//       mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.my_recycler_view);
        //        mRecyclerView.setLongPressDragEnabled(true);// 开启长按拖拽
//        mRecyclerView.setItemViewSwipeEnabled(false);// 开启滑动删除。
//        // 设置菜单创建器。
//        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
//       // 设置菜单Item点击监听。
//        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
//        mRecyclerView.addItemDecoration(new MDLinearRvDividerDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
//滑动删除与拖拽排序
        ItemTouchHelper.Callback callback = new RecycleItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        checkViewEmpty();

    }

// 菜单点击监听。

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(MainActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(MainActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(MainActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };



    private List<YJBean> getData() {
        List<YJBean> mList = DaoUtils.quryDateAll();
        return mList;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    /**
     * @Params: jump target playeractivity
     * @Author: liuguodong
     * @Date: 2018/2/9 13:12
     * @return：
     */
    private void luanchPlayerActivity(String myJdevice, String url,String ip) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putExtra(PUTEXTART_NAME, myJdevice);
        intent.putExtra(PUTEXTART_URL, url);
        intent.putExtra(PUTEXTART_IP, ip);
        startActivity(intent );
    }

    /**
     * @Params: add chick
     * @Author: liuguodong
     * @Date: 2018/2/7 13:03
     * @return：
     */
    public void addOnChick_devices(View view) {
        startActivityForResult(new Intent(MainActivity.this, UrlActivity.class), REQUST_CODE_FINSHED);
    }

    public void fileOnChick_devices(View view) {
        startActivity(new Intent(MainActivity.this, FileActivity.class));
    }

    /**
     * @Params: 下拉舒心监听
     * @Author: liuguodong
     * @Date: 2018/2/8 10:38
     * @return：
     */
    private SwipeRefreshLayout.OnRefreshListener refreshlistion = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mAdapter.updateData(getData());
            Toast.makeText(MainActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
            mRefreshLayout.setRefreshing(false);
        }
    };

    /**
     * @Params: checkdate emplelinear
     * @Author: liuguodong
     * @Date: 2018/2/8 15:44
     * @return：
     */
    public void checkViewEmpty() {
        if (getData().size() > 0) {
            mLinearViewEmpty.setVisibility(View.GONE);
            mLinearView.setVisibility(View.VISIBLE);
        } else {

            mLinearViewEmpty.setVisibility(View.VISIBLE);
            mLinearView.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUST_CODE_FINSHED == requestCode) {
            mAdapter.updateData(getData());
            mRecyclerView.setAdapter(mAdapter);
            checkViewEmpty();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
