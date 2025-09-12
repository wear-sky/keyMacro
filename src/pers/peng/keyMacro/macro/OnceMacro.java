package pers.peng.keyMacro.macro;

import pers.peng.keyMacro.macroCell.MacroCell;
import pers.peng.keyMacro.main.Main;
import pers.peng.keyMacro.utils.Constants.SystemStatus;

/**
 * 单次执行宏，按一下执行一次，执行完成后才能触发下一次
 *
 * @author peng
 *
 */
public class OnceMacro extends Macro {

    /**
     * 构造单次执行宏
     *
     * @param macroCells 宏单元数组，按顺序执行
     */
    public OnceMacro(MacroCell[] macroCells) {
        this.macroCells = macroCells;
    }

    /**
     * 触发键按下
     */
    @Override
    public void keyDown() {
        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * 触发键弹起
     */
    @Override
    public void keyUp() {
    }

    /**
     * 删除宏
     */
    @Override
    public void delete() {
        isDeleted = true;
        this.interrupt();
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
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException _) {
                }
            }
            if (!isDeleted && Main.systemStatus == SystemStatus.RUNNING) {
                for (MacroCell macroCell : macroCells) {
                    if (this.isInterrupted()) {
                        break;
                    }
                    macroCell.performed();
                }
            }
        }
    }

}
