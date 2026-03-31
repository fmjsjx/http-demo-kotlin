package com.github.fmjsjx.demo.http.core.entity.model3;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.*;
import com.github.fmjsjx.bson.model3.core.util.*;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;
import org.jspecify.annotations.*;

import java.time.LocalDateTime;
import java.util.*;

@NullMarked
public final class LoginInfo extends AbstractObjectModel<LoginInfo> {

    public static final String STORE_NAME_COUNT = "cnt";
    public static final String STORE_NAME_DAYS = "d";
    public static final String STORE_NAME_CONTINUOUS_DAYS = "cnd";
    public static final String STORE_NAME_MAX_CONTINUOUS_DAYS = "mcd";
    public static final String STORE_NAME_GAMING_DAYS = "gmd";
    public static final String STORE_NAME_MAX_GAMING_DAYS = "mgd";
    public static final String STORE_NAME_IP = "ip";
    public static final String STORE_NAME_LOGIN_TIME = "lgt";

    public static final String DISPLAY_NAME_COUNT = "count";
    public static final String DISPLAY_NAME_DAYS = "days";
    public static final String DISPLAY_NAME_CONTINUOUS_DAYS = "continuousDays";
    public static final String DISPLAY_NAME_MAX_CONTINUOUS_DAYS = "maxContinuousDays";
    public static final String DISPLAY_NAME_GAMING_DAYS = "gamingDays";
    public static final String DISPLAY_NAME_MAX_GAMING_DAYS = "maxGamingDays";
    public static final String DISPLAY_NAME_IP = "ip";
    public static final String DISPLAY_NAME_LOGGED_IN_AT = "loggedInAt";

    public static final int FIELD_INDEX_COUNT = 0;
    public static final int FIELD_INDEX_DAYS = 1;
    public static final int FIELD_INDEX_CONTINUOUS_DAYS = 2;
    public static final int FIELD_INDEX_MAX_CONTINUOUS_DAYS = 3;
    public static final int FIELD_INDEX_GAMING_DAYS = 4;
    public static final int FIELD_INDEX_MAX_GAMING_DAYS = 5;
    public static final int FIELD_INDEX_IP = 6;
    public static final int FIELD_INDEX_LOGIN_TIME = 7;
    public static final int FIELD_INDEX_LOGGED_IN_AT = 8;

