package gradle;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import util.PathUtils;

/**
 * ------------------------------------------------------------------------------------------------*
 * File name: GradleResolver
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 04-Jan-18
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/
public class GradleResolver {
    public static void resolve(Project project) {
        PsiFile psiFile = PathUtils.getAppGradle(project);
        Document buildGradleDoc = PsiDocumentManager.getInstance(project).getDocument(psiFile);
        String str_buildGradleDoc = psiFile.getText();
        if (str_buildGradleDoc.contains("libs/processing-core.jar"))
        {
            return;
        }
        int d_index = str_buildGradleDoc.indexOf("dependencies");
        for (int i = d_index; d_index < str_buildGradleDoc.length(); i++) {
            if (str_buildGradleDoc.charAt(i) == '{') {
                d_index = i;
                break;
            }
        }
        int fina_scope_index = d_index;
        Runnable r = () -> {
            String dependencyText = "\n    compile files('libs/processing-core.jar')\n";
            buildGradleDoc.insertString(fina_scope_index + 1, dependencyText);
        };

        WriteCommandAction.runWriteCommandAction(psiFile.getProject(), r);
    }
}
