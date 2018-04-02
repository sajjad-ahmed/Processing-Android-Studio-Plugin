package Core;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.command.undo.UndoUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementFactory;
import com.intellij.psi.xml.*;

import org.jetbrains.annotations.NotNull;

import static util.PathUtils.getManifestFile;

/**
 * ------------------------------------------------------------------------------------------------*
 * File name: Core.Manifest
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 28-Dec-17
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/


public class Manifest
{

    protected static void resolve(Project project, String className)
    {
        PsiFile manifestFile = getManifestFile(project);
        XmlDocument xml = (XmlDocument) manifestFile.getFirstChild();
        XmlTag[] xmlRoot = xml.getRootTag().getSubTags();

        new WriteCommandAction(project)
        {
            @Override
            protected void run(@NotNull Result result)
            {
                XmlTag tagFromText = XmlElementFactory.getInstance(project).createTagFromText("<activity android:name=\"." + className + "\"></activity>");
                for (XmlTag t : xmlRoot)
                {
                    if (t.getName().equals("application"))
                    {
                        t.addSubTag(tagFromText, true);
                        break;
                    }
                }
                UndoUtil.markPsiFileForUndo(manifestFile);
            }
        }.execute();
    }
}