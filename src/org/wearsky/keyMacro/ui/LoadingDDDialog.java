package org.wearsky.keyMacro.ui;

import org.wearsky.keyMacro.utils.DD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;

/**
 * 加载DD驱动窗口类
 *
 * @author peng
 *
 */
public class LoadingDDDialog extends JDialog {

    /**
     * 序列化核验版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造加载DD驱动窗口
     */
    public LoadingDDDialog() {
        setModal(true);
        setResizable(false);
        setTitle("加载DD驱动");
        setBounds(100, 100, 350, 75);
        setLocationRelativeTo(rootPane);
        setLocation(getX(), getY() - 100);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel loadingDDPanel = new JPanel();
        loadingDDPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        FlowLayout flowLayout = (FlowLayout) loadingDDPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        JLabel loadingDDLabel = new JLabel("正在加载DD驱动，请稍候...");
        loadingDDLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loadingDDLabel.setBounds(10, 10, 314, 25);
        loadingDDPanel.add(loadingDDLabel);
        setContentPane(loadingDDPanel);
        addWindowListener(new LoadingDDDialogAdapter());
    }

    /**
     * 加载DD驱动窗口适配器
     *
     * @author peng
     *
     */
    class LoadingDDDialogAdapter extends WindowAdapter {

        /**
         * 打开加载DD驱动窗口后被触发执行
         */
        @Override
        public void windowOpened(WindowEvent e) {
            if (DD.INSTANCE.DD_btn(0) != 1) {// 应该是加载、初始化DD驱动，若不等于1则失败
                System.exit(0);
            }
            LoadingDDDialog.this.dispose();
        }
    }
}