    @JSONType(alphabetic = false)
    public static final class LoginInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COUNT)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COUNT)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COUNT)
        private int count;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DAYS)
        private int days;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CONTINUOUS_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CONTINUOUS_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CONTINUOUS_DAYS)
        private int continuousDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_MAX_CONTINUOUS_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_MAX_CONTINUOUS_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_MAX_CONTINUOUS_DAYS)
        private int maxContinuousDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_GAMING_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_GAMING_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_GAMING_DAYS)
        private int gamingDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_MAX_GAMING_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_MAX_GAMING_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_MAX_GAMING_DAYS)
        private int maxGamingDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_IP)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_IP)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_IP)
        private @Nullable String ip;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LOGIN_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LOGIN_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LOGIN_TIME)
        private @Nullable Long loginTime;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getContinuousDays() {
            return continuousDays;
        }

        public void setContinuousDays(int continuousDays) {
            this.continuousDays = continuousDays;
        }

        public int getMaxContinuousDays() {
            return maxContinuousDays;
        }

        public void setMaxContinuousDays(int maxContinuousDays) {
            this.maxContinuousDays = maxContinuousDays;
        }

        public int getGamingDays() {
            return gamingDays;
        }

        public void setGamingDays(int gamingDays) {
            this.gamingDays = gamingDays;
        }

        public int getMaxGamingDays() {
            return maxGamingDays;
        }

        public void setMaxGamingDays(int maxGamingDays) {
            this.maxGamingDays = maxGamingDays;
        }

        public @Nullable String getIp() {
            return ip;
        }

        public void setIp(@Nullable String ip) {
            this.ip = ip;
        }

        public @Nullable Long getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(@Nullable Long loginTime) {
            this.loginTime = loginTime;
        }
    }

    private int count;
    private int days;
    private int continuousDays;
    private int maxContinuousDays;
    private int gamingDays;
    private int maxGamingDays;
    private @Nullable String ip;
    private @Nullable LocalDateTime loginTime;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count != this.count) {
            this.count = count;
            triggerChange(FIELD_INDEX_COUNT);
        }
    }

    public int increaseCount() {
        triggerChange(FIELD_INDEX_COUNT);
        return ++count;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        if (days != this.days) {
            this.days = days;
            triggerChange(FIELD_INDEX_DAYS);
        }
    }

    public int increaseDays() {
        triggerChange(FIELD_INDEX_DAYS);
        return ++days;
    }

    public int getContinuousDays() {
        return continuousDays;
    }

    public void setContinuousDays(int continuousDays) {
        if (continuousDays != this.continuousDays) {
            this.continuousDays = continuousDays;
            triggerChange(FIELD_INDEX_CONTINUOUS_DAYS);
        }
    }

    public int increaseContinuousDays() {
        triggerChange(FIELD_INDEX_CONTINUOUS_DAYS);
        return ++continuousDays;
    }

    public int getMaxContinuousDays() {
        return maxContinuousDays;
    }

    public void setMaxContinuousDays(int maxContinuousDays) {
        if (maxContinuousDays != this.maxContinuousDays) {
            this.maxContinuousDays = maxContinuousDays;
            triggerChange(FIELD_INDEX_MAX_CONTINUOUS_DAYS);
        }
    }

    public int getGamingDays() {
        return gamingDays;
    }

    public void setGamingDays(int gamingDays) {
        if (gamingDays != this.gamingDays) {
            this.gamingDays = gamingDays;
            triggerChange(FIELD_INDEX_GAMING_DAYS);
        }
    }

    public int getMaxGamingDays() {
        return maxGamingDays;
    }

    public void setMaxGamingDays(int maxGamingDays) {
        if (maxGamingDays != this.maxGamingDays) {
            this.maxGamingDays = maxGamingDays;
            triggerChange(FIELD_INDEX_MAX_GAMING_DAYS);
        }
    }

    public @Nullable String getIp() {
        return ip;
    }

    public void setIp(@Nullable String ip) {
        if (!Objects.equals(this.ip, ip)) {
            this.ip = ip;
            triggerChange(FIELD_INDEX_IP);
        }
    }

    public @Nullable LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(@Nullable LocalDateTime loginTime) {
        if (!Objects.equals(this.loginTime, loginTime)) {
            this.loginTime = loginTime;
            fieldsChanged(FIELD_INDEX_LOGIN_TIME, FIELD_INDEX_LOGGED_IN_AT);
        }
    }

    public long getLoggedInAt() {
        return DateTimeUtil.toEpochMilli(getLoginTime());
    }

    @Override
    protected LoginInfo cleanFields() {
        count = 0;
        days = 0;
        continuousDays = 0;
        maxContinuousDays = 0;
        gamingDays = 0;
        maxGamingDays = 0;
        ip = null;
        loginTime = null;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            updates.add(Updates.set(path().path(STORE_NAME_COUNT), new BsonInt32(getCount())));
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_DAYS), new BsonInt32(getDays())));
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_CONTINUOUS_DAYS), new BsonInt32(getContinuousDays())));
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_MAX_CONTINUOUS_DAYS), new BsonInt32(getMaxContinuousDays())));
        }
        if (changedFields.get(FIELD_INDEX_GAMING_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_GAMING_DAYS), new BsonInt32(getGamingDays())));
        }
        if (changedFields.get(FIELD_INDEX_MAX_GAMING_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_MAX_GAMING_DAYS), new BsonInt32(getMaxGamingDays())));
        }
        if (changedFields.get(FIELD_INDEX_IP)) {
            var _ip = getIp();
            if (_ip == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_IP)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_IP), new BsonString(_ip)));
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGIN_TIME)) {
            var _loginTime = getLoginTime();
            if (_loginTime == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_LOGIN_TIME)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_LOGIN_TIME), BsonValueUtil.toBsonDateTime(_loginTime)));
            }
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            data.put(DISPLAY_NAME_COUNT, getCount());
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            data.put(DISPLAY_NAME_DAYS, getDays());
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            data.put(DISPLAY_NAME_CONTINUOUS_DAYS, getContinuousDays());
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            data.put(DISPLAY_NAME_MAX_CONTINUOUS_DAYS, getMaxContinuousDays());
        }
        if (changedFields.get(FIELD_INDEX_GAMING_DAYS)) {
            data.put(DISPLAY_NAME_GAMING_DAYS, getGamingDays());
        }
        if (changedFields.get(FIELD_INDEX_MAX_GAMING_DAYS)) {
            data.put(DISPLAY_NAME_MAX_GAMING_DAYS, getMaxGamingDays());
        }
        if (changedFields.get(FIELD_INDEX_IP)) {
            var _ip = getIp();
            if (_ip != null) {
                data.put(DISPLAY_NAME_IP, _ip);
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGGED_IN_AT)) {
            data.put(DISPLAY_NAME_LOGGED_IN_AT, getLoggedInAt());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_COUNT, getCount());
        _displayData.put(DISPLAY_NAME_DAYS, getDays());
        _displayData.put(DISPLAY_NAME_CONTINUOUS_DAYS, getContinuousDays());
        _displayData.put(DISPLAY_NAME_MAX_CONTINUOUS_DAYS, getMaxContinuousDays());
        _displayData.put(DISPLAY_NAME_GAMING_DAYS, getGamingDays());
        _displayData.put(DISPLAY_NAME_MAX_GAMING_DAYS, getMaxGamingDays());
        var _ip = getIp();
        if (_ip != null) {
            _displayData.put(DISPLAY_NAME_IP, _ip);
        }
        _displayData.put(DISPLAY_NAME_LOGGED_IN_AT, getLoggedInAt());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_COUNT, new BsonInt32(getCount()));
        _bsonValue.put(STORE_NAME_DAYS, new BsonInt32(getDays()));
        _bsonValue.put(STORE_NAME_CONTINUOUS_DAYS, new BsonInt32(getContinuousDays()));
        _bsonValue.put(STORE_NAME_MAX_CONTINUOUS_DAYS, new BsonInt32(getMaxContinuousDays()));
        _bsonValue.put(STORE_NAME_GAMING_DAYS, new BsonInt32(getGamingDays()));
        _bsonValue.put(STORE_NAME_MAX_GAMING_DAYS, new BsonInt32(getMaxGamingDays()));
        var _ip = getIp();
        if (_ip != null) {
            _bsonValue.put(STORE_NAME_IP, new BsonString(_ip));
        }
        var _loginTime = getLoginTime();
        if (_loginTime != null) {
            _bsonValue.put(STORE_NAME_LOGIN_TIME, BsonValueUtil.toBsonDateTime(_loginTime));
        }
        return _bsonValue;
    }

    @Override
    public LoginInfo load(BsonDocument src) {
        resetStates();
        count = BsonUtil.intValue(src, STORE_NAME_COUNT).orElse(0);
        days = BsonUtil.intValue(src, STORE_NAME_DAYS).orElse(0);
        continuousDays = BsonUtil.intValue(src, STORE_NAME_CONTINUOUS_DAYS).orElse(0);
        maxContinuousDays = BsonUtil.intValue(src, STORE_NAME_MAX_CONTINUOUS_DAYS).orElse(0);
        gamingDays = BsonUtil.intValue(src, STORE_NAME_GAMING_DAYS).orElse(0);
        maxGamingDays = BsonUtil.intValue(src, STORE_NAME_MAX_GAMING_DAYS).orElse(0);
        ip = BsonUtil.stringValue(src, STORE_NAME_IP).orElse(null);
        loginTime = BsonUtil.dateTimeValue(src, STORE_NAME_LOGIN_TIME).orElse(null);
        return this;
    }

    @Override
    public LoginInfoStoreData toStoreData() {
        var _storeData = new LoginInfoStoreData();
        _storeData.count = getCount();
        _storeData.days = getDays();
        _storeData.continuousDays = getContinuousDays();
        _storeData.maxContinuousDays = getMaxContinuousDays();
        _storeData.gamingDays = getGamingDays();
        _storeData.maxGamingDays = getMaxGamingDays();
        var _ip = getIp();
        if (_ip != null) {
            _storeData.ip = _ip;
        }
        var _loginTime = getLoginTime();
        if (_loginTime != null) {
            _storeData.loginTime = DateTimeUtil.toEpochMilli(_loginTime);
        }
        return _storeData;
    }

    @Override
    public LoginInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof LoginInfoStoreData _storeData) {
            count = _storeData.count;
            days = _storeData.days;
            continuousDays = _storeData.continuousDays;
            maxContinuousDays = _storeData.maxContinuousDays;
            gamingDays = _storeData.gamingDays;
            maxGamingDays = _storeData.maxGamingDays;
            var _ip = _storeData.ip;
            if (_ip != null) {
                ip = _ip;
            }
            var _loginTime = _storeData.loginTime;
            if (_loginTime != null) {
                loginTime = DateTimeUtil.ofEpochMilli(_loginTime);
            }
        }
        return this;
    }

    @Override
    public boolean anyUpdated() {
        if (isFullUpdate()) {
            return true;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_GAMING_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_MAX_GAMING_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_IP) && getIp() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN_TIME) && getLoginTime() != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_IP) && getIp() == null) {
            data.put(DISPLAY_NAME_IP, BsonModelConstants.DELETED_VALUE);
        }
    }

    @Override
    public boolean anyDeleted() {
        if (isFullUpdate()) {
            return false;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(FIELD_INDEX_IP) && getIp() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN_TIME) && getLoginTime() == null) {
            return true;
        }
        return false;
    }

    @Override
    public int deletedSize() {
        if (isFullUpdate()) {
            return 0;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var __size = 0;
        if (changedFields.get(FIELD_INDEX_IP) && getIp() == null) {
            __size++;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN_TIME) && getLoginTime() == null) {
            __size++;
        }
        return __size;
    }

    @Override
    public LoginInfo deepCopy() {
        return new LoginInfo().deepCopyFrom(this);
    }

    @Override
    public LoginInfo deepCopyFrom(LoginInfo src) {
        count = src.getCount();
        days = src.getDays();
        continuousDays = src.getContinuousDays();
        maxContinuousDays = src.getMaxContinuousDays();
        gamingDays = src.getGamingDays();
        maxGamingDays = src.getMaxGamingDays();
        ip = src.getIp();
        loginTime = src.getLoginTime();
        return this;
    }

    @Override
    public String toString() {
        return "LoginInfo(count=" + getCount() +
                ", days=" + getDays() +
                ", continuousDays=" + getContinuousDays() +
                ", maxContinuousDays=" + getMaxContinuousDays() +
                ", gamingDays=" + getGamingDays() +
                ", maxGamingDays=" + getMaxGamingDays() +
                ", ip=" + getIp() +
                ", loginTime=" + getLoginTime() +
                ", loggedInAt=" + getLoggedInAt() +
                ")";
    }

}
