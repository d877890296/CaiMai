package com.hyf.tdlibrary.manager;

/**
 * Created by Lukejun on 2016/8/3.
 */
public class VersionBean {
    private String vercode;         //版本号
    private String vername;         //版本名称
    private String upgradeUrl;        //更新地址
    private String upgradeContent;  //更新内容

    public VersionBean(String vercode, String vername, String upgradeUrl, String upgradeContent) {
        this.vercode = vercode;
        this.vername = vername;
        this.upgradeUrl = upgradeUrl;
        this.upgradeContent = upgradeContent;

    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getVername() {
        return vername;
    }

    public void setVername(String vername) {
        this.vername = vername;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getUpgradeContent() {
        return upgradeContent;
    }

    public void setUpgradeContent(String upgradeContent) {
        this.upgradeContent = upgradeContent;
    }
}
