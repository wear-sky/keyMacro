package org.wearsky.keyMacro.main;

import org.wearsky.keyMacro.macro.Macro;
import org.wearsky.keyMacro.ui.LoadingDDDialog;
import org.wearsky.keyMacro.ui.MainFrame;
import org.wearsky.keyMacro.utils.Constants.SystemStatus;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 主类
 *
 * @author peng
 *
 */
public class Main {

    /**
     * 系统状态
     *
     * @see SystemStatus
     */
    public static volatile SystemStatus systemStatus = SystemStatus.STOPPED;

    /**
     * 正在运行中的宏
     */
    public static Map<Integer, Macro> macros = new HashMap<>();

    /**
     * 主函数
     *
     * @param args 函数参数
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException _) {
        }
        EventQueue.invokeLater(() -> {
            LoadingDDDialog loadingDDDialog = new LoadingDDDialog();
            loadingDDDialog.setVisible(true);
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

}
