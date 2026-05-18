package com.allcll.reviewbot.agent;

import com.fasterxml.jackson.databind.JsonNode;

public interface Tool {
    String name();

    String description();

    JsonNode inputSchema();

    ToolResult execute(JsonNode input);
}
