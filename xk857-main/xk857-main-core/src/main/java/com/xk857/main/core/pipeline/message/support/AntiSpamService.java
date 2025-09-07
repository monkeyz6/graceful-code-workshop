package com.xk857.main.core.pipeline.message.support;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 简单反垃圾（敏感词拦截）
 */
@Service
public class AntiSpamService {
    private static final Set<String> BANNED = Set.of("违法", "违规", "小广告");

    public boolean pass(String content) {
        if (content == null) return true;
        for (String word : BANNED) {
            if (content.contains(word)) {
                return false;
            }
        }
        return true;
    }
}

