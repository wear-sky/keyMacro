package pers.peng.keyMacro.main;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pers.peng.keyMacro.macro.Macro;
import pers.peng.keyMacro.ui.LoadingDDDialog;
import pers.peng.keyMacro.ui.MainFrame;
import pers.peng.keyMacro.utils.Constants.SystemStatus;

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
	public static SystemStatus systemStatus = SystemStatus.STOPPED;

	/**
	 * 正在运行中的宏
	 */
	public static Map<Integer, Macro> macros = new HashMap<Integer, Macro>();

	/**
	 * 主函数
	 * 
	 * @param args 函数参数
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LoadingDDDialog loadingDDDialog = new LoadingDDDialog();
				loadingDDDialog.setVisible(true);
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}
		});
	}

}
