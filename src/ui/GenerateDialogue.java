package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Core.CodeGenerator;
import gradle.GradleResolver;
import util.PathUtils;


/**
 * ------------------------------------------------------------------------------------------------*
 * File name: GenerateDialogue
 * Project name: Processing
 * Author: SAJJAD AHMED NILOY
 * Created on: 28-Dec-17
 * License: MIT License [Copyright (c) 2018 sajjad ahmed niloy]
 * License link: https://opensource.org/licenses/MIT
 * ------------------------------------------------------------------------------------------------*
 **/


public class GenerateDialogue extends JDialog
{
    private Project project;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel sampleLabel;
    private JTextField activityNameField;
    private JTextField sketchNameField;
    private JTextField layoutNameField;
    private JLabel errorLabel;


    public GenerateDialogue(Project project)
    {

        this.project = project;
        contentPane.setRequestFocusEnabled(true);

        contentPane.setSize(880, 440);

        setContentPane(contentPane);
        setModal(true);


        getRootPane().setDefaultButton(buttonOK);
        initGUIElements();


        sampleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/sample1.png"))); // NOI18N
        sampleLabel.setSize(256, 384);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initGUIElements()
    {
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());


        activityNameField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                changedTextAction();
            }

            public void removeUpdate(DocumentEvent e)
            {
                changedTextAction();
            }

            public void insertUpdate(DocumentEvent e)
            {
                changedTextAction();
            }

            public void changedTextAction()
            {
                System.out.println(activityNameField.getText());
            }
        });
    }

    private void onOK()
    {
        GradleResolver.resolve(project);

        String activityName_str = activityNameField.getText();
        String sketchName_str = sketchNameField.getText();
        String layoutName_str = layoutNameField.getText();

        String error_str = "";
//        if (!activityName_str.equals("") && !sketchName_str.equals("") && !layoutName_str.equals("")) {
//            VirtualFile smoduleSourceRoot = ProjectRootManager.getInstance(project).getFileIndex().getSourceRootForFile(psiClass.getContainingFile().getVirtualFile());
        VirtualFile appPackageBaseDir = PathUtils.getAppPackageBaseDir(project);
        PsiDirectory rootPsiDirectory = PsiManager.getInstance(project).findDirectory(appPackageBaseDir);//.getContainingFile().getContainingDirectory();
        PsiClass mainClass = JavaDirectoryService.getInstance().createClass(rootPsiDirectory, activityName_str);
        PsiClass sketchClass = JavaDirectoryService.getInstance().createClass(rootPsiDirectory, sketchName_str);
        PsiClass layoutFile = JavaDirectoryService.getInstance().createClass(rootPsiDirectory, layoutName_str);


        CodeGenerator codeGenerator = new CodeGenerator(project);
        codeGenerator.generateMain(mainClass);
        codeGenerator.generateSketch(sketchClass);

//        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
//        factory.createVariableDeclarationStatement()
//        PsiImportStatement importStatement = factory.createImportStatement(mainClass);
//        JavaCodeStyleManager.getInstance(project).shortenClassReferences(importStatement);


        errorLabel.setVisible(false);
        dispose();

//        } else {
//            int errorCount = 0;
//            if (TextUtils.isEmpty(activityName_str)) {
//                error_str.concat("Activity name, ");
//                errorCount++;
//            }
//
//            if (TextUtils.isEmpty(sketchName_str)) {
//                error_str.concat("Sketch name, ");
//                errorCount++;
//            }
//
//            if (TextUtils.isEmpty(layoutName_str)) {
//                error_str.concat("Layout name, ");
//                errorCount++;
//            }
//            error_str.concat("can not be empty.");
//            System.out.println("_________ error");
//            errorLabel.setText("la la lal allaa");
////            errorLabel.setVisible(true);
////            contentPane.updateUI();
//
//        }

    }


    private void onCancel()
    {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        GenerateDialogue dialog = new GenerateDialogue();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
