package com.wearsky.keyMacro.ui;

import com.wearsky.keyMacro.main.Main;
import com.wearsky.keyMacro.utils.Constants.SystemStatus;
import com.wearsky.keyMacro.utils.Constants.TriggerType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.util.Iterator;

/**
 * 宏创建窗口
 *
 * @author peng
 *
 */
public class CreateMacroDialog extends MacroDialog {

    /**
     * 序列化核验版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造宏创建窗口
     *
     * @param owner 主窗口
     * @param modal 是否阻止用户操作其他窗口
     */
    public CreateMacroDialog(Frame owner, boolean modal) {
        super(owner, modal);
        addWindowListener(new CreateMacroDialogAdapter());
        DefaultListModel<String> targetKeyListModel = new DefaultListModel<>();
        targetKeyList.setModel(targetKeyListModel);
        confirmButton.addActionListener(new CreateMacroConfirmButtonListener((MainFrame) owner));
        burstsRadioButton.setSelected(true);
    }

    /**
     * 宏创建窗口适配器
     *
     * @author peng
     *
     */
    static class CreateMacroDialogAdapter extends WindowAdapter {

        /**
         * 监听窗口关闭
         */
        @Override
        public void windowClosing(WindowEvent e) {
            Main.systemStatus = SystemStatus.STOPPED;
        }
    }

    /**
     * 创建宏确认键监听器
     *
     * @author peng
     *
     */
    class CreateMacroConfirmButtonListener implements ActionListener {

        /**
         * 主窗口
         */
        private final MainFrame mainFrame;

        /**
         * 创建宏构造确认键监听器
         *
         * @param mainFrame 主窗口
         */
        public CreateMacroConfirmButtonListener(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
        }

        /**
         * 监听确认键被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String triggerKey = triggerKeyField.getText().trim();
            if (triggerKey.isEmpty()) {
                JOptionPane.showMessageDialog(CreateMacroDialog.this, "触发键未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DefaultListModel<String> targetKeyListModel = (DefaultListModel<String>) targetKeyList.getModel();
            if (targetKeyListModel.isEmpty()) {
                JOptionPane.showMessageDialog(CreateMacroDialog.this, "连发键未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TriggerType triggerType;
            if (burstsRadioButton.isSelected()) {
                triggerType = TriggerType.连发;
            } else if (switchRadioButton.isSelected()) {
                triggerType = TriggerType.开关;
            } else if (onceRadioButton.isSelected()) {
                triggerType = TriggerType.单次;
            } else {
                JOptionPane.showMessageDialog(CreateMacroDialog.this, "触发模式未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String intervalTime = intervalTimeTextField.getText().trim();
            if (triggerType != TriggerType.单次 && intervalTime.isEmpty()) {
                JOptionPane.showMessageDialog(CreateMacroDialog.this, "连发间隔未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Iterator<String> targetKeysIterator = targetKeyListModel.elements().asIterator();
            StringBuilder targetKeys = new StringBuilder(targetKeysIterator.next());
            while (targetKeysIterator.hasNext()) {
                targetKeys.append(",").append(targetKeysIterator.next());
            }
            DefaultTableModel macrosTableModel = (DefaultTableModel) mainFrame.getMacrosTable().getModel();
            Object[] macroData = {triggerKey, targetKeys.toString(), triggerType.name(), intervalTime};
            macrosTableModel.addRow(macroData);
            dispose();
        }
    }

}
