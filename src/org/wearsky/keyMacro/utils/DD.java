package org.wearsky.keyMacro.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.wearsky.keyMacro.utils.Constants.DdCode;

/**
 * 连接DD驱动链接库
 *
 * @author peng
 *
 */
public interface DD extends Library {

    /**
     * DD驱动实例
     */
    DD INSTANCE = Native.load("./dll/dd", DD.class);

    /**
     * 鼠标绝对移动
     *
     * @param x 以屏幕左上角为原点，以像素为单位
     * @param y 以屏幕左上角为原点，以像素为单位
     */
    void DD_mov(int x, int y);

    /**
     * 鼠标点击
     *
     * @param btn 鼠标按键码
     * @see DdCode
     */
    int DD_btn(int btn);

    /**
     * 键盘按键
     *
     * @param ddCode 键盘按键码
     * @param flag   1、按下；2、放开。
     * @see DdCode
     */
    void DD_key(int ddCode, int flag);

}
