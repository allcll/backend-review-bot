package com.allcll.reviewbot;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String prNumber = require(dotenv, "PR_NUMBER");
        String repo = require(dotenv, "REPO");
        String workspace = require(dotenv, "WORKSPACE");
        String apiKey = require(dotenv, "ANTHROPIC_API_KEY");
        boolean dryRun = "true".equalsIgnoreCase(value(dotenv, "DRY_RUN", "false"));

        System.out.println("PR_NUMBER=" + prNumber);
        System.out.println("REPO=" + repo);
        System.out.println("WORKSPACE=" + workspace);
        System.out.println("ANTHROPIC_API_KEY=" + mask(apiKey));
        System.out.println("DRY_RUN=" + dryRun);
    }

    private static String require(Dotenv dotenv, String name) {
        String v = value(dotenv, name, null);
        if (v == null || v.isBlank()) {
            System.err.println("Missing required env var: " + name);
            System.exit(1);
        }
        return v;
    }

    private static String value(Dotenv dotenv, String name, String defaultValue) {
        String v = dotenv.get(name);
        return v != null ? v : defaultValue;
    }

    private static String mask(String secret) {
        if (secret == null || secret.length() < 8) return "***";
        return secret.substring(0, 4) + "…" + secret.substring(secret.length() - 4);
    }
}
