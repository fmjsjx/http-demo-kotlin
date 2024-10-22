package com.github.fmjsjx.demo.http.core.entity.model;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fmjsjx.bson.model.core.BsonUtil;
import com.github.fmjsjx.bson.model2.core.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;

import java.util.*;

public class StatisticsInfo extends ObjectModel<StatisticsInfo> {

    public static final String BNAME_VIDEO_COUNT = "vct";
    public static final String BNAME_VIDEO_COUNTS = "vcs";
    public static final String BNAME_GAMING_COUNT = "gct";

    private int videoCount;
    private final SingleValueMapModel<Integer, Integer> videoCounts = SingleValueMapModel.integerKeysMap(SingleValueTypes.INTEGER).parent(this).key(BNAME_VIDEO_COUNTS).index(1);
    private int gamingCount;

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        if (videoCount != this.videoCount) {
            this.videoCount = videoCount;
            fieldChanged(0);
        }
    }

    public int increaseVideoCount() {
        fieldChanged(0);
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
            fieldChanged(2);
        }
    }

    public int increaseGamingCount() {
        fieldChanged(2);
        return ++gamingCount;
    }

    public boolean videoCountChanged() {
        return changedFields.get(0);
    }

    public boolean videoCountsChanged() {
        return changedFields.get(1);
    }

    public boolean gamingCountChanged() {
        return changedFields.get(2);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_VIDEO_COUNT, new BsonInt32(videoCount));
        bson.append(BNAME_VIDEO_COUNTS, videoCounts.toBson());
        bson.append(BNAME_GAMING_COUNT, new BsonInt32(gamingCount));
        return bson;
    }

    @Override
    public StatisticsInfo load(BsonDocument src) {
        resetStates();
        videoCount = BsonUtil.intValue(src, BNAME_VIDEO_COUNT).orElseThrow();
        BsonUtil.documentValue(src, BNAME_VIDEO_COUNTS).ifPresentOrElse(videoCounts::load, videoCounts::clean);
        gamingCount = BsonUtil.intValue(src, BNAME_GAMING_COUNT).orElseThrow();
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_VIDEO_COUNT, videoCount);
        jsonNode.set(BNAME_VIDEO_COUNTS, videoCounts.toJsonNode());
        jsonNode.put(BNAME_GAMING_COUNT, gamingCount);
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_VIDEO_COUNT, videoCount);
        jsonObject.put(BNAME_VIDEO_COUNTS, videoCounts.toFastjson2Node());
        jsonObject.put(BNAME_GAMING_COUNT, gamingCount);
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
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
        if (changedFields.get(1) && videoCounts.anyUpdated()) {
            return true;
        }
        if (changedFields.get(2)) {
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
        if (changedFields.get(1) && videoCounts.anyDeleted()) {
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
        if (changedFields.get(1) && videoCounts.anyDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public StatisticsInfo clean() {
        videoCount = 0;
        videoCounts.clean();
        gamingCount = 0;
        resetStates();
        return this;
    }

    @Override
    public StatisticsInfo deepCopy() {
        var copy = new StatisticsInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(StatisticsInfo src) {
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
            updates.add(Updates.set(path().resolve(BNAME_VIDEO_COUNT).value(), videoCount));
        }
        if (changedFields.get(1)) {
            videoCounts.appendUpdates(updates);
        }
        if (changedFields.get(2)) {
            updates.add(Updates.set(path().resolve(BNAME_GAMING_COUNT).value(), gamingCount));
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        videoCount = BsonUtil.intValue(src, BNAME_VIDEO_COUNT).orElseThrow();
        BsonUtil.objectValue(src, BNAME_VIDEO_COUNTS).ifPresentOrElse(videoCounts::load, videoCounts::clean);
        gamingCount = BsonUtil.intValue(src, BNAME_GAMING_COUNT).orElseThrow();
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
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
        if (changedFields.get(0)) {
            data.put("videoCount", videoCount);
        }
        if (changedFields.get(2)) {
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
        return "StatisticsInfo(" + "videoCount=" + videoCount +
                ", videoCounts=" + videoCounts +
                ", gamingCount=" + gamingCount +
                ")";
    }

}
