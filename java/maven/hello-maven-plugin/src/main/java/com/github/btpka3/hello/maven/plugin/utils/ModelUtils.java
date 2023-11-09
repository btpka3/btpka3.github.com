package com.github.btpka3.hello.maven.plugin.utils;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.function.Function;

/**
 * @author dangqian.zll
 * @date 2023/11/1
 */
public class ModelUtils {

    /**
     * 注意：读取后的内容不会包含任何注释信息。
     *
     * @param reader
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static Model readPom(Reader reader) throws XmlPullParserException, IOException {
        MavenXpp3Reader mavenReader = new MavenXpp3Reader();
        return mavenReader.read(reader);
    }

    public static void writePom(Writer writer, Model model) throws IOException {
        MavenXpp3Writer mavenWriter = new MavenXpp3Writer();
        mavenWriter.write(writer, model);
    }

    public static void addExcludeToDependencyManagement(
            Model model,
            Function<Dependency, Boolean> dependencyFilter,
            List<Exclusion> exclusionList
    ) {
        // TODO
    }


}
