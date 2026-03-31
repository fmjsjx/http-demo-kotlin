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
public final class GuideInfo extends AbstractObjectModel<GuideInfo> {

    public static final String STORE_NAME_STATUS = "s";

    public static final String DISPLAY_NAME_STATUS = "status";

    public static final int FIELD_INDEX_STATUS = 0;

    @JSONType(alphabetic = false)
    public static final class GuideInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_STATUS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_STATUS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_STATUS)
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status != this.status) {
            this.status = status;
            triggerChange(FIELD_INDEX_STATUS);
        }
    }

    @Override
    protected GuideInfo cleanFields() {
        status = 0;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_STATUS)) {
            updates.add(Updates.set(path().path(STORE_NAME_STATUS), new BsonInt32(getStatus())));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_STATUS)) {
            data.put(DISPLAY_NAME_STATUS, getStatus());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_STATUS, getStatus());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_STATUS, new BsonInt32(getStatus()));
        return _bsonValue;
    }

    @Override
    public GuideInfo load(BsonDocument src) {
        resetStates();
        status = BsonUtil.intValue(src, STORE_NAME_STATUS).orElse(0);
        return this;
    }

    @Override
    public GuideInfoStoreData toStoreData() {
        var _storeData = new GuideInfoStoreData();
        _storeData.status = getStatus();
        return _storeData;
    }

    @Override
    public GuideInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof GuideInfoStoreData _storeData) {
            status = _storeData.status;
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
        if (changedFields.get(FIELD_INDEX_STATUS)) {
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
    public GuideInfo deepCopy() {
        return new GuideInfo().deepCopyFrom(this);
    }

    @Override
    public GuideInfo deepCopyFrom(GuideInfo src) {
        status = src.getStatus();
        return this;
    }

    @Override
    public String toString() {
        return "GuideInfo(status=" + getStatus() +
                ")";
    }

}
