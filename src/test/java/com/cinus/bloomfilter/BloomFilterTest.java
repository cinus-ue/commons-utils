package com.cinus.bloomfilter;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class BloomFilterTest {

    @Test
    public void test_bloomfilter() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000, 100);
        for (int i = 0; i < 100; i++) {
            String val = UUID.randomUUID().toString();
            bloomFilter.add(val);
            Assert.assertTrue(bloomFilter.contains(val));
        }
        Assert.assertFalse(bloomFilter.contains(UUID.randomUUID().toString()));
    }
}
