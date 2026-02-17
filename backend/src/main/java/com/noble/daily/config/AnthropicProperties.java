package com.noble.daily.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Anthropic API 설정 프로퍼티
 */
@Component
@ConfigurationProperties(prefix = "anthropic")
@Getter
@Setter
public class AnthropicProperties {
    private String apiKey;
    private String apiUrl;
    private String model;
}
