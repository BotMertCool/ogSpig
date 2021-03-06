package org.bukkit.util;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class StringUtilStartsWithTest {

    @Parameter(0)
    public String base;
    @Parameter(1)
    public String prefix;
    @Parameter(2)
    public boolean result;

    @Parameters(name = "{index}: {0} startsWith {1} == {2}")
    public static List<Object[]> data() {
        return ImmutableList.<Object[]>of(
                new Object[]{
                        "Apple",
                        "Apples",
                        false
                },
                new Object[]{
                        "Apples",
                        "Apple",
                        true
                },
                new Object[]{
                        "Apple",
                        "Apple",
                        true
                },
                new Object[]{
                        "Apple",
                        "apples",
                        false
                },
                new Object[]{
                        "apple",
                        "Apples",
                        false
                },
                new Object[]{
                        "apple",
                        "apples",
                        false
                },
                new Object[]{
                        "Apples",
                        "apPL",
                        true
                },
                new Object[]{
                        "123456789",
                        "1234567",
                        true
                },
                new Object[]{
                        "",
                        "",
                        true
                },
                new Object[]{
                        "string",
                        "",
                        true
                }
        );
    }

    @Test
    public void testFor() {
        assertThat(base + " starts with " + prefix + ": " + result, StringUtil.startsWithIgnoreCase(base, prefix), is(result));
    }
}
