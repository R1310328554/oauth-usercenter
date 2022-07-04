package com.lk.learn.oauth.usercenter.entity;

import java.util.Date;
import java.util.List;

public class WeiboUser {

    private long id;
    private String idstr;
    private int classs; // 关键字class 冲突了怎么办？ 只有改名？
    private String screen_name;
    private String name;
    private String province;
    private String city;
    private String location;
    private String description;
    private String url;
    private String profile_image_url;
    private boolean light_ring;
    private String cover_image_phone;
    private String profile_url;
    private String domain;
    private String weihao;
    private String gender;
    private int followers_count;
    private String followers_count_str;
    private int friends_count;
    private int pagefriends_count;
    private int statuses_count;
    private int video_status_count;
    private int video_play_count;
    private int favourites_count;
    private Date created_at;
    private boolean following;
    private boolean allow_all_act_msg;
    private boolean geo_enabled;
    private boolean verified;
    private int verified_type;
    private String remark;
    private Insecurity insecurity;
    private Status status;
    private int ptype;
    private boolean allow_all_comment;
    private String avatar_large;
    private String avatar_hd;
    private String verified_reason;
    private String verified_trade;
    private String verified_reason_url;
    private String verified_source;
    private String verified_source_url;
    private boolean follow_me;
    private boolean like;
    private boolean like_me;
    private int online_status;
    private int bi_followers_count;
    private String lang;
    private int star;
    private int mbtype;
    private int mbrank;
    private int svip;
    private int mb_expire_time;
    private int block_word;
    private int block_app;
    private int chaohua_ability;
    private int brand_ability;
    private int nft_ability;
    private int vplus_ability;
    private int wenda_ability;
    private int live_ability;
    private int gongyi_ability;
    private int paycolumn_ability;
    private int credit_score;
    private long user_ability;
    private int urank;
    private int story_read_state;
    private int vclub_member;
    private int is_teenager;
    private int is_guardian;
    private int is_teenager_list;
    private int pc_new;
    private boolean special_follow;
    private int planet_video;
    private int video_mark;
    private int live_status;
    private int user_ability_extend;
    private Status_total_counter status_total_counter;
    private Video_total_counter video_total_counter;
    private int brand_account;
    private int hongbaofei;
    private int green_mode;


    class Status {

        private Visible visible;
        private Date created_at;
        private long id;
        private String idstr;
        private String mid;
        private boolean can_edit;
        private int show_additional_indication;
        private String text;
        private int source_allowclick;
        private int source_type;
        private String source;
        private boolean favorited;
        private boolean truncated;
        private String in_reply_to_status_id;
        private String in_reply_to_user_id;
        private String in_reply_to_screen_name;
        private List<String> pic_urls;
        private String geo;
        private boolean is_paid;
        private int mblog_vip_type;
        private List<Annotations> annotations;
        private int reposts_count;
        private int comments_count;
        private int reprint_cmt_count;
        private int attitudes_count;
        private int pending_approval_count;
        private boolean isLongText;
        private int reward_exhibition_type;
        private int hide_flag;
        private int mlevel;
        private int biz_feature;
        private int hasActionTypeCard;
        private List<String> darwin_tags;
        private List<String> hot_weibo_tags;
        private List<String> text_tag_tips;
        private int mblogtype;
        private String rid;
        private int userType;
        private int more_info_type;
        private int positive_recom_flag;
        private int content_auth;
        private String gif_ids;
        private int is_show_bulletin;
        private Comment_manage_info comment_manage_info;
        private int repost_type;
        private int pic_num;
        private int reprint_type;
        private boolean can_reprint;
        private int new_comment_style;

        public void setVisible(Visible visible) {
            this.visible = visible;
        }

        public Visible getVisible() {
            return visible;
        }

        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setIdstr(String idstr) {
            this.idstr = idstr;
        }

