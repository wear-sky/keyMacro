package pers.peng.keyMacro.ui;

import pers.peng.keyMacro.utils.Constants.TriggerType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

/**
 * 修改宏窗口
 *
 * @author peng
 *
 */
public class ModifyMacroDialog extends MacroDialog {

    /**
     * 构造修改宏窗口
     *
     * @param owner                  主窗口
     * @param modal                  是否阻止用户操作其他窗口
     * @param macrosTableSelectedRow 被修改的宏行号
     */
    public ModifyMacroDialog(Frame owner, boolean modal, int macrosTableSelectedRow) {
        super(owner, modal);
        DefaultListModel<String> targetKeyListModel = new DefaultListModel<>();
        targetKeyList.setModel(targetKeyListModel);
        confirmButton
                .addActionListener(new ModifyMacroConfirmButtonListener((MainFrame) owner, macrosTableSelectedRow));
        DefaultTableModel macroTableModel = (DefaultTableModel) ((MainFrame) owner).getMacrosTable().getModel();
        String triggerKey = (String) macroTableModel.getValueAt(macrosTableSelectedRow, 0);
        String targetKeys = (String) macroTableModel.getValueAt(macrosTableSelectedRow, 1);
        String triggerType = (String) macroTableModel.getValueAt(macrosTableSelectedRow, 2);
        String intervalTime = (String) macroTableModel.getValueAt(macrosTableSelectedRow, 3);
        addWindowListener(new ModifyMacroDialogAdapter((MainFrame) owner, macrosTableSelectedRow, triggerKey,
                targetKeys, triggerType, intervalTime));
        triggerKeyField.setText(triggerKey);
        for (String targetKey : targetKeys.split(",")) {
            targetKeyListModel.addElement(targetKey);
        }
        switch (TriggerType.valueOf(triggerType)) {
            case 开关:
                switchRadioButton.setSelected(true);
                break;
            case 单次:
                onceRadioButton.setSelected(true);
                break;
            default:
                burstsRadioButton.setSelected(true);
                break;
        }
        intervalTimeTextField.setText(intervalTime);
        int columnCount = macroTableModel.getColumnCount();
        for (int column = 0; column < columnCount; column++) {
            macroTableModel.setValueAt("", macrosTableSelectedRow, column);
        }
    }

    /**
     * 修改宏窗口适配器
     *
     * @author peng
     *
     */
    static class ModifyMacroDialogAdapter extends WindowAdapter {

        /**
         * 主窗口
         */
        private final MainFrame mainFrame;

        /**
         * 被修改的宏行号
         */
        private final int macrosTableSelectedRow;

        /**
         * 触发键
         */
        private final String triggerKey;

        /**
         * 连发键
         */
        private final String targetKeys;

        /**
         * 触发类型
         */
        private final String triggerType;

        /**
         * 触发间隔/ms
         */
        private final String intervalTime;

        /**
         * 构造修改宏窗口适配器
         *
         * @param mainFrame              主窗口
         * @param macrosTableSelectedRow 被修改的宏行号
         * @param triggerKey             触发键
         * @param targetKeys             连发键
         * @param triggerType            触发方式
         * @param intervalTime           触发间隔/ms
         */
        public ModifyMacroDialogAdapter(MainFrame mainFrame, int macrosTableSelectedRow, String triggerKey,
                                        String targetKeys, String triggerType, String intervalTime) {
            this.mainFrame = mainFrame;
            this.macrosTableSelectedRow = macrosTableSelectedRow;
            this.triggerKey = triggerKey;
            this.targetKeys = targetKeys;
            this.triggerType = triggerType;
            this.intervalTime = intervalTime;
        }

        /**
         * 窗口关闭过程触发执行
         */
        @Override
        public void windowClosing(WindowEvent e) {
            DefaultTableModel macroTableModel = (DefaultTableModel) mainFrame.getMacrosTable().getModel();
            macroTableModel.setValueAt(triggerKey, macrosTableSelectedRow, 0);
            macroTableModel.setValueAt(targetKeys, macrosTableSelectedRow, 1);
            macroTableModel.setValueAt(triggerType, macrosTableSelectedRow, 2);
            macroTableModel.setValueAt(intervalTime, macrosTableSelectedRow, 3);
        }
    }

    /**
     * 修改宏确认键监听器
     *
     * @author peng
     *
     */
    class ModifyMacroConfirmButtonListener implements ActionListener {

        /**
         * 主窗口
         */
        private final MainFrame mainFrame;

        /**
         * 被修改的宏行号
         */
        private final int macrosTableSelectedRow;

        /**
         * 构造修改宏确认键监听器
         *
         * @param mainFrame              主窗口
         * @param macrosTableSelectedRow 被修改的宏行号
         */
        public ModifyMacroConfirmButtonListener(MainFrame mainFrame, int macrosTableSelectedRow) {
            this.mainFrame = mainFrame;
            this.macrosTableSelectedRow = macrosTableSelectedRow;
        }

        /**
         * 构造修改宏确认键被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String triggerKey = triggerKeyField.getText().trim();
            if (triggerKey.isEmpty()) {
                JOptionPane.showMessageDialog(ModifyMacroDialog.this, "触发键未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DefaultListModel<String> targetKeyListModel = (DefaultListModel<String>) targetKeyList.getModel();
            if (targetKeyListModel.isEmpty()) {
                JOptionPane.showMessageDialog(ModifyMacroDialog.this, "连发键未设置", "错误", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(ModifyMacroDialog.this, "触发模式未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String intervalTime = intervalTimeTextField.getText().trim();
            if (triggerType != TriggerType.单次 && intervalTime.isEmpty()) {
                JOptionPane.showMessageDialog(ModifyMacroDialog.this, "连发间隔未设置", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Iterator<String> targetKeysIterator = targetKeyListModel.elements().asIterator();
            StringBuilder targetKeys = new StringBuilder(targetKeysIterator.next());
            while (targetKeysIterator.hasNext()) {
                targetKeys.append(",").append(targetKeysIterator.next());
            }
            DefaultTableModel macrosTableModel = (DefaultTableModel) mainFrame.getMacrosTable().getModel();
            macrosTableModel.setValueAt(triggerKey, macrosTableSelectedRow, 0);
            macrosTableModel.setValueAt(targetKeys.toString(), macrosTableSelectedRow, 1);
            macrosTableModel.setValueAt(triggerType.name(), macrosTableSelectedRow, 2);
            macrosTableModel.setValueAt(intervalTime, macrosTableSelectedRow, 3);
            dispose();
        }
    }

}
