package com.wearsky.keyMacro.ui;

import com.wearsky.keyMacro.main.Main;
import com.wearsky.keyMacro.utils.Constants;
import com.wearsky.keyMacro.utils.Constants.SystemStatus;
import com.wearsky.keyMacro.utils.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;

/**
 * 宏窗口抽象类
 *
 * @author peng
 *
 */
public abstract class MacroDialog extends JDialog {

    /**
     * 序列化核验版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 触发键录入框
     */
    protected JTextField triggerKeyField;

    /**
     * 连发键列表
     */
    protected JList<String> targetKeyList;

    /**
     * 录入连发键按钮
     */
    protected JButton createTargetKeyButton;

    /**
     * 上移连发键按钮
     */
    protected JButton moveUpTargetKeyButton;

    /**
     * 下移连发键按钮
     */
    protected JButton moveDownTargetKeyButton;

    /**
     * 删除连发键按钮
     */
    protected JButton deleteTargetKeyButton;

    /**
     * 清除连发键列表按钮
     */
    protected JButton clearTargetKeyButton;

    /**
     * 录入鼠标左键按钮
     */
    protected JButton leftClick;

    /**
     * 录入鼠标右键按钮
     */
    protected JButton rightClick;

    /**
     * 录入鼠标中键按钮
     */
    protected JButton mButton;

    /**
     * 录入鼠标上侧键按钮
     */
    protected JButton xButton2;

    /**
     * 录入鼠标下侧键按钮
     */
    protected JButton xButton1;

    /**
     * 鼠标移动横坐标
     */
    protected JTextField xPointTextField;

    /**
     * 鼠标移动纵坐标
     */
    protected JTextField yPointTextField;

    /**
     * 录入鼠标移动按钮
     */
    protected JButton mouseMoveButton;

    /**
     * 延迟录入框
     */
    protected JTextField delayTextField;

    /**
     * 录入延迟按钮
     */
    protected JButton delayButton;

    /**
     * 连发模式单选框
     */
    protected JRadioButton burstsRadioButton;

    /**
     * 开关模式单选框
     */
    protected JRadioButton switchRadioButton;

    /**
     * 单次模式单选框
     */
    protected JRadioButton onceRadioButton;

    /**
     * 连发间隔输入框
     */
    protected JTextField intervalTimeTextField;

    /**
     * 确认按钮
     */
    protected JButton confirmButton;

    /**
     * 获取触发键录入框
     *
     * @return 触发键录入框
     */
    public JTextField getTriggerKeyField() {
        return triggerKeyField;
    }

    /**
     * 获取连发键列表
     *
     * @return 连发键列表
     */
    public JList<String> getTargetKeyList() {
        return targetKeyList;
    }

