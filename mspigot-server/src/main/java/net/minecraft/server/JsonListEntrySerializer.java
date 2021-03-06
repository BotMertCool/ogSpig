package net.minecraft.server;

// CraftBukkit - Imported for package private use in JsonList

import net.minecraft.util.com.google.gson.*;

import java.lang.reflect.Type;

class JsonListEntrySerializer implements JsonDeserializer, JsonSerializer {

    final JsonList a;

    private JsonListEntrySerializer(JsonList jsonlist) {
        this.a = jsonlist;
    }

    JsonListEntrySerializer(JsonList jsonlist, JsonListType jsonlisttype) {
        this(jsonlist);
    }

    public JsonElement a(JsonListEntry jsonlistentry, Type type, JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();

        jsonlistentry.a(jsonobject);
        return jsonobject;
    }

    public JsonListEntry a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            JsonListEntry jsonlistentry = this.a.a(jsonobject);

            return jsonlistentry;
        } else {
            return null;
        }
    }

    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.a((JsonListEntry) object, type, jsonserializationcontext);
    }

    public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
        return this.a(jsonelement, type, jsondeserializationcontext);
    }
}
