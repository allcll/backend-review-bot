package com.allcll.reviewbot.tools;

import com.allcll.reviewbot.agent.Tool;
import com.allcll.reviewbot.agent.ToolResult;
import com.allcll.reviewbot.github.GitHubClient;
import com.fasterxml.jackson.databind.JsonNode;

public class GetPrDiffTool implements Tool {
    public GetPrDiffTool(GitHubClient github) {
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
