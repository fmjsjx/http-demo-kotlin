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
public final class BasicInfo extends AbstractObjectModel<BasicInfo> {

    public static final String STORE_NAME_NICKNAME = "nn";
    public static final String STORE_NAME_FACE_ID = "fi";
    public static final String STORE_NAME_FACE_URL = "fu";

    public static final String DISPLAY_NAME_NICKNAME = "nickname";
    public static final String DISPLAY_NAME_FACE_ID = "faceId";
    public static final String DISPLAY_NAME_FACE_URL = "faceUrl";

    public static final int FIELD_INDEX_NICKNAME = 0;
    public static final int FIELD_INDEX_FACE_ID = 1;
    public static final int FIELD_INDEX_FACE_URL = 2;

    @JSONType(alphabetic = false)
    public static final class BasicInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_NICKNAME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_NICKNAME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_NICKNAME)
        private @Nullable String nickname;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_FACE_ID)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_FACE_ID)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_FACE_ID)
        private int faceId;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_FACE_URL)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_FACE_URL)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_FACE_URL)
        private @Nullable String faceUrl;

        public @Nullable String getNickname() {
            return nickname;
        }

        public void setNickname(@Nullable String nickname) {
            this.nickname = nickname;
        }

        public int getFaceId() {
            return faceId;
        }

        public void setFaceId(int faceId) {
            this.faceId = faceId;
        }

        public @Nullable String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(@Nullable String faceUrl) {
            this.faceUrl = faceUrl;
        }
    }

    private @Nullable String nickname;
    private int faceId;
    private @Nullable String faceUrl;

    public @Nullable String getNickname() {
        return nickname;
    }

    public void setNickname(@Nullable String nickname) {
        if (!Objects.equals(this.nickname, nickname)) {
            this.nickname = nickname;
            triggerChange(FIELD_INDEX_NICKNAME);
        }
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        if (faceId != this.faceId) {
            this.faceId = faceId;
            triggerChange(FIELD_INDEX_FACE_ID);
        }
    }

    public @Nullable String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(@Nullable String faceUrl) {
        if (!Objects.equals(this.faceUrl, faceUrl)) {
            this.faceUrl = faceUrl;
            triggerChange(FIELD_INDEX_FACE_URL);
        }
    }

    @Override
    protected BasicInfo cleanFields() {
        nickname = null;
        faceId = 0;
        faceUrl = null;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_NICKNAME)) {
            var _nickname = getNickname();
            if (_nickname == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_NICKNAME)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_NICKNAME), new BsonString(_nickname)));
            }
        }
        if (changedFields.get(FIELD_INDEX_FACE_ID)) {
            updates.add(Updates.set(path().path(STORE_NAME_FACE_ID), new BsonInt32(getFaceId())));
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL)) {
            var _faceUrl = getFaceUrl();
            if (_faceUrl == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_FACE_URL)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_FACE_URL), new BsonString(_faceUrl)));
            }
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_NICKNAME)) {
            var _nickname = getNickname();
            if (_nickname != null) {
                data.put(DISPLAY_NAME_NICKNAME, _nickname);
            }
        }
        if (changedFields.get(FIELD_INDEX_FACE_ID)) {
            data.put(DISPLAY_NAME_FACE_ID, getFaceId());
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL)) {
            var _faceUrl = getFaceUrl();
            if (_faceUrl != null) {
                data.put(DISPLAY_NAME_FACE_URL, _faceUrl);
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        var _nickname = getNickname();
        if (_nickname != null) {
            _displayData.put(DISPLAY_NAME_NICKNAME, _nickname);
        }
        _displayData.put(DISPLAY_NAME_FACE_ID, getFaceId());
        var _faceUrl = getFaceUrl();
        if (_faceUrl != null) {
            _displayData.put(DISPLAY_NAME_FACE_URL, _faceUrl);
        }
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        var _nickname = getNickname();
        if (_nickname != null) {
            _bsonValue.put(STORE_NAME_NICKNAME, new BsonString(_nickname));
        }
        _bsonValue.put(STORE_NAME_FACE_ID, new BsonInt32(getFaceId()));
        var _faceUrl = getFaceUrl();
        if (_faceUrl != null) {
            _bsonValue.put(STORE_NAME_FACE_URL, new BsonString(_faceUrl));
        }
        return _bsonValue;
    }

    @Override
    public BasicInfo load(BsonDocument src) {
        resetStates();
        nickname = BsonUtil.stringValue(src, STORE_NAME_NICKNAME).orElse(null);
        faceId = BsonUtil.intValue(src, STORE_NAME_FACE_ID).orElse(0);
        faceUrl = BsonUtil.stringValue(src, STORE_NAME_FACE_URL).orElse(null);
        return this;
    }

    @Override
    public BasicInfoStoreData toStoreData() {
        var _storeData = new BasicInfoStoreData();
        var _nickname = getNickname();
        if (_nickname != null) {
            _storeData.nickname = _nickname;
        }
        _storeData.faceId = getFaceId();
        var _faceUrl = getFaceUrl();
        if (_faceUrl != null) {
            _storeData.faceUrl = _faceUrl;
        }
        return _storeData;
    }

    @Override
    public BasicInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof BasicInfoStoreData _storeData) {
            var _nickname = _storeData.nickname;
            if (_nickname != null) {
                nickname = _nickname;
            }
            faceId = _storeData.faceId;
            var _faceUrl = _storeData.faceUrl;
            if (_faceUrl != null) {
                faceUrl = _faceUrl;
            }
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
        if (changedFields.get(FIELD_INDEX_NICKNAME) && getNickname() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FACE_ID)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL) && getFaceUrl() != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_NICKNAME) && getNickname() == null) {
            data.put(DISPLAY_NAME_NICKNAME, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL) && getFaceUrl() == null) {
            data.put(DISPLAY_NAME_FACE_URL, BsonModelConstants.DELETED_VALUE);
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
        if (changedFields.get(FIELD_INDEX_NICKNAME) && getNickname() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL) && getFaceUrl() == null) {
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
        if (changedFields.get(FIELD_INDEX_NICKNAME) && getNickname() == null) {
            __size++;
        }
        if (changedFields.get(FIELD_INDEX_FACE_URL) && getFaceUrl() == null) {
            __size++;
        }
        return __size;
    }

    @Override
    public BasicInfo deepCopy() {
        return new BasicInfo().deepCopyFrom(this);
    }

    @Override
    public BasicInfo deepCopyFrom(BasicInfo src) {
        nickname = src.getNickname();
        faceId = src.getFaceId();
        faceUrl = src.getFaceUrl();
        return this;
    }

    @Override
    public String toString() {
        return "BasicInfo(nickname=" + getNickname() +
                ", faceId=" + getFaceId() +
                ", faceUrl=" + getFaceUrl() +
                ")";
    }

}
