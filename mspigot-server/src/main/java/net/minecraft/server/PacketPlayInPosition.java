package net.minecraft.server;

public class PacketPlayInPosition extends PacketPlayInFlying {

    public PacketPlayInPosition() {
        this.hasPos = true;
    }

    public void a(PacketDataSerializer packetdataserializer) {
        this.x = packetdataserializer.readDouble();
        // Spigot start - protocol patch
        if (packetdataserializer.version < 16) {
            this.y = packetdataserializer.readDouble();
            this.stance = packetdataserializer.readDouble();
        } else {
            this.y = packetdataserializer.readDouble();
            this.stance = y + 1.62;
        }
        // Spigot end
        this.z = packetdataserializer.readDouble();
        super.a(packetdataserializer);
    }

    public void b(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeDouble(this.x);
        packetdataserializer.writeDouble(this.y);
        packetdataserializer.writeDouble(this.stance);
        packetdataserializer.writeDouble(this.z);
        super.b(packetdataserializer);
    }

    public void handle(PacketListener packetlistener) {
        super.a((PacketPlayInListener) packetlistener);
    }
}
