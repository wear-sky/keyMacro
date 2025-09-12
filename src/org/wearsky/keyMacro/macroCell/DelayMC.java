package org.wearsky.keyMacro.macroCell;

import java.util.concurrent.TimeUnit;

/**
 * 延迟宏单元
 *
 * @param delay 延迟/ms
 * @author peng
 */
public record DelayMC(long delay) implements MacroCell {

    /**
     * 执行宏单元
     */
    @Override
    public void performed() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();// sleep中中断，中断状态会被清空，需要重新中断
        }
    }

}
