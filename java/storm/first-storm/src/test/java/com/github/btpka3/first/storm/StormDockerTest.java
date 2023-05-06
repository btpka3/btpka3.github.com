package com.github.btpka3.first.storm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author dangqian.zll
 * @date 2023/3/3
 */
public class StormDockerTest {

    GenericContainer stormContainer = new GenericContainer(DockerImageName.parse("storm:2.4.0-temurin"));

    @BeforeEach
    public void setUp() {
        stormContainer = new GenericContainer(DockerImageName.parse("storm:2.4.0-temurin"));
    }

    @Test
    public void test01() {


    }
}