    /**
     * 构造宏窗口
     *
     * @param owner 主窗口
     * @param modal 是否阻止用户操作其他窗口
     */
    public MacroDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
        setTitle("创建宏");
        setBounds(100, 100, 350, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel macroDialogPanel = new JPanel();
        macroDialogPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        setContentPane(macroDialogPanel);
        JLabel triggerKeyLabel = new JLabel("触发键：");
        triggerKeyLabel.setBounds(10, 10, 48, 25);
        triggerKeyField = new JTextField();
        triggerKeyField.setBounds(68, 10, 256, 25);
        triggerKeyField.addKeyListener(new TriggerKeyFieldKeyAdapter());
        triggerKeyField.addFocusListener(new TriggerKeyFieldFocusAdapter());
        JLabel targetKeyLabel = new JLabel("连发键：");
        targetKeyLabel.setBounds(10, 45, 48, 25);
        targetKeyList = new JList<>();
        JScrollPane targetKeyScrollPane = new JScrollPane(targetKeyList);
        targetKeyScrollPane.setBounds(78, 45, 210, 309);
        createTargetKeyButton = new JButton();
        createTargetKeyButton.setBounds(298, 45, 26, 26);
        createTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/加号.png", 26, 26));
        createTargetKeyButton.setToolTipText("开始录入连发键");
        createTargetKeyButton.addActionListener(new CreateTargetKeyButtonListener());
        moveUpTargetKeyButton = new JButton();
        moveUpTargetKeyButton.setBounds(298, 81, 26, 26);
        moveUpTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/向上.png", 26, 26));
        moveUpTargetKeyButton.setToolTipText("上移");
        moveUpTargetKeyButton.addActionListener(new MoveUpTargetKeyButtonListener());
        moveDownTargetKeyButton = new JButton();
        moveDownTargetKeyButton.setBounds(298, 117, 26, 26);
        moveDownTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/向下.png", 26, 26));
        moveDownTargetKeyButton.setToolTipText("下移");
        moveDownTargetKeyButton.addActionListener(new MoveDownTargetKeyButtonListener());
        deleteTargetKeyButton = new JButton();
        deleteTargetKeyButton.setBounds(298, 153, 26, 26);
        deleteTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/叉号.png", 26, 26));
        deleteTargetKeyButton.setToolTipText("删除选中");
        deleteTargetKeyButton.addActionListener(new DeleteTargetKeyButtonListener());
        clearTargetKeyButton = new JButton();
        clearTargetKeyButton.setBounds(298, 189, 26, 26);
        clearTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/清空.png", 26, 26));
        clearTargetKeyButton.setToolTipText("清空所有");
        clearTargetKeyButton.addActionListener(new ClearTargetKeyButtonListener());
        leftClick = new JButton();
        leftClick.setBounds(10, 80, 26, 26);
        leftClick.setIcon(IconUtils.getButtonIcon("./img/鼠标左键.png", 26, 26));
        leftClick.setToolTipText("鼠标左键");
        leftClick.addActionListener(new LeftClickListener());
        rightClick = new JButton();
        rightClick.setBounds(46, 80, 26, 26);
        rightClick.setIcon(IconUtils.getButtonIcon("./img/鼠标右键.png", 26, 26));
        rightClick.setToolTipText("鼠标右键");
        rightClick.addActionListener(new RightClickListener());
        mButton = new JButton();
        mButton.setBounds(46, 116, 26, 26);
        mButton.setIcon(IconUtils.getButtonIcon("./img/鼠标中键.png", 26, 26));
        mButton.setToolTipText("鼠标中键");
        mButton.addActionListener(new MButtonListener());
        xButton2 = new JButton();
        xButton2.setBounds(10, 116, 26, 26);
        xButton2.setIcon(IconUtils.getButtonIcon("./img/鼠标侧键上.png", 26, 26));
        xButton2.setToolTipText("鼠标侧键上");
        xButton2.addActionListener(new XButton2Listener());
        xButton1 = new JButton();
        xButton1.setBounds(10, 152, 26, 26);
        xButton1.setIcon(IconUtils.getButtonIcon("./img/鼠标侧键下.png", 26, 26));
        xButton1.setToolTipText("鼠标侧键下");
        xButton1.addActionListener(new XButton1Listener());
        JLabel mouseButtonLabel = new JLabel("(鼠标按键)");
        mouseButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mouseButtonLabel.setBounds(10, 179, 60, 25);
        xPointTextField = new JTextField();
        xPointTextField.setBounds(10, 214, 41, 21);
        xPointTextField.addKeyListener(new NumberTextFieldListener());
        JLabel mouseMovePointLabel = new JLabel("x");
        mouseMovePointLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mouseMovePointLabel.setBounds(10, 232, 41, 15);
        yPointTextField = new JTextField();
        yPointTextField.setBounds(10, 245, 41, 21);
        yPointTextField.addKeyListener(new NumberTextFieldListener());
        mouseMoveButton = new JButton();
        mouseMoveButton.setBounds(55, 214, 13, 52);
        mouseMoveButton.setToolTipText("插入鼠标移动");
        mouseMoveButton.setIcon(IconUtils.getButtonIcon("./img/向右.png", 13, 52));
        mouseMoveButton.addActionListener(new MouseMoveButtonListener());
        JLabel mouseMoveLabel = new JLabel("(鼠标移动)");
        mouseMoveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mouseMoveLabel.setBounds(10, 266, 60, 25);
        delayTextField = new JTextField();
        delayTextField.setBounds(10, 301, 41, 21);
        delayTextField.addKeyListener(new NumberTextFieldListener());
        delayButton = new JButton();
        delayButton.setBounds(55, 301, 13, 21);
        delayButton.setToolTipText("插入延迟");
        delayButton.setIcon(IconUtils.getButtonIcon("./img/向右.png", 13, 21));
        delayButton.addActionListener(new DelayButtonListener());
        JLabel delayLabel = new JLabel("(延迟/ms)");
        delayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        delayLabel.setBounds(10, 322, 60, 25);
        JLabel triggerTypeLabel = new JLabel("触发方式：");
        triggerTypeLabel.setBounds(10, 360, 60, 23);
        burstsRadioButton = new JRadioButton("连发");
        burstsRadioButton.setBounds(68, 360, 60, 23);
        switchRadioButton = new JRadioButton("开关");
        switchRadioButton.setBounds(130, 360, 60, 23);
        onceRadioButton = new JRadioButton("单次");
        onceRadioButton.addChangeListener(new OnceRadioButtonListener());
        onceRadioButton.setBounds(192, 360, 60, 23);
        ButtonGroup triggerTypeButtonGroup = new ButtonGroup();
        triggerTypeButtonGroup.add(burstsRadioButton);
        triggerTypeButtonGroup.add(switchRadioButton);
        triggerTypeButtonGroup.add(onceRadioButton);
        JLabel intervalTimeLabel = new JLabel("连发间隔：");
        intervalTimeLabel.setBounds(10, 393, 60, 25);
        intervalTimeTextField = new JTextField();
        intervalTimeTextField.setBounds(68, 393, 228, 25);
        intervalTimeTextField.addKeyListener(new NumberTextFieldListener());
        JLabel intervalTimeUnitLabel = new JLabel("ms");
        intervalTimeUnitLabel.setBounds(306, 393, 18, 25);
        confirmButton = new JButton("确定");
        confirmButton.setBounds(265, 428, 59, 23);
        getRootPane().setDefaultButton(confirmButton);
        macroDialogPanel.setLayout(null);
        macroDialogPanel.add(triggerKeyLabel);
        macroDialogPanel.add(triggerKeyField);
        macroDialogPanel.add(targetKeyLabel);
        macroDialogPanel.add(targetKeyScrollPane);
        macroDialogPanel.add(createTargetKeyButton);
        macroDialogPanel.add(moveUpTargetKeyButton);
        macroDialogPanel.add(moveDownTargetKeyButton);
        macroDialogPanel.add(deleteTargetKeyButton);
        macroDialogPanel.add(clearTargetKeyButton);
        macroDialogPanel.add(leftClick);
        macroDialogPanel.add(rightClick);
        macroDialogPanel.add(mButton);
        macroDialogPanel.add(xButton2);
        macroDialogPanel.add(xButton1);
        macroDialogPanel.add(mouseButtonLabel);
        macroDialogPanel.add(xPointTextField);
        macroDialogPanel.add(mouseMovePointLabel);
        macroDialogPanel.add(yPointTextField);
        macroDialogPanel.add(mouseMoveButton);
        macroDialogPanel.add(mouseMoveLabel);
        macroDialogPanel.add(delayTextField);
        macroDialogPanel.add(delayButton);
        macroDialogPanel.add(delayLabel);
        macroDialogPanel.add(triggerTypeLabel);
        macroDialogPanel.add(burstsRadioButton);
        macroDialogPanel.add(switchRadioButton);
        macroDialogPanel.add(onceRadioButton);
        macroDialogPanel.add(intervalTimeLabel);
        macroDialogPanel.add(intervalTimeTextField);
        macroDialogPanel.add(intervalTimeUnitLabel);
        macroDialogPanel.add(confirmButton);
    }

