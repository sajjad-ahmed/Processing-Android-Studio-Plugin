package Core;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import ui.GenerateDialogue;
import util.PathUtils;

/**
 * ------------------------------------------------------------------------------------------------*
 * File name: Core.MainAction
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 28-Dec-17
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/


public class MainAction extends AnAction
{

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        System.out.println("package---:" + PathUtils.getAppPackageName(e.getProject()));
        PsiFile manifestFile = PathUtils.getManifestFile(e.getProject());

        System.out.println("manifest---:" + manifestFile);
        System.out.println("package base dir---:" + PathUtils.getAppPackageBaseDir(e.getProject()));
        System.out.println("gradle dir---:" + PathUtils.getAppGradle(e.getProject()));
        GenerateDialogue generateDialogue = new GenerateDialogue(e.getProject());
        generateDialogue.pack();
        generateDialogue.setVisible(true);
//        Core.Manifest.resolve(e.getProject());
//        LibsHandler.resolve(e.getProject());
//        GradleResolver.resolve(e.getProject());

//        ExternalSystemUtil.refreshProject(
//                e.getProject(),new ProjectSystemId("GRADLE"), PathUtils.getRootAddress(e.getProject()), false,
//                ProgressExecutionMode.IN_BACKGROUND_ASYNC);

    }

    @Override
    public void update(AnActionEvent e)
    {

    }

    private PsiClass getPsi(AnActionEvent e)
    {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null)
        {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        if (psiClass == null)
        {
            e.getPresentation().setEnabled(false);
        }
        return psiClass;
    }
}
