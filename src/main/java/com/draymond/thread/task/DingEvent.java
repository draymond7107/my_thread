package com.draymond.thread.task;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 钉钉回调事件对象
 */
public class DingEvent implements Serializable {
    //创建套件后，验证回调URL创建有效事件（第一次保存回调URL之前）
    public static final String EVENT_CHECK_CREATE_SUITE_URL = "check_create_suite_url";
    //创建套件后，验证回调URL变更有效事件（第一次保存回调URL之后）
    public static final String EVENT_CHECK_UPADTE_SUITE_URL = "check_update_suite_url";
    //suite_ticket推送事件
    public static final String EVENT_SUITE_TICKET = "suite_ticket";
    //企业授权开通应用事件
    public static final String EVENT_TMP_AUTH_CODE = "tmp_auth_code";

    //企业授权取消解除
    public static final String EVENT_SUITE_RELIEVE = "suite_relieve";

    //业务回调事件
    //通讯录用户增加
    public static final String EVENT_USER_ADD_ORG = "user_add_org";
    //通讯录用户更改
    public static final String EVENT_USER_MODIFY_ORG = "user_modify_org";
    //通讯录用户离职
    public static final String EVENT_USER_LEAVE_ORG = "user_leave_org";
    //通讯录用户被设为管理员
    public static final String EVENT_ORG_ADMIN_ADD = "org_admin_add";
    //通讯录用户被取消设置管理员
    public static final String EVENT_ORG_ADMIN_REMOVE = "org_admin_remove";
    //通讯录企业部门创建
    public static final String EVENT_ORG_DEPT_CREATE = "org_dept_create";
    //通讯录企业部门修改
    public static final String EVENT_ORG_DEPT_MODIFY = "org_dept_modify";
    //通讯录企业部门删除
    public static final String EVENT_ORG_DEPT_REMOVE = "org_dept_remove";
    //企业被解散
    public static final String EVENT_ORG_REMOVE = "org_remove";
    //企业信息发生变更
    public static final String EVENT_ORG_CHANGE = "org_change";


    private String eventType;
    private long timeStamp;
    private String authCode;
    private String authCorpId;
    private String suiteKey;
    private String plainText;
    private JSONObject data;

    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public String getAuthCorpId() {
        return authCorpId;
    }
    public void setAuthCorpId(String authCorpId) {
        this.authCorpId = authCorpId;
    }
    public String getSuiteKey() {
        return suiteKey;
    }
    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }
    public String getPlainText() {
        return plainText;
    }
    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
    public JSONObject getData() {
        return data;
    }
    public void setData(JSONObject data) {
        this.data = data;
    }
}
