package org.wearsky.keyMacro.macroCell;

import org.wearsky.keyMacro.utils.Constants;
import org.wearsky.keyMacro.utils.DD;

/**
 * 鼠标按键宏单元
 *
 * @param ddCode dd代码
 * @author peng
 * @see Constants.DdCode
 */
public record MouseClickMC(int ddCode) implements MacroCell {

    /**
     * dd驱动实例
     */
    private static final DD dd = DD.INSTANCE;

    /**
     * 执行宏单元
     */
    @Override
    public void performed() {
        dd.DD_btn(ddCode);
    }

}
