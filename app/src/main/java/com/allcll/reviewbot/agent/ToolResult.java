package com.allcll.reviewbot.agent;

public record ToolResult(String content, boolean isError) {
    public static ToolResult ok(String content) {
        throw new UnsupportedOperationException("TODO");
    }

    public static ToolResult error(String message) {
        throw new UnsupportedOperationException("TODO");
    }
}