    /**
     * 触发键录入框焦点适配器
     *
     * @author peng
     *
     */
    static class TriggerKeyFieldFocusAdapter extends FocusAdapter {

        /**
         * 触发键录入框获得焦点
         */
        @Override
        public void focusGained(FocusEvent e) {
            Main.systemStatus = SystemStatus.RECORDIND_TRIGGERKEY;
        }

        /**
         * 触发键录入框失去焦点
         */
        @Override
        public void focusLost(FocusEvent e) {
            Main.systemStatus = SystemStatus.STOPPED;
        }
    }

    /**
     * 触发键录入框按键适配器
     *
     * @author peng
     *
     */
    static class TriggerKeyFieldKeyAdapter extends KeyAdapter {

        /**
         * 按键输入
         */
        @Override
        public void keyTyped(KeyEvent e) {
            e.consume();
        }
    }

    /**
     * 录入连发键按钮监听器
     *
     * @author peng
     *
     */
    class CreateTargetKeyButtonListener implements ActionListener {

        /**
         * 录入连发键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.systemStatus == SystemStatus.RECORDIND_TARGETKEY) {
                confirmButton.setEnabled(true);
                if (!onceRadioButton.isSelected()) {
                    intervalTimeTextField.setEnabled(true);
                }
                onceRadioButton.setEnabled(true);
                switchRadioButton.setEnabled(true);
                burstsRadioButton.setEnabled(true);
                createTargetKeyButton.setToolTipText("开始录入连发键");
                createTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/加号.png", 26, 26));
                triggerKeyField.setEnabled(true);
                Main.systemStatus = SystemStatus.STOPPED;
            } else {
                triggerKeyField.setEnabled(false);
                createTargetKeyButton.setIcon(IconUtils.getButtonIcon("./img/勾.png", 26, 26));
                createTargetKeyButton.setToolTipText("停止录入连发键");
                burstsRadioButton.setEnabled(false);
                switchRadioButton.setEnabled(false);
                onceRadioButton.setEnabled(false);
                intervalTimeTextField.setEnabled(false);
                confirmButton.setEnabled(false);
                Main.systemStatus = SystemStatus.RECORDIND_TARGETKEY;
            }
        }
    }

    /**
     * 上移连发键按钮监听器
     *
     * @author peng
     *
     */
    class MoveUpTargetKeyButtonListener implements ActionListener {

