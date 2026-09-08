package com.github.btpka3.first.embabel.memory;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class IntentToolMapping {

    private String pattern;
    private List<ToolCall> tools = new ArrayList<>();
    private int hitCount;
    private double successScore;
    private Instant lastUsed;
    private String note;
}
