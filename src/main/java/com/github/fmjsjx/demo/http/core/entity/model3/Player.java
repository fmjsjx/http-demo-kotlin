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
public final class Player extends AbstractRootModel<Player> {

    public static final String COLLECTION_NAME = "player";

    public static final String STORE_NAME_UID = "_id";
    public static final String STORE_NAME_PREFERENCES = "pfc";
    public static final String STORE_NAME_BASIC = "bsc";
    public static final String STORE_NAME_LOGIN = "lgn";
    public static final String STORE_NAME_GUIDE = "gd";
    public static final String STORE_NAME_WALLET = "wlt";
    public static final String STORE_NAME_ITEMS = "itm";
    public static final String STORE_NAME_STATISTICS = "stc";
    public static final String STORE_NAME_DAILY = "dly";
    public static final String STORE_NAME_UPDATE_VERSION = "_uv";
    public static final String STORE_NAME_CREATE_TIME = "_ct";
    public static final String STORE_NAME_UPDATE_TIME = "_ut";

    public static final String DISPLAY_NAME_UID = "uid";
    public static final String DISPLAY_NAME_PREFERENCES = "preferences";
    public static final String DISPLAY_NAME_BASIC = "basic";
    public static final String DISPLAY_NAME_LOGIN = "login";
    public static final String DISPLAY_NAME_GUIDE = "guide";
    public static final String DISPLAY_NAME_WALLET = "wallet";
    public static final String DISPLAY_NAME_ITEMS = "items";
    public static final String DISPLAY_NAME_STATISTICS = "statistics";
    public static final String DISPLAY_NAME_DAILY = "daily";
    public static final String DISPLAY_NAME_CREATED_AT = "createdAt";
    public static final String DISPLAY_NAME_UPDATED_AT = "updatedAt";

    public static final int FIELD_INDEX_UID = 0;
    public static final int FIELD_INDEX_PREFERENCES = 1;
    public static final int FIELD_INDEX_BASIC = 2;
    public static final int FIELD_INDEX_LOGIN = 3;
    public static final int FIELD_INDEX_GUIDE = 4;
    public static final int FIELD_INDEX_WALLET = 5;
    public static final int FIELD_INDEX_ITEMS = 6;
    public static final int FIELD_INDEX_STATISTICS = 7;
    public static final int FIELD_INDEX_DAILY = 8;
    public static final int FIELD_INDEX_UPDATE_VERSION = 9;
    public static final int FIELD_INDEX_CREATE_TIME = 10;
    public static final int FIELD_INDEX_UPDATE_TIME = 11;
    public static final int FIELD_INDEX_CREATED_AT = 12;
    public static final int FIELD_INDEX_UPDATED_AT = 13;

