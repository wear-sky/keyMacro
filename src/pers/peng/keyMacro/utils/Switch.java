package pers.peng.keyMacro.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JTable;

import pers.peng.keyMacro.macro.ContinualMacro;
import pers.peng.keyMacro.macro.Macro;
import pers.peng.keyMacro.macro.OnceMacro;
import pers.peng.keyMacro.macro.SwitchMacro;
import pers.peng.keyMacro.macroCell.DelayMC;
import pers.peng.keyMacro.macroCell.KeyboardMC;
import pers.peng.keyMacro.macroCell.MacroCell;
import pers.peng.keyMacro.macroCell.MouseClickMC;
import pers.peng.keyMacro.macroCell.MouseMoveMC;
import pers.peng.keyMacro.main.Main;
import pers.peng.keyMacro.ui.MainFrame;
import pers.peng.keyMacro.ui.MainFrame.MacrosTableModel;
import pers.peng.keyMacro.utils.Constants.DdCode;
import pers.peng.keyMacro.utils.Constants.SystemStatus;
import pers.peng.keyMacro.utils.Constants.TriggerType;
import pers.peng.keyMacro.utils.Constants.VkCode;

/**
 * 状态切换类
 * 
 * @author peng
 *
 */
public class Switch {

	/**
	 * 启动运行
	 * 
	 * @param mainFrame 主窗口
	 */
	public static void start(MainFrame mainFrame) {
		if (Main.systemStatus == SystemStatus.STOPPED) {
			synchronized (Switch.class) {
				if (Main.systemStatus == SystemStatus.STOPPED) {
					doStart(mainFrame);
					changeUI(mainFrame, true);
				}
			}
		}
	}

	/**
	 * 暂停运行
	 * 
	 * @param mainFrame 主窗口
	 */
	public static void stop(MainFrame mainFrame) {
		if (Main.systemStatus == SystemStatus.RUNNING) {
			synchronized (Switch.class) {
				if (Main.systemStatus == SystemStatus.RUNNING) {
					doStop(mainFrame);
					changeUI(mainFrame, false);
				}
			}
		}
	}

	/**
	 * 切换配置
	 * 
	 * @param mainFrame 主窗口
	 */
	public static void switchConfig(MainFrame mainFrame) {
		synchronized (Switch.class) {
			if (Main.systemStatus == SystemStatus.RUNNING) {
				doStop(mainFrame);
				doSwitchConfig(mainFrame);
				doStart(mainFrame);
				changeUI(mainFrame, true);
			} else {
				doSwitchConfig(mainFrame);
				changeUI(mainFrame, false);
			}
		}
	}

	/**
	 * 执行启动运行过程
	 * 
	 * @param mainFrame 主窗口
	 */
	private static void doStart(MainFrame mainFrame) {
		JTable macrosTable = mainFrame.getMacrosTable();
		int rowCount = macrosTable.getRowCount();
		for (int row = 0; row < rowCount; row++) {
			VkCode triggerKey = VkCode.getVkCode((String) macrosTable.getValueAt(row, 0));
			if (triggerKey == null) {
				continue;
			}
			String[] ddCodesName = ((String) macrosTable.getValueAt(row, 1)).split(",");
			int length = ddCodesName.length;
			MacroCell[] macroCells = new MacroCell[length];
			for (int index = 0; index < length; index++) {
				if (ddCodesName[index].startsWith("↓")) {
					macroCells[index] = new KeyboardMC(DdCode.getDdCode(ddCodesName[index].substring(1)).value(), 1);
				} else if (ddCodesName[index].startsWith("↑")) {
					macroCells[index] = new KeyboardMC(DdCode.getDdCode(ddCodesName[index].substring(1)).value(), 2);
				} else if (ddCodesName[index].startsWith("→")) {
					String[] xy = ddCodesName[index].substring(1).split("x");
					int x = Integer.parseInt(xy[0]);
					int y = Integer.parseInt(xy[1]);
					macroCells[index] = new MouseMoveMC(x, y);
				} else if (ddCodesName[index].startsWith("-")) {
					macroCells[index] = new DelayMC(Long.parseLong(ddCodesName[index].substring(1)));
				} else {
					macroCells[index] = new MouseClickMC(DdCode.getDdCode(ddCodesName[index].substring(1)).value());
				}
			}
			Macro macro;
			switch (TriggerType.getTriggerType((String) macrosTable.getValueAt(row, 2))) {
			case 开关:
				macro = new SwitchMacro(macroCells, Long.valueOf((String) macrosTable.getValueAt(row, 3)));
				break;
			case 单次:
				macro = new OnceMacro(macroCells);
				break;
			default:
				macro = new ContinualMacro(macroCells, Long.valueOf((String) macrosTable.getValueAt(row, 3)));
				break;
			}
			macro.start();
			Main.macros.put(triggerKey.value(), macro);
		}
		Main.systemStatus = SystemStatus.RUNNING;
	}

