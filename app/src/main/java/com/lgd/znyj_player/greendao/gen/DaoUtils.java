package com.lgd.znyj_player.greendao.gen;

import android.app.Application;
import android.text.TextUtils;

import com.lgd.znyj_player.Applacationx;
import com.lgd.znyj_player.bean.YJBean;
import com.lgd.znyj_player.utils.OverallUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */

public class DaoUtils {
    public void insetValue(YJBean bean) {

        Applacationx.getDaoInstant().getYJBeanDao().insertOrReplace(bean);
    }

    /**
     * 通过名字加载指定数据
     *
     * @return 数据列表
     */

    public static String loadDateValue(String name) {
        if (isQueryDeviceExist(name)) {
            long id = OverallUtils.longPressLong(name);
            return Applacationx.getDaoInstant().getYJBeanDao().load(id).getMYJdevice();
        }
        return "";
    }

    /**
     * @Params: 设备名称
     * @Author: liuguodong
     * @Date: 2018/2/7 17:36
     * @return： 判断设备是否存在数据库中
     */
    public static boolean isQueryDeviceExist(String decevice) {
        if (TextUtils.isEmpty(decevice)) {
            return false;
        }
        List joes = Applacationx.getDaoInstant().getYJBeanDao().queryBuilder()
                .where(YJBeanDao.Properties.MYJdevice.eq(decevice))
                .orderAsc(YJBeanDao.Properties.MYJdevice)
                .list();
        if (joes == null) {
            return false;
        }
        if (joes.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Params: 查询所有数据
     * @Author: liuguodong
     * @Date: 2018/2/7 18:36
     * @return：
     */
    public  static List<YJBean> quryDateAll() {
        List joes = Applacationx.getDaoInstant().getYJBeanDao().queryBuilder()
                .orderAsc(YJBeanDao.Properties.MYJdevice)
                .list();
        return joes;
    }
    public static void deleteItem(String devices){
        Applacationx.getDaoInstant().getYJBeanDao().deleteByKey(OverallUtils.longPressLong(devices));
    }
}
