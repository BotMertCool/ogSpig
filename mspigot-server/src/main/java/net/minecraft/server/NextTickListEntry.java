package net.minecraft.server;

public class NextTickListEntry implements Comparable {

    private static long f;
    private final Block g;
    public int a;
    public int b;
    public int c;
    public long d;
    public int e;
    private long h;

    public NextTickListEntry(int i, int j, int k, Block block) {
        this.h = (long) (f++);
        this.a = i;
        this.b = j;
        this.c = k;
        this.g = block;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        } else {
            NextTickListEntry nextticklistentry = (NextTickListEntry) object;

            return this.a == nextticklistentry.a && this.b == nextticklistentry.b && this.c == nextticklistentry.c && Block.a(this.g, nextticklistentry.g);
        }
    }

    public int hashCode() {
        return (c & 0xff) | ((a & 0x7fff) << 8) | ((b & 0x7fff) << 24) | ((a < 0) ? 0x0080000000 : 0) | ((b < 0) ? 0x0000008000 : 0);
    }

    public NextTickListEntry a(long i) {
        this.d = i;
        return this;
    }

    public void a(int i) {
        this.e = i;
    }

    public int compareTo(NextTickListEntry nextticklistentry) {
        return this.d < nextticklistentry.d ? -1 : (this.d > nextticklistentry.d ? 1 : (this.e != nextticklistentry.e ? this.e - nextticklistentry.e : (this.h < nextticklistentry.h ? -1 : (this.h > nextticklistentry.h ? 1 : 0))));
    }

    public String toString() {
        return Block.getId(this.g) + ": (" + this.a + ", " + this.b + ", " + this.c + "), " + this.d + ", " + this.e + ", " + this.h;
    }

    public Block a() {
        return this.g;
    }

    public int compareTo(Object object) {
        return this.compareTo((NextTickListEntry) object);
    }
}
