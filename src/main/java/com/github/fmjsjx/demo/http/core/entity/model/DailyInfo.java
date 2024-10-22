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

import java.time.LocalDate;
import java.util.*;

public class DailyInfo extends ObjectModel<DailyInfo> {

    public static final String BNAME_DAY = "day";
    public static final String BNAME_COIN = "cn";
    public static final String BNAME_DIAMOND = "dm";
    public static final String BNAME_VIDEO_COUNT = "vdc";
    public static final String BNAME_VIDEO_COUNTS = "vdcs";
    public static final String BNAME_GAMING_COUNT = "gct";

    private LocalDate day;
    private int coin;
    private int diamond;
    private int videoCount;
    private final SingleValueMapModel<Integer, Integer> videoCounts = SingleValueMapModel.integerKeysMap(SingleValueTypes.INTEGER).parent(this).key(BNAME_VIDEO_COUNTS).index(4);
    private int gamingCount;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        Objects.requireNonNull(day, "day must not be null");
        if (!day.equals(this.day)) {
            this.day = day;
            fieldChanged(0);
        }
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        if (coin != this.coin) {
            this.coin = coin;
            fieldChanged(1);
        }
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        if (diamond != this.diamond) {
            this.diamond = diamond;
            fieldChanged(2);
        }
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        if (videoCount != this.videoCount) {
            this.videoCount = videoCount;
            fieldChanged(3);
        }
    }

    public int increaseVideoCount() {
        fieldChanged(3);
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
            fieldChanged(5);
        }
    }

    public int increaseGamingCount() {
        fieldChanged(5);
        return ++gamingCount;
    }

    public boolean dayChanged() {
        return changedFields.get(0);
    }

    public boolean coinChanged() {
        return changedFields.get(1);
    }

    public boolean diamondChanged() {
        return changedFields.get(2);
    }

    public boolean videoCountChanged() {
        return changedFields.get(3);
    }

    public boolean videoCountsChanged() {
        return changedFields.get(4);
    }

    public boolean gamingCountChanged() {
        return changedFields.get(5);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_DAY, new BsonInt32(DateTimeUtil.toNumber(day)));
        bson.append(BNAME_COIN, new BsonInt32(coin));
        bson.append(BNAME_DIAMOND, new BsonInt32(diamond));
        bson.append(BNAME_VIDEO_COUNT, new BsonInt32(videoCount));
        bson.append(BNAME_VIDEO_COUNTS, videoCounts.toBson());
        bson.append(BNAME_GAMING_COUNT, new BsonInt32(gamingCount));
        return bson;
    }

    @Override
    public DailyInfo load(BsonDocument src) {
        resetStates();
        day = BsonUtil.intValue(src, BNAME_DAY).stream().mapToObj(DateTimeUtil::toDate).findFirst().orElseThrow();
        coin = BsonUtil.intValue(src, BNAME_COIN).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
        videoCount = BsonUtil.intValue(src, BNAME_VIDEO_COUNT).orElseThrow();
        BsonUtil.documentValue(src, BNAME_VIDEO_COUNTS).ifPresentOrElse(videoCounts::load, videoCounts::clean);
        gamingCount = BsonUtil.intValue(src, BNAME_GAMING_COUNT).orElseThrow();
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_DAY, DateTimeUtil.toNumber(day));
        jsonNode.put(BNAME_COIN, coin);
        jsonNode.put(BNAME_DIAMOND, diamond);
        jsonNode.put(BNAME_VIDEO_COUNT, videoCount);
        jsonNode.set(BNAME_VIDEO_COUNTS, videoCounts.toJsonNode());
        jsonNode.put(BNAME_GAMING_COUNT, gamingCount);
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_DAY, DateTimeUtil.toNumber(day));
        jsonObject.put(BNAME_COIN, coin);
        jsonObject.put(BNAME_DIAMOND, diamond);
        jsonObject.put(BNAME_VIDEO_COUNT, videoCount);
        jsonObject.put(BNAME_VIDEO_COUNTS, videoCounts.toFastjson2Node());
        jsonObject.put(BNAME_GAMING_COUNT, gamingCount);
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        data.put("coin", coin);
        data.put("diamond", diamond);
        data.put("videoCount", videoCount);
        data.put("gamingCount", gamingCount);
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
        if (changedFields.get(4) && videoCounts.anyUpdated()) {
            return true;
        }
        if (changedFields.get(5)) {
            return true;
        }
        return false;
    }

    @Override
    protected void resetChildren() {
        videoCounts.reset();
    }

    @Override
    protected int deletedSize() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var n = 0;
        if (changedFields.get(4) && videoCounts.anyDeleted()) {
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
        if (changedFields.get(4) && videoCounts.anyDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public DailyInfo clean() {
        day = null;
        coin = 0;
        diamond = 0;
        videoCount = 0;
        videoCounts.clean();
        gamingCount = 0;
        resetStates();
        return this;
    }

    @Override
    public DailyInfo deepCopy() {
        var copy = new DailyInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(DailyInfo src) {
        day = src.day;
        coin = src.coin;
        diamond = src.diamond;
        videoCount = src.videoCount;
        src.videoCounts.deepCopyTo(videoCounts, false);
        gamingCount = src.gamingCount;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            updates.add(Updates.set(path().resolve(BNAME_DAY).value(), DateTimeUtil.toNumber(day)));
        }
        if (changedFields.get(1)) {
            updates.add(Updates.set(path().resolve(BNAME_COIN).value(), coin));
        }
        if (changedFields.get(2)) {
            updates.add(Updates.set(path().resolve(BNAME_DIAMOND).value(), diamond));
        }
        if (changedFields.get(3)) {
            updates.add(Updates.set(path().resolve(BNAME_VIDEO_COUNT).value(), videoCount));
        }
        if (changedFields.get(4)) {
            videoCounts.appendUpdates(updates);
        }
        if (changedFields.get(5)) {
            updates.add(Updates.set(path().resolve(BNAME_GAMING_COUNT).value(), gamingCount));
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        day = BsonUtil.intValue(src, BNAME_DAY).stream().mapToObj(DateTimeUtil::toDate).findFirst().orElseThrow();
        coin = BsonUtil.intValue(src, BNAME_COIN).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
        videoCount = BsonUtil.intValue(src, BNAME_VIDEO_COUNT).orElseThrow();
        BsonUtil.objectValue(src, BNAME_VIDEO_COUNTS).ifPresentOrElse(videoCounts::load, videoCounts::clean);
        gamingCount = BsonUtil.intValue(src, BNAME_GAMING_COUNT).orElseThrow();
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
        day = BsonUtil.intValue(src, BNAME_DAY).stream().mapToObj(DateTimeUtil::toDate).findFirst().orElseThrow();
        coin = BsonUtil.intValue(src, BNAME_COIN).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
        videoCount = BsonUtil.intValue(src, BNAME_VIDEO_COUNT).orElseThrow();
        BsonUtil.objectValue(src, BNAME_VIDEO_COUNTS).ifPresentOrElse(videoCounts::loadFastjson2Node, videoCounts::clean);
        gamingCount = BsonUtil.intValue(src, BNAME_GAMING_COUNT).orElseThrow();
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(1)) {
            data.put("coin", coin);
        }
        if (changedFields.get(2)) {
            data.put("diamond", diamond);
        }
        if (changedFields.get(3)) {
            data.put("videoCount", videoCount);
        }
        if (changedFields.get(5)) {
            data.put("gamingCount", gamingCount);
        }
    }

    @Override
    public Map<Object, Object> toDeletedData() {
        return null;
    }

    @Override
    protected void appendDeletedData(Map<Object, Object> data) {
    }

    @Override
    public String toString() {
        return "DailyInfo(" + "day=" + day +
                ", coin=" + coin +
                ", diamond=" + diamond +
                ", videoCount=" + videoCount +
                ", videoCounts=" + videoCounts +
                ", gamingCount=" + gamingCount +
                ")";
    }

}
