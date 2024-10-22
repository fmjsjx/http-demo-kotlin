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

public class Player extends RootModel<Player> {

    public static final String COLLECTION_NAME = "player";

    public static final String BNAME_UID = "_id";
    public static final String BNAME_PREFERENCES = "pfc";
    public static final String BNAME_BASIC = "bsc";
    public static final String BNAME_LOGIN = "lgn";
    public static final String BNAME_GUIDE = "gd";
    public static final String BNAME_WALLET = "wlt";
    public static final String BNAME_ITEMS = "itm";
    public static final String BNAME_STATISTICS = "stc";
    public static final String BNAME_DAILY = "dly";
    public static final String BNAME_UPDATE_VERSION = "_uv";
    public static final String BNAME_CREATE_TIME = "_ct";
    public static final String BNAME_UPDATE_TIME = "_ut";

    private long uid;
    private final PreferencesInfo preferences = new PreferencesInfo().parent(this).key(BNAME_PREFERENCES).index(1);
    private final BasicInfo basic = new BasicInfo().parent(this).key(BNAME_BASIC).index(2);
    private final LoginInfo login = new LoginInfo().parent(this).key(BNAME_LOGIN).index(3);
    private final GuideInfo guide = new GuideInfo().parent(this).key(BNAME_GUIDE).index(4);
    private final WalletInfo wallet = new WalletInfo().parent(this).key(BNAME_WALLET).index(5);
    private final SingleValueMapModel<Integer, Integer> items = SingleValueMapModel.integerKeysMap(SingleValueTypes.INTEGER).parent(this).key(BNAME_ITEMS).index(6);
    private final StatisticsInfo statistics = new StatisticsInfo().parent(this).key(BNAME_STATISTICS).index(7);
    private final DailyInfo daily = new DailyInfo().parent(this).key(BNAME_DAILY).index(8);
    private int updateVersion;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        if (uid != this.uid) {
            this.uid = uid;
            fieldChanged(0);
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
            fieldChanged(9);
        }
    }

    public int increaseUpdateVersion() {
        fieldChanged(9);
        return ++updateVersion;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        Objects.requireNonNull(createTime, "createTime must not be null");
        if (!createTime.equals(this.createTime)) {
            this.createTime = createTime;
            fieldChanged(10);
        }
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        Objects.requireNonNull(updateTime, "updateTime must not be null");
        if (!updateTime.equals(this.updateTime)) {
            this.updateTime = updateTime;
            fieldChanged(11);
        }
    }

    public boolean uidChanged() {
        return changedFields.get(0);
    }

    public boolean preferencesChanged() {
        return changedFields.get(1);
    }

    public boolean basicChanged() {
        return changedFields.get(2);
    }

    public boolean loginChanged() {
        return changedFields.get(3);
    }

    public boolean guideChanged() {
        return changedFields.get(4);
    }

    public boolean walletChanged() {
        return changedFields.get(5);
    }

    public boolean itemsChanged() {
        return changedFields.get(6);
    }

    public boolean statisticsChanged() {
        return changedFields.get(7);
    }

    public boolean dailyChanged() {
        return changedFields.get(8);
    }

    public boolean updateVersionChanged() {
        return changedFields.get(9);
    }

    public boolean createTimeChanged() {
        return changedFields.get(10);
    }

    public boolean updateTimeChanged() {
        return changedFields.get(11);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_UID, new BsonInt64(uid));
        bson.append(BNAME_PREFERENCES, preferences.toBson());
        bson.append(BNAME_BASIC, basic.toBson());
        bson.append(BNAME_LOGIN, login.toBson());
        bson.append(BNAME_GUIDE, guide.toBson());
        bson.append(BNAME_WALLET, wallet.toBson());
        bson.append(BNAME_ITEMS, items.toBson());
        bson.append(BNAME_STATISTICS, statistics.toBson());
        bson.append(BNAME_DAILY, daily.toBson());
        bson.append(BNAME_UPDATE_VERSION, new BsonInt32(updateVersion));
        bson.append(BNAME_CREATE_TIME, BsonUtil.toBsonDateTime(createTime));
        bson.append(BNAME_UPDATE_TIME, BsonUtil.toBsonDateTime(updateTime));
        return bson;
    }

    @Override
    public Player load(BsonDocument src) {
        resetStates();
        uid = BsonUtil.longValue(src, BNAME_UID).orElseThrow();
        BsonUtil.documentValue(src, BNAME_PREFERENCES).ifPresentOrElse(preferences::load, preferences::clean);
        BsonUtil.documentValue(src, BNAME_BASIC).ifPresentOrElse(basic::load, basic::clean);
        BsonUtil.documentValue(src, BNAME_LOGIN).ifPresentOrElse(login::load, login::clean);
        BsonUtil.documentValue(src, BNAME_GUIDE).ifPresentOrElse(guide::load, guide::clean);
        BsonUtil.documentValue(src, BNAME_WALLET).ifPresentOrElse(wallet::load, wallet::clean);
        BsonUtil.documentValue(src, BNAME_ITEMS).ifPresentOrElse(items::load, items::clean);
        BsonUtil.documentValue(src, BNAME_STATISTICS).ifPresentOrElse(statistics::load, statistics::clean);
        BsonUtil.documentValue(src, BNAME_DAILY).ifPresentOrElse(daily::load, daily::clean);
        updateVersion = BsonUtil.intValue(src, BNAME_UPDATE_VERSION).orElseThrow();
        createTime = BsonUtil.dateTimeValue(src, BNAME_CREATE_TIME).orElseThrow();
        updateTime = BsonUtil.dateTimeValue(src, BNAME_UPDATE_TIME).orElseThrow();
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_UID, uid);
        jsonNode.set(BNAME_PREFERENCES, preferences.toJsonNode());
        jsonNode.set(BNAME_BASIC, basic.toJsonNode());
        jsonNode.set(BNAME_LOGIN, login.toJsonNode());
        jsonNode.set(BNAME_GUIDE, guide.toJsonNode());
        jsonNode.set(BNAME_WALLET, wallet.toJsonNode());
        jsonNode.set(BNAME_ITEMS, items.toJsonNode());
        jsonNode.set(BNAME_STATISTICS, statistics.toJsonNode());
        jsonNode.set(BNAME_DAILY, daily.toJsonNode());
        jsonNode.put(BNAME_UPDATE_VERSION, updateVersion);
        jsonNode.put(BNAME_CREATE_TIME, DateTimeUtil.toEpochMilli(createTime));
        jsonNode.put(BNAME_UPDATE_TIME, DateTimeUtil.toEpochMilli(updateTime));
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_UID, uid);
        jsonObject.put(BNAME_PREFERENCES, preferences.toFastjson2Node());
        jsonObject.put(BNAME_BASIC, basic.toFastjson2Node());
        jsonObject.put(BNAME_LOGIN, login.toFastjson2Node());
        jsonObject.put(BNAME_GUIDE, guide.toFastjson2Node());
        jsonObject.put(BNAME_WALLET, wallet.toFastjson2Node());
        jsonObject.put(BNAME_ITEMS, items.toFastjson2Node());
        jsonObject.put(BNAME_STATISTICS, statistics.toFastjson2Node());
        jsonObject.put(BNAME_DAILY, daily.toFastjson2Node());
        jsonObject.put(BNAME_UPDATE_VERSION, updateVersion);
        jsonObject.put(BNAME_CREATE_TIME, DateTimeUtil.toEpochMilli(createTime));
        jsonObject.put(BNAME_UPDATE_TIME, DateTimeUtil.toEpochMilli(updateTime));
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        data.put("uid", uid);
        data.put("preferences", preferences.toData());
        data.put("basic", basic.toData());
        data.put("login", login.toData());
        data.put("guide", guide.toData());
        data.put("wallet", wallet.toData());
        data.put("items", items.toData());
        data.put("statistics", statistics.toData());
        data.put("daily", daily.toData());
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
        if (changedFields.get(1) && preferences.anyUpdated()) {
            return true;
        }
        if (changedFields.get(2) && basic.anyUpdated()) {
            return true;
        }
        if (changedFields.get(3) && login.anyUpdated()) {
            return true;
        }
        if (changedFields.get(4) && guide.anyUpdated()) {
            return true;
        }
        if (changedFields.get(5) && wallet.anyUpdated()) {
            return true;
        }
        if (changedFields.get(6) && items.anyUpdated()) {
            return true;
        }
        if (changedFields.get(7) && statistics.anyUpdated()) {
            return true;
        }
        if (changedFields.get(8) && daily.anyUpdated()) {
            return true;
        }
        if (changedFields.get(9)) {
            return true;
        }
        if (changedFields.get(10)) {
            return true;
        }
        if (changedFields.get(11)) {
            return true;
        }
        return false;
    }

    @Override
    protected void resetChildren() {
        preferences.reset();
        basic.reset();
        login.reset();
        guide.reset();
        wallet.reset();
        items.reset();
        statistics.reset();
        daily.reset();
    }

    @Override
    protected int deletedSize() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var n = 0;
        if (changedFields.get(1) && preferences.anyDeleted()) {
            n++;
        }
        if (changedFields.get(2) && basic.anyDeleted()) {
            n++;
        }
        if (changedFields.get(3) && login.anyDeleted()) {
            n++;
        }
        if (changedFields.get(4) && guide.anyDeleted()) {
            n++;
        }
        if (changedFields.get(5) && wallet.anyDeleted()) {
            n++;
        }
        if (changedFields.get(6) && items.anyDeleted()) {
            n++;
        }
        if (changedFields.get(7) && statistics.anyDeleted()) {
            n++;
        }
        if (changedFields.get(8) && daily.anyDeleted()) {
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
        if (changedFields.get(1) && preferences.anyDeleted()) {
            return true;
        }
        if (changedFields.get(2) && basic.anyDeleted()) {
            return true;
        }
        if (changedFields.get(3) && login.anyDeleted()) {
            return true;
        }
        if (changedFields.get(4) && guide.anyDeleted()) {
            return true;
        }
        if (changedFields.get(5) && wallet.anyDeleted()) {
            return true;
        }
        if (changedFields.get(6) && items.anyDeleted()) {
            return true;
        }
        if (changedFields.get(7) && statistics.anyDeleted()) {
            return true;
        }
        if (changedFields.get(8) && daily.anyDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public Player clean() {
        uid = 0;
        preferences.clean();
        basic.clean();
        login.clean();
        guide.clean();
        wallet.clean();
        items.clean();
        statistics.clean();
        daily.clean();
        updateVersion = 0;
        createTime = null;
        updateTime = null;
        resetStates();
        return this;
    }

    @Override
    public Player deepCopy() {
        var copy = new Player();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(Player src) {
        uid = src.uid;
        src.preferences.deepCopyTo(preferences, false);
        src.basic.deepCopyTo(basic, false);
        src.login.deepCopyTo(login, false);
        src.guide.deepCopyTo(guide, false);
        src.wallet.deepCopyTo(wallet, false);
        src.items.deepCopyTo(items, false);
        src.statistics.deepCopyTo(statistics, false);
        src.daily.deepCopyTo(daily, false);
        updateVersion = src.updateVersion;
        createTime = src.createTime;
        updateTime = src.updateTime;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            updates.add(Updates.set(path().resolve(BNAME_UID).value(), uid));
        }
        if (changedFields.get(1)) {
            preferences.appendUpdates(updates);
        }
        if (changedFields.get(2)) {
            basic.appendUpdates(updates);
        }
        if (changedFields.get(3)) {
            login.appendUpdates(updates);
        }
        if (changedFields.get(4)) {
            guide.appendUpdates(updates);
        }
        if (changedFields.get(5)) {
            wallet.appendUpdates(updates);
        }
        if (changedFields.get(6)) {
            items.appendUpdates(updates);
        }
        if (changedFields.get(7)) {
            statistics.appendUpdates(updates);
        }
        if (changedFields.get(8)) {
            daily.appendUpdates(updates);
        }
        if (changedFields.get(9)) {
            updates.add(Updates.set(path().resolve(BNAME_UPDATE_VERSION).value(), updateVersion));
        }
        if (changedFields.get(10)) {
            updates.add(Updates.set(path().resolve(BNAME_CREATE_TIME).value(), BsonUtil.toBsonDateTime(createTime)));
        }
        if (changedFields.get(11)) {
            updates.add(Updates.set(path().resolve(BNAME_UPDATE_TIME).value(), BsonUtil.toBsonDateTime(updateTime)));
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        uid = BsonUtil.longValue(src, BNAME_UID).orElseThrow();
        BsonUtil.objectValue(src, BNAME_PREFERENCES).ifPresentOrElse(preferences::load, preferences::clean);
        BsonUtil.objectValue(src, BNAME_BASIC).ifPresentOrElse(basic::load, basic::clean);
        BsonUtil.objectValue(src, BNAME_LOGIN).ifPresentOrElse(login::load, login::clean);
        BsonUtil.objectValue(src, BNAME_GUIDE).ifPresentOrElse(guide::load, guide::clean);
        BsonUtil.objectValue(src, BNAME_WALLET).ifPresentOrElse(wallet::load, wallet::clean);
        BsonUtil.objectValue(src, BNAME_ITEMS).ifPresentOrElse(items::load, items::clean);
        BsonUtil.objectValue(src, BNAME_STATISTICS).ifPresentOrElse(statistics::load, statistics::clean);
        BsonUtil.objectValue(src, BNAME_DAILY).ifPresentOrElse(daily::load, daily::clean);
        updateVersion = BsonUtil.intValue(src, BNAME_UPDATE_VERSION).orElseThrow();
        createTime = BsonUtil.dateTimeValue(src, BNAME_CREATE_TIME).orElseThrow();
        updateTime = BsonUtil.dateTimeValue(src, BNAME_UPDATE_TIME).orElseThrow();
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
        uid = BsonUtil.longValue(src, BNAME_UID).orElseThrow();
        BsonUtil.objectValue(src, BNAME_PREFERENCES).ifPresentOrElse(preferences::loadFastjson2Node, preferences::clean);
        BsonUtil.objectValue(src, BNAME_BASIC).ifPresentOrElse(basic::loadFastjson2Node, basic::clean);
        BsonUtil.objectValue(src, BNAME_LOGIN).ifPresentOrElse(login::loadFastjson2Node, login::clean);
        BsonUtil.objectValue(src, BNAME_GUIDE).ifPresentOrElse(guide::loadFastjson2Node, guide::clean);
        BsonUtil.objectValue(src, BNAME_WALLET).ifPresentOrElse(wallet::loadFastjson2Node, wallet::clean);
        BsonUtil.objectValue(src, BNAME_ITEMS).ifPresentOrElse(items::loadFastjson2Node, items::clean);
        BsonUtil.objectValue(src, BNAME_STATISTICS).ifPresentOrElse(statistics::loadFastjson2Node, statistics::clean);
        BsonUtil.objectValue(src, BNAME_DAILY).ifPresentOrElse(daily::loadFastjson2Node, daily::clean);
        updateVersion = BsonUtil.intValue(src, BNAME_UPDATE_VERSION).orElseThrow();
        createTime = BsonUtil.dateTimeValue(src, BNAME_CREATE_TIME).orElseThrow();
        updateTime = BsonUtil.dateTimeValue(src, BNAME_UPDATE_TIME).orElseThrow();
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            data.put("uid", uid);
        }
        if (changedFields.get(1)) {
            var preferencesUpdateData = preferences.toUpdateData();
            if (preferencesUpdateData != null) {
                data.put("preferences", preferencesUpdateData);
            }
        }
        if (changedFields.get(2)) {
            var basicUpdateData = basic.toUpdateData();
            if (basicUpdateData != null) {
                data.put("basic", basicUpdateData);
            }
        }
        if (changedFields.get(3)) {
            var loginUpdateData = login.toUpdateData();
            if (loginUpdateData != null) {
                data.put("login", loginUpdateData);
            }
        }
        if (changedFields.get(4)) {
            var guideUpdateData = guide.toUpdateData();
            if (guideUpdateData != null) {
                data.put("guide", guideUpdateData);
            }
        }
        if (changedFields.get(5)) {
            var walletUpdateData = wallet.toUpdateData();
            if (walletUpdateData != null) {
                data.put("wallet", walletUpdateData);
            }
        }
        if (changedFields.get(6)) {
            var itemsUpdateData = items.toUpdateData();
            if (itemsUpdateData != null) {
                data.put("items", itemsUpdateData);
            }
        }
        if (changedFields.get(7)) {
            var statisticsUpdateData = statistics.toUpdateData();
            if (statisticsUpdateData != null) {
                data.put("statistics", statisticsUpdateData);
            }
        }
        if (changedFields.get(8)) {
            var dailyUpdateData = daily.toUpdateData();
            if (dailyUpdateData != null) {
                data.put("daily", dailyUpdateData);
            }
        }
    }

    @Override
    protected void appendDeletedData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(1)) {
            var preferencesDeletedData = preferences.toDeletedData();
            if (preferencesDeletedData != null) {
                data.put("preferences", preferencesDeletedData);
            }
        }
        if (changedFields.get(2)) {
            var basicDeletedData = basic.toDeletedData();
            if (basicDeletedData != null) {
                data.put("basic", basicDeletedData);
            }
        }
        if (changedFields.get(3)) {
            var loginDeletedData = login.toDeletedData();
            if (loginDeletedData != null) {
                data.put("login", loginDeletedData);
            }
        }
        if (changedFields.get(4)) {
            var guideDeletedData = guide.toDeletedData();
            if (guideDeletedData != null) {
                data.put("guide", guideDeletedData);
            }
        }
        if (changedFields.get(5)) {
            var walletDeletedData = wallet.toDeletedData();
            if (walletDeletedData != null) {
                data.put("wallet", walletDeletedData);
            }
        }
        if (changedFields.get(6)) {
            var itemsDeletedData = items.toDeletedData();
            if (itemsDeletedData != null) {
                data.put("items", itemsDeletedData);
            }
        }
        if (changedFields.get(7)) {
            var statisticsDeletedData = statistics.toDeletedData();
            if (statisticsDeletedData != null) {
                data.put("statistics", statisticsDeletedData);
            }
        }
        if (changedFields.get(8)) {
            var dailyDeletedData = daily.toDeletedData();
            if (dailyDeletedData != null) {
                data.put("daily", dailyDeletedData);
            }
        }
    }

    @Override
    public String toString() {
        return "Player(" + "uid=" + uid +
                ", preferences=" + preferences +
                ", basic=" + basic +
                ", login=" + login +
                ", guide=" + guide +
                ", wallet=" + wallet +
                ", items=" + items +
                ", statistics=" + statistics +
                ", daily=" + daily +
                ", updateVersion=" + updateVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ")";
    }

}
