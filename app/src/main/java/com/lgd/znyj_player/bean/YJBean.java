package com.lgd.znyj_player.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/2/7.
 */
@Entity
public class YJBean {
    @Id
    private Long id;
    @Property
    private String mYJName;
    @Property
    private String mYJdevice;
    @Property
    private String mYJip;
    @Property
    private String mYJUrl;
    @Property
    private String mYJother;
    @Property
    private String mYJother2;
    @Property
    private String mYJother3;
    @Property
    private String mYJother4;
    @Property
    private String mYJother5;
    @Property
    private String mYJother6;
    public String getMYJother6() {
        return this.mYJother6;
    }
    public void setMYJother6(String mYJother6) {
        this.mYJother6 = mYJother6;
    }
    public String getMYJother5() {
        return this.mYJother5;
    }
    public void setMYJother5(String mYJother5) {
        this.mYJother5 = mYJother5;
    }
    public String getMYJother4() {
        return this.mYJother4;
    }
    public void setMYJother4(String mYJother4) {
        this.mYJother4 = mYJother4;
    }
    public String getMYJother3() {
        return this.mYJother3;
    }
    public void setMYJother3(String mYJother3) {
        this.mYJother3 = mYJother3;
    }
    public String getMYJother2() {
        return this.mYJother2;
    }
    public void setMYJother2(String mYJother2) {
        this.mYJother2 = mYJother2;
    }
    public String getMYJother() {
        return this.mYJother;
    }
    public void setMYJother(String mYJother) {
        this.mYJother = mYJother;
    }
    public String getMYJUrl() {
        return this.mYJUrl;
    }
    public void setMYJUrl(String mYJUrl) {
        this.mYJUrl = mYJUrl;
    }
    public String getMYJName() {
        return this.mYJName;
    }
    public void setMYJName(String mYJName) {
        this.mYJName = mYJName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMYJip() {
        return this.mYJip;
    }
    public void setMYJip(String mYJip) {
        this.mYJip = mYJip;
    }
    public String getMYJdevice() {
        return this.mYJdevice;
    }
    public void setMYJdevice(String mYJdevice) {
        this.mYJdevice = mYJdevice;
    }
    @Generated(hash = 1742510131)
    public YJBean(Long id, String mYJName, String mYJdevice, String mYJip,
            String mYJUrl, String mYJother, String mYJother2, String mYJother3,
            String mYJother4, String mYJother5, String mYJother6) {
        this.id = id;
        this.mYJName = mYJName;
        this.mYJdevice = mYJdevice;
        this.mYJip = mYJip;
        this.mYJUrl = mYJUrl;
        this.mYJother = mYJother;
        this.mYJother2 = mYJother2;
        this.mYJother3 = mYJother3;
        this.mYJother4 = mYJother4;
        this.mYJother5 = mYJother5;
        this.mYJother6 = mYJother6;
    }
    @Generated(hash = 1374628885)
    public YJBean() {
    }
}
