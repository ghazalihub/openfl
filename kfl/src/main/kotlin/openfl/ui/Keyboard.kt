package openfl.ui

object Keyboard {
    const val NUMBER_0: Int = 48
    const val NUMBER_1: Int = 49
    const val NUMBER_2: Int = 50
    const val NUMBER_3: Int = 51
    const val NUMBER_4: Int = 52
    const val NUMBER_5: Int = 53
    const val NUMBER_6: Int = 54
    const val NUMBER_7: Int = 55
    const val NUMBER_8: Int = 56
    const val NUMBER_9: Int = 57
    const val A: Int = 65
    const val B: Int = 66
    const val C: Int = 67
    const val D: Int = 68
    const val E: Int = 69
    const val F: Int = 70
    const val G: Int = 71
    const val H: Int = 72
    const val I: Int = 73
    const val J: Int = 74
    const val K: Int = 75
    const val L: Int = 76
    const val M: Int = 77
    const val N: Int = 78
    const val O: Int = 79
    const val P: Int = 80
    const val Q: Int = 81
    const val R: Int = 82
    const val S: Int = 83
    const val T: Int = 84
    const val U: Int = 85
    const val V: Int = 86
    const val W: Int = 87
    const val X: Int = 88
    const val Y: Int = 89
    const val Z: Int = 90
    const val NUMPAD_0: Int = 96
    const val NUMPAD_1: Int = 97
    const val NUMPAD_2: Int = 98
    const val NUMPAD_3: Int = 99
    const val NUMPAD_4: Int = 100
    const val NUMPAD_5: Int = 101
    const val NUMPAD_6: Int = 102
    const val NUMPAD_7: Int = 103
    const val NUMPAD_8: Int = 104
    const val NUMPAD_9: Int = 105
    const val NUMPAD_MULTIPLY: Int = 106
    const val NUMPAD_ADD: Int = 107
    const val NUMPAD_ENTER: Int = 108
    const val NUMPAD_SUBTRACT: Int = 109
    const val NUMPAD_DECIMAL: Int = 110
    const val NUMPAD_DIVIDE: Int = 111
    const val F1: Int = 112
    const val F2: Int = 113
    const val F3: Int = 114
    const val F4: Int = 115
    const val F5: Int = 116
    const val F6: Int = 117
    const val F7: Int = 118
    const val F8: Int = 119
    const val F9: Int = 120
    const val F10: Int = 121
    const val F11: Int = 122
    const val F12: Int = 123
    const val F13: Int = 124
    const val F14: Int = 125
    const val F15: Int = 126
    const val BACKSPACE: Int = 8
    const val TAB: Int = 9
    const val ALTERNATE: Int = 18
    const val ENTER: Int = 13
    const val COMMAND: Int = 15
    const val SHIFT: Int = 16
    const val CONTROL: Int = 17
    const val BREAK: Int = 19
    const val CAPS_LOCK: Int = 20
    const val NUMPAD: Int = 21
    const val ESCAPE: Int = 27
    const val SPACE: Int = 32
    const val PAGE_UP: Int = 33
    const val PAGE_DOWN: Int = 34
    const val END: Int = 35
    const val HOME: Int = 36
    const val LEFT: Int = 37
    const val RIGHT: Int = 39
    const val UP: Int = 38
    const val DOWN: Int = 40
    const val INSERT: Int = 45
    const val DELETE: Int = 46
    const val NUMLOCK: Int = 144
    const val SEMICOLON: Int = 186
    const val EQUAL: Int = 187
    const val COMMA: Int = 188
    const val MINUS: Int = 189
    const val PERIOD: Int = 190
    const val SLASH: Int = 191
    const val BACKQUOTE: Int = 192
    const val LEFTBRACKET: Int = 219
    const val BACKSLASH: Int = 220
    const val RIGHTBRACKET: Int = 221
    const val QUOTE: Int = 222

    var capsLock: Boolean = false
        internal set
    var numLock: Boolean = false
        internal set

    fun isAccessible(): Boolean = false

    internal fun __getCharCode(key: Int, shift: Boolean = false, capsLock: Boolean = false): Int {
        var effectiveShift = shift
        if (key in A..Z) {
            effectiveShift = shift != capsLock
        }
        if (!effectiveShift) {
            when (key) {
                BACKSPACE -> return 8
                TAB -> return 9
                ENTER -> return 13
                ESCAPE -> return 27
                SPACE -> return 32
                SEMICOLON -> return 59
                EQUAL -> return 61
                COMMA -> return 44
                MINUS -> return 45
                PERIOD -> return 46
                SLASH -> return 47
                BACKQUOTE -> return 96
                LEFTBRACKET -> return 91
                BACKSLASH -> return 92
                RIGHTBRACKET -> return 93
                QUOTE -> return 39
            }
            if (key in NUMBER_0..NUMBER_9) return key - NUMBER_0 + 48
            if (key in A..Z) return key - A + 97
        } else {
            when (key) {
                NUMBER_0 -> return 41
                NUMBER_1 -> return 33
                NUMBER_2 -> return 64
                NUMBER_3 -> return 35
                NUMBER_4 -> return 36
                NUMBER_5 -> return 37
                NUMBER_6 -> return 94
                NUMBER_7 -> return 38
                NUMBER_8 -> return 42
                NUMBER_9 -> return 40
                SEMICOLON -> return 58
                EQUAL -> return 43
                COMMA -> return 60
                MINUS -> return 95
                PERIOD -> return 62
                SLASH -> return 63
                BACKQUOTE -> return 126
                LEFTBRACKET -> return 123
                BACKSLASH -> return 124
                RIGHTBRACKET -> return 125
                QUOTE -> return 34
            }
            if (key in A..Z) return key - A + 65
        }
        if (key in NUMPAD_0..NUMPAD_9) return key - NUMPAD_0 + 48
        when (key) {
            NUMPAD_MULTIPLY -> return 42
            NUMPAD_ADD -> return 43
            NUMPAD_ENTER -> return 44
            NUMPAD_DECIMAL -> return 45
            NUMPAD_DIVIDE -> return 46
            DELETE -> return 127
            ENTER -> return 13
            BACKSPACE -> return 8
        }
        return 0
    }
}