        /**
         * 上移连发键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = targetKeyList.getSelectedIndex();
            if (selectedIndex != -1 && selectedIndex != 0) {
                DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
                String selectedValue = defaultListModel.elementAt(selectedIndex);
                defaultListModel.set(selectedIndex, defaultListModel.elementAt(selectedIndex - 1));
                defaultListModel.set(selectedIndex - 1, selectedValue);
                targetKeyList.setSelectedIndex(selectedIndex - 1);
            }
        }
    }

    /**
     * 下一连发键按钮监听器
     *
     * @author peng
     *
     */
    class MoveDownTargetKeyButtonListener implements ActionListener {

        /**
         * 下一连发键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = targetKeyList.getSelectedIndex();
            if (selectedIndex != targetKeyList.getLastVisibleIndex()) {
                DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
                String selectedValue = defaultListModel.elementAt(selectedIndex);
                defaultListModel.set(selectedIndex, defaultListModel.elementAt(selectedIndex + 1));
                defaultListModel.set(selectedIndex + 1, selectedValue);
                targetKeyList.setSelectedIndex(selectedIndex + 1);
            }
        }
    }

    /**
     * 删除连发键按钮监听器
     *
     * @author peng
     *
     */
    class DeleteTargetKeyButtonListener implements ActionListener {

        /**
         * 删除连发键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = targetKeyList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(MacroDialog.this, "请先选择需要删除的连发键", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ((DefaultListModel<String>) targetKeyList.getModel()).removeElementAt(selectedIndex);
        }
    }

    /**
     * 清除连发键按钮监听器
     *
     * @author peng
     *
     */
    class ClearTargetKeyButtonListener implements ActionListener {

