package pers.peng.keyMacro.hook;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;
import pers.peng.keyMacro.macro.Macro;
import pers.peng.keyMacro.main.Main;
import pers.peng.keyMacro.ui.MacroDialog;
import pers.peng.keyMacro.ui.MainFrame;
import pers.peng.keyMacro.utils.Constants.DdCode;
import pers.peng.keyMacro.utils.Constants.VkCode;
import pers.peng.keyMacro.utils.Switch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;

/**
 * 键盘钩子，监听处理键盘输入
 *
 * @author peng
 *
 */
public class KeyboardHook extends Thread {

    /**
     * User32实例
     */
    private static final User32 user32 = User32.INSTANCE;

    /**
     * 主窗口
     */
    private final MainFrame mainFrame;

    /**
     * 宏窗口
     */
    private MacroDialog macroDialog;

    /**
     * 钩子
     */
    private HHOOK hHook;

    /**
     * 构造键盘钩子
     *
     * @param mainFrame 主窗口
     */
    public KeyboardHook(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * 设置宏窗口
     *
     * @param macroDialog 宏窗口
     */
    public void setMacroDialog(MacroDialog macroDialog) {
        this.macroDialog = macroDialog;
    }

    /**
     * 脱钩
     */
    public void unHook() {
        user32.UnhookWindowsHookEx(hHook);
    }

    /**
     * 启动钩子线程
     */
    @Override
    public void run() {
        LowLevelKeyboardProc lowLevelKeyboardProc = new MacroKeyboardProc();
        hHook = user32.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, lowLevelKeyboardProc,
                Kernel32.INSTANCE.GetModuleHandle(null), 0);
        int result;
        MSG msg = new MSG();
        while ((result = user32.GetMessage(msg, null, 0, 0)) != 0) {
            user32.TranslateMessage(msg);
            user32.DispatchMessage(msg);
            if (result == -1) {
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "获取键盘输入出错", "错误", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
        user32.UnhookWindowsHookEx(hHook);
    }

    /**
     * 实现钩子过程
     *
     * @author peng
     *
     */
    class MacroKeyboardProc implements LowLevelKeyboardProc {
        @Override
        public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT kBDLLHookStruct) {
            int vkCode = kBDLLHookStruct.vkCode;
            int type = wParam.intValue();
            switch (Main.systemStatus) {
                case RUNNING:// 正在运行
                    Macro macro = Main.macros.get(vkCode);
                    if (macro != null) {
                        if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                            macro.keyDown();
                        } else {
                            macro.keyUp();
                        }
                        return new LRESULT(1);
                    }
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        VkCode pressKey = VkCode.getVkCode(vkCode);
                        if (pressKey != null) {
                            if (pressKey.name().equals(mainFrame.getSwitchField().getText().trim())) {
                                Switch.stop(mainFrame);
                                return new LRESULT(1);
                            } else if (pressKey.name().equals(mainFrame.getSwitchConfigField().getText().trim())) {
                                switchConfig();
                                return new LRESULT(1);
                            }
                        }
                    }
                    return user32.CallNextHookEx(hHook, nCode, wParam,
                            new LPARAM(Pointer.nativeValue(kBDLLHookStruct.getPointer())));
                case RECORDIND_TRIGGERKEY:// 正在录入触发键
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        VkCode triggerKey = VkCode.getVkCode(vkCode);
                        if (triggerKey == null) {
                            JOptionPane.showMessageDialog(macroDialog, "不支持的触发键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        String triggerKeyName = triggerKey.name();
                        if (isSelectConfigKeyExists(triggerKeyName, mainFrame.getSwitchConfigField())) {
                            JOptionPane.showMessageDialog(macroDialog, "已设定为切换键的按键不能设定为触发键", "错误",
                                    JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isSwitchKeyExists(triggerKeyName, mainFrame.getSwitchField())) {
                            JOptionPane.showMessageDialog(macroDialog, "已设定为开关键的按键不能设定为触发键", "错误",
                                    JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isTriggerKeyExists(triggerKeyName, mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(macroDialog, "触发键已存在", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isTargetKeyExists(triggerKeyName, macroDialog.getTargetKeyList(), mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(macroDialog, "已设定为连发键的按键不能设定为触发键", "错误",
                                    JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        macroDialog.getTriggerKeyField().setText(triggerKey.name());
                    }
                    return new LRESULT(1);
                case RECORDIND_TARGETKEY:// 正在录入连发键
                    VkCode targetKey = VkCode.getVkCode(vkCode);
                    if (targetKey == null) {
                        JOptionPane.showMessageDialog(macroDialog, "不支持的连发键", "错误", JOptionPane.ERROR_MESSAGE);
                        return new LRESULT(1);
                    }
                    String targetKeyName = targetKey.name();
                    DdCode ddCode = DdCode.getDdCode(targetKeyName);
                    if (ddCode == null) {
                        JOptionPane.showMessageDialog(macroDialog, "不支持的连发键", "错误", JOptionPane.ERROR_MESSAGE);
                        return new LRESULT(1);
                    }
                    if (isSelectConfigKeyExists(targetKeyName, mainFrame.getSwitchConfigField())) {
                        JOptionPane.showMessageDialog(macroDialog, "已设定为切换键的按键不能设定为连发键", "错误", JOptionPane.ERROR_MESSAGE);
                        return new LRESULT(1);
                    }
                    if (isSwitchKeyExists(targetKeyName, mainFrame.getSwitchField())) {
                        JOptionPane.showMessageDialog(macroDialog, "已设定为开关键的按键不能设定为连发键", "错误", JOptionPane.ERROR_MESSAGE);
                        return new LRESULT(1);
                    }
                    if (isTriggerKeyExists(targetKeyName, macroDialog.getTriggerKeyField(), mainFrame.getMacrosTable())) {
                        JOptionPane.showMessageDialog(macroDialog, "已设定为触发键的按键不能设定为连发键", "错误", JOptionPane.ERROR_MESSAGE);
                        return new LRESULT(1);
                    }
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        ((DefaultListModel<String>) macroDialog.getTargetKeyList().getModel())
                                .addElement("↓" + targetKeyName);
                    } else {
                        ((DefaultListModel<String>) macroDialog.getTargetKeyList().getModel())
                                .addElement("↑" + targetKeyName);
                    }
                    return new LRESULT(1);
                case RECORDIND_SWITCHKEY:// 正在录入开关键
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        VkCode newSwitchKey = VkCode.getVkCode(vkCode);
                        if (newSwitchKey == null) {
                            JOptionPane.showMessageDialog(mainFrame, "不支持的开关键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        String newSwitchKeyName = newSwitchKey.name();
                        if (isSelectConfigKeyExists(newSwitchKeyName, mainFrame.getSwitchConfigField())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为切换键的按键不能设定为开关键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isTriggerKeyExists(newSwitchKeyName, mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为触发键的按键不能设定为开关键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isTargetKeyExists(newSwitchKeyName, mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为连发键的按键不能设定为开关键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        mainFrame.getSwitchField().setText(newSwitchKey.name());
                    }
                    return new LRESULT(1);
                case RECORDIND_SWITCHCONFIGKEY:// 正在录入配置文件切换键
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        VkCode newSelectConfigKey = VkCode.getVkCode(vkCode);
                        if (newSelectConfigKey == null) {
                            JOptionPane.showMessageDialog(mainFrame, "不支持的切换键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        String newSelectConfigKeyName = newSelectConfigKey.name();
                        if (isTriggerKeyExists(newSelectConfigKeyName, mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为触发键的按键不能设定为切换键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isTargetKeyExists(newSelectConfigKeyName, mainFrame.getMacrosTable())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为连发键的按键不能设定为切换键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        if (isSwitchKeyExists(newSelectConfigKeyName, mainFrame.getSwitchField())) {
                            JOptionPane.showMessageDialog(mainFrame, "已设定为开关键的按键不能设定为切换键", "错误", JOptionPane.ERROR_MESSAGE);
                            return new LRESULT(1);
                        }
                        mainFrame.getSwitchConfigField().setText(newSelectConfigKey.name());
                    }
                    return new LRESULT(1);
                case STOPPED:// 非运行状态
                    if (type == WinUser.WM_KEYDOWN || type == WinUser.WM_SYSKEYDOWN) {
                        VkCode pressKey = VkCode.getVkCode(vkCode);
                        if (pressKey != null) {
                            if (pressKey.name().equals(mainFrame.getSwitchField().getText().trim())) {
                                Switch.start(mainFrame);
                                return new LRESULT(1);
                            } else if (pressKey.name().equals(mainFrame.getSwitchConfigField().getText().trim())) {
                                switchConfig();
                                return new LRESULT(1);
                            }
                        }
                    }
                    return user32.CallNextHookEx(hHook, nCode, wParam,
                            new LPARAM(Pointer.nativeValue(kBDLLHookStruct.getPointer())));
                default:
                    return user32.CallNextHookEx(hHook, nCode, wParam,
                            new LPARAM(Pointer.nativeValue(kBDLLHookStruct.getPointer())));
            }
        }
    }

    /**
     * 检查是否存在触发键
     *
     * @param checkKeyName        检查的键名
     * @param triggerKeyTextField 被检查的触发键录入框
     * @param macroTable          被检查的宏表格
     * @return 是否存在
     */
    private boolean isTriggerKeyExists(String checkKeyName, JTextField triggerKeyTextField, JTable macroTable) {
        if (checkKeyName.equals(triggerKeyTextField.getText().trim())) {
            return true;
        }
        return isTriggerKeyExists(checkKeyName, macroTable);
    }

    /**
     * 检查是否存在触发键
     *
     * @param checkKeyName 检查的键名
     * @param macroTable   被检查的宏表格
     * @return 是否存在
     */
    private boolean isTriggerKeyExists(String checkKeyName, JTable macroTable) {
        DefaultTableModel macrosTableModel = (DefaultTableModel) macroTable.getModel();
        int rowCount = macrosTableModel.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            if (checkKeyName.equals(macrosTableModel.getValueAt(row, 0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否存在连发键
     *
     * @param checkKeyName  检查的键名
     * @param targetKeyList 被检查的连发键列表
     * @param macroTable    被检查的宏表格
     * @return 是否存在
     */
    private boolean isTargetKeyExists(String checkKeyName, JList<String> targetKeyList, JTable macroTable) {
        Iterator<String> targetKeys = ((DefaultListModel<String>) targetKeyList.getModel()).elements().asIterator();
        while (targetKeys.hasNext()) {
            String targetKeyName = targetKeys.next().substring(1);
            if (checkKeyName.equals(targetKeyName)) {
                return true;
            }
        }
        return isTargetKeyExists(checkKeyName, macroTable);
    }

    /**
     * 检查是否存在连发键
     *
     * @param checkKeyName 检查的键名
     * @param macroTable   被检查的宏表格
     * @return 是否存在
     */
    private boolean isTargetKeyExists(String checkKeyName, JTable macroTable) {
        DefaultTableModel macrosTableModel = (DefaultTableModel) macroTable.getModel();
        int rowCount = macrosTableModel.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            String[] targetKeys = ((String) macroTable.getValueAt(row, 1)).split(",");
            for (String targetKeyName : targetKeys) {
                if (!targetKeyName.isEmpty() && checkKeyName.equals(targetKeyName.substring(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查是否存在开关键
     *
     * @param checkKeyName       检查的键名
     * @param switchKeyTextField 被检查的开关键录入框
     * @return 是否存在
     */
    private boolean isSwitchKeyExists(String checkKeyName, JTextField switchKeyTextField) {
        return checkKeyName.equals(switchKeyTextField.getText().trim());
    }

    /**
     * 检查是否存在配置文件切换键
     *
     * @param checkKeyName             检查的键名
     * @param selectConfigKeyTextField 被检查的配置文件切换键录入框
     * @return 是否存在
     */
    private boolean isSelectConfigKeyExists(String checkKeyName, JTextField selectConfigKeyTextField) {
        return checkKeyName.equals(selectConfigKeyTextField.getText().trim());
    }

    /**
     * 切换下一个配置文件
     */
    private void switchConfig() {
        JComboBox<String> selectConfigComboBox = mainFrame.getSelectConfigComboBox();
        int itemCount = selectConfigComboBox.getItemCount();
        if (itemCount == 0) {
            return;
        }
        int selectedItem = selectConfigComboBox.getSelectedIndex();
        if (++selectedItem >= itemCount) {
            selectConfigComboBox.setSelectedIndex(0);
        } else {
            selectConfigComboBox.setSelectedIndex(selectedItem);
        }
    }

}
