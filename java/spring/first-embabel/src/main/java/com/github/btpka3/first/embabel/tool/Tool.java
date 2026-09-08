package com.github.btpka3.first.embabel.tool;

import java.util.Map;

public interface Tool {

    String name();

    String description();

    String execute(Map<String, String> args);
}
