package com.github.btpka3.first.intellij.plugin;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author dangqian.zll
 * @date 2021/2/19
 */
@State(name="btpka3.DemoProjectService.state")
@Service
public final class DemoProjectService implements PersistentStateComponent<DemoProjectService.State> {

    static class State {
        public String value;
    }

    private State myState = new State();

    private static final ExtensionPointName<DemoExtensionPoint> EP_NAME = ExtensionPointName.create("com.github.btpka3.myDynamicExtensionPoint");

    private final Project myProject;

    public DemoProjectService(Project project) {
        myProject = project;
    }

    public void someServiceMethod(String parameter) {
        //AnotherService anotherService = myProject.getService(AnotherService.class);
        //String result = anotherService.anotherServiceMethod(parameter, false);
        // do some more stuff

        for (DemoExtensionPoint extension : EP_NAME.getExtensionList()) {
            String key = extension.getKey();
            System.out.println("key = " + key);
        }
    }

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }
}
