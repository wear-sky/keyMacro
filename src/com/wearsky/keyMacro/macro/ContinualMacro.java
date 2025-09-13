package com.wearsky.keyMacro.macro;

import com.wearsky.keyMacro.macroCell.MacroCell;
import com.wearsky.keyMacro.main.Main;
import com.wearsky.keyMacro.utils.Constants.SystemStatus;

import java.util.concurrent.TimeUnit;

/**
 * 持续触发宏，按下触发，松开停止
 *
 * @author peng
 *
 */
public class ContinualMacro extends Macro {

    /**
     * 每一轮循环的时间间隔/ms
     */
    private final long intervalTime;

    /**
     * 构造持续触发宏，按下触发，松开停止
     *
     * @param macroCells   宏单元数组，按顺序触发
     * @param intervalTime 每一轮循环的时间间隔/ms
     */
    public ContinualMacro(MacroCell[] macroCells, long intervalTime) {
        this.macroCells = macroCells;
        this.intervalTime = intervalTime;
    }

    /**
     * 触发键按下
     */
    @Override
    public void keyDown() {
        if (!isRunning) {
            synchronized (this) {
                if (!isRunning) {
                    isRunning = true;
                    this.notifyAll();
                }
            }
        }
    }

    /**
     * 触发键弹起
     */
    @Override
    public void keyUp() {
        if (isRunning) {
            synchronized (this) {
                if (isRunning) {
                    isRunning = false;
                    this.interrupt();
                }
            }
        }
    }

    /**
     * 删除宏
     */
    @Override
    public void delete() {
        synchronized (this) {
            isRunning = false;
            isDeleted = true;
            this.interrupt();
        }
        try {
            this.join();
        } catch (InterruptedException _) {
        }
    }

    /**
     * 启动宏线程
     */
    @Override
    public void run() {
        while (!isDeleted) {
            if (isRunning && Main.systemStatus == SystemStatus.RUNNING) {
                for (MacroCell macroCell : macroCells) {
                    if (this.isInterrupted()) {
                        break;
                    }
                    macroCell.performed();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(intervalTime);
                } catch (InterruptedException _) {
                }
            } else {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException _) {
                    }
                }
            }
        }
    }

}
