package com.github.fmjsjx.demo.http.core.entity.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fmjsjx.bson.model.core.BsonUtil;
import com.github.fmjsjx.bson.model2.core.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;

import java.util.*;

public class BasicInfo extends ObjectModel<BasicInfo> {

    public static final String BNAME_NICKNAME = "nn";
    public static final String BNAME_FACE_ID = "fi";
    public static final String BNAME_FACE_URL = "fu";

    private String nickname;
    private int faceId;
    private String faceUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (!Objects.equals(nickname, this.nickname)) {
            this.nickname = nickname;
            fieldChanged(0);
        }
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        if (faceId != this.faceId) {
            this.faceId = faceId;
            fieldChanged(1);
        }
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        if (!Objects.equals(faceUrl, this.faceUrl)) {
            this.faceUrl = faceUrl;
            fieldChanged(2);
        }
    }

    public boolean nicknameChanged() {
        return changedFields.get(0);
    }

    public boolean faceIdChanged() {
        return changedFields.get(1);
    }

    public boolean faceUrlChanged() {
        return changedFields.get(2);
    }

    @Override
    public BsonDocument toBson() {
        var bson = new BsonDocument();
        var nickname = this.nickname;
        if (nickname != null) {
            bson.append(BNAME_NICKNAME, new BsonString(nickname));
        }
        bson.append(BNAME_FACE_ID, new BsonInt32(faceId));
        var faceUrl = this.faceUrl;
        if (faceUrl != null) {
            bson.append(BNAME_FACE_URL, new BsonString(faceUrl));
        }
        return bson;
    }

    @Override
    public BasicInfo load(BsonDocument src) {
        resetStates();
        nickname = BsonUtil.stringValue(src, BNAME_NICKNAME).orElse(null);
        faceId = BsonUtil.intValue(src, BNAME_FACE_ID).orElseThrow();
        faceUrl = BsonUtil.stringValue(src, BNAME_FACE_URL).orElse(null);
        return this;
    }

    @Override
    public JsonNode toJsonNode() {
        var jsonNode = JsonNodeFactory.instance.objectNode();
        var nickname = this.nickname;
        if (nickname != null) {
            jsonNode.put(BNAME_NICKNAME, nickname);
        }
        jsonNode.put(BNAME_FACE_ID, faceId);
        var faceUrl = this.faceUrl;
        if (faceUrl != null) {
            jsonNode.put(BNAME_FACE_URL, faceUrl);
        }
        return jsonNode;
    }

    @Override
    public Map<Object, Object> toData() {
        var data = new LinkedHashMap<>();
        var nickname = this.nickname;
        if (nickname != null) {
            data.put("nickname", nickname);
        }
        data.put("faceId", faceId);
        var faceUrl = this.faceUrl;
        if (faceUrl != null) {
            data.put("faceUrl", faceUrl);
        }
        return data;
    }

    @Override
    public boolean anyUpdated() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(0) && nickname != null) {
            return true;
        }
        if (changedFields.get(1)) {
            return true;
        }
        if (changedFields.get(2) && faceUrl != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void resetChildren() {
    }

    @Override
    protected int deletedSize() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var n = 0;
        if (changedFields.get(0) && nickname == null) {
            n++;
        }
        if (changedFields.get(2) && faceUrl == null) {
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
        if (changedFields.get(0) && nickname == null) {
            return true;
        }
        if (changedFields.get(2) && faceUrl == null) {
            return true;
        }
        return false;
    }

    @Override
    public BasicInfo clean() {
        nickname = null;
        faceId = 0;
        faceUrl = null;
        resetStates();
        return this;
    }

    @Override
    public BasicInfo deepCopy() {
        var copy = new BasicInfo();
        deepCopyTo(copy, false);
        return copy;
    }

    @Override
    public void deepCopyFrom(BasicInfo src) {
        nickname = src.nickname;
        faceId = src.faceId;
        faceUrl = src.faceUrl;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            var nickname = this.nickname;
            if (nickname == null) {
                updates.add(Updates.unset(path().resolve(BNAME_NICKNAME).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_NICKNAME).value(), nickname));
            }
        }
        if (changedFields.get(1)) {
            updates.add(Updates.set(path().resolve(BNAME_FACE_ID).value(), faceId));
        }
        if (changedFields.get(2)) {
            var faceUrl = this.faceUrl;
            if (faceUrl == null) {
                updates.add(Updates.unset(path().resolve(BNAME_FACE_URL).value()));
            } else {
                updates.add(Updates.set(path().resolve(BNAME_FACE_URL).value(), faceUrl));
            }
        }
    }

    @Override
    protected void loadObjectNode(JsonNode src) {
        resetStates();
        nickname = BsonUtil.stringValue(src, BNAME_NICKNAME).orElse(null);
        faceId = BsonUtil.intValue(src, BNAME_FACE_ID).orElseThrow();
        faceUrl = BsonUtil.stringValue(src, BNAME_FACE_URL).orElse(null);
    }

    @Override
    protected void appendUpdateData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(0)) {
            var nickname = this.nickname;
            if (nickname != null) {
                data.put("nickname", nickname);
            }
        }
        if (changedFields.get(1)) {
            data.put("faceId", faceId);
        }
        if (changedFields.get(2)) {
            var faceUrl = this.faceUrl;
            if (faceUrl != null) {
                data.put("faceUrl", faceUrl);
            }
        }
    }

    @Override
    protected void appendDeletedData(Map<Object, Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(0) && nickname == null) {
            data.put("nickname", 1);
        }
        if (changedFields.get(2) && faceUrl == null) {
            data.put("faceUrl", 1);
        }
    }

    @Override
    public String toString() {
        return "BasicInfo(" + "nickname=" + nickname +
                ", faceId=" + faceId +
                ", faceUrl=" + faceUrl +
                ")";
    }

}
