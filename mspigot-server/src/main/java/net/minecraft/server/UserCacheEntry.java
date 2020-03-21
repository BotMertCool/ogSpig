package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;

import java.util.Date;

class UserCacheEntry {

    final UserCache a;
    private final GameProfile b;
    private final Date c;

    private UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date) {
        this.a = usercache;
        this.b = gameprofile;
        this.c = date;
    }

    UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date, GameProfileLookup gameprofilelookup) {
        this(usercache, gameprofile, date);
    }

    static Date a(UserCacheEntry usercacheentry) {
        return usercacheentry.c;
    }

    public GameProfile a() {
        return this.b;
    }

    public Date b() {
        return this.c;
    }
}
