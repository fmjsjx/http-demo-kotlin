package com.github.fmjsjx.demo.http.core.entity.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fmjsjx.bson.model.core.BsonUtil;
import com.github.fmjsjx.bson.model2.core.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;

import java.util.*;

public class PreferencesInfo extends ObjectModel<PreferencesInfo> {

    public static final String BNAME_CUSTOM = "ctm";
    public static final String BNAME_FEATURES = "fts";
    public static final String BNAME_ATTRIBUTES = "atr";

    private String custom;
    private List<String> features;
    private final SingleValueMapModel<String, String> attributes = SingleValueMapModel.stringKeysMap(SingleValueTypes.STRING).parent(this).key(BNAME_ATTRIBUTES).index(2);

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        if (!Objects.equals(custom, this.custom)) {
            this.custom = custom;
            fieldChanged(0);
        }
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        if (!Objects.equals(features, this.features)) {
            this.features = features;
            fieldChanged(1);
        }
    }

    public SingleValueMapModel<String, String> getAttributes() {
        return attributes;
    }

    public boolean customChanged() {
        return changedFields.get(0);
    }

    public boolean featuresChanged() {
        return changedFields.get(1);
    }

    public boolean attributesChanged() {
        return changedFields.get(2);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        var custom = this.custom;
        if (custom != null) {
            bson.append(BNAME_CUSTOM, new BsonString(custom));
        }
        var features = this.features;
        if (features != null) {
            bson.append(BNAME_FEATURES, BsonUtil.toBsonArray(features, BsonString::new));
        }
        bson.append(BNAME_ATTRIBUTES, attributes.toBson());
        return bson;
    }

    @Override
    public PreferencesInfo load(BsonDocument src) {
        resetStates();
        custom = BsonUtil.stringValue(src, BNAME_CUSTOM).orElse(null);
        features = BsonUtil.arrayValue(src, BNAME_FEATURES, BsonString::getValue).orElse(null);
        BsonUtil.documentValue(src, BNAME_ATTRIBUTES).ifPresentOrElse(attributes::load, attributes::clean);
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        var custom = this.custom;
        if (custom != null) {
            jsonNode.put(BNAME_CUSTOM, custom);
        }
        var features = this.features;
        if (features != null) {
            var featuresArrayNode = jsonNode.arrayNode(features.size());
            features.forEach(featuresArrayNode::add);
            jsonNode.set(BNAME_FEATURES, featuresArrayNode);
        }
        jsonNode.set(BNAME_ATTRIBUTES, attributes.toJsonNode());
        return jsonNode;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        var custom = this.custom;
        if (custom != null) {
            data.put("custom", custom);
        }
        var features = this.features;
        if (features != null) {
            data.put("features", features);
        }
        data.put("attributes", attributes.toData());
        return data;
    }

    @Override
    public boolean anyUpdated() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(0) && custom != null) {
            return true;
        }
        if (changedFields.get(1) && features != null) {
            return true;
        }
        if (changedFields.get(2) && attributes.anyUpdated()) {
            return true;
        }
        return false;
    }

    @Override
    protected void resetChildren() {
        attributes.reset();
    }

    @Override
    protected int deletedSize() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var n = 0;
        if (changedFields.get(0) && custom == null) {
            n++;
        }
        if (changedFields.get(1) && features == null) {
            n++;
        }
        if (changedFields.get(2) && attributes.anyDeleted()) {
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
        if (changedFields.get(0) && custom == null) {
            return true;
        }
        if (changedFields.get(1) && features == null) {
            return true;
        }
        if (changedFields.get(2) && attributes.anyDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public PreferencesInfo clean() {
        custom = null;
        features = null;
        attributes.clean();
        resetStates();
        return this;
    }

    @Override
    public PreferencesInfo deepCopy() {
        var copy = new PreferencesInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(PreferencesInfo src) {
        custom = src.custom;
        var features = src.features;
        if (features != null) {
            this.features = new ArrayList<>(src.features);
        } else {
            this.features = null;
        }
        src.attributes.deepCopyTo(attributes, false);
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            var custom = this.custom;
            if (custom == null) {
                updates.add(Updates.unset(path().resolve(BNAME_CUSTOM).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_CUSTOM).value(), custom));
            }
        }
        if (changedFields.get(1)) {
            var features = this.features;
            if (features == null) {
                updates.add(Updates.unset(path().resolve(BNAME_FEATURES).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_FEATURES).value(), BsonUtil.toBsonArray(features, BsonString::new)));
            }
        }
        if (changedFields.get(2)) {
            attributes.appendUpdates(updates);
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        custom = BsonUtil.stringValue(src, BNAME_CUSTOM).orElse(null);
        features = BsonUtil.listValue(src, BNAME_FEATURES, JsonNode::textValue).orElse(null);
        BsonUtil.objectValue(src, BNAME_ATTRIBUTES).ifPresentOrElse(attributes::load, attributes::clean);
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            var custom = this.custom;
            if (custom != null) {
                data.put("custom", custom);
            }
        }
        if (changedFields.get(1)) {
            var features = this.features;
            if (features != null) {
                data.put("features", features);
            }
        }
        if (changedFields.get(2)) {
            var attributesUpdateData = attributes.toUpdateData();
            if (attributesUpdateData != null) {
                data.put("attributes", attributesUpdateData);
            }
        }
    }

    @Override
    protected void appendDeletedData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(0) && custom == null) {
            data.put("custom", 1);
        }
        if (changedFields.get(1) && features == null) {
            data.put("features", 1);
        }
        if (changedFields.get(2)) {
            var attributesDeletedData = attributes.toDeletedData();
            if (attributesDeletedData != null) {
                data.put("attributes", attributesDeletedData);
            }
        }
    }

    @Override
    public String toString() {
        return "PreferencesInfo(" + "custom=" + custom +
                ", features=" + features +
                ", attributes=" + attributes +
                ")";
    }

}
