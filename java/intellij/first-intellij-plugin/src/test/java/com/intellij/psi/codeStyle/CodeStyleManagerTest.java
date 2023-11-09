package com.intellij.psi.codeStyle;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;
import org.junit.jupiter.api.Test;

public class CodeStyleManagerTest {

    @Test
    public void test() throws Exception {

        String emptyPrjectDir = System.getProperty("user.home") + "/IdeaProjects/empy-proj";

        Project project = ProjectManager.getInstance().loadAndOpenProject(emptyPrjectDir);
        CodeStyleManager styleManager = CodeStyleManager.getInstance(project);
        PsiElement psiFile = null;
        styleManager.reformat(psiFile);
    }
}