	/**
	 * 执行暂停运行过程
	 * 
	 * @param mainFrame 主窗口
	 */
	private static void doStop(MainFrame mainFrame) {
		Main.systemStatus = SystemStatus.STOPPED;
		Iterator<Macro> macrosIterator = Main.macros.values().iterator();
		while (macrosIterator.hasNext()) {
			Macro macro = macrosIterator.next();
			macro.delete();
		}
		Main.macros.clear();
	}

	/**
	 * 执行切换配置过程
	 * 
	 * @param mainFrame 主窗口
	 */
	private static void doSwitchConfig(MainFrame mainFrame) {
		JTable macrosTable = mainFrame.getMacrosTable();
		MacrosTableModel macrosTableModel = (MacrosTableModel) macrosTable.getModel();
		while (macrosTableModel.getRowCount() != 0) {
			macrosTableModel.removeRow(0);
		}
		JComboBox<String> selectConfigComboBox = mainFrame.getSelectConfigComboBox();
		if (selectConfigComboBox.getSelectedIndex() == -1) {
			return;
		}
		File configFile = new File("./config/" + selectConfigComboBox.getSelectedItem().toString().trim() + ".ini");
		if (!configFile.exists()) {
			return;
		}
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(configFile);
			bufferedReader = new BufferedReader(fileReader);
			mainFrame.getSwitchField().setText(bufferedReader.readLine());
			int columnCount = macrosTable.getColumnCount();
			String[] macro = new String[columnCount];
			while (true) {
				for (int column = 0; column < columnCount; column++) {
					macro[column] = bufferedReader.readLine();
				}
				if (macro[0] == null || "".equals(macro[0])) {
					break;
				}
				macrosTableModel.addRow(macro);
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				fileReader.close();
				bufferedReader.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	/**
	 * 修UI面控件状态
	 * 
	 * @param mainFrame 主窗口
	 * @param isRunning 是否正在运行中
	 */
	private static void changeUI(MainFrame mainFrame, boolean isRunning) {
		if (mainFrame.getSelectConfigComboBox().getSelectedIndex() == -1) {
			mainFrame.getSelectConfigComboBox().setEnabled(true);
			mainFrame.getCreateConfigButton().setEnabled(true);
			mainFrame.getDeleteConfigButton().setEnabled(true);
			mainFrame.getInputConfigButton().setEnabled(true);
			mainFrame.getOutputConfigButton().setEnabled(true);
			mainFrame.getSwitchConfigField().setEnabled(true);
			mainFrame.getCreateMacroButton().setEnabled(false);
			mainFrame.getModifyMacroButton().setEnabled(false);
			mainFrame.getDeleteMacroButton().setEnabled(false);
			mainFrame.getClearMacroButton().setEnabled(false);
			mainFrame.getSwitchField().setEnabled(false);
			mainFrame.getSwitchButton().setText("开始");
			mainFrame.getSwitchButton().setEnabled(false);
			mainFrame.getTrayIcon().setImage(IconUtils.getTrayIcon());
			return;
		}
		mainFrame.getSelectConfigComboBox().setEnabled(!isRunning);
		mainFrame.getCreateConfigButton().setEnabled(!isRunning);
		mainFrame.getDeleteConfigButton().setEnabled(!isRunning);
		mainFrame.getInputConfigButton().setEnabled(!isRunning);
		mainFrame.getOutputConfigButton().setEnabled(!isRunning);
		mainFrame.getSwitchConfigField().setEnabled(!isRunning);
		mainFrame.getCreateMacroButton().setEnabled(!isRunning);
		mainFrame.getModifyMacroButton().setEnabled(!isRunning);
		mainFrame.getDeleteMacroButton().setEnabled(!isRunning);
		mainFrame.getClearMacroButton().setEnabled(!isRunning);
		mainFrame.getSwitchField().setEnabled(!isRunning);
		mainFrame.getSwitchButton().setText(isRunning ? "停止" : "开始");
		mainFrame.getSwitchButton().setEnabled(true);
		mainFrame.getTrayIcon().setImage(IconUtils.getTrayIcon(
				mainFrame.getSelectConfigComboBox().getSelectedItem().toString().substring(0, 1), isRunning));
		if (isRunning) {
			mainFrame.setVisible(false);
		}
	}

}
