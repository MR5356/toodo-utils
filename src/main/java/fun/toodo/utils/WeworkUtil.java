package fun.toodo.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.toodo.utils.exception.ToodoException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Slf4j
public class WeworkUtil {
    private String corpID;
    private String corpSecret;
    private String agentID;
    private Map<String, Object> tokenStore = new HashMap<>();

    public static final String MSG_TYPE_MARKDOWN = "markdown";
    public static final String MSG_TYPE_TEXT = "text";

    public WeworkUtil(String corpID, String corpSecret, String agentID) {
        this.corpID = corpID;
        this.corpSecret = corpSecret;
        this.agentID = agentID;

        // 初始化刷新token
        getAccessToken();
    }

    /**
     * 获取token
     * @return token
     */
    public String getAccessToken() {
        refreshAccessToken();
        return (String) this.tokenStore.get("token");
    }

    /**
     * 获取token过期时间
     * @return token过期时间
     */
    public LocalDateTime getAccessTokenExpiresTime() {
        return Validator.isNotNull(this.tokenStore.get("expiresTime")) ? (LocalDateTime) this.tokenStore.get("expiresTime") : LocalDateTime.now();
    }

    /**
     * 刷新token
     * @return token
     */
    @SneakyThrows
    public void refreshAccessToken() {
        if (getAccessTokenExpiresTime().minusSeconds(60).isAfter(LocalDateTime.now())) {
            return;
        }
        log.info("刷新AccessToken");
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + this.corpID + "&corpsecret=" + this.corpSecret;
        JSONObject res = new JSONObject(ToodoHttpUtil.httpExecute(ToodoHttpUtil.httpGet(url)));
        if (res.getInt("errcode").equals(0)) {
            LocalDateTime expiresTime = LocalDateTime.now().plusSeconds(7200);
            tokenStore.put("token", res.getStr("access_token"));
            tokenStore.put("expiresTime", expiresTime);
        } else {
            throw new ToodoException(res.getStr("errmsg"));
        }
    }

    /**
     * 发送markdown消息
     * @param message 需要发送的消息
     * @param token token
     * @param users 接收者列表
     */
    public void sendMessage(String message, List<String> users) {
        Map<String, Object> data = new HashMap<>();
        data.put("touser", String.join("|", users));
        data.put("msgtype", MSG_TYPE_MARKDOWN);
        data.put("agentid", this.agentID);
        data.put("markdown", new JSONObject().set("content", message));
        JSONObject res = new JSONObject(HttpUtil.post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + this.getAccessToken(), JSONUtil.toJsonStr(data)));
        log.info("123");
        if (res.getInt("errcode") == 0) {
            log.info("企业微信发送成功");
        } else {
            log.error("企业微信发送失败：{}", res.getStr("errmsg"));
        }
    }

    /**
     * 发送自定义类型消息
     * @param message 需要发送的消息
     * @param token token
     * @param users 接收者列表
     * @param msgType text或者markdown
     */
    public void sendMessage(String message, List<String> users, String msgType) {
        Map<String, Object> data = new HashMap<>();
        data.put("touser", String.join("|", users));
        data.put("msgtype", msgType);
        data.put("agentid", this.agentID);
        data.put(msgType, new JSONObject().set("content", message));
        JSONObject res = new JSONObject(HttpUtil.post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + this.getAccessToken(), JSONUtil.toJsonStr(data)));
        if (res.getInt("errcode") == 0) {
            log.info("企业微信发送成功");
        } else {
            log.error("企业微信发送失败：{}", res.getStr("errmsg"));
        }
    }

    /**
     * 发送webhook消息，不支持群内@人
     * @param webhook webhook地址
     * @param message 消息，支持text，markdown
     * @param msgType 支持text，markdown
     */
    @SneakyThrows
    public static void sendMessage(String webhook, String message, String msgType) {
        if (!List.of("text", "markdown").contains(msgType)) {
            throw new ToodoException("不支持的消息类型");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("msgtype", msgType);
        data.put(msgType, new JSONObject().set("content", message));
        JSONObject res = new JSONObject(HttpUtil.post(webhook, JSONUtil.toJsonStr(data)));
        if (res.getInt("errcode") == 0) {
            log.info("企业微信发送成功");
        } else {
            log.error("企业微信发送失败：{}", res.getStr("errmsg"));
        }
    }

    /**
     * 发送webhook消息，支持群内@人
     * @param webhook webhook地址
     * @param message 消息，仅支持text
     * @param msgType 仅支持text
     * @param mentionedList 提醒人列表
     */
    @SneakyThrows
    public static void sendMessage(String webhook, String message, String msgType, List<String> mentionedList) {
        if (!msgType.equals("text")) {
            throw new ToodoException("不支持的消息类型");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("msgtype", msgType);
        data.put(msgType, new JSONObject().set("content", message).set("mentioned_list", mentionedList));
        JSONObject res = new JSONObject(HttpUtil.post(webhook, JSONUtil.toJsonStr(data)));
        if (res.getInt("errcode") == 0) {
            log.info("企业微信发送成功");
        } else {
            log.error("企业微信发送失败：{}", res.getStr("errmsg"));
        }
    }

    /**
     * 根据部门ID获取部门成员
     * @param departmentID 部门ID
     * @return 部门成员列表
     */
    public List<Object> getUserListByDepartmentID(String departmentID) {
        log.info("获取成员列表");
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=" + this.getAccessToken() + "&department_id=" + departmentID + "&fetch_child=FETCH_CHILD";
        JSONObject res = new JSONObject(ToodoHttpUtil.httpExecute(ToodoHttpUtil.httpGet(url)));
        if (res.getInt("errcode").equals(0)) {
            return res.getBeanList("userlist", Object.class);
        } else {
            log.error("部门 {} 成员获取失败", departmentID);
            return new ArrayList<>();
        }
    }
}