        public String getIdstr() {
            return idstr;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMid() {
            return mid;
        }

        public void setCan_edit(boolean can_edit) {
            this.can_edit = can_edit;
        }

        public boolean getCan_edit() {
            return can_edit;
        }

        public void setShow_additional_indication(int show_additional_indication) {
            this.show_additional_indication = show_additional_indication;
        }

        public int getShow_additional_indication() {
            return show_additional_indication;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setSource_allowclick(int source_allowclick) {
            this.source_allowclick = source_allowclick;
        }

        public int getSource_allowclick() {
            return source_allowclick;
        }

        public void setSource_type(int source_type) {
            this.source_type = source_type;
        }

        public int getSource_type() {
            return source_type;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public boolean getFavorited() {
            return favorited;
        }

        public void setTruncated(boolean truncated) {
            this.truncated = truncated;
        }

        public boolean getTruncated() {
            return truncated;
        }

        public void setIn_reply_to_status_id(String in_reply_to_status_id) {
            this.in_reply_to_status_id = in_reply_to_status_id;
        }

        public String getIn_reply_to_status_id() {
            return in_reply_to_status_id;
        }

        public void setIn_reply_to_user_id(String in_reply_to_user_id) {
            this.in_reply_to_user_id = in_reply_to_user_id;
        }

        public String getIn_reply_to_user_id() {
            return in_reply_to_user_id;
        }

        public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
            this.in_reply_to_screen_name = in_reply_to_screen_name;
        }

        public String getIn_reply_to_screen_name() {
            return in_reply_to_screen_name;
        }

        public void setPic_urls(List<String> pic_urls) {
            this.pic_urls = pic_urls;
        }

        public List<String> getPic_urls() {
            return pic_urls;
        }

        public void setGeo(String geo) {
            this.geo = geo;
        }

        public String getGeo() {
            return geo;
        }

        public void setIs_paid(boolean is_paid) {
            this.is_paid = is_paid;
        }

        public boolean getIs_paid() {
            return is_paid;
        }

        public void setMblog_vip_type(int mblog_vip_type) {
            this.mblog_vip_type = mblog_vip_type;
        }

        public int getMblog_vip_type() {
            return mblog_vip_type;
        }

        public void setAnnotations(List<Annotations> annotations) {
            this.annotations = annotations;
        }

        public List<Annotations> getAnnotations() {
            return annotations;
        }

        public void setReposts_count(int reposts_count) {
            this.reposts_count = reposts_count;
        }

        public int getReposts_count() {
            return reposts_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setReprint_cmt_count(int reprint_cmt_count) {
            this.reprint_cmt_count = reprint_cmt_count;
        }

        public int getReprint_cmt_count() {
            return reprint_cmt_count;
        }

        public void setAttitudes_count(int attitudes_count) {
            this.attitudes_count = attitudes_count;
        }

        public int getAttitudes_count() {
            return attitudes_count;
        }

        public void setPending_approval_count(int pending_approval_count) {
            this.pending_approval_count = pending_approval_count;
        }

        public int getPending_approval_count() {
            return pending_approval_count;
        }

        public void setIsLongText(boolean isLongText) {
            this.isLongText = isLongText;
        }

        public boolean getIsLongText() {
            return isLongText;
        }

        public void setReward_exhibition_type(int reward_exhibition_type) {
            this.reward_exhibition_type = reward_exhibition_type;
        }

        public int getReward_exhibition_type() {
            return reward_exhibition_type;
        }

        public void setHide_flag(int hide_flag) {
            this.hide_flag = hide_flag;
        }

        public int getHide_flag() {
            return hide_flag;
        }

        public void setMlevel(int mlevel) {
            this.mlevel = mlevel;
        }

        public int getMlevel() {
            return mlevel;
        }

        public void setBiz_feature(int biz_feature) {
            this.biz_feature = biz_feature;
        }

        public int getBiz_feature() {
            return biz_feature;
        }

        public void setHasActionTypeCard(int hasActionTypeCard) {
            this.hasActionTypeCard = hasActionTypeCard;
        }

        public int getHasActionTypeCard() {
            return hasActionTypeCard;
        }

        public void setDarwin_tags(List<String> darwin_tags) {
            this.darwin_tags = darwin_tags;
        }

        public List<String> getDarwin_tags() {
            return darwin_tags;
        }

        public void setHot_weibo_tags(List<String> hot_weibo_tags) {
            this.hot_weibo_tags = hot_weibo_tags;
        }

        public List<String> getHot_weibo_tags() {
            return hot_weibo_tags;
        }

        public void setText_tag_tips(List<String> text_tag_tips) {
            this.text_tag_tips = text_tag_tips;
        }

        public List<String> getText_tag_tips() {
            return text_tag_tips;
        }

        public void setMblogtype(int mblogtype) {
            this.mblogtype = mblogtype;
        }

        public int getMblogtype() {
            return mblogtype;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getRid() {
            return rid;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getUserType() {
            return userType;
        }

        public void setMore_info_type(int more_info_type) {
            this.more_info_type = more_info_type;
        }

        public int getMore_info_type() {
            return more_info_type;
        }

        public void setPositive_recom_flag(int positive_recom_flag) {
            this.positive_recom_flag = positive_recom_flag;
        }

        public int getPositive_recom_flag() {
            return positive_recom_flag;
        }

        public void setContent_auth(int content_auth) {
            this.content_auth = content_auth;
        }

        public int getContent_auth() {
            return content_auth;
        }

        public void setGif_ids(String gif_ids) {
            this.gif_ids = gif_ids;
        }

        public String getGif_ids() {
            return gif_ids;
        }

        public void setIs_show_bulletin(int is_show_bulletin) {
            this.is_show_bulletin = is_show_bulletin;
        }

        public int getIs_show_bulletin() {
            return is_show_bulletin;
        }

        public void setComment_manage_info(Comment_manage_info comment_manage_info) {
            this.comment_manage_info = comment_manage_info;
        }

        public Comment_manage_info getComment_manage_info() {
            return comment_manage_info;
        }

        public void setRepost_type(int repost_type) {
            this.repost_type = repost_type;
        }

        public int getRepost_type() {
            return repost_type;
        }

        public void setPic_num(int pic_num) {
            this.pic_num = pic_num;
        }

        public int getPic_num() {
            return pic_num;
        }

        public void setReprint_type(int reprint_type) {
            this.reprint_type = reprint_type;
        }

        public int getReprint_type() {
            return reprint_type;
        }

        public void setCan_reprint(boolean can_reprint) {
            this.can_reprint = can_reprint;
        }

        public boolean getCan_reprint() {
            return can_reprint;
        }

        public void setNew_comment_style(int new_comment_style) {
            this.new_comment_style = new_comment_style;
        }

        public int getNew_comment_style() {
            return new_comment_style;
        }

    }

    class Video_total_counter {

        private int play_cnt;
        public void setPlay_cnt(int play_cnt) {
            this.play_cnt = play_cnt;
        }
        public int getPlay_cnt() {
            return play_cnt;
        }

    }

    class Status_total_counter {

        private int total_cnt;
        private int repost_cnt;
        private int comment_cnt;
        private int like_cnt;
        private int comment_like_cnt;
        public void setTotal_cnt(int total_cnt) {
            this.total_cnt = total_cnt;
        }
        public int getTotal_cnt() {
            return total_cnt;
        }

        public void setRepost_cnt(int repost_cnt) {
            this.repost_cnt = repost_cnt;
        }
        public int getRepost_cnt() {
            return repost_cnt;
        }

        public void setComment_cnt(int comment_cnt) {
            this.comment_cnt = comment_cnt;
        }
        public int getComment_cnt() {
            return comment_cnt;
        }

        public void setLike_cnt(int like_cnt) {
            this.like_cnt = like_cnt;
        }
        public int getLike_cnt() {
            return like_cnt;
        }

        public void setComment_like_cnt(int comment_like_cnt) {
            this.comment_like_cnt = comment_like_cnt;
        }
        public int getComment_like_cnt() {
            return comment_like_cnt;
        }

    }

    class Annotations {

        private String client_mblogid;
        public void setClient_mblogid(String client_mblogid) {
            this.client_mblogid = client_mblogid;
        }
        public String getClient_mblogid() {
            return client_mblogid;
        }

    }

    class Insecurity {

        private boolean sexual_content;
        public void setSexual_content(boolean sexual_content) {
            this.sexual_content = sexual_content;
        }
        public boolean getSexual_content() {
            return sexual_content;
        }

    }

    class Visible {

        private int type;
        private int list_id;
        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setList_id(int list_id) {
            this.list_id = list_id;
        }
        public int getList_id() {
            return list_id;
        }

    }



    class Comment_manage_info {

        private int comment_permission_type;
        private int approval_comment_type;
        private int comment_sort_type;
        public void setComment_permission_type(int comment_permission_type) {
            this.comment_permission_type = comment_permission_type;
        }
        public int getComment_permission_type() {
            return comment_permission_type;
        }

        public void setApproval_comment_type(int approval_comment_type) {
            this.approval_comment_type = approval_comment_type;
        }
        public int getApproval_comment_type() {
            return approval_comment_type;
        }

        public void setComment_sort_type(int comment_sort_type) {
            this.comment_sort_type = comment_sort_type;
        }
        public int getComment_sort_type() {
            return comment_sort_type;
        }

    }

}
