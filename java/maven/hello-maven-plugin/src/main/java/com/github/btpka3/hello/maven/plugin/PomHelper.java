package com.github.btpka3.hello.maven.plugin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Profile;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Helper class for modifying pom files.
 * <p>
 * copied from versions-maven-plugin and modified.
 */
public class PomHelper {
    public static final String APACHE_MAVEN_PLUGINS_GROUPID = "org.apache.maven.plugins";

    /**
     * Gets the raw model before any interpolation what-so-ever.
     *
     * @param project The project to get the raw model for.
     * @return The raw model.
     * @throws IOException if the file is not found or if the file does not parse.
     */
    public static Model getRawModel(MavenProject project)
            throws IOException {
        return getRawModel(project.getFile());
    }

    /**
     * Gets the raw model before any interpolation what-so-ever.
     *
     * @param moduleProjectFile The project file to get the raw model for.
     * @return The raw model.
     * @throws IOException if the file is not found or if the file does not parse.
     */
    public static Model getRawModel(File moduleProjectFile)
            throws IOException {
        try (FileInputStream input = new FileInputStream(moduleProjectFile)) {
            return new MavenXpp3Reader().read(input);
        } catch (XmlPullParserException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * A lightweight expression evaluation function.
     *
     * @param expr       The expression to evaluate.
     * @param properties The properties to substitute.
     * @return The evaluated expression.
     */
    public static String evaluate(String expr, Map<String, String> properties) {
        if (expr == null) {
            return null;
        }

        String expression = stripTokens(expr);
        if (expression.equals(expr)) {
            int index = expr.indexOf("${");
            if (index >= 0) {
                int lastIndex = expr.indexOf("}", index);
                if (lastIndex >= 0) {
                    String retVal = expr.substring(0, index);

                    if (index > 0 && expr.charAt(index - 1) == '$') {
                        retVal += expr.substring(index + 1, lastIndex + 1);
                    } else {
                        retVal += evaluate(expr.substring(index, lastIndex + 1), properties);
                    }

                    retVal += evaluate(expr.substring(lastIndex + 1), properties);
                    return retVal;
                }
            }

            // Was not an expression
            if (expression.contains("$$")) {
                return expression.replaceAll("\\$\\$", "\\$");
            } else {
                return expression;
            }
        }

        String value = properties.get(expression);

        if (value != null) {
            int exprStartDelimiter = value.indexOf("${");

            if (exprStartDelimiter >= 0) {
                if (exprStartDelimiter > 0) {
                    value = value.substring(0, exprStartDelimiter)
                            + evaluate(value.substring(exprStartDelimiter), properties);
                } else {
                    value = evaluate(value.substring(exprStartDelimiter), properties);
                }
            }
        } else {
            // TODO find a way to log that and not use this System.out!!
            // this class could be a component with logger injected !!
            System.out.println("expression: " + expression + " no value ");
        }
        return value == null ? expr : value;
    }

    /**
     * Strips the expression token markers from the start and end of the string.
     *
     * @param expr the string (perhaps with token markers)
     * @return the string (definately without token markers)
     */
    private static String stripTokens(String expr) {
        if (expr.startsWith("${") && expr.indexOf("}") == expr.length() - 1) {
            expr = expr.substring(2, expr.length() - 1);
        }
        return expr;
    }

    /**
     * Checks if two versions or ranges have an overlap.
     *
     * @param leftVersionOrRange  the 1st version number or range to test
     * @param rightVersionOrRange the 2nd version number or range to test
     * @return true if both versions have an overlap
     * @throws InvalidVersionSpecificationException if the versions can't be parsed to a range
     */
    public static boolean isVersionOverlap(String leftVersionOrRange, String rightVersionOrRange)
            throws InvalidVersionSpecificationException {
        VersionRange pomVersionRange = createVersionRange(leftVersionOrRange);
        if (!pomVersionRange.hasRestrictions()) {
            return true;
        }

        VersionRange oldVersionRange = createVersionRange(rightVersionOrRange);
        if (!oldVersionRange.hasRestrictions()) {
            return true;
        }

        VersionRange result = oldVersionRange.restrict(pomVersionRange);
        return result.hasRestrictions();
    }

    private static VersionRange createVersionRange(String versionOrRange)
            throws InvalidVersionSpecificationException {
        VersionRange versionRange = VersionRange.createFromVersionSpec(versionOrRange);
        if (versionRange.getRecommendedVersion() != null) {
            versionRange = VersionRange.createFromVersionSpec("[" + versionOrRange + "]");
        }
        return versionRange;
    }

    /**
     * Returns a set of all child modules for a project, including any defined in profiles (ignoring profile
     * activation).
     *
     * @param project The project.
     * @param logger  The logger to use.
     * @return the set of all child modules of the project.
     */
    public static Set getAllChildModules(MavenProject project, Log logger) {
        return getAllChildModules(project.getOriginalModel(), logger);
    }

    /**
     * Returns a set of all child modules for a project, including any defined in profiles (ignoring profile
     * activation).
     *
     * @param model  The project model.
     * @param logger The logger to use.
     * @return the set of all child modules of the project.
     */
    public static Set<String> getAllChildModules(Model model, Log logger) {
        logger.debug("Finding child modules...");
        Set<String> childModules = new TreeSet<String>();
        childModules.addAll(model.getModules());
        for (Profile profile : model.getProfiles()) {
            childModules.addAll(profile.getModules());
        }
        debugModules(logger, "Child modules:", childModules);
        return childModules;
    }

    /**
     * Outputs a debug message with a list of modules.
     *
     * @param logger  The logger to log to.
     * @param message The message to display.
     * @param modules The modules to append to the message.
     */
    public static void debugModules(Log logger, String message, Collection modules) {
        Iterator i;
        if (logger.isDebugEnabled()) {
            logger.debug(message);
            if (modules.isEmpty()) {
                logger.debug("None.");
            } else {
                i = modules.iterator();
                while (i.hasNext()) {
                    logger.debug("  " + i.next());
                }
            }

        }
    }

    /**
     * Modifies the collection of child modules removing those which cannot be found relative to the parent.
     *
     * @param logger       The logger to log to.
     * @param project      the project.
     * @param childModules the child modules.
     */
    public static void removeMissingChildModules(Log logger, MavenProject project, Collection<String> childModules) {
        removeMissingChildModules(logger, project.getBasedir(), childModules);
    }

    /**
     * Modifies the collection of child modules removing those which cannot be found relative to the parent.
     *
     * @param logger       The logger to log to.
     * @param basedir      the project basedir.
     * @param childModules the child modules.
     */
    public static void removeMissingChildModules(Log logger, File basedir, Collection<String> childModules) {
        logger.debug("Removing child modules which are missing...");
        Iterator<String> i = childModules.iterator();
        while (i.hasNext()) {
            String modulePath = i.next();
            File moduleFile = new File(basedir, modulePath);

            if (moduleFile.isDirectory() && new File(moduleFile, "pom.xml").isFile()) {
                // it's a directory that exists
                continue;
            }

            if (moduleFile.isFile()) {
                // it's the pom.xml file directly referenced and it exists.
                continue;
            }

            logger.debug("Removing missing child module " + modulePath);
            i.remove();
        }
        debugModules(logger, "After removing missing", childModules);
    }

    /**
     * Extracts the version from a raw model, interpolating from the parent if necessary.
     *
     * @param model The model.
     * @return The version.
     */
    public static String getVersion(Model model) {
        String targetVersion = model.getVersion();
        if (targetVersion == null && model.getParent() != null) {
            targetVersion = model.getParent().getVersion();
        }
        return targetVersion;
    }

    /**
     * Checks to see if the model contains an explicitly specified version.
     *
     * @param model The model.
     * @return {@code true} if the model explicitly specifies the project version, i.e. /project/version
     */
    public static boolean isExplicitVersion(Model model) {
        return model.getVersion() != null;
    }

    /**
     * Extracts the artifactId from a raw model, interpolating from the parent if necessary.
     *
     * @param model The model.
     * @return The artifactId.
     */
    public static String getArtifactId(Model model) {
        String sourceArtifactId = model.getArtifactId();
        if (sourceArtifactId == null && model.getParent() != null) {
            sourceArtifactId = model.getParent().getArtifactId();
        }
        return sourceArtifactId;
    }

    /**
     * Extracts the groupId from a raw model, interpolating from the parent if necessary.
     *
     * @param model The model.
     * @return The groupId.
     */
    public static String getGroupId(Model model) {
        String targetGroupId = model.getGroupId();
        if (targetGroupId == null && model.getParent() != null) {
            targetGroupId = model.getParent().getGroupId();
        }
        return targetGroupId;
    }

    /**
     * Builds a map of raw models keyed by module path.
     *
     * @param project The project to build from.
     * @param logger  The logger for logging.
     * @return A map of raw models keyed by path relative to the project's basedir.
     * @throws IOException if things go wrong.
     */
    public static Map<String, Model> getReactorModels(MavenProject project, Log logger)
            throws IOException {
        Map<String, Model> result = new LinkedHashMap<>();
        final Model model = getRawModel(project);
        final String path = "";
        result.put(path, model);
        result.putAll(getReactorModels(path, model, project, logger));
        return result;
    }

    /**
     * Builds a sub-map of raw models keyed by module path.
     *
     * @param path    The relative path to base the sub-map on.
     * @param model   The model at the relative path.
     * @param project The project to build from.
     * @param logger  The logger for logging.
     * @return A map of raw models keyed by path relative to the project's basedir.
     * @throws IOException if things go wrong.
     */
    private static Map<String, Model> getReactorModels(String path, Model model, MavenProject project, Log logger)
            throws IOException {
        if (path.length() > 0 && !path.endsWith("/")) {
            path += '/';
        }
        Map<String, Model> result = new LinkedHashMap<>();
        Map<String, Model> childResults = new LinkedHashMap<>();

        File baseDir = path.length() > 0 ? new File(project.getBasedir(), path) : project.getBasedir();

        Set<String> childModules = getAllChildModules(model, logger);

        removeMissingChildModules(logger, baseDir, childModules);

        for (String moduleName : childModules) {
            String modulePath = path + moduleName;

            File moduleDir = new File(baseDir, moduleName);

            File moduleProjectFile;

            if (moduleDir.isDirectory()) {
                moduleProjectFile = new File(moduleDir, "pom.xml");
            } else {
                // i don't think this should ever happen... but just in case
                // the module references the file-name
                moduleProjectFile = moduleDir;
            }

            try {
                // the aim of this goal is to fix problems when the project cannot be parsed by Maven
                // so we have to work with the raw model and not the interpolated parsed model from maven
                Model moduleModel = getRawModel(moduleProjectFile);
                result.put(modulePath, moduleModel);
                childResults.putAll(getReactorModels(modulePath, moduleModel, project, logger));
            } catch (IOException e) {
                logger.debug("Could not parse " + moduleProjectFile.getPath(), e);
            }
        }
        result.putAll(childResults); // more efficient update order if all children are added after siblings
        return result;
    }

    /**
     * Returns all the models that have a specified groupId and artifactId as parent.
     *
     * @param reactor    The map of models keyed by path.
     * @param groupId    The groupId of the parent.
     * @param artifactId The artifactId of the parent.
     * @return a map of models that have a specified groupId and artifactId as parent keyed by path.
     */
    public static Map<String, Model> getChildModels(Map<String, Model> reactor, String groupId, String artifactId) {
        final Map<String, Model> result = new LinkedHashMap<String, Model>();
        for (Map.Entry<String, Model> entry : reactor.entrySet()) {
            final String path = entry.getKey();
            final Model model = entry.getValue();
            final Parent parent = model.getParent();
            if (parent != null && groupId.equals(parent.getGroupId())
                    && artifactId.equals(parent.getArtifactId())) {
                result.put(path, model);
            }
        }
        return result;
    }

    /**
     * Returns the model that has the specified groupId and artifactId or <code>null</code> if no such model exists.
     *
     * @param reactor    The map of models keyed by path.
     * @param groupId    The groupId to match.
     * @param artifactId The artifactId to match.
     * @return The model or <code>null</code> if the model was not in the reactor.
     */
    public static Model getModel(Map<String, Model> reactor, String groupId, String artifactId) {
        Map.Entry<String, Model> entry = getModelEntry(reactor, groupId, artifactId);
        return entry == null ? null : entry.getValue();
    }

    /**
     * Returns the model that has the specified groupId and artifactId or <code>null</code> if no such model exists.
     *
     * @param reactor    The map of models keyed by path.
     * @param groupId    The groupId to match.
     * @param artifactId The artifactId to match.
     * @return The model entry or <code>null</code> if the model was not in the reactor.
     */
    public static Map.Entry<String, Model> getModelEntry(Map<String, Model> reactor, String groupId,
                                                         String artifactId) {
        for (Map.Entry<String, Model> entry : reactor.entrySet()) {
            Model model = entry.getValue();
            if (groupId.equals(getGroupId(model)) && artifactId.equals(getArtifactId(model))) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Returns a count of how many parents a model has in the reactor.
     *
     * @param reactor The map of models keyed by path.
     * @param model   The model.
     * @return The number of parents of this model in the reactor.
     */
    public static int getReactorParentCount(Map<String, Model> reactor, Model model) {
        if (model.getParent() == null) {
            return 0;
        } else {
            Model parentModel = getModel(reactor, model.getParent().getGroupId(), model.getParent().getArtifactId());
            if (parentModel != null) {
                return getReactorParentCount(reactor, parentModel) + 1;
            }
            return 0;
        }
    }

    /**
     * Reads a file into a String.
     *
     * @param outFile The file to read.
     * @return String The content of the file.
     * @throws IOException when things go wrong.
     */
    public static StringBuilder readXmlFile(File outFile)
            throws IOException {
        try (Reader reader = ReaderFactory.newXmlReader(outFile)) {
            return new StringBuilder(IOUtil.toString(reader));
        }
    }

    /**
     * Returns the GAV coordinates of a model.
     *
     * @param model the model.
     * @return the GAV coordinates of a model.
     */
    public static String getGAV(Model model) {
        return getGroupId(model) + ":" + getArtifactId(model) + ":" + getVersion(model);
    }

}
