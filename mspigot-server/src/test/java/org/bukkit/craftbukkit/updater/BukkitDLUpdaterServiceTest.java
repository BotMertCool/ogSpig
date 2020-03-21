package org.bukkit.craftbukkit.updater;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BukkitDLUpdaterServiceTest {
    @Test(expected = IOException.class)
    public void testHostNotFound() throws IOException {
        BukkitDLUpdaterService service = new BukkitDLUpdaterService("404.example.org");

        service.fetchArtifact("rb");
    }

    @Test(expected = FileNotFoundException.class)
    public void testArtifactNotFound() throws IOException {
        BukkitDLUpdaterService service = new BukkitDLUpdaterService("dl.bukkit.org");

        service.fetchArtifact("meep");
    }

    @Test
    public void testArtifactExists() throws IOException {
        BukkitDLUpdaterService service = new BukkitDLUpdaterService("dl.bukkit.org");

        assertThat(service.fetchArtifact("latest-dev"), is(not(nullValue())));
    }
}
