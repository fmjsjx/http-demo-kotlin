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
public final class PreferencesInfo extends AbstractObjectModel<PreferencesInfo> {

    public static final String STORE_NAME_CUSTOM = "ctm";
    public static final String STORE_NAME_FEATURES = "fts";
    public static final String STORE_NAME_ATTRIBUTES = "atr";

    public static final String DISPLAY_NAME_CUSTOM = "custom";
    public static final String DISPLAY_NAME_FEATURES = "features";
    public static final String DISPLAY_NAME_ATTRIBUTES = "attributes";

    public static final int FIELD_INDEX_CUSTOM = 0;
    public static final int FIELD_INDEX_FEATURES = 1;
    public static final int FIELD_INDEX_ATTRIBUTES = 2;

    @JSONType(alphabetic = false)
    public static final class PreferencesInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CUSTOM)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CUSTOM)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CUSTOM)
        private @Nullable String custom;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_FEATURES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_FEATURES)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_FEATURES)
        private @Nullable List<@Nullable String> features;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ATTRIBUTES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ATTRIBUTES)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_ATTRIBUTES, implementation = LinkedHashMap.class)
        private Map<String, String> attributes;

        public @Nullable String getCustom() {
            return custom;
        }

        public void setCustom(@Nullable String custom) {
            this.custom = custom;
        }

        public @Nullable List<@Nullable String> getFeatures() {
            return features;
        }

        public void setFeatures(@Nullable List<@Nullable String> features) {
            this.features = features;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    private @Nullable String custom;
    private @Nullable List<@Nullable String> features;
    private final SingleValueMapModel<String, String> attributes = SingleValueMapModel.stringKeysMap(SingleValues.string())
            .parent(this).index(FIELD_INDEX_ATTRIBUTES).key(STORE_NAME_ATTRIBUTES);

    public @Nullable String getCustom() {
        return custom;
    }

    public void setCustom(@Nullable String custom) {
        if (!Objects.equals(this.custom, custom)) {
            this.custom = custom;
            triggerChange(FIELD_INDEX_CUSTOM);
        }
    }

    public @Nullable List<@Nullable String> getFeatures() {
        return features;
    }

    public void setFeatures(@Nullable List<@Nullable String> features) {
        if (!Objects.equals(this.features, features)) {
            this.features = features;
            triggerChange(FIELD_INDEX_FEATURES);
        }
    }

    public SingleValueMapModel<String, String> getAttributes() {
        return attributes;
    }

    @Override
    protected PreferencesInfo resetChildren() {
        getAttributes().reset();
        return this;
    }

    @Override
    protected PreferencesInfo cleanFields() {
        custom = null;
        features = null;
        getAttributes().clean();
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM)) {
            var _custom = getCustom();
            if (_custom == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_CUSTOM)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_CUSTOM), new BsonString(_custom)));
            }
        }
        if (changedFields.get(FIELD_INDEX_FEATURES)) {
            var _features = getFeatures();
            if (_features == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_FEATURES)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_FEATURES), BsonValueUtil.toBsonArray(_features, BsonString::new)));
            }
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            getAttributes().appendUpdates(updates);
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM)) {
            var _custom = getCustom();
            if (_custom != null) {
                data.put(DISPLAY_NAME_CUSTOM, _custom);
            }
        }
        if (changedFields.get(FIELD_INDEX_FEATURES)) {
            var _features = getFeatures();
            if (_features != null) {
                data.put(DISPLAY_NAME_FEATURES, _features);
            }
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            var _attributes = getAttributes().toUpdated();
            if (_attributes != null) {
                data.put(DISPLAY_NAME_ATTRIBUTES, _attributes);
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        var _custom = getCustom();
        if (_custom != null) {
            _displayData.put(DISPLAY_NAME_CUSTOM, _custom);
        }
        var _features = getFeatures();
        if (_features != null) {
            _displayData.put(DISPLAY_NAME_FEATURES, new ArrayList<>(_features));
        }
        _displayData.put(DISPLAY_NAME_ATTRIBUTES, getAttributes().toDisplayData());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        var _custom = getCustom();
        if (_custom != null) {
            _bsonValue.put(STORE_NAME_CUSTOM, new BsonString(_custom));
        }
        var _features = getFeatures();
        if (_features != null) {
            _bsonValue.put(STORE_NAME_FEATURES, BsonValueUtil.toBsonArray(_features, BsonString::new));
        }
        _bsonValue.put(STORE_NAME_ATTRIBUTES, getAttributes().toBsonValue());
        return _bsonValue;
    }

    @Override
    public PreferencesInfo load(BsonDocument src) {
        resetStates();
        custom = BsonUtil.stringValue(src, STORE_NAME_CUSTOM).orElse(null);
        features = BsonUtil.arrayValue(src, STORE_NAME_FEATURES).map(BsonValueUtil::mapToStringList).orElse(null);
        BsonUtil.documentValue(src, STORE_NAME_ATTRIBUTES).ifPresentOrElse(getAttributes()::load, getAttributes()::clean);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PreferencesInfoStoreData toStoreData() {
        var _storeData = new PreferencesInfoStoreData();
        var _custom = getCustom();
        if (_custom != null) {
            _storeData.custom = _custom;
        }
        var _features = getFeatures();
        if (_features != null) {
            _storeData.features = _features;
        }
        _storeData.attributes = (Map<String, String>) getAttributes().toStoreData();
        return _storeData;
    }

    @Override
    public PreferencesInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof PreferencesInfoStoreData _storeData) {
            var _custom = _storeData.custom;
            if (_custom != null) {
                custom = _custom;
            }
            var _features = _storeData.features;
            if (_features != null) {
                features = _features;
            }
            getAttributes().loadStoreData(_storeData.attributes);
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
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES) && getAttributes().anyUpdated()) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            data.put(DISPLAY_NAME_CUSTOM, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            data.put(DISPLAY_NAME_FEATURES, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            var _attributes = getAttributes().toDeleted();
            if (_attributes != null) {
                data.put(DISPLAY_NAME_ATTRIBUTES, _attributes);
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
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES) && getAttributes().anyDeleted()) {
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
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            __size++;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            __size++;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            __size += getAttributes().deletedSize();
        }
        return __size;
    }

    @Override
    public PreferencesInfo deepCopy() {
        return new PreferencesInfo().deepCopyFrom(this);
    }

    @Override
    public PreferencesInfo deepCopyFrom(PreferencesInfo src) {
        custom = src.getCustom();
        var _features = src.getFeatures();
        if (_features != null) {
            features = new ArrayList<>(_features);
        } else {
            features = null;
        }
        getAttributes().deepCopyFrom(src.getAttributes());
        return this;
    }

    @Override
    public String toString() {
        return "PreferencesInfo(custom=" + getCustom() +
                ", features=" + getFeatures() +
                ", attributes=" + getAttributes() +
                ")";
    }

}
