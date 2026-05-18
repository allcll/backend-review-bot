package com.allcll.reviewbot.tools;

import com.allcll.reviewbot.agent.Tool;
import com.allcll.reviewbot.agent.ToolResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.nio.file.Path;

public class ListDirectoryTool implements Tool {
    public ListDirectoryTool(Path workspaceRoot) {
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public JsonNode inputSchema() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public ToolResult execute(JsonNode input) {
        throw new UnsupportedOperationException("TODO");
    }
}
