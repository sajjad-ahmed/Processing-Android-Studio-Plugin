package util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * ------------------------------------------------------------------------------------------------*
 * File name: PathUtils
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 28-Dec-17
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/

public class PathUtils {


    public static VirtualFile getAppPackageBaseDir(Project project) {
        try {
            String path = project.getBasePath() + File.separator +
                    "app" + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "java" + File.separator +
                    getAppPackageName(project).replace(".", File.separator);
            return LocalFileSystem.getInstance().findFileByPath(path);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRootAddress(Project project) {
        try {
            String path = project.getBasePath() + File.separator +
                    "app" + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "java" + File.separator +
                    getAppPackageName(project).replace(".", File.separator);
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    public static PsiFile getManifestFile(Project project) {
        try {
            String path = project.getBasePath() + File.separator +
                    "app" + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "AndroidManifest.xml";
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
            if (virtualFile == null) return null;
            return PsiManager.getInstance(project).findFile(virtualFile);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLibDir(Project project) {
        try {
            String path = project.getBasePath() + File.separator +
                    "app" + File.separator +
                    "libs" + File.separator;
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    public static PsiFile getAppGradle(Project project) {
        try {
            String path = project.getBasePath() + File.separator +
                    "app" + File.separator +
                    "build.gradle";
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
            if (virtualFile == null) return null;
            return PsiManager.getInstance(project).findFile(virtualFile);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppPackageName(Project project) {
        try {
            PsiFile manifestFile = getManifestFile(project);
            XmlDocument xml = (XmlDocument) manifestFile.getFirstChild();
            return xml.getRootTag().getAttribute("package").getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String getFilePackageName(VirtualFile dir) {
        if (!dir.isDirectory()) {
            dir = dir.getParent();
        }
        String path = dir.getPath().replace("/", ".");
        String preText = "src.main.java";
        int preIndex = path.indexOf(preText) + preText.length() + 1;
        path = path.substring(preIndex);
        return path;
    }

    //todo nont working
    private PsiDirectory findDirectory(String path, PsiDirectory baseDir) {
        PsiDirectory res = baseDir;
        String[] s = StringUtils.splitPreserveAllTokens(path, ".");
        for (int i = 0; i < s.length; ++i) {
            res = res.findSubdirectory(s[i]);
            if (null == res) {
                return res;
            }
        }

        return res;
    }

    public static boolean fileExists(Project project, String fileName, String folderPath) throws IOException
    {
        try {
            return project.getBaseDir().findFileByRelativePath(folderPath).findFileByRelativePath(fileName).exists();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
