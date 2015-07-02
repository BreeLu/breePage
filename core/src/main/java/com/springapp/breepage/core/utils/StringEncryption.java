package com.springapp.breepage.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringEncryption {
    private static final Logger LOG = LoggerFactory.getLogger(StringEncryption.class);

    public static String generateSHA256(String salt, String... params) {
        String paramsUsedToVerify = "";
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                LOG.info("one of params is null.");
            } else {
                paramsUsedToVerify += param;
            }
        }
        String signature = paramsUsedToVerify + "{" + salt + "}";
        return DigestUtils.sha256Hex(signature);
    }

    public static String generateMd5(String salt, String... params) {
        String paramsUsedToVerify = "";
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                LOG.info("one of params is null.");
            } else {
                paramsUsedToVerify += param;
            }
        }
        String signature = paramsUsedToVerify + salt;
        return DigestUtils.md5Hex(signature);
    }
}