    @JSONType(alphabetic = false)
    public static final class PlayerStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_UID)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_UID)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_UID)
        private long uid;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_PREFERENCES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_PREFERENCES)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_PREFERENCES)
        private PreferencesInfo.PreferencesInfoStoreData preferences;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_BASIC)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_BASIC)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_BASIC)
        private BasicInfo.BasicInfoStoreData basic;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LOGIN)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LOGIN)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LOGIN)
        private LoginInfo.LoginInfoStoreData login;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_GUIDE)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_GUIDE)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_GUIDE)
        private GuideInfo.GuideInfoStoreData guide;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_WALLET)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_WALLET)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_WALLET)
        private WalletInfo.WalletInfoStoreData wallet;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ITEMS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ITEMS)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_ITEMS, implementation = LinkedHashMap.class)
        private Map<String, Integer> items;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_STATISTICS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_STATISTICS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_STATISTICS)
        private StatisticsInfo.StatisticsInfoStoreData statistics;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DAILY)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DAILY)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DAILY)
        private DailyInfo.DailyInfoStoreData daily;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_UPDATE_VERSION)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_UPDATE_VERSION)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_UPDATE_VERSION)
        private int updateVersion;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CREATE_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CREATE_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CREATE_TIME)
        private long createTime;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_UPDATE_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_UPDATE_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_UPDATE_TIME)
        private long updateTime;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public PreferencesInfo.PreferencesInfoStoreData getPreferences() {
            return preferences;
        }

        public void setPreferences(PreferencesInfo.PreferencesInfoStoreData preferences) {
            this.preferences = preferences;
        }

        public BasicInfo.BasicInfoStoreData getBasic() {
            return basic;
        }

        public void setBasic(BasicInfo.BasicInfoStoreData basic) {
            this.basic = basic;
        }

        public LoginInfo.LoginInfoStoreData getLogin() {
            return login;
        }

        public void setLogin(LoginInfo.LoginInfoStoreData login) {
            this.login = login;
        }

        public GuideInfo.GuideInfoStoreData getGuide() {
            return guide;
        }

        public void setGuide(GuideInfo.GuideInfoStoreData guide) {
            this.guide = guide;
        }

        public WalletInfo.WalletInfoStoreData getWallet() {
            return wallet;
        }

        public void setWallet(WalletInfo.WalletInfoStoreData wallet) {
            this.wallet = wallet;
        }

        public Map<String, Integer> getItems() {
            return items;
        }

        public void setItems(Map<String, Integer> items) {
            this.items = items;
        }

        public StatisticsInfo.StatisticsInfoStoreData getStatistics() {
            return statistics;
        }

        public void setStatistics(StatisticsInfo.StatisticsInfoStoreData statistics) {
            this.statistics = statistics;
        }

        public DailyInfo.DailyInfoStoreData getDaily() {
            return daily;
        }

        public void setDaily(DailyInfo.DailyInfoStoreData daily) {
            this.daily = daily;
        }

        public int getUpdateVersion() {
            return updateVersion;
        }

        public void setUpdateVersion(int updateVersion) {
            this.updateVersion = updateVersion;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    private long uid;
    private final PreferencesInfo preferences = new PreferencesInfo()
            .parent(this).index(FIELD_INDEX_PREFERENCES).key(STORE_NAME_PREFERENCES);
    private final BasicInfo basic = new BasicInfo()
            .parent(this).index(FIELD_INDEX_BASIC).key(STORE_NAME_BASIC);
    private final LoginInfo login = new LoginInfo()
            .parent(this).index(FIELD_INDEX_LOGIN).key(STORE_NAME_LOGIN);
    private final GuideInfo guide = new GuideInfo()
            .parent(this).index(FIELD_INDEX_GUIDE).key(STORE_NAME_GUIDE);
    private final WalletInfo wallet = new WalletInfo()
            .parent(this).index(FIELD_INDEX_WALLET).key(STORE_NAME_WALLET);
    private final SingleValueMapModel<Integer, Integer> items = SingleValueMapModel.integerKeysMap(SingleValues.integer())
            .parent(this).index(FIELD_INDEX_ITEMS).key(STORE_NAME_ITEMS);
    private final StatisticsInfo statistics = new StatisticsInfo()
            .parent(this).index(FIELD_INDEX_STATISTICS).key(STORE_NAME_STATISTICS);
    private final DailyInfo daily = new DailyInfo()
            .parent(this).index(FIELD_INDEX_DAILY).key(STORE_NAME_DAILY);
    private int updateVersion;
    private LocalDateTime createTime = BsonModelConstants.EPOCH_DATE_TIME;
    private LocalDateTime updateTime = BsonModelConstants.EPOCH_DATE_TIME;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        if (uid != this.uid) {
            this.uid = uid;
            triggerChange(FIELD_INDEX_UID);
        }
    }

    public PreferencesInfo getPreferences() {
        return preferences;
    }

    public BasicInfo getBasic() {
        return basic;
    }

    public LoginInfo getLogin() {
        return login;
    }

    public GuideInfo getGuide() {
        return guide;
    }

    public WalletInfo getWallet() {
        return wallet;
    }

    public SingleValueMapModel<Integer, Integer> getItems() {
        return items;
    }

    public StatisticsInfo getStatistics() {
        return statistics;
    }

    public DailyInfo getDaily() {
        return daily;
    }

    public int getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(int updateVersion) {
        if (updateVersion != this.updateVersion) {
            this.updateVersion = updateVersion;
            triggerChange(FIELD_INDEX_UPDATE_VERSION);
        }
    }

    public int increaseUpdateVersion() {
        triggerChange(FIELD_INDEX_UPDATE_VERSION);
        return ++updateVersion;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        if (!createTime.equals(this.createTime)) {
            this.createTime = createTime;
            fieldsChanged(FIELD_INDEX_CREATE_TIME, FIELD_INDEX_CREATED_AT);
        }
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        if (!updateTime.equals(this.updateTime)) {
            this.updateTime = updateTime;
            fieldsChanged(FIELD_INDEX_UPDATE_TIME, FIELD_INDEX_UPDATED_AT);
        }
    }

    public long getCreatedAt() {
        return DateTimeUtil.toEpochMilli(getCreateTime());
    }

    public long getUpdatedAt() {
        return DateTimeUtil.toEpochMilli(getUpdateTime());
    }

    @Override
    protected Class<PlayerStoreData> storeDataType() {
        return PlayerStoreData.class;
    }

    @Override
    protected Player resetChildren() {
        getPreferences().reset();
        getBasic().reset();
        getLogin().reset();
        getGuide().reset();
        getWallet().reset();
        getItems().reset();
        getStatistics().reset();
        getDaily().reset();
        return this;
    }

    @Override
    protected Player cleanFields() {
        uid = 0L;
        getPreferences().clean();
        getBasic().clean();
        getLogin().clean();
        getGuide().clean();
        getWallet().clean();
        getItems().clean();
        getStatistics().clean();
        getDaily().clean();
        updateVersion = 0;
        createTime = BsonModelConstants.EPOCH_DATE_TIME;
        updateTime = BsonModelConstants.EPOCH_DATE_TIME;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_UID)) {
            updates.add(Updates.set(path().path(STORE_NAME_UID), new BsonInt64(getUid())));
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            getPreferences().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_BASIC)) {
            getBasic().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            getLogin().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_GUIDE)) {
            getGuide().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            getWallet().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            getItems().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS)) {
            getStatistics().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_DAILY)) {
            getDaily().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_UPDATE_VERSION)) {
            updates.add(Updates.set(path().path(STORE_NAME_UPDATE_VERSION), new BsonInt32(getUpdateVersion())));
        }
        if (changedFields.get(FIELD_INDEX_CREATE_TIME)) {
            updates.add(Updates.set(path().path(STORE_NAME_CREATE_TIME), BsonValueUtil.toBsonDateTime(getCreateTime())));
        }
        if (changedFields.get(FIELD_INDEX_UPDATE_TIME)) {
            updates.add(Updates.set(path().path(STORE_NAME_UPDATE_TIME), BsonValueUtil.toBsonDateTime(getUpdateTime())));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_UID)) {
            data.put(DISPLAY_NAME_UID, getUid());
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toUpdated();
            if (_preferences != null) {
                data.put(DISPLAY_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_BASIC)) {
            var _basic = getBasic().toUpdated();
            if (_basic != null) {
                data.put(DISPLAY_NAME_BASIC, _basic);
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            var _login = getLogin().toUpdated();
            if (_login != null) {
                data.put(DISPLAY_NAME_LOGIN, _login);
            }
        }
        if (changedFields.get(FIELD_INDEX_GUIDE)) {
            var _guide = getGuide().toUpdated();
            if (_guide != null) {
                data.put(DISPLAY_NAME_GUIDE, _guide);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toUpdated();
            if (_wallet != null) {
                data.put(DISPLAY_NAME_WALLET, _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toUpdated();
            if (_items != null) {
                data.put(DISPLAY_NAME_ITEMS, _items);
            }
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS)) {
            var _statistics = getStatistics().toUpdated();
            if (_statistics != null) {
                data.put(DISPLAY_NAME_STATISTICS, _statistics);
            }
        }
        if (changedFields.get(FIELD_INDEX_DAILY)) {
            var _daily = getDaily().toUpdated();
            if (_daily != null) {
                data.put(DISPLAY_NAME_DAILY, _daily);
            }
        }
        if (changedFields.get(FIELD_INDEX_CREATED_AT)) {
            data.put(DISPLAY_NAME_CREATED_AT, getCreatedAt());
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_AT)) {
            data.put(DISPLAY_NAME_UPDATED_AT, getUpdatedAt());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_UID, getUid());
        _displayData.put(DISPLAY_NAME_PREFERENCES, getPreferences().toDisplayData());
        _displayData.put(DISPLAY_NAME_BASIC, getBasic().toDisplayData());
        _displayData.put(DISPLAY_NAME_LOGIN, getLogin().toDisplayData());
        _displayData.put(DISPLAY_NAME_GUIDE, getGuide().toDisplayData());
        _displayData.put(DISPLAY_NAME_WALLET, getWallet().toDisplayData());
        _displayData.put(DISPLAY_NAME_ITEMS, getItems().toDisplayData());
        _displayData.put(DISPLAY_NAME_STATISTICS, getStatistics().toDisplayData());
        _displayData.put(DISPLAY_NAME_DAILY, getDaily().toDisplayData());
        _displayData.put(DISPLAY_NAME_CREATED_AT, getCreatedAt());
        _displayData.put(DISPLAY_NAME_UPDATED_AT, getUpdatedAt());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_UID, new BsonInt64(getUid()));
        _bsonValue.put(STORE_NAME_PREFERENCES, getPreferences().toBsonValue());
        _bsonValue.put(STORE_NAME_BASIC, getBasic().toBsonValue());
        _bsonValue.put(STORE_NAME_LOGIN, getLogin().toBsonValue());
        _bsonValue.put(STORE_NAME_GUIDE, getGuide().toBsonValue());
        _bsonValue.put(STORE_NAME_WALLET, getWallet().toBsonValue());
        _bsonValue.put(STORE_NAME_ITEMS, getItems().toBsonValue());
        _bsonValue.put(STORE_NAME_STATISTICS, getStatistics().toBsonValue());
        _bsonValue.put(STORE_NAME_DAILY, getDaily().toBsonValue());
        _bsonValue.put(STORE_NAME_UPDATE_VERSION, new BsonInt32(getUpdateVersion()));
        _bsonValue.put(STORE_NAME_CREATE_TIME, BsonValueUtil.toBsonDateTime(getCreateTime()));
        _bsonValue.put(STORE_NAME_UPDATE_TIME, BsonValueUtil.toBsonDateTime(getUpdateTime()));
        return _bsonValue;
    }

    @Override
    public Player load(BsonDocument src) {
        resetStates();
        uid = BsonUtil.longValue(src, STORE_NAME_UID).orElse(0L);
        BsonUtil.documentValue(src, STORE_NAME_PREFERENCES).ifPresentOrElse(getPreferences()::load, getPreferences()::clean);
        BsonUtil.documentValue(src, STORE_NAME_BASIC).ifPresentOrElse(getBasic()::load, getBasic()::clean);
        BsonUtil.documentValue(src, STORE_NAME_LOGIN).ifPresentOrElse(getLogin()::load, getLogin()::clean);
        BsonUtil.documentValue(src, STORE_NAME_GUIDE).ifPresentOrElse(getGuide()::load, getGuide()::clean);
        BsonUtil.documentValue(src, STORE_NAME_WALLET).ifPresentOrElse(getWallet()::load, getWallet()::clean);
        BsonUtil.documentValue(src, STORE_NAME_ITEMS).ifPresentOrElse(getItems()::load, getItems()::clean);
        BsonUtil.documentValue(src, STORE_NAME_STATISTICS).ifPresentOrElse(getStatistics()::load, getStatistics()::clean);
        BsonUtil.documentValue(src, STORE_NAME_DAILY).ifPresentOrElse(getDaily()::load, getDaily()::clean);
        updateVersion = BsonUtil.intValue(src, STORE_NAME_UPDATE_VERSION).orElse(0);
        createTime = BsonUtil.dateTimeValue(src, STORE_NAME_CREATE_TIME).orElse(BsonModelConstants.EPOCH_DATE_TIME);
        updateTime = BsonUtil.dateTimeValue(src, STORE_NAME_UPDATE_TIME).orElse(BsonModelConstants.EPOCH_DATE_TIME);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlayerStoreData toStoreData() {
        var _storeData = new PlayerStoreData();
        _storeData.uid = getUid();
        _storeData.preferences = getPreferences().toStoreData();
        _storeData.basic = getBasic().toStoreData();
        _storeData.login = getLogin().toStoreData();
        _storeData.guide = getGuide().toStoreData();
        _storeData.wallet = getWallet().toStoreData();
        _storeData.items = (Map<String, Integer>) getItems().toStoreData();
        _storeData.statistics = getStatistics().toStoreData();
        _storeData.daily = getDaily().toStoreData();
        _storeData.updateVersion = getUpdateVersion();
        _storeData.createTime = DateTimeUtil.toEpochMilli(getCreateTime());
        _storeData.updateTime = DateTimeUtil.toEpochMilli(getUpdateTime());
        return _storeData;
    }

    @Override
    public Player loadStoreData(Object data) {
        resetStates();
        if (data instanceof PlayerStoreData _storeData) {
            uid = _storeData.uid;
            getPreferences().loadStoreData(_storeData.preferences);
            getBasic().loadStoreData(_storeData.basic);
            getLogin().loadStoreData(_storeData.login);
            getGuide().loadStoreData(_storeData.guide);
            getWallet().loadStoreData(_storeData.wallet);
            getItems().loadStoreData(_storeData.items);
            getStatistics().loadStoreData(_storeData.statistics);
            getDaily().loadStoreData(_storeData.daily);
            updateVersion = _storeData.updateVersion;
            createTime = DateTimeUtil.ofEpochMilli(_storeData.createTime);
            updateTime = DateTimeUtil.ofEpochMilli(_storeData.updateTime);
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
        if (changedFields.get(FIELD_INDEX_UID)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_BASIC) && getBasic().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN) && getLogin().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_GUIDE) && getGuide().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_WALLET) && getWallet().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ITEMS) && getItems().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS) && getStatistics().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DAILY) && getDaily().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_UPDATE_VERSION)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_CREATE_TIME)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_UPDATE_TIME)) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toDeleted();
            if (_preferences != null) {
                data.put(DISPLAY_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_BASIC)) {
            var _basic = getBasic().toDeleted();
            if (_basic != null) {
                data.put(DISPLAY_NAME_BASIC, _basic);
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            var _login = getLogin().toDeleted();
            if (_login != null) {
                data.put(DISPLAY_NAME_LOGIN, _login);
            }
        }
        if (changedFields.get(FIELD_INDEX_GUIDE)) {
            var _guide = getGuide().toDeleted();
            if (_guide != null) {
                data.put(DISPLAY_NAME_GUIDE, _guide);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toDeleted();
            if (_wallet != null) {
                data.put(DISPLAY_NAME_WALLET, _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toDeleted();
            if (_items != null) {
                data.put(DISPLAY_NAME_ITEMS, _items);
            }
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS)) {
            var _statistics = getStatistics().toDeleted();
            if (_statistics != null) {
                data.put(DISPLAY_NAME_STATISTICS, _statistics);
            }
        }
        if (changedFields.get(FIELD_INDEX_DAILY)) {
            var _daily = getDaily().toDeleted();
            if (_daily != null) {
                data.put(DISPLAY_NAME_DAILY, _daily);
            }
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
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_BASIC) && getBasic().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN) && getLogin().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_GUIDE) && getGuide().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_WALLET) && getWallet().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ITEMS) && getItems().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS) && getStatistics().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DAILY) && getDaily().anyDeleted()) {
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
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            __size += getPreferences().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_BASIC)) {
            __size += getBasic().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            __size += getLogin().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_GUIDE)) {
            __size += getGuide().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            __size += getWallet().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            __size += getItems().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_STATISTICS)) {
            __size += getStatistics().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_DAILY)) {
            __size += getDaily().deletedSize();
        }
        return __size;
    }

    @Override
    public Player deepCopy() {
        return new Player().deepCopyFrom(this);
    }

    @Override
    public Player deepCopyFrom(Player src) {
        uid = src.getUid();
        getPreferences().deepCopyFrom(src.getPreferences());
        getBasic().deepCopyFrom(src.getBasic());
        getLogin().deepCopyFrom(src.getLogin());
        getGuide().deepCopyFrom(src.getGuide());
        getWallet().deepCopyFrom(src.getWallet());
        getItems().deepCopyFrom(src.getItems());
        getStatistics().deepCopyFrom(src.getStatistics());
        getDaily().deepCopyFrom(src.getDaily());
        updateVersion = src.getUpdateVersion();
        createTime = src.getCreateTime();
        updateTime = src.getUpdateTime();
        return this;
    }

    @Override
    public String toString() {
        return "Player(uid=" + getUid() +
                ", preferences=" + getPreferences() +
                ", basic=" + getBasic() +
                ", login=" + getLogin() +
                ", guide=" + getGuide() +
                ", wallet=" + getWallet() +
                ", items=" + getItems() +
                ", statistics=" + getStatistics() +
                ", daily=" + getDaily() +
                ", updateVersion=" + getUpdateVersion() +
                ", createTime=" + getCreateTime() +
                ", updateTime=" + getUpdateTime() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ")";
    }

}
