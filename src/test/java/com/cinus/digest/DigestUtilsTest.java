package com.cinus.digest;

import org.junit.Assert;
import org.junit.Test;

public class DigestUtilsTest {
    @Test
    public void test_digest() {
        String MD5 = "74ce51274344c29e2710251d1f898679";
        String SHA1 = "60df2b2c3e1fab48645155a5a1a2ebe97aa205a2";
        String SHA256 = "dd1403ee94532fa20b02811437c4a542f31df17205997131da539118d7e20dc9";
        String SHA384 = "b9e478be75280da55cb113fdc9031e5824240b8996f2ce0cbe747b0a22c2704572d25b7d59eea9ac26114d4e61115e84";
        String SHA512 = "d4dbb980a5147a9760b81fbd4dc9b12a9396bb516709bb2b941b58dbf2f5312d0da3a4d70271377eeba1a6fb620ea97c5a6647545d077ebafaae81f61eec4c62";

        Assert.assertEquals(MD5, DigestUtils.hexdigest("SHAUtils", DigestUtils.MD5));
        Assert.assertEquals(SHA1, DigestUtils.shaHex("SHAUtils"));
        Assert.assertEquals(SHA256, DigestUtils.sha256Hex("SHAUtils"));
        Assert.assertEquals(SHA384, DigestUtils.hexdigest("SHAUtils", DigestUtils.SHA384));
        Assert.assertEquals(SHA512, DigestUtils.sha512Hex("SHAUtils"));

    }
}
