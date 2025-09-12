package org.wearsky.keyMacro.utils;

/**
 * 常量类
 *
 * @author peng
 *
 */
public class Constants {

    /**
     * 系统状态
     *
     * @author peng
     *
     */
    public enum SystemStatus {

        /**
         * 运行中
         */
        RUNNING(),

        /**
         * 正在录入触发键
         */
        RECORDIND_TRIGGERKEY(),

        /**
         * 正在录入连发键
         */
        RECORDIND_TARGETKEY(),

        /**
         * 正在录入开关键
         */
        RECORDIND_SWITCHKEY(),

        /**
         * 正在录入切换配置快捷键
         */
        RECORDIND_SWITCHCONFIGKEY(),

        /**
         * 暂停中
         */
        STOPPED();

        /**
         * 系统状态枚举类构造器
         *
         */
        SystemStatus() {
        }

    }

    /**
     * 键盘钩子按键码
     *
     * @author peng
     *
     */
    public enum VkCode {

        ESC(27),

        F1(112),

        F2(113),

        F3(114),

        F4(115),

        F5(116),

        F6(117),

        F7(118),

        F8(119),

        F9(120),

        F10(121),

        F11(122),

        F12(123),

        BACK_QUOTE(192),

        NUM1(49),

        NUM2(50),

        NUM3(51),

        NUM4(52),

        NUM5(53),

        NUM6(54),

        NUM7(55),

        NUM8(56),

        NUM9(57),

        NUM0(48),

        MINUS(189),

        EQUALS(187),

        BACK_SPACE(8),

        TAB(9),

        Q(81),

        W(87),

        E(69),

        R(82),

        T(84),

        Y(89),

        U(85),

        I(73),

        O(79),

        P(80),

        OPEN_BRACKET(219),

        CLOSE_BRACKET(221),

        BACK_SLASH(220),

        CAPS_LOCK(20),

        A(65),

        S(83),

        D(68),

        F(70),

        G(71),

        H(72),

        J(74),

        K(75),

        L(76),

        SEMICOLON(186),

        QUOTE(222),

        ENTER(13),

        LEFT_SHIFT(160),

        Z(90),

        X(88),

        C(67),

        V(86),

        B(66),

        N(78),

        M(77),

        COMMA(188),

        PERIOD(190),

        SLASH(191),

        RIGHT_SHIFT(161),

        LEFT_CTRL(162),

        LEFT_WIN(91),

        SPACE(32),

        MENU(93),

        RIGHT_CTRL(163),

        PRINT_SCREEN(44),

        SCROLL_LOCK(145),

        PAUSE_BREAK(19),

        INSERT(45),

        DELETE(46),

        HOME(36),

        END(35),

        PAGE_UP(33),

        PAGE_DOWN(34),

        UP(38),

        LEFT(37),

        DOWN(40),

        RIGHT(39),

        NUMPAD0(96),

        NUMPAD1(97),

        NUMPAD2(98),

        NUMPAD3(99),

        NUMPAD4(100),

        NUMPAD5(101),

        NUMPAD6(102),

        NUMPAD7(103),

        NUMPAD8(104),

        NUMPAD9(105),

//		NUM_LOCK(144),

        DIVIDE(111),

        MULTIPLY(106),

        SUBTRACT(109),

        ADD(107),

        NUMPAD_ENTER(13),

        DECIMAL(110);

        /**
         * 常量值
         */
        private final Integer value;

        /**
         * 键盘钩子按键码枚举类构造器
         *
         * @param value 常量值
         */
        VkCode(Integer value) {
            this.value = value;
        }

        /**
         * 取得整型的常量值
         *
         * @return 常量值
         */
        public Integer value() {
            return this.value;
        }

        /**
         * 根据整型的常量值取得指定枚举常量
         *
         * @param value 整型的常量值
         * @return 枚举常量实例
         */
        public static VkCode getVkCode(Integer value) throws IllegalArgumentException {
            for (VkCode vkCode : VkCode.values()) {
                if (vkCode.value.equals(value)) {
                    return vkCode;
                }
            }
            return null;
        }

        /**
         * 根据枚举常量的名字取得指定枚举常量
         *
         * @param name 枚举常量的名字
         * @return 枚举常量实例
         */
        public static VkCode getVkCode(String name) throws IllegalArgumentException {
            for (VkCode vkCode : VkCode.values()) {
                if (name.equals(vkCode.name())) {
                    return vkCode;
                }
            }
            return null;
        }
    }

