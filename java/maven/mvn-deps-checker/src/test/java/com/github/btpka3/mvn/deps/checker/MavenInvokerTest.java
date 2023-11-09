package com.github.btpka3.mvn.deps.checker;

import lombok.SneakyThrows;
import org.apache.maven.shared.invoker.*;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dangqian.zll
 * @date 2023/9/19
 */
public class MavenInvokerTest {

    @SneakyThrows
    public void test01() {
        InvocationRequest request = new DefaultInvocationRequest();
        String pomPath = null;
        request.setPomFile(new File(pomPath));
        request.setGoals(Arrays.asList("dependency:list"));
        Properties properties = new Properties();
        properties.setProperty("outputFile", "dependencies.txt"); // redirect output to a file
        properties.setProperty("outputAbsoluteArtifactFilename", "true"); // with paths
        properties.setProperty("includeScope", "runtime"); // only runtime (scope compile + runtime)
// if only interested in scope runtime, you may replace with excludeScope = compile
        request.setProperties(properties);

        Invoker invoker = new DefaultInvoker();
// the Maven home can be omitted if the "maven.home" system property is set
        invoker.setMavenHome(new File("/path/to/maven/home"));
        invoker.setOutputHandler(null); // not interested in Maven output itself
        InvocationResult result = invoker.execute(request);
        if (result.getExitCode() != 0) {
            throw new IllegalStateException("Build failed.");
        }

        Pattern pattern = Pattern.compile("(?:compile|runtime):(.*)");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("dependencies.txt"))) {
            while (!"The following files have been resolved:".equals(reader.readLine())) ;
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // group 1 contains the path to the file
                    System.out.println(matcher.group(1));
                }
            }
        }
    }

}
