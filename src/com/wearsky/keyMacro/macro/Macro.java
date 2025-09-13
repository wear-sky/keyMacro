package com.wearsky.keyMacro.macro;

import com.wearsky.keyMacro.macroCell.MacroCell;

/**
 * 宏抽象类
 *
 * @author peng
 *
 */
public abstract class Macro extends Thread {

    /**
     * 宏单元数组，按顺序触发
     */
    protected MacroCell[] macroCells;

    /**
     * 运行状态
     */
    protected volatile boolean isRunning = false;

    /**
     * 删除状态
     */
    protected boolean isDeleted = false;

    /**
     * 触发键按下
     */
    public abstract void keyDown();

    /**
     * 触发键弹起
     */
    public abstract void keyUp();

    /**
     * 删除宏
     */
    public abstract void delete();

}
