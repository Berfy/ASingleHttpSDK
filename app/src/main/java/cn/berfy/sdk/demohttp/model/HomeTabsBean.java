package cn.berfy.sdk.demohttp.model;

import java.io.Serializable;
import java.util.List;

/**
 * --------------------
 * <p>Author：
 * PC
 * <p>Created Time:
 * 2018/3/16
 * <p>Intro:
 * <p> 首页tab
 * <p>Thinking:
 * <p>
 * <p>Problem:
 * <p>
 * <p>Attention:
 * --------------------
 */

public class HomeTabsBean extends BaseResponse implements Serializable{
    /**
     * asher_banner_tabs : [{"tab_name":"游戏推荐","asher_banner_tab_id":"1","pos":"1","action":{}},{"tab_name":"免费试玩","asher_banner_tab_id":"2","pos":"2","action":{}},{"tab_name":"热门排行","asher_banner_tab_id":"3","pos":"3","action":{"action_page":"19","action_params":{"category":"24"}}},{"tab_name":"动作格斗","asher_banner_tab_id":"4","pos":"4","action":{"action_page":"19","action_params":{"category":"4"}}},{"tab_name":"冒险解谜","asher_banner_tab_id":"5","pos":"5","action":{"action_page":"19","action_params":{"category":"5"}}},{"tab_name":"体育竞速","asher_banner_tab_id":"6","pos":"6","action":{"action_page":"19","action_params":{"category":"6"}}},{"tab_name":"飞行射击","asher_banner_tab_id":"7","pos":"7","action":{"action_page":"19","action_params":{"category":"7"}}}]
     * default_tab_id : 1
     */

    private int default_tab_id;
    private List<AsherBannerTabsBean> asher_banner_tabs;

    private String tab_gravity  ;

    public String getTab_gravity() {
        return tab_gravity;
    }

    public int getDefault_tab_id() {
        return default_tab_id;
    }

    public void setDefault_tab_id(int default_tab_id) {
        this.default_tab_id = default_tab_id;
    }

    public List<AsherBannerTabsBean> getAsher_banner_tabs() {
        return asher_banner_tabs;
    }

    public void setAsher_banner_tabs(List<AsherBannerTabsBean> asher_banner_tabs) {
        this.asher_banner_tabs = asher_banner_tabs;
    }

    public static class AsherBannerTabsBean implements Serializable{
        /**
         * tab_name : 游戏推荐
         * asher_banner_tab_id : 1
         * pos : 1
         * action : {}
         */

        private String tab_name;
        private int asher_banner_tab_id;
        private String pos;
        private ActionBean action;

        public String getTab_name() {
            return tab_name;
        }

        public void setTab_name(String tab_name) {
            this.tab_name = tab_name;
        }

        public int getAsher_banner_tab_id() {
            return asher_banner_tab_id;
        }

        public void setAsher_banner_tab_id(int asher_banner_tab_id) {
            this.asher_banner_tab_id = asher_banner_tab_id;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public ActionBean getAction() {
            return action;
        }

        public void setAction(ActionBean action) {
            this.action = action;
        }

        public static class ActionBean implements Serializable{

            int action_page ;
            ActionCommenBean action_params;

            public ActionCommenBean getAction_params() {
                return action_params;
            }

            public int getAction_page() {
                return action_page;
            }
        }

    }


    /**
     * asher_banner_tabs : [{"tab_name":"游戏推荐","asher_banner_tab_id":"1","pos":"1","action":{}},{"tab_name":"免费试玩","asher_banner_tab_id":"2","pos":"2","action":{}},{"tab_name":"热门排行","asher_banner_tab_id":"3","pos":"3","action":{"action_page":"19","action_params":{"category":"24"}}},{"tab_name":"动作格斗","asher_banner_tab_id":"4","pos":"4","action":{"action_page":"19","action_params":{"category":"4"}}},{"tab_name":"冒险解谜","asher_banner_tab_id":"5","pos":"5","action":{"action_page":"19","action_params":{"category":"5"}}},{"tab_name":"体育竞速","asher_banner_tab_id":"6","pos":"6","action":{"action_page":"19","action_params":{"category":"6"}}},{"tab_name":"飞行射击","asher_banner_tab_id":"7","pos":"7","action":{"action_page":"19","action_params":{"category":"7"}}}]
     * default_tab_id : 1
     */


}
