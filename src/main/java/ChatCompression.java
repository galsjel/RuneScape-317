public class ChatCompression {

	private static final char[] cbuf = new char[100];
	private static final Buffer buf = new Buffer(new byte[100]);

	private static final char[] TABLE = { //
			// only this first row is actually combined with anything else
			' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', //
			// the rest are just 'accepted' values.
			'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', //
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', //
			' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\u00A3', '$', '%', '"', '[', ']' //
	};

	private static final int RANGE = ' ' + '\u00A3'; // least significant char + most significant char (in terms of value)

	public static String unpack(int expectedLength, Buffer buffer) {
		int length = 0;
		int carry = -1;

		for (int i = 0; i < expectedLength; i++) {
			int value = buffer.get1U();
			int nibble = (value >> 4) & 0xf;

			if (carry == -1) {
				if (nibble < 13) {
					cbuf[length++] = TABLE[nibble];
				} else {
					carry = nibble;
				}
			} else {
				cbuf[length++] = TABLE[((carry << 4) + nibble) - RANGE];
				carry = -1;
			}

			nibble = value & 0xf;

			if (carry == -1) {
				if (nibble < 13) {
					cbuf[length++] = TABLE[nibble];
				} else {
					carry = nibble;
				}
			} else {
				cbuf[length++] = TABLE[((carry << 4) + nibble) - RANGE];
				carry = -1;
			}
		}

		// basic sentence casing
		// "hi. i'm a line." -> "Hi. I'm a line."
		boolean uppercase = true;
		for (int i = 0; i < length; i++) {
			char c = cbuf[i];
			if (uppercase && (c >= 'a') && (c <= 'z')) {
				cbuf[i] -= 'a' - 'A';
				uppercase = false;
			}

			if ((c == '.') || (c == '!') || (c == '?')) {
				uppercase = true;
			}
		}

		return new String(cbuf, 0, length);
	}

	public static void pack(String s, Buffer dst) {
		if (s.length() > 80) {
			s = s.substring(0, 80);
		}
		s = s.toLowerCase();

		int carry = -1;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			int index = 0;
			for (int j = 0; j < TABLE.length; j++) {
				if (ch != TABLE[j]) {
					continue;
				}
				index = j;
				break;
			}

			if (index > 12) {
				index += RANGE;
			}

			if (carry == -1) {
				if (index < 13) {
					carry = index;
				} else {
					dst.put1(index);
				}
			} else if (index < 13) {
				dst.put1((carry << 4) + index);
				carry = -1;
			} else {
				dst.put1((carry << 4) + (index >> 4));
				carry = index & 0xf;
			}
		}

		if (carry != -1) {
			dst.put1(carry << 4);
		}
	}

	public static String method527(String string) {
		buf.position = 0;
		pack(string, buf);
		int length = buf.position;
		buf.position = 0;
		return unpack(length, buf);
	}

	private ChatCompression() {

	}

}
