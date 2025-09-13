package com.wearsky.keyMacro.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图标工具类
 *
 * @author peng
 *
 */
public class IconUtils {

    /**
     * 根据字符串生成系统托盘图标
     *
     * @param word      需要画进图标里的字符串
     * @param isRunning 是否正在运行中
     * @return 系统托盘图标
     */
    public static Image getTrayIcon(String word, boolean isRunning) {
        int systemTrayIconWidth = SystemTray.getSystemTray().getTrayIconSize().width;
        int systemTrayIconHeight = SystemTray.getSystemTray().getTrayIconSize().height;
        BufferedImage bufferedImage = new BufferedImage(systemTrayIconWidth, systemTrayIconHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();
        if (isRunning) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.LIGHT_GRAY);
        }
        for (int rowCount = systemTrayIconHeight * 3 / 4; rowCount < systemTrayIconHeight; rowCount++) {
            graphics.drawLine(0, rowCount, systemTrayIconWidth - 1, rowCount);
        }
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("微软雅黑", Font.PLAIN, systemTrayIconWidth));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString(word, (systemTrayIconWidth - fontMetrics.stringWidth(word)) / 2,
                fontMetrics.getAscent() - (fontMetrics.getHeight() - systemTrayIconHeight) / 2);
        graphics.dispose();
        return bufferedImage;
    }

    /**
     * 生成无配置文件时的系统托盘图标
     *
     * @return 系统托盘图标
     */
    public static Image getTrayIcon() {
        int systemTrayIconWidth = SystemTray.getSystemTray().getTrayIconSize().width;
        int systemTrayIconHeight = SystemTray.getSystemTray().getTrayIconSize().height;
        try {
            return ImageIO.read(new File("./img/键盘.png")).getScaledInstance(systemTrayIconWidth, systemTrayIconHeight,
                    Image.SCALE_AREA_AVERAGING);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取按钮图标，并进行缩放
     *
     * @param filePath 图片路径
     * @param width    宽
     * @param height   高
     */
    public static ImageIcon getButtonIcon(String filePath, int width, int height) {
        try {
            return new ImageIcon(
                    ImageIO.read(new File(filePath)).getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
        } catch (IOException e) {
            return null;
        }
    }

}
