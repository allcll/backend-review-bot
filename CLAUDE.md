# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Purpose

GitHub PR code review bot, Java 21, intended to run in GitHub Actions against `allcll/allcll-backend` (Spring Boot + JPA + MySQL). The bot drives an agentic loop: it calls Anthropic's Messages API, executes file/search/PR tools when the model issues `tool_use` blocks, feeds `tool_result` blocks back, and submits a final GitHub review.

**The review procedure is not in this repo.** This codebase only provides the runtime (agent loop, tools, GitHub/Anthropic clients). The actual review steps live in the **target repo** at `.claude/commands/review-pr.md`, plus per-PR-type checklists at `.claude/skills/{analysis,bug-fix,refactoring,testing}/SKILL.md`. The bot reads those files from the cloned workspace at runtime and follows them. Don't reimplement review logic here — extend tools / agent infra instead.

## Common commands

Run from the repo root. Always use the Gradle wrapper (`./gradlew`), not a system Gradle.

- Build everything: `./gradlew build`
- Run the app: `./gradlew :app:run`
- Run tests: `./gradlew :app:test`
- Run a single test class: `./gradlew :app:test --tests com.allcll.reviewbot.SomeTest`
- Run a single test method: `./gradlew :app:test --tests 'com.allcll.reviewbot.SomeTest.someMethod'`
- **Build the distribution jar (Shadow fat-jar):** `./gradlew :app:shadowJar` — output under `app/build/libs/`. This is the artifact GitHub Actions runs.

The configuration cache is enabled (`org.gradle.configuration-cache=true`); tasks that aren't cache-compatible will fail rather than silently fall back.

## Runtime environment variables

Read by `com.allcll.reviewbot.App` at startup. `.env` in the working directory is loaded via `dotenv-java` and merged with the process env.

| Var | Required | Purpose |
| --- | --- | --- |
| `PR_NUMBER` | yes | PR number to review (e.g. `123`). |
| `REPO` | yes | `owner/name`, e.g. `allcll/allcll-backend`. Used by `gh` CLI. |
| `WORKSPACE` | yes | Absolute path to the cloned target repo. `.claude/commands/review-pr.md` etc. are resolved relative to this. |
| `ANTHROPIC_API_KEY` | yes | API key for the Messages API. |
| `DRY_RUN` | no (default `false`) | When `true`, comment/review-submit tools log to stdout instead of calling GitHub. |

Missing required vars → exit 1 with an explicit message. `ANTHROPIC_API_KEY` is masked in startup logs.

## Architecture

- **Single Gradle subproject `:app`**, Java 21 toolchain auto-resolved via foojay. Distribution is a shadow uber-jar.
- **`gh` CLI is a runtime dependency.** `GitHubClient` shells out via `ProcessBuilder`. The Actions runner must have `gh` on PATH and `GH_TOKEN` (or be authenticated). This codebase does **not** speak the GitHub REST API directly.
- **Version catalog** at `gradle/libs.versions.toml` is the single source of truth for shared deps (guava, junit). Newer additions (jackson, dotenv) are currently pinned inline in `app/build.gradle.kts` — move to the catalog once they're used in more than one place.

### Package layout (`com.allcll.reviewbot`)

- `agent/` — generic agent runtime, independent of any specific tool or LLM provider.
  - `Tool` — interface (`name`, `description`, `inputSchema`, `execute`).
  - `ToolResult` — record carrying the string content + `isError` flag that maps onto an Anthropic `tool_result` block.
  - `ToolRegistry` — name → Tool lookup.
  - `AgentLoop` — drives `messages.create` → handle `tool_use` → feed `tool_result` back, bounded by `maxTurns`. Pseudocode for the intended flow lives in `AgentLoop.run()`.
- `tools/` — concrete `Tool` implementations. Split by capability surface:
  - Workspace-facing (need `WORKSPACE` path): `ReadFileTool`, `SearchCodeTool`, `ListDirectoryTool`.
  - PR-facing (need `GitHubClient`): `GetPrDiffTool`, `GetPrMetadataTool`, `PostReviewCommentTool`, `SubmitPrReviewTool`. The last two also take `dryRun`.
- `anthropic/AnthropicClient` — wraps the Messages API. Other code talks to it via Jackson `JsonNode` for both request and response.
- `github/GitHubClient` — wraps `gh` CLI invocations.
- `prompt/SystemPrompt` — holds the system prompt constant. The prompt deliberately delegates the *procedure* to `.claude/commands/review-pr.md` in the target repo; don't bake review steps in here.

### How the pieces fit at runtime

1. `App.main` resolves env vars, instantiates `GitHubClient`, `AnthropicClient`, registers the seven tools into a `ToolRegistry`, and constructs an `AgentLoop`.
2. The initial user message tells the model the PR number; the model then reads `.claude/commands/review-pr.md` via `ReadFileTool` and proceeds from there.
3. Tool side effects (posting comments, submitting the review) flow through `PostReviewCommentTool` / `SubmitPrReviewTool`, both of which honor `DRY_RUN`.

The agent loop itself is currently a skeleton (`UnsupportedOperationException("TODO")`); the wiring above is the target shape, not the present state.
