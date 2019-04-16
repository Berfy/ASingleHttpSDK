package cn.berfy.sdk.demohttp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * --------------------
 * <p>Author：
 * PC
 * <p>Created Time:
 * 2018/5/14
 * <p>Intro:
 * <p> 通用的action参数字段
 * <p>Thinking:
 * <p>
 * <p>Problem:
 * <p>
 * <p>Attention:
 * --------------------
 */

public class ActionCommenBean implements Serializable,Parcelable{

    int game_id;
    int fight_game_id;
    int game_mode;


    String share_pic;
    String share_url;
    String share_title;
    String share_content;
    String desc;//下载APK描述

    String video_type;////fight， compete， video

    String video_sub;////{ title: "最新视频", type: "new" }, { title: "上升最快", type: "fast" }, { title: "最热视频", type: "hot" }, { title: "精彩对决", type: "fight_video" }, { title: "分类查找", type: "bygame" }, { title: "雷曼挑战", type: "rayman_challenge" } ]

    int video_id;
    int social_type=1;//0:最近联系，1:我的好友，2:我的关注，3:我的粉丝，4:添加好友，5:黑名单。

    int vip_sub;        //（特权管理中，特权的ID）
    int Vtype;        //1：默认选中“购买格来会员”，2：默认选中“购买超级会员”

    int banner_tab_id;    //通栏TabID

    int room_type;//对战房间类型

    int account_id;//用户ID
    int save_page;
    int pack_id;
    int chargepoint_id;
    int rmb;
    String code;
    String url;
    String keyword;
    String moneycode;
    int sub_id;            //常见问题ID
    int tab_id;            //首页通栏
    int tasktype_id;    //任务ID
    Object region_id;
    int advert_pa_id;
    int advert_pa_type;
    int pa_type_switch;
    int advert_type;

    int v_id;//打开会员页第几个tab


    //int type; //分享类型，分享成功后回调使用

    int id; //分享id
    int category;
    int old_level;
    int new_level;
    int paytype_id;
    int startlevel;
    int endlevel;
    int gold_method;
//    视频中心普通视频 1
    int share_type ;

    //跳到游戏列表的字段
    private int label_id;
    //标签的名称
    private String label_name;

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    //支付相关
    private int allow_skip;
    private String type = "";

    //支付宝支付加密串
    private String signed_request_url;

    private String out_trade_no;
    private String order_id;

    String barTitle;



    public void setBarTitle(String barTitle) {
        this.barTitle = barTitle;
    }

    public String getBarTitle() {
        return barTitle;
    }

    private ActionParamsPayCfg app_pay;


