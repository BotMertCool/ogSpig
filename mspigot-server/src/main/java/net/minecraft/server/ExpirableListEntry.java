package net.minecraft.server;

import net.minecraft.util.com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ExpirableListEntry extends JsonListEntry {

    public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    protected final Date b;
    protected final String c;
    protected final Date d;
    protected final String e;

    public ExpirableListEntry(Object object, Date date, String s, Date date1, String s1) {
        super(object);
        this.b = date == null ? new Date() : date;
        this.c = s == null ? "(Unknown)" : s;
        this.d = date1;
        this.e = s1 == null ? "Banned by an operator." : s1;
    }

    protected ExpirableListEntry(Object object, JsonObject jsonobject) {
        super(checkExpiry(object, jsonobject), jsonobject); // CraftBukkit - check expiry

        Date date;

        try {
            date = jsonobject.has("created") ? a.parse(jsonobject.get("created").getAsString()) : new Date();
        } catch (ParseException parseexception) {
            date = new Date();
        }

        this.b = date;
        this.c = jsonobject.has("source") ? jsonobject.get("source").getAsString() : "(Unknown)";

        Date date1;

        try {
            date1 = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
        } catch (ParseException parseexception1) {
            date1 = null;
        }

        this.d = date1;
        this.e = jsonobject.has("reason") ? jsonobject.get("reason").getAsString() : "Banned by an operator.";
    }

    private static Object checkExpiry(Object object, JsonObject jsonobject) {
        Date expires = null;

        try {
            expires = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
        } catch (ParseException ex) {
            // Guess we don't have a date
        }

        if (expires == null || expires.after(new Date())) {
            return object;
        } else {
            return null;
        }
    }

    public Date getExpires() {
        return this.d;
    }

    public String getReason() {
        return this.e;
    }

    boolean hasExpired() {
        return this.d == null ? false : this.d.before(new Date());
    }

    protected void a(JsonObject jsonobject) {
        jsonobject.addProperty("created", a.format(this.b));
        jsonobject.addProperty("source", this.c);
        jsonobject.addProperty("expires", this.d == null ? "forever" : a.format(this.d));
        jsonobject.addProperty("reason", this.e);
    }

    // CraftBukkit start
    public String getSource() {
        return this.c;
    }

    public Date getCreated() {
        return this.b;
    }
    // CraftBukkit end
}
