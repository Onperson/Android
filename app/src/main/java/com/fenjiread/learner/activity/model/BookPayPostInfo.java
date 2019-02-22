package com.fenjiread.learner.activity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 预约支付接口请求参数
 */
@XStreamAlias("xml")
public class BookPayPostInfo {


    private String appid;
    private String  mch_id;
    private String  contract_mchid;
    private String  contract_appid;
    private String  out_trade_no;
    private String  device_info;
    private String  nonce_str;
    private String  body;
    private String  detail;
    private String  attach;
    private String  notify_url;
    private int  total_fee;
    private String  spbill_create_ip;
    private String  time_start;
    private String  time_expire;
    private String  goods_tag;
    private String  trade_type;
    private String  product_id;
    private String  limit_pay;
    private String  openid;
    private int  plan_id;
    private String  contract_code;
    private int  request_serial;
    private String  contract_display_account;
    private String  contract_notify_url	;
    private String  sign;

    public BookPayPostInfo(String appid, String mch_id, String contract_mchid, String contract_appid, String out_trade_no,
                           String device_info, String nonce_str, String body, String detail, String attach, String notify_url,
                           int total_fee, String spbill_create_ip, String time_start, String time_expire, String goods_tag,
                           String trade_type, String product_id, String limit_pay, String openid, int plan_id, String contract_code,
                           int request_serial, String contract_display_account, String contract_notify_url, String sign) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.contract_mchid = contract_mchid;
        this.contract_appid = contract_appid;
        this.out_trade_no = out_trade_no;
        this.device_info = device_info;
        this.nonce_str = nonce_str;
        this.body = body;
        this.detail = detail;
        this.attach = attach;
        this.notify_url = notify_url;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.time_start = time_start;
        this.time_expire = time_expire;
        this.goods_tag = goods_tag;
        this.trade_type = trade_type;
        this.product_id = product_id;
        this.limit_pay = limit_pay;
        this.openid = openid;
        this.plan_id = plan_id;
        this.contract_code = contract_code;
        this.request_serial = request_serial;
        this.contract_display_account = contract_display_account;
        this.contract_notify_url = contract_notify_url;
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BookPayPostInfo{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", contract_mchid='" + contract_mchid + '\'' +
                ", contract_appid='" + contract_appid + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", body='" + body + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", total_fee=" + total_fee +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", goods_tag='" + goods_tag + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", product_id='" + product_id + '\'' +
                ", limit_pay='" + limit_pay + '\'' +
                ", openid='" + openid + '\'' +
                ", plan_id=" + plan_id +
                ", contract_code='" + contract_code + '\'' +
                ", request_serial=" + request_serial +
                ", contract_display_account='" + contract_display_account + '\'' +
                ", contract_notify_url='" + contract_notify_url + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getContract_mchid() {
        return contract_mchid;
    }

    public void setContract_mchid(String contract_mchid) {
        this.contract_mchid = contract_mchid;
    }

    public String getContract_appid() {
        return contract_appid;
    }

    public void setContract_appid(String contract_appid) {
        this.contract_appid = contract_appid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public int getRequest_serial() {
        return request_serial;
    }

    public void setRequest_serial(int request_serial) {
        this.request_serial = request_serial;
    }

    public String getContract_display_account() {
        return contract_display_account;
    }

    public void setContract_display_account(String contract_display_account) {
        this.contract_display_account = contract_display_account;
    }

    public String getContract_notify_url() {
        return contract_notify_url;
    }

    public void setContract_notify_url(String contract_notify_url) {
        this.contract_notify_url = contract_notify_url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
