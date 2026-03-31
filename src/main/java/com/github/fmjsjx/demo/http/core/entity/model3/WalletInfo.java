package com.github.fmjsjx.demo.http.core.entity.model3;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.*;
import com.github.fmjsjx.bson.model3.core.util.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;
import org.jspecify.annotations.*;

import java.util.*;

@NullMarked
public final class WalletInfo extends AbstractObjectModel<WalletInfo> {

    public static final String STORE_NAME_COIN_TOTAL = "ct";
    public static final String STORE_NAME_COIN_USED = "cu";
    public static final String STORE_NAME_DIAMOND = "b";

    public static final String DISPLAY_NAME_COIN_TOTAL = "coinTotal";
    public static final String DISPLAY_NAME_COIN = "coin";
    public static final String DISPLAY_NAME_DIAMOND = "diamond";

    public static final int FIELD_INDEX_COIN_TOTAL = 0;
    public static final int FIELD_INDEX_COIN_USED = 1;
    public static final int FIELD_INDEX_COIN = 2;
    public static final int FIELD_INDEX_DIAMOND = 3;

    @JSONType(alphabetic = false)
    public static final class WalletInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COIN_TOTAL)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COIN_TOTAL)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COIN_TOTAL)
        private int coinTotal;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COIN_USED)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COIN_USED)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COIN_USED)
        private int coinUsed;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DIAMOND)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DIAMOND)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DIAMOND)
        private int diamond;

        public int getCoinTotal() {
            return coinTotal;
        }

        public void setCoinTotal(int coinTotal) {
            this.coinTotal = coinTotal;
        }

        public int getCoinUsed() {
            return coinUsed;
        }

        public void setCoinUsed(int coinUsed) {
            this.coinUsed = coinUsed;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }
    }

    private int coinTotal;
    private int coinUsed;
    private int diamond;

    public int getCoinTotal() {
        return coinTotal;
    }

    public void setCoinTotal(int coinTotal) {
        if (coinTotal != this.coinTotal) {
            this.coinTotal = coinTotal;
            fieldsChanged(FIELD_INDEX_COIN_TOTAL, FIELD_INDEX_COIN);
        }
    }

    public int getCoinUsed() {
        return coinUsed;
    }

    public void setCoinUsed(int coinUsed) {
        if (coinUsed != this.coinUsed) {
            this.coinUsed = coinUsed;
            fieldsChanged(FIELD_INDEX_COIN_USED, FIELD_INDEX_COIN);
        }
    }

    public int getCoin() {
        return getCoinTotal() - getCoinUsed();
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

    @Override
    protected WalletInfo cleanFields() {
        coinTotal = 0;
        coinUsed = 0;
        diamond = 0;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            updates.add(Updates.set(path().path(STORE_NAME_COIN_TOTAL), new BsonInt32(getCoinTotal())));
        }
        if (changedFields.get(FIELD_INDEX_COIN_USED)) {
            updates.add(Updates.set(path().path(STORE_NAME_COIN_USED), new BsonInt32(getCoinUsed())));
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            updates.add(Updates.set(path().path(STORE_NAME_DIAMOND), new BsonInt32(getDiamond())));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            data.put(DISPLAY_NAME_COIN_TOTAL, getCoinTotal());
        }
        if (changedFields.get(FIELD_INDEX_COIN)) {
            data.put(DISPLAY_NAME_COIN, getCoin());
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            data.put(DISPLAY_NAME_DIAMOND, getDiamond());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_COIN_TOTAL, getCoinTotal());
        _displayData.put(DISPLAY_NAME_COIN, getCoin());
        _displayData.put(DISPLAY_NAME_DIAMOND, getDiamond());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_COIN_TOTAL, new BsonInt32(getCoinTotal()));
        _bsonValue.put(STORE_NAME_COIN_USED, new BsonInt32(getCoinUsed()));
        _bsonValue.put(STORE_NAME_DIAMOND, new BsonInt32(getDiamond()));
        return _bsonValue;
    }

    @Override
    public WalletInfo load(BsonDocument src) {
        resetStates();
        coinTotal = BsonUtil.intValue(src, STORE_NAME_COIN_TOTAL).orElse(0);
        coinUsed = BsonUtil.intValue(src, STORE_NAME_COIN_USED).orElse(0);
        diamond = BsonUtil.intValue(src, STORE_NAME_DIAMOND).orElse(0);
        return this;
    }

    @Override
    public WalletInfoStoreData toStoreData() {
        var _storeData = new WalletInfoStoreData();
        _storeData.coinTotal = getCoinTotal();
        _storeData.coinUsed = getCoinUsed();
        _storeData.diamond = getDiamond();
        return _storeData;
    }

    @Override
    public WalletInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof WalletInfoStoreData _storeData) {
            coinTotal = _storeData.coinTotal;
            coinUsed = _storeData.coinUsed;
            diamond = _storeData.diamond;
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
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_COIN_USED)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
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
        return false;
    }

    @Override
    public int deletedSize() {
        return 0;
    }

    @Override
    public WalletInfo deepCopy() {
        return new WalletInfo().deepCopyFrom(this);
    }

    @Override
    public WalletInfo deepCopyFrom(WalletInfo src) {
        coinTotal = src.getCoinTotal();
        coinUsed = src.getCoinUsed();
        diamond = src.getDiamond();
        return this;
    }

    @Override
    public String toString() {
        return "WalletInfo(coinTotal=" + getCoinTotal() +
                ", coinUsed=" + getCoinUsed() +
                ", coin=" + getCoin() +
                ", diamond=" + getDiamond() +
                ")";
    }

}
