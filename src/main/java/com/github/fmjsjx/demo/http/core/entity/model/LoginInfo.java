package com.github.fmjsjx.demo.http.core.entity.model;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fmjsjx.bson.model.core.BsonUtil;
import com.github.fmjsjx.bson.model2.core.*;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.*;

public class LoginInfo extends ObjectModel<LoginInfo> {

    public static final String BNAME_COUNT = "cnt";
    public static final String BNAME_DAYS = "d";
    public static final String BNAME_CONTINUOUS_DAYS = "cnd";
    public static final String BNAME_MAX_CONTINUOUS_DAYS = "mcd";
    public static final String BNAME_GAMING_DAYS = "gmd";
    public static final String BNAME_MAX_GAMING_DAYS = "mgd";
    public static final String BNAME_IP = "ip";
    public static final String BNAME_LOGIN_TIME = "lgt";

    private int count;
    private int days;
    private int continuousDays;
    private int maxContinuousDays;
    private int gamingDays;
    private int maxGamingDays;
    private String ip;
    private LocalDateTime loginTime;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count != this.count) {
            this.count = count;
            fieldChanged(0);
        }
    }

    public int increaseCount() {
        fieldChanged(0);
        return ++count;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        if (days != this.days) {
            this.days = days;
            fieldChanged(1);
        }
    }

    public int increaseDays() {
        fieldChanged(1);
        return ++days;
    }

    public int getContinuousDays() {
        return continuousDays;
    }

    public void setContinuousDays(int continuousDays) {
        if (continuousDays != this.continuousDays) {
            this.continuousDays = continuousDays;
            fieldChanged(2);
        }
    }

    public int increaseContinuousDays() {
        fieldChanged(2);
        return ++continuousDays;
    }

    public int getMaxContinuousDays() {
        return maxContinuousDays;
    }

    public void setMaxContinuousDays(int maxContinuousDays) {
        if (maxContinuousDays != this.maxContinuousDays) {
            this.maxContinuousDays = maxContinuousDays;
            fieldChanged(3);
        }
    }

    public int getGamingDays() {
        return gamingDays;
    }

    public void setGamingDays(int gamingDays) {
        if (gamingDays != this.gamingDays) {
            this.gamingDays = gamingDays;
            fieldChanged(4);
        }
    }

    public int getMaxGamingDays() {
        return maxGamingDays;
    }

    public void setMaxGamingDays(int maxGamingDays) {
        if (maxGamingDays != this.maxGamingDays) {
            this.maxGamingDays = maxGamingDays;
            fieldChanged(5);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        if (!Objects.equals(ip, this.ip)) {
            this.ip = ip;
            fieldChanged(6);
        }
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        if (!Objects.equals(loginTime, this.loginTime)) {
            this.loginTime = loginTime;
            fieldChanged(7);
        }
    }

    public boolean countChanged() {
        return changedFields.get(0);
    }

    public boolean daysChanged() {
        return changedFields.get(1);
    }

    public boolean continuousDaysChanged() {
        return changedFields.get(2);
    }

    public boolean maxContinuousDaysChanged() {
        return changedFields.get(3);
    }

    public boolean gamingDaysChanged() {
        return changedFields.get(4);
    }

    public boolean maxGamingDaysChanged() {
        return changedFields.get(5);
    }

    public boolean ipChanged() {
        return changedFields.get(6);
    }

    public boolean loginTimeChanged() {
        return changedFields.get(7);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_COUNT, new BsonInt32(count));
        bson.append(BNAME_DAYS, new BsonInt32(days));
        bson.append(BNAME_CONTINUOUS_DAYS, new BsonInt32(continuousDays));
        bson.append(BNAME_MAX_CONTINUOUS_DAYS, new BsonInt32(maxContinuousDays));
        bson.append(BNAME_GAMING_DAYS, new BsonInt32(gamingDays));
        bson.append(BNAME_MAX_GAMING_DAYS, new BsonInt32(maxGamingDays));
        var ip = this.ip;
        if (ip != null) {
            bson.append(BNAME_IP, new BsonString(ip));
        }
        var loginTime = this.loginTime;
        if (loginTime != null) {
            bson.append(BNAME_LOGIN_TIME, BsonUtil.toBsonDateTime(loginTime));
        }
        return bson;
    }

    @Override
    public LoginInfo load(BsonDocument src) {
        resetStates();
        count = BsonUtil.intValue(src, BNAME_COUNT).orElseThrow();
        days = BsonUtil.intValue(src, BNAME_DAYS).orElseThrow();
        continuousDays = BsonUtil.intValue(src, BNAME_CONTINUOUS_DAYS).orElseThrow();
        maxContinuousDays = BsonUtil.intValue(src, BNAME_MAX_CONTINUOUS_DAYS).orElseThrow();
        gamingDays = BsonUtil.intValue(src, BNAME_GAMING_DAYS).orElseThrow();
        maxGamingDays = BsonUtil.intValue(src, BNAME_MAX_GAMING_DAYS).orElseThrow();
        ip = BsonUtil.stringValue(src, BNAME_IP).orElse(null);
        loginTime = BsonUtil.dateTimeValue(src, BNAME_LOGIN_TIME).orElse(null);
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_COUNT, count);
        jsonNode.put(BNAME_DAYS, days);
        jsonNode.put(BNAME_CONTINUOUS_DAYS, continuousDays);
        jsonNode.put(BNAME_MAX_CONTINUOUS_DAYS, maxContinuousDays);
        jsonNode.put(BNAME_GAMING_DAYS, gamingDays);
        jsonNode.put(BNAME_MAX_GAMING_DAYS, maxGamingDays);
        var ip = this.ip;
        if (ip != null) {
            jsonNode.put(BNAME_IP, ip);
        }
        var loginTime = this.loginTime;
        if (loginTime != null) {
            jsonNode.put(BNAME_LOGIN_TIME, DateTimeUtil.toEpochMilli(loginTime));
        }
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_COUNT, count);
        jsonObject.put(BNAME_DAYS, days);
        jsonObject.put(BNAME_CONTINUOUS_DAYS, continuousDays);
        jsonObject.put(BNAME_MAX_CONTINUOUS_DAYS, maxContinuousDays);
        jsonObject.put(BNAME_GAMING_DAYS, gamingDays);
        jsonObject.put(BNAME_MAX_GAMING_DAYS, maxGamingDays);
        var ip = this.ip;
        if (ip != null) {
            jsonObject.put(BNAME_IP, ip);
        }
        var loginTime = this.loginTime;
        if (loginTime != null) {
            jsonObject.put(BNAME_LOGIN_TIME, DateTimeUtil.toEpochMilli(loginTime));
        }
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        data.put("count", count);
        data.put("days", days);
        data.put("continuousDays", continuousDays);
        data.put("maxContinuousDays", maxContinuousDays);
        data.put("gamingDays", gamingDays);
        data.put("maxGamingDays", maxGamingDays);
        var ip = this.ip;
        if (ip != null) {
            data.put("ip", ip);
        }
        return data;
    }

    @Override
    public boolean anyUpdated() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(0)) {
            return true;
        }
        if (changedFields.get(1)) {
            return true;
        }
        if (changedFields.get(2)) {
            return true;
        }
        if (changedFields.get(3)) {
            return true;
        }
        if (changedFields.get(4)) {
            return true;
        }
        if (changedFields.get(5)) {
            return true;
        }
        if (changedFields.get(6) && ip != null) {
            return true;
        }
        if (changedFields.get(7) && loginTime != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void resetChildren() {
    }

    @Override
    protected int deletedSize() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var n = 0;
        if (changedFields.get(6) && ip == null) {
            n++;
        }
        if (changedFields.get(7) && loginTime == null) {
            n++;
        }
        return n;
    }

    @Override
    public boolean anyDeleted() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(6) && ip == null) {
            return true;
        }
        if (changedFields.get(7) && loginTime == null) {
            return true;
        }
        return false;
    }

    @Override
    public LoginInfo clean() {
        count = 0;
        days = 0;
        continuousDays = 0;
        maxContinuousDays = 0;
        gamingDays = 0;
        maxGamingDays = 0;
        ip = null;
        loginTime = null;
        resetStates();
        return this;
    }

    @Override
    public LoginInfo deepCopy() {
        var copy = new LoginInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(LoginInfo src) {
        count = src.count;
        days = src.days;
        continuousDays = src.continuousDays;
        maxContinuousDays = src.maxContinuousDays;
        gamingDays = src.gamingDays;
        maxGamingDays = src.maxGamingDays;
        ip = src.ip;
        loginTime = src.loginTime;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            updates.add(Updates.set(path().resolve(BNAME_COUNT).value(), count));
        }
        if (changedFields.get(1)) {
            updates.add(Updates.set(path().resolve(BNAME_DAYS).value(), days));
        }
        if (changedFields.get(2)) {
            updates.add(Updates.set(path().resolve(BNAME_CONTINUOUS_DAYS).value(), continuousDays));
        }
        if (changedFields.get(3)) {
            updates.add(Updates.set(path().resolve(BNAME_MAX_CONTINUOUS_DAYS).value(), maxContinuousDays));
        }
        if (changedFields.get(4)) {
            updates.add(Updates.set(path().resolve(BNAME_GAMING_DAYS).value(), gamingDays));
        }
        if (changedFields.get(5)) {
            updates.add(Updates.set(path().resolve(BNAME_MAX_GAMING_DAYS).value(), maxGamingDays));
        }
        if (changedFields.get(6)) {
            var ip = this.ip;
            if (ip == null) {
                updates.add(Updates.unset(path().resolve(BNAME_IP).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_IP).value(), ip));
            }
        }
        if (changedFields.get(7)) {
            var loginTime = this.loginTime;
            if (loginTime == null) {
                updates.add(Updates.unset(path().resolve(BNAME_LOGIN_TIME).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_LOGIN_TIME).value(), BsonUtil.toBsonDateTime(loginTime)));
            }
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        count = BsonUtil.intValue(src, BNAME_COUNT).orElseThrow();
        days = BsonUtil.intValue(src, BNAME_DAYS).orElseThrow();
        continuousDays = BsonUtil.intValue(src, BNAME_CONTINUOUS_DAYS).orElseThrow();
        maxContinuousDays = BsonUtil.intValue(src, BNAME_MAX_CONTINUOUS_DAYS).orElseThrow();
        gamingDays = BsonUtil.intValue(src, BNAME_GAMING_DAYS).orElseThrow();
        maxGamingDays = BsonUtil.intValue(src, BNAME_MAX_GAMING_DAYS).orElseThrow();
        ip = BsonUtil.stringValue(src, BNAME_IP).orElse(null);
        loginTime = BsonUtil.dateTimeValue(src, BNAME_LOGIN_TIME).orElse(null);
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
        count = BsonUtil.intValue(src, BNAME_COUNT).orElseThrow();
        days = BsonUtil.intValue(src, BNAME_DAYS).orElseThrow();
        continuousDays = BsonUtil.intValue(src, BNAME_CONTINUOUS_DAYS).orElseThrow();
        maxContinuousDays = BsonUtil.intValue(src, BNAME_MAX_CONTINUOUS_DAYS).orElseThrow();
        gamingDays = BsonUtil.intValue(src, BNAME_GAMING_DAYS).orElseThrow();
        maxGamingDays = BsonUtil.intValue(src, BNAME_MAX_GAMING_DAYS).orElseThrow();
        ip = BsonUtil.stringValue(src, BNAME_IP).orElse(null);
        loginTime = BsonUtil.dateTimeValue(src, BNAME_LOGIN_TIME).orElse(null);
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            data.put("count", count);
        }
        if (changedFields.get(1)) {
            data.put("days", days);
        }
        if (changedFields.get(2)) {
            data.put("continuousDays", continuousDays);
        }
        if (changedFields.get(3)) {
            data.put("maxContinuousDays", maxContinuousDays);
        }
        if (changedFields.get(4)) {
            data.put("gamingDays", gamingDays);
        }
        if (changedFields.get(5)) {
            data.put("maxGamingDays", maxGamingDays);
        }
        if (changedFields.get(6)) {
            var ip = this.ip;
            if (ip != null) {
                data.put("ip", ip);
            }
        }
    }

    @Override
    protected void appendDeletedData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(6) && ip == null) {
            data.put("ip", 1);
        }
    }

    @Override
    public String toString() {
        return "LoginInfo(" + "count=" + count +
                ", days=" + days +
                ", continuousDays=" + continuousDays +
                ", maxContinuousDays=" + maxContinuousDays +
                ", gamingDays=" + gamingDays +
                ", maxGamingDays=" + maxGamingDays +
                ", ip=" + ip +
                ", loginTime=" + loginTime +
                ")";
    }

}
