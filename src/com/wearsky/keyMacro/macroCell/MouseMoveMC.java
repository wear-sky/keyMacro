package com.wearsky.keyMacro.macroCell;

import com.wearsky.keyMacro.utils.DD;

/**
 * 鼠标移动宏单元
 *
 * @param x 鼠标移动横坐标，显示器左上角为0
 * @param y 鼠标移动纵坐标，显示器左上角为0
 * @author peng
 */
public record MouseMoveMC(int x, int y) implements MacroCell {

    /**
     * dd驱动实例
     */
    private final static DD dd = DD.INSTANCE;

    /**
     * 执行宏单元
     */
    @Override
    public void performed() {
        dd.DD_mov(x, y);
    }

}