    public enum DdCode {

        ESC(100),

        F1(101),

        F2(102),

        F3(103),

        F4(104),

        F5(105),

        F6(106),

        F7(107),

        F8(108),

        F9(109),

        F10(110),

        F11(111),

        F12(112),

        BACK_QUOTE(200),

        NUM1(201),

        NUM2(202),

        NUM3(203),

        NUM4(204),

        NUM5(205),

        NUM6(206),

        NUM7(207),

        NUM8(208),

        NUM9(209),

        NUM0(210),

        MINUS(211),

        EQUALS(212),

        BACK_SPACE(214),

        TAB(300),

        Q(301),

        W(302),

        E(303),

        R(304),

        T(305),

        Y(306),

        U(307),

        I(308),

        O(309),

        P(310),

        OPEN_BRACKET(311),

        CLOSE_BRACKET(312),

        BACK_SLASH(213),

        CAPS_LOCK(400),

        A(401),

        S(402),

        D(403),

        F(404),

        G(405),

        H(406),

        J(407),

        K(408),

        L(409),

        SEMICOLON(410),

        QUOTE(411),

        ENTER(313),

        LEFT_SHIFT(500),

        Z(501),

        X(502),

        C(503),

        V(504),

        B(505),

        N(506),

        M(507),

        COMMA(508),

        PERIOD(509),

        SLASH(510),

        RIGHT_SHIFT(511),

        LEFT_CTRL(600),

        LEFT_WIN(601),

        LEFT_ALT(602),

        SPACE(603),

        RIGHT_ALD(604),

        MENU(606),

        RIGHT_CTRL(607),

        PRINT_SCREEN(700),

        SCROLL_LOCK(701),

        PAUSE_BREAK(702),

        INSERT(703),

        DELETE(706),

        HOME(704),

        END(707),

        PAGE_UP(705),

        PAGE_DOWN(708),

        UP(709),

        LEFT(710),

        DOWN(711),

        RIGHT(712),

        NUMPAD0(800),

        NUMPAD1(801),

        NUMPAD2(802),

        NUMPAD3(803),

        NUMPAD4(804),

        NUMPAD5(805),

        NUMPAD6(806),

        NUMPAD7(807),

        NUMPAD8(808),

        NUMPAD9(809),

        NUM_LOCK(810),

        DIVIDE(811),

        MULTIPLY(812),

        SUBTRACT(813),

        ADD(814),

        NUMPAD_ENTER(815),

        DECIMAL(816),

        LEFT_CLICK_DOWN(1),

        LEFT_CLICK_UP(2),

        RIGHT_CLICK_DOWN(4),

        RIGHT_CLICK_UP(8),

        MBUTTON_DOWN(16),

        MBUTTON_UP(32),

        XBUTTON1_DOWN(64),

        XBUTTON1_UP(128),

        XBUTTON2_DOWN(256),

        XBUTTON2_UP(512);

        private final Integer value;

        DdCode(Integer value) {
            this.value = value;
        }

        /**
         * 取得整型的枚举值
         *
         * @return 枚举值
         */
        public Integer value() {
            return this.value;
        }

        /**
         * 根据枚举常量的名字取得指定枚举常量
         *
         * @param name 枚举常量的名字
         * @return 枚举常量实例
         */
        public static DdCode getDdCode(String name) throws IllegalArgumentException {
            for (DdCode ddCode : DdCode.values()) {
                if (name.equals(ddCode.name())) {
                    return ddCode;
                }
            }
            return null;
        }
    }

    /**
     * 宏触发方式
     *
     * @author peng
     *
     */
    public enum TriggerType {

        连发(),

        开关(),

        单次();

        /**
         * 宏触发方式类构造器
         *
         */
        TriggerType() {
        }

        /**
         * 根据枚举常量的名字取得指定枚举常量
         *
         * @param name 枚举常量的名字
         * @return 枚举常量实例
         */
        public static TriggerType getTriggerType(String name) throws IllegalArgumentException {
            for (TriggerType triggerType : TriggerType.values()) {
                if (triggerType.name().equals(name)) {
                    return triggerType;
                }
            }
            return null;
        }

    }

}
