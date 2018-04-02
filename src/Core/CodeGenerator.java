package Core;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import org.jetbrains.annotations.NotNull;

/**
 * ------------------------------------------------------------------------------------------------*
 * File name: CodeGenerator
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 04-Jan-18
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/
public class CodeGenerator
{

    private Project project;

    public CodeGenerator(Project p)
    {
        project = p;
    }

    public void generateSketch(PsiClass psiClass)
    {
        new WriteCommandAction(project)
        {
            @Override
            protected void run(@NotNull Result result)
            {
                StringBuilder setup_builder = new StringBuilder("public void setup() {");
                setup_builder.append("\n    //your code here\n");
                setup_builder.append("}");

                StringBuilder settings_builder = new StringBuilder("public void settings() {\n");
                settings_builder.append("    //your code here\n");
                settings_builder.append("    //Ex: size(600, 600);\n");
                settings_builder.append("}");

                StringBuilder draw_builder = new StringBuilder("public void draw() {\n");
                draw_builder.append("    //your code here\n");
                draw_builder.append("}");

                PsiElementFactory psiElementFactory = JavaPsiFacade.getElementFactory(project);

                PsiElement pApplet = psiClass.getExtendsList().add(psiElementFactory.createPackageReferenceElement("PApplet"));
                JavaCodeStyleManager.getInstance(project).shortenClassReferences(pApplet);

                PsiMethod setupPsiMethod = psiElementFactory.createMethodFromText(setup_builder.toString(), psiClass);
                PsiMethod settingsPsiMethod = psiElementFactory.createMethodFromText(settings_builder.toString(), psiClass);
                PsiMethod drawPsiMethod = psiElementFactory.createMethodFromText(draw_builder.toString(), psiClass);

                psiElementFactory.createImportStatement(psiClass); //to remove

                psiClass.add(setupPsiMethod);
                psiClass.add(settingsPsiMethod);
                psiClass.add(drawPsiMethod);

            }
        }.execute();
    }

    public void generateMain(PsiClass psiClass)
    {
        new WriteCommandAction(project)
        {
            @Override
            protected void run(@NotNull Result result)
            {
                StringBuilder onCreate_builder = new StringBuilder("public void onCreate(Bundle savedInstanceState) {");
                onCreate_builder.append("\n        super.onCreate(savedInstanceState);\n");
                onCreate_builder.append("        FrameLayout frame = new FrameLayout(this);\n");
                onCreate_builder.append("        frame.setId(CompatUtils.getUniqueViewId());\n");
                onCreate_builder.append("        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));\n");
                onCreate_builder.append("        sketch = new Sketch();\n");
                onCreate_builder.append("        PFragment fragment = new PFragment(sketch);\n");
                onCreate_builder.append("        fragment.setView(frame, this);\n");
                onCreate_builder.append("}");

                StringBuilder onNewIntent_builder = new StringBuilder("public void onNewIntent(Intent intent) {\n");
                onNewIntent_builder.append("    if (sketch != null) {\n");
                onNewIntent_builder.append("        sketch.onNewIntent(intent);\n");
                onNewIntent_builder.append("    }");
                onNewIntent_builder.append("}");

                StringBuilder onRequestPermissionsResult_builder = new StringBuilder("public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {\n");
                onRequestPermissionsResult_builder.append("    if (sketch != null) {\n");
                onRequestPermissionsResult_builder.append("    sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
                onRequestPermissionsResult_builder.append("    }\n");
                onRequestPermissionsResult_builder.append("}");

                PsiElementFactory psiElementFactory = JavaPsiFacade.getElementFactory(project);


                PsiElement appCompatActivity_psiElement = psiClass.getExtendsList().add(psiElementFactory.createPackageReferenceElement("AppCompatActivity"));

                JavaCodeStyleManager.getInstance(project).shortenClassReferences(appCompatActivity_psiElement); //.shortenClassReferences(pApplet);

                PsiMethod onCreatePsiMethod = psiElementFactory.createMethodFromText(onCreate_builder.toString(), psiClass);
                PsiMethod newIntentPsiMethod = psiElementFactory.createMethodFromText(onNewIntent_builder.toString(), psiClass);
                PsiMethod onRequestPermissionResultPsiMethod = psiElementFactory.createMethodFromText(onRequestPermissionsResult_builder.toString(), psiClass);


                psiClass.add(onCreatePsiMethod);
                psiClass.add(newIntentPsiMethod);
                psiClass.add(onRequestPermissionResultPsiMethod);

            }
        }.execute();
    }
}
