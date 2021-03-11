import java.util.Arrays;

/**
 * Utility class containing an assortment of string functions.
 */
public class StringUtil {

	private static final char[] BASE37_TABLE = { //
			'_', //
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', //
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' //
	};

	public static long toBase37(String s) {
		long l = 0L;
		for (int i = 0; (i < s.length()) && (i < 12); i++) {
			char c = s.charAt(i);
			l *= 37L;
			if ((c >= 'A') && (c <= 'Z')) {
				l += (1 + c) - 'A';
			} else if ((c >= 'a') && (c <= 'z')) {
				l += (1 + c) - 'a';
			} else if ((c >= '0') && (c <= '9')) {
				l += (27 + c) - '0';
			}
		}
		while (((l % 37L) == 0L) && (l != 0L)) {
			l /= 37L;
		}
		return l;
	}

	public static String fromBase37(long value) {
		if ((value <= 0L) || (value >= 0x5b5b57f8a98a5dd1L)/*37^12*/) {
			return "invalid_name";
		}
		if ((value % 37L) == 0L) {
			return "invalid_name";
		}
		int len = 0;
		char[] tmp = new char[12];
		while (value != 0L) {
			long last = value;
			value /= 37L;
			tmp[11 - len++] = BASE37_TABLE[(int) (last - (value * 37L))];
		}
		return new String(tmp, 12 - len, len);
	}

	public static long hashCode(String s) {
		s = s.toUpperCase();
		long hash = 0L;
		for (int i = 0; i < s.length(); i++) {
			hash = ((hash * 61L) + (long) s.charAt(i)) - 32L;
			hash = (hash + (hash >> 56)) & 0xffffffffffffffL;
		}
		return hash;
	}

	public static String formatIPv4(int ipv4) {
		return String.format("%d.%d.%d.%d", (ipv4 >> 24) & 0xff, (ipv4 >> 16) & 0xff, (ipv4 >> 8) & 0xff, ipv4 & 0xff);
	}

	public static String formatName(String s) {
		if (s.length() > 0) {
			char[] ac = s.toCharArray();
			for (int j = 0; j < ac.length; j++) {
				if (ac[j] == '_') {
					ac[j] = ' ';
					if (((j + 1) < ac.length) && (ac[j + 1] >= 'a') && (ac[j + 1] <= 'z')) {
						ac[j + 1] = (char) ((ac[j + 1] + 'A') - 'a');
					}
				}
			}
			if ((ac[0] >= 'a') && (ac[0] <= 'z')) {
				ac[0] = (char) ((ac[0] + 'A') - 'a');
			}
			return new String(ac);
		} else {
			return s;
		}
	}

	public static String toAsterisks(String s) {
		char[] chars = new char[s.length()];
		Arrays.fill(chars, '*');
		return new String(chars, 0, chars.length);
	}

	private StringUtil() {

	}

}
