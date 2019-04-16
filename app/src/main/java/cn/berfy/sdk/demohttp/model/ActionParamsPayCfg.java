package cn.berfy.sdk.demohttp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/****************************
 * 创建时间：2018年 05月17日 14:20
 * 项目名称：Android  
 * @author 赵强
 * @version 1.0
 * @since JDK 1.8.0
 * 文件名称：ActionParamsPayCfg  
 * 类说明：  
 ****************************/
public class ActionParamsPayCfg implements Serializable{

    //QQ
    private String appId;
    private String bargainorId;
    private String nonce;
    private String pubAcc;
    private String tokenId;
    private String signType;

    //WX
    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBargainorId() {
        return bargainorId;
    }

    public void setBargainorId(String bargainorId) {
        this.bargainorId = bargainorId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPubAcc() {
        return pubAcc;
    }

    public void setPubAcc(String pubAcc) {
        this.pubAcc = pubAcc;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ActionParamsPayCfg{" +
                "appId='" + appId + '\'' +
                ", bargainorId='" + bargainorId + '\'' +
                ", nonce='" + nonce + '\'' +
                ", pubAcc='" + pubAcc + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", signType='" + signType + '\'' +
                ", appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageX='" + packageX + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
