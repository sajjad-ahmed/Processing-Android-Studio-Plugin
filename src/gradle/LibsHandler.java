package gradle;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.io.IOException;

import util.PathUtils;
import util.Str;


/**
 * ------------------------------------------------------------------------------------------------*
 * File name: LibsHandler
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 28-Dec-17
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/


public class LibsHandler
{
    public static void resolve(Project project)
    {
        File file = null;
        try
        {
            IdeaPluginDescriptor runtimePlugin = PluginManager.getPlugin(PluginId.getId(Str.PLUGIN_ID));
            file = new File(runtimePlugin.getPath().getAbsolutePath(), "classes" + File.separator + "assets" + File.separator + Str.JAR_FILE_NAME);
        } catch (Exception e)
        {

            e.printStackTrace();
        }

        try
        {
            File outputFile = new File(PathUtils.getLibDir(project) + Str.JAR_FILE_NAME);
            outputFile.createNewFile();
            FileUtil.copy(file, outputFile);
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
}
