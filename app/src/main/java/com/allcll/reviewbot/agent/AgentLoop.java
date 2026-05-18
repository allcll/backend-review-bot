package com.allcll.reviewbot.agent;

import com.allcll.reviewbot.anthropic.AnthropicClient;

public class AgentLoop {
    public AgentLoop(AnthropicClient anthropic, ToolRegistry tools, int maxTurns) {
    }

    public void run(String initialUserMessage) {
        // (1) messages 초기화 — user 메시지로 시작
        //     List<Message> messages = new ArrayList<>();
        //     messages.add(userMessage(initialUserMessage));
        //
        // (2) anthropic.messages.create 호출
        //     JsonNode request = buildRequest(SYSTEM_PROMPT, messages, tools.all());
        //     JsonNode response = anthropic.createMessage(request);
        //
        // (3) stop_reason 이 "end_turn" 이면 종료 — 루프 break
        //
        // (4) tool_use 면 각 tool_use 블록을 실행해서 tool_result 모으기
        //     for (block in response.content where block.type == "tool_use") {
        //         Tool tool = tools.get(block.name);
        //         ToolResult result = tool.execute(block.input);
        //         toolResults.add(toolResultBlock(block.id, result));
        //     }
        //
        // (5) tool_result 들을 user 메시지로 추가하고 (2) 로
        //     messages.add(assistantMessage(response.content));
        //     messages.add(userMessage(toolResults));
        //     continue;
        //
        // (6) maxTurns 초과 시 중단 — 경고 로깅 후 throw / return
        //     if (++turn >= maxTurns) throw new IllegalStateException("max turns exceeded");
        throw new UnsupportedOperationException("TODO");
    }
}
