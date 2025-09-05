package my.test.my.jboss.modules;

import org.jboss.modules.*;
import org.jboss.modules.Module;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/21
 */
public class MyTest {
    public void x() {
        ModuleClassLoader mcl = null;

        Module m = mcl.getModule();
        m.getClassLoader();
        ModuleFinder mf = null;

    }

    public ModuleSpec y() throws IOException {
        URI uri = URI.create("jar:/path/to/xxx.jar");
        FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
        final Path rootPath = fs.getRootDirectories().iterator().next();
        ModuleSpec.Builder builder = ModuleSpec.build("name");
        builder.addResourceRoot(
                ResourceLoaderSpec.createResourceLoaderSpec(
                        ResourceLoaders.createPathResourceLoader("root", rootPath)
                )
        );

        final Module myModule = Module.forClass(getClass());
        DependencySpec dep = new ModuleDependencySpecBuilder()
                .setName("the-dependency-name")
                .build();
        builder.addDependency(
                new ModuleDependencySpecBuilder()
                        .setName(myModule.getName())
                        .setModuleLoader(myModule.getModuleLoader())
                        .build()
        );
        DependencySpec spec = DependencySpec.createSystemDependencySpec(
                Collections.singleton("javax/smartcardio")
        );
        return builder.create();
    }

    public void testResourceLoader(){
        ResourceLoader rl = null;
        rl.getResource("String");
    }
}
