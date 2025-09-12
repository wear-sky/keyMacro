package org.wearsky.keyMacro.utils;

import org.wearsky.keyMacro.macro.ContinualMacro;
import org.wearsky.keyMacro.macro.Macro;
import org.wearsky.keyMacro.macro.OnceMacro;
import org.wearsky.keyMacro.macro.SwitchMacro;
import org.wearsky.keyMacro.macroCell.*;
import org.wearsky.keyMacro.main.Main;
import org.wearsky.keyMacro.ui.MainFrame;
import org.wearsky.keyMacro.ui.MainFrame.MacrosTableModel;
import org.wearsky.keyMacro.utils.Constants.DdCode;
import org.wearsky.keyMacro.utils.Constants.SystemStatus;
import org.wearsky.keyMacro.utils.Constants.TriggerType;
import org.wearsky.keyMacro.utils.Constants.VkCode;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

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
                    doStop();
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
                doStop();
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
                    macroCells[index] = new KeyboardMC(Objects.requireNonNull(DdCode.getDdCode(ddCodesName[index].substring(1))).value(), KeyboardMC.KEY_DOWN);
                } else if (ddCodesName[index].startsWith("↑")) {
                    macroCells[index] = new KeyboardMC(Objects.requireNonNull(DdCode.getDdCode(ddCodesName[index].substring(1))).value(), KeyboardMC.KEY_UP);
                } else if (ddCodesName[index].startsWith("→")) {
                    String[] xy = ddCodesName[index].substring(1).split("x");
                    int x = Integer.parseInt(xy[0]);
                    int y = Integer.parseInt(xy[1]);
                    macroCells[index] = new MouseMoveMC(x, y);
                } else if (ddCodesName[index].startsWith("-")) {
                    macroCells[index] = new DelayMC(Long.parseLong(ddCodesName[index].substring(1)));
                } else {
                    macroCells[index] = new MouseClickMC(Objects.requireNonNull(DdCode.getDdCode(ddCodesName[index].substring(1))).value());
                }
            }
            Macro macro = switch (Objects.requireNonNull(TriggerType.getTriggerType((String) macrosTable.getValueAt(row, 2)))) {
                case 开关 -> new SwitchMacro(macroCells, Long.parseLong((String) macrosTable.getValueAt(row, 3)));
                case 单次 -> new OnceMacro(macroCells);
                default -> new ContinualMacro(macroCells, Long.parseLong((String) macrosTable.getValueAt(row, 3)));
            };
            macro.start();
            Main.macros.put(triggerKey.value(), macro);
        }
        Main.systemStatus = SystemStatus.RUNNING;
    }

    /**
     * 执行暂停运行过程
     *
     */
    private static void doStop() {
        Main.systemStatus = SystemStatus.STOPPED;
        for (Macro macro : Main.macros.values()) {
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
        File configFile = new File("./config/" + Objects.requireNonNull(selectConfigComboBox.getSelectedItem()).toString().trim() + ".ini");
        if (!configFile.exists()) {
            return;
        }
        try (FileReader fileReader = new FileReader(configFile); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            try {
                mainFrame.getSwitchField().setText(bufferedReader.readLine());
                int columnCount = macrosTable.getColumnCount();
                String[] macro = new String[columnCount];
                while (true) {
                    for (int column = 0; column < columnCount; column++) {
                        macro[column] = bufferedReader.readLine();
                    }
                    if (macro[0] == null || macro[0].isEmpty()) {
                        break;
                    }
                    macrosTableModel.addRow(macro);
                }
            } catch (IOException _) {
            }
        } catch (IOException _) {
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
                Objects.requireNonNull(mainFrame.getSelectConfigComboBox().getSelectedItem()).toString().substring(0, 1), isRunning));
        if (isRunning) {
            mainFrame.setVisible(false);
        }
    }

}
