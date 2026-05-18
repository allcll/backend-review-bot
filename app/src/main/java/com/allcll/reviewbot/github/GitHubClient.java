package com.allcll.reviewbot.github;

public class GitHubClient {
    public GitHubClient(String repo) {
    }

    public String getPrDiff(int prNumber) {
        throw new UnsupportedOperationException("TODO");
    }

    public String getPrMetadata(int prNumber) {
        throw new UnsupportedOperationException("TODO");
    }

    public void postReviewComment(int prNumber, String path, int line, String body) {
        throw new UnsupportedOperationException("TODO");
    }

    public void submitReview(int prNumber, String event, String body) {
        throw new UnsupportedOperationException("TODO");
    }
}
