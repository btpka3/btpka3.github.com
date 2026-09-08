package com.github.btpka3.first.spring.boot4.ai.embabel;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.RequireNameMatch;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.api.common.workflow.control.SimpleAgentBuilder;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.Blackboard;
import com.embabel.agent.domain.io.UserInput;
import lombok.Data;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */
@Agent(description = "Agent that writes and reviews stories")
public class DemoAgent {
    @Action
    public String writeStory(UserInput userInput, OperationContext context) {
        return context.ai()
                .withAutoLlm()
                .createObject("""
                                You are a creative writer who aims to delight and surprise.
                                Write a story about %s
                                """.formatted(userInput.getContent()),
                        String.class
                );
    }

    @AchievesGoal(description = "Review a story")
    @Action
    public String reviewStory(@RequireNameMatch String story, OperationContext context) {
        return context.ai()
                .withLlmByRole("reviewer")
                .createObject("""
                                    You are a meticulous editor.
                                    Carefully review this story:
                                    %s
                                """.formatted(story),
                        String.class);
    }

    protected void x(Ai ai, Blackboard blackboard) {
        // Add objects to blackboard
        blackboard.addObject(new Object());
        blackboard.set("result", new Object());

        // Retrieve objects by type
        Person person = blackboard.last(Person.class);
        List<Person> allPersons = blackboard.objectsOfType(Person.class);

        // Check conditions
        blackboard.setCondition("userVerified", true);
        boolean verified = blackboard.getCondition("userVerified"); // ②

        // Hide an object
        blackboard.hide("somethingWeDontWantToPlanOnLater"); // ③

        AgentProcess agentProcess = null;
    }

    protected void x() {
        var agent = SimpleAgentBuilder
                .returning(Person.class) // ①
                .running(tac -> tac.ai() // ②
                        .withDefaultLlm()
                        .createObject("Tell me a joke", Person.class))
                .buildAgent("joker", "This is guaranteed to return a dreadful joke"); // ③


    }

    @Data
    public static class Person {
        private String name;
    }
}
