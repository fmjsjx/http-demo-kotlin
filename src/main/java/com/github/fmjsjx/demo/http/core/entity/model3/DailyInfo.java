package com.github.fmjsjx.demo.http.core.entity.model3;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.*;
import com.github.fmjsjx.bson.model3.core.util.*;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;
import org.jspecify.annotations.*;

import java.time.LocalDate;
import java.util.*;

@NullMarked
public final class DailyInfo extends AbstractObjectModel<DailyInfo> {

    public static final String STORE_NAME_DAY = "day";
    public static final String STORE_NAME_COIN = "cn";
    public static final String STORE_NAME_DIAMOND = "dm";
    public static final String STORE_NAME_VIDEO_COUNT = "vdc";
    public static final String STORE_NAME_VIDEO_COUNTS = "vdcs";
    public static final String STORE_NAME_GAMING_COUNT = "gct";

    public static final String DISPLAY_NAME_COIN = "coin";
    public static final String DISPLAY_NAME_DIAMOND = "diamond";
    public static final String DISPLAY_NAME_VIDEO_COUNT = "videoCount";
    public static final String DISPLAY_NAME_GAMING_COUNT = "gamingCount";

    public static final int FIELD_INDEX_DAY = 0;
    public static final int FIELD_INDEX_COIN = 1;
    public static final int FIELD_INDEX_DIAMOND = 2;
    public static final int FIELD_INDEX_VIDEO_COUNT = 3;
    public static final int FIELD_INDEX_VIDEO_COUNTS = 4;
    public static final int FIELD_INDEX_GAMING_COUNT = 5;

