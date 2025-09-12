package org.wearsky.keyMacro.macroCell;

import org.wearsky.keyMacro.utils.Constants;
import org.wearsky.keyMacro.utils.DD;

/**
 * 键盘宏单元
 *
 * @param ddCode  dd代码
 * @param keyType 按键动作类型
 * @author peng
 * @see Constants.DdCode
 * @see #KEY_DOWN
 * @see #KEY_UP
 */
public record KeyboardMC(int ddCode, int keyType) implements MacroCell {

    /**
     * 按下
     */
    public static final int KEY_DOWN = 1;

    /**
     * 弹起
     */
    public static final int KEY_UP = 2;

    /**
     * dd驱动实例
     */
    private static final DD dd = DD.INSTANCE;

    /**
     * 执行宏单元
     */
    @Override
    public void performed() {
        dd.DD_key(ddCode, keyType);
    }

}
