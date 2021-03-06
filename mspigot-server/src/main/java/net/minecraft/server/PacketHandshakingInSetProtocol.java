package net.minecraft.server;

import java.io.IOException;

public class PacketHandshakingInSetProtocol extends Packet {

    public String b; // CraftBukkit private -> public
    public int c; // CraftBukkit private -> public
    private int a;
    private EnumProtocol d;

    public PacketHandshakingInSetProtocol() {
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException { // CraftBukkit - added throws
        this.a = packetdataserializer.a();
        this.b = packetdataserializer.c(Short.MAX_VALUE); // Spigot
        this.c = packetdataserializer.readUnsignedShort();
        this.d = EnumProtocol.a(packetdataserializer.a());
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException { // CraftBukkit - added throws
        packetdataserializer.b(this.a);
        packetdataserializer.a(this.b);
        packetdataserializer.writeShort(this.c);
        packetdataserializer.b(this.d.c());
    }

    public void a(PacketHandshakingInListener packethandshakinginlistener) {
        packethandshakinginlistener.a(this);
    }

    public boolean a() {
        return true;
    }

    public EnumProtocol c() {
        return this.d;
    }

    public int d() {
        return this.a;
    }

    public void handle(PacketListener packetlistener) {
        this.a((PacketHandshakingInListener) packetlistener);
    }
}