    public int getLabel_id() {
        return label_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public int getFight_game_id() {
        return fight_game_id;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public String getShare_pic() {
        return share_pic;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getShare_title() {
        return share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideo_type() {
        return video_type;
    }

    public String getVideo_sub() {
        return video_sub;
    }

    public int getVideo_id() {
        return video_id;
    }

    public int getSocial_type() {
        return social_type;
    }

    public int getVip_sub() {
        return vip_sub;
    }

    public int getVtype() {
        return Vtype;
    }

    public int getBanner_tab_id() {
        return banner_tab_id;
    }

    public int getRoom_type() {
        return room_type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getSave_page() {
        return save_page;
    }

    public int getPack_id() {
        return pack_id;
    }

    public int getChargepoint_id() {
        return chargepoint_id;
    }

    public int getRmb() {
        return rmb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getMoneycode() {
        return moneycode;
    }

    public int getSub_id() {
        return sub_id;
    }

    public int getTab_id() {
        return tab_id;
    }

    public int getTasktype_id() {
        return tasktype_id;
    }

    public Object getRegion_id() {
        return region_id;
    }

    public int getAdvert_pa_id() {
        return advert_pa_id;
    }

    public int getAdvert_pa_type() {
        return advert_pa_type;
    }

    public int getPa_type_switch() {
        return pa_type_switch;
    }

    public int getAdvert_type() {
        return advert_type;
    }

    public int getV_id() {
        return v_id;
    }

    public int getAllow_skip() {
        return allow_skip;
    }

    public String getType() {
        return type;
    }

    int shareContentType;

    public int getShareContentType() {
        return shareContentType;
    }

    public String getSigned_request_url() {
        return signed_request_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public ActionParamsPayCfg getApp_pay() {
        return app_pay;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getOld_level() {
        return old_level;
    }

    public int getNew_level() {
        return new_level;
    }

    public int getPaytype_id() {
        return paytype_id;
    }

    public int getStartlevel() {
        return startlevel;
    }

    public int getEndlevel() {
        return endlevel;
    }

    public int getGold_method() {
        return gold_method;
    }

    public int getShare_type() {
        return share_type;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setFight_game_id(int fight_game_id) {
        this.fight_game_id = fight_game_id;
    }

    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;
    }

    public void setShare_pic(String share_pic) {
        this.share_pic = share_pic;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public void setVideo_sub(String video_sub) {
        this.video_sub = video_sub;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public void setSocial_type(int social_type) {
        this.social_type = social_type;
    }

    public void setVip_sub(int vip_sub) {
        this.vip_sub = vip_sub;
    }

    public void setVtype(int vtype) {
        Vtype = vtype;
    }

    public void setBanner_tab_id(int banner_tab_id) {
        this.banner_tab_id = banner_tab_id;
    }

    public void setRoom_type(int room_type) {
        this.room_type = room_type;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setSave_page(int save_page) {
        this.save_page = save_page;
    }

    public void setPack_id(int pack_id) {
        this.pack_id = pack_id;
    }

    public void setChargepoint_id(int chargepoint_id) {
        this.chargepoint_id = chargepoint_id;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMoneycode(String moneycode) {
        this.moneycode = moneycode;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public void setTasktype_id(int tasktype_id) {
        this.tasktype_id = tasktype_id;
    }

    public void setRegion_id(Object region_id) {
        this.region_id = region_id;
    }

    public void setAdvert_pa_id(int advert_pa_id) {
        this.advert_pa_id = advert_pa_id;
    }

    public void setAdvert_pa_type(int advert_pa_type) {
        this.advert_pa_type = advert_pa_type;
    }

    public void setPa_type_switch(int pa_type_switch) {
        this.pa_type_switch = pa_type_switch;
    }

    public void setAdvert_type(int advert_type) {
        this.advert_type = advert_type;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setOld_level(int old_level) {
        this.old_level = old_level;
    }

    public void setNew_level(int new_level) {
        this.new_level = new_level;
    }

    public void setPaytype_id(int paytype_id) {
        this.paytype_id = paytype_id;
    }

    public void setStartlevel(int startlevel) {
        this.startlevel = startlevel;
    }

    public void setEndlevel(int endlevel) {
        this.endlevel = endlevel;
    }

    public void setGold_method(int gold_method) {
        this.gold_method = gold_method;
    }

    public void setShare_type(int share_type) {
        this.share_type = share_type;
    }

    public void setAllow_skip(int allow_skip) {
        this.allow_skip = allow_skip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSigned_request_url(String signed_request_url) {
        this.signed_request_url = signed_request_url;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setApp_pay(ActionParamsPayCfg app_pay) {
        this.app_pay = app_pay;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    //316新加加入游戏字段
    int pay_ment;
    int save_id;
    int serial_id;
    String room_name;
    String room_pwd;
    int extension_id;       //扩展id

    public int getPayment() {
        return pay_ment;
    }

    public void setPayment(int payment) {
        this.pay_ment = payment;
    }

    public int getSave_id() {
        return save_id;
    }

    public void setSave_id(int save_id) {
        this.save_id = save_id;
    }

    public int getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(int serial_id) {
        this.serial_id = serial_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_pwd() {
        return room_pwd;
    }

    public void setRoom_pwd(String room_pwd) {
        this.room_pwd = room_pwd;
    }

    public int getExtension_id() {
        return extension_id;
    }

    public void setExtension_id(int extension_id) {
        this.extension_id = extension_id;
    }
    //316新加加入游戏字段


    @Override
    public String toString() {
        return "ActionCommenBean{" +
                "game_id=" + game_id +
                ", fight_game_id=" + fight_game_id +
                ", game_mode=" + game_mode +
                ", share_pic='" + share_pic + '\'' +
                ", share_url='" + share_url + '\'' +
                ", share_title='" + share_title + '\'' +
                ", share_content='" + share_content + '\'' +
                ", desc='" + desc + '\'' +
                ", video_type='" + video_type + '\'' +
                ", video_sub='" + video_sub + '\'' +
                ", video_id=" + video_id +
                ", social_type=" + social_type +
                ", vip_sub=" + vip_sub +
                ", Vtype=" + Vtype +
                ", banner_tab_id=" + banner_tab_id +
                ", room_type=" + room_type +
                ", account_id=" + account_id +
                ", save_page=" + save_page +
                ", pack_id=" + pack_id +
                ", chargepoint_id=" + chargepoint_id +
                ", rmb=" + rmb +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", keyword='" + keyword + '\'' +
                ", moneycode='" + moneycode + '\'' +
                ", sub_id=" + sub_id +
                ", tab_id=" + tab_id +
                ", tasktype_id=" + tasktype_id +
                ", region_id=" + region_id +
                ", advert_pa_id=" + advert_pa_id +
                ", advert_pa_type=" + advert_pa_type +
                ", pa_type_switch=" + pa_type_switch +
                ", advert_type=" + advert_type +
                ", v_id=" + v_id +
                ", id=" + id +
                ", category=" + category +
                ", old_level=" + old_level +
                ", new_level=" + new_level +
                ", paytype_id=" + paytype_id +
                ", startlevel=" + startlevel +
                ", endlevel=" + endlevel +
                ", gold_method=" + gold_method +
                ", share_type=" + share_type +
                ", label_id=" + label_id +
                ", label_name='" + label_name + '\'' +
                ", allow_skip=" + allow_skip +
                ", type='" + type + '\'' +
                ", signed_request_url='" + signed_request_url + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", order_id='" + order_id + '\'' +
                ", barTitle='" + barTitle + '\'' +
                ", app_pay=" + app_pay +
                ", shareContentType=" + shareContentType +
                ", payment=" + pay_ment +
                ", save_id=" + save_id +
                ", serial_id=" + serial_id +
                ", room_name='" + room_name + '\'' +
                ", room_pwd='" + room_pwd + '\'' +
                ", extension_id=" + extension_id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.game_id);
        dest.writeInt(this.fight_game_id);
        dest.writeInt(this.game_mode);
        dest.writeString(this.share_pic);
        dest.writeString(this.share_url);
        dest.writeString(this.share_title);
        dest.writeString(this.share_content);
        dest.writeString(this.desc);
        dest.writeString(this.video_type);
        dest.writeString(this.video_sub);
        dest.writeInt(this.video_id);
        dest.writeInt(this.social_type);
        dest.writeInt(this.vip_sub);
        dest.writeInt(this.Vtype);
        dest.writeInt(this.banner_tab_id);
        dest.writeInt(this.room_type);
        dest.writeInt(this.account_id);
        dest.writeInt(this.save_page);
        dest.writeInt(this.pack_id);
        dest.writeInt(this.chargepoint_id);
        dest.writeInt(this.rmb);
        dest.writeString(this.code);
        dest.writeString(this.url);
        dest.writeString(this.keyword);
        dest.writeString(this.moneycode);
        dest.writeInt(this.sub_id);
        dest.writeInt(this.tab_id);
        dest.writeInt(this.tasktype_id);
        if (region_id!=null) {
            if (region_id instanceof String) {
                dest.writeString((String) region_id);
            } else if (region_id instanceof Long) {
                dest.writeLong((Long) region_id);
            } else if (region_id instanceof Integer) {
                dest.writeInt((Integer) region_id);
            }
        }
        dest.writeInt(this.advert_pa_id);
        dest.writeInt(this.advert_pa_type);
        dest.writeInt(this.pa_type_switch);
        dest.writeInt(this.advert_type);
        dest.writeInt(this.v_id);
        dest.writeInt(this.id);
        dest.writeInt(this.category);
        dest.writeInt(this.old_level);
        dest.writeInt(this.new_level);
        dest.writeInt(this.paytype_id);
        dest.writeInt(this.startlevel);
        dest.writeInt(this.endlevel);
        dest.writeInt(this.gold_method);
        dest.writeInt(this.share_type);
        dest.writeInt(this.label_id);
        dest.writeString(this.label_name);
        dest.writeInt(this.allow_skip);
        dest.writeString(this.type);
        dest.writeString(this.signed_request_url);
        dest.writeString(this.out_trade_no);
        dest.writeString(this.order_id);
        dest.writeString(this.barTitle);
        dest.writeSerializable(this.app_pay);
        dest.writeInt(this.shareContentType);

        dest.writeInt(this.pay_ment);
        dest.writeInt(this.save_id);
        dest.writeInt(this.serial_id);
        dest.writeString(this.room_name);
        dest.writeString(this.room_pwd);
        dest.writeInt(this.extension_id);



    }

    public ActionCommenBean() {
    }

    protected ActionCommenBean(Parcel in) {
        this.game_id = in.readInt();
        this.fight_game_id = in.readInt();
        this.game_mode = in.readInt();
        this.share_pic = in.readString();
        this.share_url = in.readString();
        this.share_title = in.readString();
        this.share_content = in.readString();
        this.desc = in.readString();
        this.video_type = in.readString();
        this.video_sub = in.readString();
        this.video_id = in.readInt();
        this.social_type = in.readInt();
        this.vip_sub = in.readInt();
        this.Vtype = in.readInt();
        this.banner_tab_id = in.readInt();
        this.room_type = in.readInt();
        this.account_id = in.readInt();
        this.save_page = in.readInt();
        this.pack_id = in.readInt();
        this.chargepoint_id = in.readInt();
        this.rmb = in.readInt();
        this.code = in.readString();
        this.url = in.readString();
        this.keyword = in.readString();
        this.moneycode = in.readString();
        this.sub_id = in.readInt();
        this.tab_id = in.readInt();
        this.tasktype_id = in.readInt();
        if (region_id!=null) {
            if (region_id instanceof String) {
                this.region_id =  in.readString();
            } else if (region_id instanceof Long) {
                this.region_id =  in.readLong();
            } else if (region_id instanceof Integer) {
                this.region_id =  in.readInt();
            }
        }
        this.advert_pa_id = in.readInt();
        this.advert_pa_type = in.readInt();
        this.pa_type_switch = in.readInt();
        this.advert_type = in.readInt();
        this.v_id = in.readInt();
        this.id = in.readInt();
        this.category = in.readInt();
        this.old_level = in.readInt();
        this.new_level = in.readInt();
        this.paytype_id = in.readInt();
        this.startlevel = in.readInt();
        this.endlevel = in.readInt();
        this.gold_method = in.readInt();
        this.share_type = in.readInt();
        this.label_id = in.readInt();
        this.label_name = in.readString();
        this.allow_skip = in.readInt();
        this.type = in.readString();
        this.signed_request_url = in.readString();
        this.out_trade_no = in.readString();
        this.order_id = in.readString();
        this.barTitle = in.readString();
        this.app_pay = (ActionParamsPayCfg) in.readSerializable();
        this.shareContentType = in.readInt();

        this.pay_ment = in.readInt();
        this.save_id = in.readInt();
        this.serial_id= in.readInt();
        this.room_name = in.readString();
        this.room_pwd = in.readString();
        this.extension_id= in.readInt();


    }

    public static final Creator<ActionCommenBean> CREATOR = new Creator<ActionCommenBean>() {
        @Override
        public ActionCommenBean createFromParcel(Parcel source) {
            return new ActionCommenBean(source);
        }

        @Override
        public ActionCommenBean[] newArray(int size) {
            return new ActionCommenBean[size];
        }
    };
}

