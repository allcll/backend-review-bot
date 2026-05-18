package com.allcll.reviewbot.tools;

import com.allcll.reviewbot.agent.Tool;
import com.allcll.reviewbot.agent.ToolResult;
import com.allcll.reviewbot.github.GitHubClient;
import com.fasterxml.jackson.databind.JsonNode;

public class SubmitPrReviewTool implements Tool {
    public SubmitPrReviewTool(GitHubClient github, boolean dryRun) {
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
