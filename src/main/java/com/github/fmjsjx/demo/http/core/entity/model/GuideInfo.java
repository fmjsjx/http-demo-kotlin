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

public class GuideInfo extends ObjectModel<GuideInfo> {

    public static final String BNAME_STATUS = "s";

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status != this.status) {
            this.status = status;
            fieldChanged(0);
        }
    }

    public boolean statusChanged() {
        return changedFields.get(0);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_STATUS, new BsonInt32(status));
        return bson;
    }

    @Override
    public GuideInfo load(BsonDocument src) {
        resetStates();
        status = BsonUtil.intValue(src, BNAME_STATUS).orElseThrow();
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_STATUS, status);
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_STATUS, status);
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        data.put("status", status);
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
        return false;
    }

    @Override
    protected void resetChildren() {
    }

    @Override
    protected int deletedSize() {
        return 0;
    }

    @Override
    public boolean anyDeleted() {
        return false;
    }

    @Override
    public GuideInfo clean() {
        status = 0;
        resetStates();
        return this;
    }

    @Override
    public GuideInfo deepCopy() {
        var copy = new GuideInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(GuideInfo src) {
        status = src.status;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            updates.add(Updates.set(path().resolve(BNAME_STATUS).value(), status));
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        status = BsonUtil.intValue(src, BNAME_STATUS).orElseThrow();
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
        status = BsonUtil.intValue(src, BNAME_STATUS).orElseThrow();
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            data.put("status", status);
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
        return "GuideInfo(" + "status=" + status +
                ")";
    }

}