    @JSONType(alphabetic = false)
    public static final class DailyInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DAY)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DAY)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DAY)
        private int day;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COIN)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COIN)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COIN)
        private int coin;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DIAMOND)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DIAMOND)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DIAMOND)
        private int diamond;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_VIDEO_COUNT)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_VIDEO_COUNT)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_VIDEO_COUNT)
        private int videoCount;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_VIDEO_COUNTS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_VIDEO_COUNTS)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_VIDEO_COUNTS, implementation = LinkedHashMap.class)
        private Map<String, Integer> videoCounts;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_GAMING_COUNT)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_GAMING_COUNT)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_GAMING_COUNT)
        private int gamingCount;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public Map<String, Integer> getVideoCounts() {
            return videoCounts;
        }

        public void setVideoCounts(Map<String, Integer> videoCounts) {
            this.videoCounts = videoCounts;
        }

        public int getGamingCount() {
            return gamingCount;
        }

        public void setGamingCount(int gamingCount) {
            this.gamingCount = gamingCount;
        }
    }

    private LocalDate day = LocalDate.EPOCH;
    private int coin;
    private int diamond;
    private int videoCount;
    private final SingleValueMapModel<Integer, Integer> videoCounts = SingleValueMapModel.integerKeysMap(SingleValues.integer())
            .parent(this).index(FIELD_INDEX_VIDEO_COUNTS).key(STORE_NAME_VIDEO_COUNTS);
    private int gamingCount;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        if (!day.equals(this.day)) {
            this.day = day;
            triggerChange(FIELD_INDEX_DAY);
        }
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        if (coin != this.coin) {
            this.coin = coin;
            triggerChange(FIELD_INDEX_COIN);
        }
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        if (diamond != this.diamond) {
            this.diamond = diamond;
            triggerChange(FIELD_INDEX_DIAMOND);
        }
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        if (videoCount != this.videoCount) {
            this.videoCount = videoCount;
            triggerChange(FIELD_INDEX_VIDEO_COUNT);
        }
    }

    public int increaseVideoCount() {
        triggerChange(FIELD_INDEX_VIDEO_COUNT);
        return ++videoCount;
    }

    public SingleValueMapModel<Integer, Integer> getVideoCounts() {
        return videoCounts;
    }

    public int getGamingCount() {
        return gamingCount;
    }

    public void setGamingCount(int gamingCount) {
        if (gamingCount != this.gamingCount) {
            this.gamingCount = gamingCount;
            triggerChange(FIELD_INDEX_GAMING_COUNT);
        }
    }

    public int increaseGamingCount() {
        triggerChange(FIELD_INDEX_GAMING_COUNT);
        return ++gamingCount;
    }

    @Override
    protected DailyInfo resetChildren() {
        getVideoCounts().reset();
        return this;
    }

    @Override
    protected DailyInfo cleanFields() {
        day = LocalDate.EPOCH;
        coin = 0;
        diamond = 0;
        videoCount = 0;
        getVideoCounts().clean();
        gamingCount = 0;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_DAY)) {
            updates.add(Updates.set(path().path(STORE_NAME_DAY), BsonValueUtil.toBsonInt32(getDay())));
        }
        if (changedFields.get(FIELD_INDEX_COIN)) {
            updates.add(Updates.set(path().path(STORE_NAME_COIN), new BsonInt32(getCoin())));
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            updates.add(Updates.set(path().path(STORE_NAME_DIAMOND), new BsonInt32(getDiamond())));
        }
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNT)) {
            updates.add(Updates.set(path().path(STORE_NAME_VIDEO_COUNT), new BsonInt32(getVideoCount())));
        }
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNTS)) {
            getVideoCounts().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_GAMING_COUNT)) {
            updates.add(Updates.set(path().path(STORE_NAME_GAMING_COUNT), new BsonInt32(getGamingCount())));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COIN)) {
            data.put(DISPLAY_NAME_COIN, getCoin());
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            data.put(DISPLAY_NAME_DIAMOND, getDiamond());
        }
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNT)) {
            data.put(DISPLAY_NAME_VIDEO_COUNT, getVideoCount());
        }
        if (changedFields.get(FIELD_INDEX_GAMING_COUNT)) {
            data.put(DISPLAY_NAME_GAMING_COUNT, getGamingCount());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_COIN, getCoin());
        _displayData.put(DISPLAY_NAME_DIAMOND, getDiamond());
        _displayData.put(DISPLAY_NAME_VIDEO_COUNT, getVideoCount());
        _displayData.put(DISPLAY_NAME_GAMING_COUNT, getGamingCount());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_DAY, BsonValueUtil.toBsonInt32(getDay()));
        _bsonValue.put(STORE_NAME_COIN, new BsonInt32(getCoin()));
        _bsonValue.put(STORE_NAME_DIAMOND, new BsonInt32(getDiamond()));
        _bsonValue.put(STORE_NAME_VIDEO_COUNT, new BsonInt32(getVideoCount()));
        _bsonValue.put(STORE_NAME_VIDEO_COUNTS, getVideoCounts().toBsonValue());
        _bsonValue.put(STORE_NAME_GAMING_COUNT, new BsonInt32(getGamingCount()));
        return _bsonValue;
    }

    @Override
    public DailyInfo load(BsonDocument src) {
        resetStates();
        day = BsonUtil.dateValue(src, STORE_NAME_DAY).orElse(LocalDate.EPOCH);
        coin = BsonUtil.intValue(src, STORE_NAME_COIN).orElse(0);
        diamond = BsonUtil.intValue(src, STORE_NAME_DIAMOND).orElse(0);
        videoCount = BsonUtil.intValue(src, STORE_NAME_VIDEO_COUNT).orElse(0);
        BsonUtil.documentValue(src, STORE_NAME_VIDEO_COUNTS).ifPresentOrElse(getVideoCounts()::load, getVideoCounts()::clean);
        gamingCount = BsonUtil.intValue(src, STORE_NAME_GAMING_COUNT).orElse(0);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DailyInfoStoreData toStoreData() {
        var _storeData = new DailyInfoStoreData();
        _storeData.day = DateTimeUtil.toNumber(getDay());
        _storeData.coin = getCoin();
        _storeData.diamond = getDiamond();
        _storeData.videoCount = getVideoCount();
        _storeData.videoCounts = (Map<String, Integer>) getVideoCounts().toStoreData();
        _storeData.gamingCount = getGamingCount();
        return _storeData;
    }

    @Override
    public DailyInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof DailyInfoStoreData _storeData) {
            day = DateTimeUtil.toDate(_storeData.day);
            coin = _storeData.coin;
            diamond = _storeData.diamond;
            videoCount = _storeData.videoCount;
            getVideoCounts().loadStoreData(_storeData.videoCounts);
            gamingCount = _storeData.gamingCount;
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
        if (changedFields.get(FIELD_INDEX_DAY)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_COIN)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNT)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNTS) && getVideoCounts().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_GAMING_COUNT)) {
            return true;
        }
        return false;
    }

    @Override
    public @Nullable Map<String, ?> toDeleted() {
        return null;
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
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNTS) && getVideoCounts().anyDeleted()) {
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
        if (changedFields.get(FIELD_INDEX_VIDEO_COUNTS)) {
            __size += getVideoCounts().deletedSize();
        }
        return __size;
    }

    @Override
    public DailyInfo deepCopy() {
        return new DailyInfo().deepCopyFrom(this);
    }

    @Override
    public DailyInfo deepCopyFrom(DailyInfo src) {
        day = src.getDay();
        coin = src.getCoin();
        diamond = src.getDiamond();
        videoCount = src.getVideoCount();
        getVideoCounts().deepCopyFrom(src.getVideoCounts());
        gamingCount = src.getGamingCount();
        return this;
    }

    @Override
    public String toString() {
        return "DailyInfo(day=" + getDay() +
                ", coin=" + getCoin() +
                ", diamond=" + getDiamond() +
                ", videoCount=" + getVideoCount() +
                ", videoCounts=" + getVideoCounts() +
                ", gamingCount=" + getGamingCount() +
                ")";
    }

}
