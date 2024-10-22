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

public class WalletInfo extends ObjectModel<WalletInfo> {

    public static final String BNAME_COIN_TOTAL = "ct";
    public static final String BNAME_COIN_USED = "cu";
    public static final String BNAME_DIAMOND = "b";

    private int coinTotal;
    private int coinUsed;
    private int diamond;

    public int getCoinTotal() {
        return coinTotal;
    }

    public void setCoinTotal(int coinTotal) {
        if (coinTotal != this.coinTotal) {
            this.coinTotal = coinTotal;
            fieldsChanged(0, 2);
        }
    }

    public int getCoinUsed() {
        return coinUsed;
    }

    public void setCoinUsed(int coinUsed) {
        if (coinUsed != this.coinUsed) {
            this.coinUsed = coinUsed;
            fieldsChanged(1, 2);
        }
    }

    public int getCoin() {
        return coinTotal - coinUsed;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        if (diamond != this.diamond) {
            this.diamond = diamond;
            fieldChanged(3);
        }
    }

    public boolean coinTotalChanged() {
        return changedFields.get(0);
    }

    public boolean coinUsedChanged() {
        return changedFields.get(1);
    }

    public boolean coinChanged() {
        return changedFields.get(2);
    }

    public boolean diamondChanged() {
        return changedFields.get(3);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        bson.append(BNAME_COIN_TOTAL, new BsonInt32(coinTotal));
        bson.append(BNAME_COIN_USED, new BsonInt32(coinUsed));
        bson.append(BNAME_DIAMOND, new BsonInt32(diamond));
        return bson;
    }

    @Override
    public WalletInfo load(BsonDocument src) {
        resetStates();
        coinTotal = BsonUtil.intValue(src, BNAME_COIN_TOTAL).orElseThrow();
        coinUsed = BsonUtil.intValue(src, BNAME_COIN_USED).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put(BNAME_COIN_TOTAL, coinTotal);
        jsonNode.put(BNAME_COIN_USED, coinUsed);
        jsonNode.put(BNAME_DIAMOND, diamond);
        return jsonNode;
    }

    @Override
    public JSONObject toFastjson2Node() {
        var jsonObject = new JSONObject();
        jsonObject.put(BNAME_COIN_TOTAL, coinTotal);
        jsonObject.put(BNAME_COIN_USED, coinUsed);
        jsonObject.put(BNAME_DIAMOND, diamond);
        return jsonObject;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        data.put("coinTotal", coinTotal);
        data.put("coin", getCoin());
        data.put("diamond", diamond);
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
        if (changedFields.get(3)) {
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
    public WalletInfo clean() {
        coinTotal = 0;
        coinUsed = 0;
        diamond = 0;
        resetStates();
        return this;
    }

    @Override
    public WalletInfo deepCopy() {
        var copy = new WalletInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(WalletInfo src) {
        coinTotal = src.coinTotal;
        coinUsed = src.coinUsed;
        diamond = src.diamond;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            updates.add(Updates.set(path().resolve(BNAME_COIN_TOTAL).value(), coinTotal));
        }
        if (changedFields.get(1)) {
            updates.add(Updates.set(path().resolve(BNAME_COIN_USED).value(), coinUsed));
        }
        if (changedFields.get(3)) {
            updates.add(Updates.set(path().resolve(BNAME_DIAMOND).value(), diamond));
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        coinTotal = BsonUtil.intValue(src, BNAME_COIN_TOTAL).orElseThrow();
        coinUsed = BsonUtil.intValue(src, BNAME_COIN_USED).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
    }

    @Override
    protected void loadJSONObject(JSONObject src) {
        resetStates();
        coinTotal = BsonUtil.intValue(src, BNAME_COIN_TOTAL).orElseThrow();
        coinUsed = BsonUtil.intValue(src, BNAME_COIN_USED).orElseThrow();
        diamond = BsonUtil.intValue(src, BNAME_DIAMOND).orElseThrow();
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            data.put("coinTotal", coinTotal);
        }
        if (changedFields.get(2)) {
            data.put("coin", getCoin());
        }
        if (changedFields.get(3)) {
            data.put("diamond", diamond);
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
        return "WalletInfo(" + "coinTotal=" + coinTotal +
                ", coinUsed=" + coinUsed +
                ", diamond=" + diamond +
                ")";
    }

}