        /**
         * 清除连发键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(MacroDialog.this, "确定清空所有已录入的按键吗？", "清空",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                ((DefaultListModel<String>) targetKeyList.getModel()).removeAllElements();
            }
        }
    }

    /**
     * 录入鼠标左键按钮监听器
     *
     * @author peng
     *
     */
    class LeftClickListener implements ActionListener {

        /**
         * 录入鼠标左键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
            defaultListModel.addElement("~" + Constants.DdCode.LEFT_CLICK_DOWN);
            defaultListModel.addElement("~" + Constants.DdCode.LEFT_CLICK_UP);
        }
    }

    /**
     * 录入鼠标右键按钮监听器
     *
     * @author peng
     *
     */
    class RightClickListener implements ActionListener {

        /**
         * 录入鼠标右键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
            defaultListModel.addElement("~" + Constants.DdCode.RIGHT_CLICK_DOWN);
            defaultListModel.addElement("~" + Constants.DdCode.RIGHT_CLICK_UP);
        }
    }

    /**
     * 录入鼠标中键按钮监听器
     *
     * @author peng
     *
     */
    class MButtonListener implements ActionListener {

        /**
         * 录入鼠标中键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
            defaultListModel.addElement("~" + Constants.DdCode.MBUTTON_DOWN);
            defaultListModel.addElement("~" + Constants.DdCode.MBUTTON_UP);
        }
    }

    /**
     * 录入鼠标上侧键按钮监听器
     *
     * @author peng
     *
     */
    class XButton2Listener implements ActionListener {

        /**
         * 录入鼠标上侧键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
            defaultListModel.addElement("~" + Constants.DdCode.XBUTTON2_DOWN);
            defaultListModel.addElement("~" + Constants.DdCode.XBUTTON2_UP);
        }
    }

    /**
     * 录入鼠标下侧键按钮监听器
     *
     * @author peng
     *
     */
    class XButton1Listener implements ActionListener {

        /**
         * 录入鼠标下侧键按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>) targetKeyList.getModel();
            defaultListModel.addElement("~" + Constants.DdCode.XBUTTON1_DOWN);
            defaultListModel.addElement("~" + Constants.DdCode.XBUTTON1_UP);
        }
    }

    /**
     * 录入鼠标移动按钮监听器
     *
     * @author peng
     *
     */
    class MouseMoveButtonListener implements ActionListener {

        /**
         * 录入鼠标移动按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String xPointText = xPointTextField.getText().trim();
            String yPointText = yPointTextField.getText().trim();
            if (!xPointText.isEmpty() && !yPointText.isEmpty()) {
                int xPoint = Integer.parseInt(xPointText);
                int yPoint = Integer.parseInt(yPointText);
                ((DefaultListModel<String>) targetKeyList.getModel()).addElement("→" + xPoint + "x" + yPoint);
            }
        }
    }

    /**
     * 录入延迟按钮监听器
     *
     * @author peng
     *
     */
    class DelayButtonListener implements ActionListener {

        /**
         * 录入延迟按钮被触发执行
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String delayText = delayTextField.getText().trim();
            if (!delayText.isEmpty()) {
                long delay = Long.parseLong(delayText);
                ((DefaultListModel<String>) targetKeyList.getModel()).addElement("-" + delay);
            }
        }
    }

    /**
     * 单次模式单选框监听器
     *
     * @author peng
     *
     */
    class OnceRadioButtonListener implements ChangeListener {

        /**
         * 单次模式单选框状态改变
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            intervalTimeTextField.setEnabled(!onceRadioButton.isSelected());
        }
    }

    /**
     * 数字输入框适配器
     *
     * @author peng
     *
     */
    static class NumberTextFieldListener extends KeyAdapter {

        /**
         * 数字输入框输入监听，阻止输入数字意外的内容
         */
        @Override
        public void keyTyped(KeyEvent e) {
            int keyChar = e.getKeyChar();
            if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                e.consume();
            }
        }
    }

}
