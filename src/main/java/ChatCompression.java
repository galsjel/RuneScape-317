public final class ChatCompression {

    private static final char[] charBuffer = new char[100];
    private static final Buffer buffer = new Buffer(new byte[100]);

    private static final char[] TABLE = {
            // only this first row is actually combined with anything else
            ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u',
            // the rest are just 'accepted' values.
            'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '£', '$', '%', '"', '[', ']'
    };

    private static final int RANGE = ' ' + '£'; // least significant char + most significant char (in terms of value)

    public static String unpack(int length, Buffer in) {
        int pos = 0;
        int carry = -1;

        for (int i = 0; i < length; i++) {
            int value = in.readU8();
            int nibble = (value >> 4) & 0b1111;

            if (carry == -1) {
                if (nibble < 13) {
                    charBuffer[pos++] = TABLE[nibble];
                } else {
                    carry = nibble;
                }
            } else {
                charBuffer[pos++] = TABLE[((carry << 4) + nibble) - RANGE];
                carry = -1;
            }

            nibble = value & 0xf;

            if (carry == -1) {
                if (nibble < 13) {
                    charBuffer[pos++] = TABLE[nibble];
                } else {
                    carry = nibble;
                }
            } else {
                charBuffer[pos++] = TABLE[((carry << 4) + nibble) - RANGE];
                carry = -1;
            }
        }

        // basic sentence casing
        // "hi. i'm a line." -> "Hi. I'm a line."
        boolean uppercase = true;
        for (int i = 0; i < pos; i++) {
            char c = charBuffer[i];
            if (uppercase && (c >= 'a') && (c <= 'z')) {
                charBuffer[i] -= 'a' - 'A';
                uppercase = false;
            }

            if ((c == '.') || (c == '!') || (c == '?')) {
                uppercase = true;
            }
        }

        return new String(charBuffer, 0, pos);
    }

    public static void pack(String in, Buffer out) {
        if (in.length() > 80) {
            in = in.substring(0, 80);
        }
        in = in.toLowerCase();

        int carry = -1;
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);

            int index = 0;
            for (int j = 0; j < TABLE.length; j++) {
                if (ch == TABLE[j]) {
                    index = j;
                    break;
                }
            }

            if (index > 12) {
                index += RANGE;
            }

            if (carry == -1) {
                if (index < 13) {
                    carry = index;
                } else {
                    out.write8(index);
                }
            } else if (index < 13) {
                out.write8((carry << 4) + index);
                carry = -1;
            } else {
                out.write8((carry << 4) + (index >> 4));
                carry = index & 0xf;
            }
        }

        if (carry != -1) {
            out.write8(carry << 4);
        }
    }

    public static String format(String string) {
        buffer.position = 0;
        pack(string, buffer);
        int length = buffer.position;
        buffer.position = 0;
        return unpack(length, buffer);
    }

    private ChatCompression() {

    }

}
