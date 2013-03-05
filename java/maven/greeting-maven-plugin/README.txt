
reference:

http://maven.apache.org/guides/plugin/guide-java-plugin-development.html


Maven - Mojo API Specification
http://maven.apache.org/developers/mojo-api-specification.html#The_Descriptor_and_Annotations

Maven: The Complete Reference
http://books.sonatype.com/books/mvnref-book/reference/


http://wiki.eclipse.org/M2E_Extension_Development

Using Plugin Tools Java5 Annotations
http://maven.apache.org/plugin-tools/maven-plugin-plugin/examples/using-annotations.html

Project build lifecycle mapping
https://docs.sonatype.org/display/M2ECLIPSE/Project+build+lifecycle+mapping


M2E plugin execution not covered
http://wiki.eclipse.org/M2E_plugin_execution_not_covered


mvn archetype:generate -B
  -DarchetypeGroupId=org.apache.maven.archetypes
  -DarchetypeArtifactId=maven-archetype-quickstart
  -DarchetypeVersion=1.0
  -DgroupId=com.company
  -DartifactId=project
  -Dversion=1.0
  -Dpackage=1.5

mvn archetype:generate -B -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-plugin  -DgroupId=me.test.maven -DartifactId=first-plugin -Dversion=0.0.1-SNAPSHOT -Dpackage=1.5

==============

mvn clean compile
mvn install

之后就可以在其他工程中使用了，
你可以创建一个新的空白的maven jar工程，并引入该plugin，最后运行  mvn compile 进行测试
测试用例工程的示例pom.xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>me.test</groupId>
  <artifactId>firstPluginTest</artifactId>
  <version>0.0.1-SNAPSHOT</version>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <build>
    <plugins>
      <plugin>
        <groupId>me.test.maven</groupId>
        <artifactId>greeting-maven-plugin</artifactId>
        <version>0.0.1-SNAPSHOT</version>

        <configuration>
          <name>zhang3</name>
        </configuration>

        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>greeting</goal>
            </goals>
          </execution>
        </executions>
      </plugin>

    </plugins>
    <pluginManagement>
      <plugins>
        <!--This plugin's configuration is used to store Eclipse m2e settings only. It has no influence on the Maven build itself.-->
        <plugin>
          <groupId>org.eclipse.m2e</groupId>
          <artifactId>lifecycle-mapping</artifactId>
          <version>1.0.0</version>
          <configuration>
            <lifecycleMappingMetadata>
              <pluginExecutions>
                <pluginExecution>
                  <pluginExecutionFilter>
                    <groupId>me.test.maven</groupId>
                    <artifactId>greeting-maven-plugin</artifactId>
                    <versionRange>[0.0.1-SNAPSHOT,)</versionRange>
                    <goals>
                      <goal>greeting</goal>
                    </goals>
                  </pluginExecutionFilter>
                  <action>
                    <execute>
                      <runOnIncremental>false</runOnIncremental>
                    </execute>
                  </action>
                </pluginExecution>
              </pluginExecutions>
            </lifecycleMappingMetadata>
          </configuration>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>

</project>

