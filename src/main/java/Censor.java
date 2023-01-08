// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class Censor {

    public static final String[] ALLOWLIST = {"cook", "cook's", "cooks", "seeks", "sheet", "woop", "woops", "faq", "noob", "noobs"};
    public static int[] fragments;
    public static char[][] badwords;
    public static byte[][][] badCombinations;
    public static char[][] domains;
    public static char[][] tlds;
    public static int[] tldType;

    public static void unpack(FileArchive archive) throws IOException {
        readBadWords(new Buffer(archive.read("badenc.txt")));
        readDomains(new Buffer(archive.read("domainenc.txt")));
        readFragments(new Buffer(archive.read("fragmentsenc.txt")));
        readTLD(new Buffer(archive.read("tldlist.txt")));
    }

    public static void readTLD(Buffer in) {
        int count = in.read32();
        tlds = new char[count][];
        tldType = new int[count];
        for (int i = 0; i < count; i++) {
            tldType[i] = in.readU8();

            char[] tld = new char[in.readU8()];
            for (int j = 0; j < tld.length; j++) {
                tld[j] = (char) in.readU8();
            }

            tlds[i] = tld;
        }
    }

    public static void readBadWords(Buffer in) {
        int count = in.read32();
        badwords = new char[count][];
        badCombinations = new byte[count][][];

        for (int i = 0; i < badwords.length; i++) {
            char[] badword = new char[in.readU8()];

            for (int j = 0; j < badword.length; j++) {
                badword[j] = (char) in.readU8();
            }

            badwords[i] = badword;

            byte[][] combination = new byte[in.readU8()][2];

            for (int j = 0; j < combination.length; j++) {
                combination[j][0] = (byte) in.readU8();
                combination[j][1] = (byte) in.readU8();
            }

            if (combination.length > 0) {
                badCombinations[i] = combination;
            }
        }
    }

    public static void readDomains(Buffer in) {
        int count = in.read32();
        domains = new char[count][];
        for (int i = 0; i < domains.length; i++) {
            char[] domain = new char[in.readU8()];
            for (int j = 0; j < domain.length; j++) {
                domain[j] = (char) in.readU8();
            }
            domains[i] = domain;
        }
    }

    public static void readFragments(Buffer buffer) {
        fragments = new int[buffer.read32()];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = buffer.readU16();
        }
    }

    public static void filterCharacters(char[] in) {
        int pos = 0;
        for (int i = 0; i < in.length; i++) {
            if (allowCharacter(in[i])) {
                in[pos] = in[i];
            } else {
                in[pos] = ' ';
            }

            if ((pos == 0) || (in[pos] != ' ') || (in[pos - 1] != ' ')) {
                pos++;
            }
        }
        for (int i = pos; i < in.length; i++) {
            in[i] = ' ';
        }
    }

    public static boolean allowCharacter(char c) {
        if (c >= ' ' && c <= '\u007F') return true;
        if (c == ' ') return true;
        if (c == '\n') return true;
        if (c == '\t') return true;
        if (c == '£') return true;
        return c == '€';
    }

    public static String filter(String in) {
        char[] output = in.toCharArray();
        filterCharacters(output);

        String trimmed = new String(output).trim();
        output = trimmed.toLowerCase().toCharArray();

        filterTLD(output);
        filterBad(output);
        filterDomains(output);
        filterFragments(output);

        String lowercase = trimmed.toLowerCase();

        for (String allowed : ALLOWLIST) {
            for (int i = -1; (i = lowercase.indexOf(allowed, i + 1)) != -1; ) {
                char[] src = allowed.toCharArray();
                System.arraycopy(src, 0, output, i, src.length);
            }
        }

        replaceUpperCases(trimmed.toCharArray(), output);
        formatUpperCases(output);
        return new String(output).trim();
    }

    public static void replaceUpperCases(char[] unfiltered, char[] in) {
        for (int i = 0; i < unfiltered.length; i++) {
            if ((in[i] != '*') && isUpperCase(unfiltered[i])) {
                in[i] = unfiltered[i];
            }
        }
    }

    public static void formatUpperCases(char[] in) {
        boolean upper = true;
        for (int i = 0; i < in.length; i++) {
            char c = in[i];
            if (isAlpha(c)) {
                if (upper) {
                    if (isLowerCase(c)) {
                        upper = false;
                    }
                } else if (isUpperCase(c)) {
                    in[i] = (char) ((c + 'a') - 'A');
                }
            } else {
                upper = true;
            }
        }
    }

    public static void filterBad(char[] in) {
        for (int passes = 0; passes < 2; passes++) {
            for (int i = badwords.length - 1; i >= 0; i--) {
                filter(badCombinations[i], in, badwords[i]);
            }
        }
    }

    public static void filterDomains(char[] in) {
        char[] filteredAt = in.clone();
        filter(null, filteredAt, new char[]{'(', 'a', ')'});
        char[] filteredDot = in.clone();
        filter(null, filteredDot, new char[]{'d', 'o', 't'});
        for (int i = domains.length - 1; i >= 0; i--) {
            filterDomain(in, domains[i], filteredDot, filteredAt);
        }
    }

    public static void filterDomain(char[] in, char[] domain, char[] filteredDot, char[] filteredAt) {
        if (domain.length > in.length) {
            return;
        }

        int stride;
        for (int start = 0; start <= (in.length - domain.length); start += stride) {
            int end = start;
            int offset = 0;
            stride = 1;

            while (end < in.length) {
                int charSize;
                char b = in[end];
                char c = '\0';

                if ((end + 1) < in.length) {
                    c = in[end + 1];
                }

                if ((offset < domain.length) && (((charSize = getEmulatedDomainCharSize(domain[offset], b, c))) > 0)) {
                    end += charSize;
                    offset++;
                    continue;
                }

                if (offset == 0) {
                    break;
                }
                if ((charSize = getEmulatedDomainCharSize(domain[offset - 1], b, c)) > 0) {
                    end += charSize;
                    if (offset == 1) {
                        stride++;
                    }
                    continue;
                }
                if ((offset >= domain.length) || !isSymbol(b)) {
                    break;
                }
                end++;
            }
            if (offset >= domain.length) {
                boolean flag1 = false;
                int k1 = method503(in, filteredAt, start);
                int l1 = method504(filteredDot, end - 1, in);
                if ((k1 > 2) || (l1 > 2)) {
                    flag1 = true;
                }
                if (flag1) {
                    for (int i2 = start; i2 < end; i2++) {
                        in[i2] = '*';
                    }
                }
            }
        }
    }

    public static int method503(char[] ac, char[] ac1, int j) {
        if (j == 0) {
            return 2;
        }
        for (int k = j - 1; k >= 0; k--) {
            if (!isSymbol(ac[k])) {
                break;
            }
            if (ac[k] == '@') {
                return 3;
            }
        }
        int l = 0;
        for (int i1 = j - 1; i1 >= 0; i1--) {
            if (!isSymbol(ac1[i1])) {
                break;
            }
            if (ac1[i1] == '*') {
                l++;
            }
        }
        if (l >= 3) {
            return 4;
        }
        return !isSymbol(ac[j - 1]) ? 0 : 1;
    }

    public static int method504(char[] ac, int i, char[] ac1) {
        if ((i + 1) == ac1.length) {
            return 2;
        }
        for (int j = i + 1; j < ac1.length; j++) {
            if (!isSymbol(ac1[j])) {
                break;
            }
            if ((ac1[j] == '.') || (ac1[j] == ',')) {
                return 3;
            }
        }
        int k = 0;
        for (int l = i + 1; l < ac1.length; l++) {
            if (!isSymbol(ac[l])) {
                break;
            }
            if (ac[l] == '*') {
                k++;
            }
        }
        if (k >= 3) {
            return 4;
        }
        return !isSymbol(ac1[i + 1]) ? 0 : 1;
    }

    public static void filterTLD(char[] in) {
        char[] filteredDot = in.clone();
        filter(null, filteredDot, new char[]{'d', 'o', 't'});

        char[] filteredSlash = in.clone();
        filter(null, filteredSlash, new char[]{'s', 'l', 'a', 's', 'h'});

        for (int i = 0; i < tlds.length; i++) {
            filterTLD(filteredSlash, tlds[i], tldType[i], filteredDot, in);
        }
    }

    // I stopped caring right here.
    public static void filterTLD(char[] ac, char[] ac1, int i, char[] ac2, char[] ac3) {
        if (ac1.length > ac3.length) {
            return;
        }
        int j;
        for (int k = 0; k <= (ac3.length - ac1.length); k += j) {
            int l = k;
            int i1 = 0;
            j = 1;
            while (l < ac3.length) {
                int j1;
                char c = ac3[l];
                char c1 = '\0';
                if ((l + 1) < ac3.length) {
                    c1 = ac3[l + 1];
                }
                if ((i1 < ac1.length) && (((j1 = getEmulatedDomainCharSize(ac1[i1], c, c1))) > 0)) {
                    l += j1;
                    i1++;
                    continue;
                }
                if (i1 == 0) {
                    break;
                }
                if ((j1 = getEmulatedDomainCharSize(ac1[i1 - 1], c, c1)) > 0) {
                    l += j1;
                    if (i1 == 1) {
                        j++;
                    }
                    continue;
                }
                if ((i1 >= ac1.length) || !isSymbol(c)) {
                    break;
                }
                l++;
            }
            if (i1 >= ac1.length) {
                boolean flag1 = false;
                int k1 = method507(ac3, k, ac2);
                int l1 = method508(ac3, ac, l - 1);
                if ((i == 1) && (k1 > 0) && (l1 > 0)) {
                    flag1 = true;
                }
                if ((i == 2) && (((k1 > 2) && (l1 > 0)) || ((k1 > 0) && (l1 > 2)))) {
                    flag1 = true;
                }
                if ((i == 3) && (k1 > 0) && (l1 > 2)) {
                    flag1 = true;
                }
                if (flag1) {
                    int i2 = k;
                    int j2 = l - 1;
                    if (k1 > 2) {
                        if (k1 == 4) {
                            boolean flag2 = false;
                            for (int l2 = i2 - 1; l2 >= 0; l2--) {
                                if (flag2) {
                                    if (ac2[l2] != '*') {
                                        break;
                                    }
                                    i2 = l2;
                                } else if (ac2[l2] == '*') {
                                    i2 = l2;
                                    flag2 = true;
                                }
                            }
                        }
                        boolean flag3 = false;
                        for (int i3 = i2 - 1; i3 >= 0; i3--) {
                            if (flag3) {
                                if (isSymbol(ac3[i3])) {
                                    break;
                                }
                                i2 = i3;
                            } else if (!isSymbol(ac3[i3])) {
                                flag3 = true;
                                i2 = i3;
                            }
                        }
                    }
                    if (l1 > 2) {
                        if (l1 == 4) {
                            boolean flag4 = false;
                            for (int j3 = j2 + 1; j3 < ac3.length; j3++) {
                                if (flag4) {
                                    if (ac[j3] != '*') {
                                        break;
                                    }
                                    j2 = j3;
                                } else if (ac[j3] == '*') {
                                    j2 = j3;
                                    flag4 = true;
                                }
                            }
                        }
                        boolean flag5 = false;
                        for (int k3 = j2 + 1; k3 < ac3.length; k3++) {
                            if (flag5) {
                                if (isSymbol(ac3[k3])) {
                                    break;
                                }
                                j2 = k3;
                            } else if (!isSymbol(ac3[k3])) {
                                flag5 = true;
                                j2 = k3;
                            }
                        }
                    }
                    for (int k2 = i2; k2 <= j2; k2++) {
                        ac3[k2] = '*';
                    }
                }
            }
        }
    }

    public static int method507(char[] ac, int j, char[] ac1) {
        if (j == 0) {
            return 2;
        }
        for (int k = j - 1; k >= 0; k--) {
            if (!isSymbol(ac[k])) {
                break;
            }
            if ((ac[k] == ',') || (ac[k] == '.')) {
                return 3;
            }
        }
        int l = 0;
        for (int i1 = j - 1; i1 >= 0; i1--) {
            if (!isSymbol(ac1[i1])) {
                break;
            }
            if (ac1[i1] == '*') {
                l++;
            }
        }
        if (l >= 3) {
            return 4;
        }
        return !isSymbol(ac[j - 1]) ? 0 : 1;
    }

    public static int method508(char[] ac, char[] ac1, int i) {
        if ((i + 1) == ac.length) {
            return 2;
        }
        for (int j = i + 1; j < ac.length; j++) {
            if (!isSymbol(ac[j])) {
                break;
            }
            if ((ac[j] == '\\') || (ac[j] == '/')) {
                return 3;
            }
        }
        int k = 0;
        for (int l = i + 1; l < ac.length; l++) {
            if (!isSymbol(ac1[l])) {
                break;
            }
            if (ac1[l] == '*') {
                k++;
            }
        }
        if (k >= 5) {
            return 4;
        }
        return !isSymbol(ac[i + 1]) ? 0 : 1;
    }

    private static void filter(byte[][] badCombinations, char[] in, char[] fragment) {
        if (fragment.length > in.length) {
            return;
        }

        int stride;
        for (int start = 0; start <= (in.length - fragment.length); start += stride) {
            int end = start;
            int fragmentOffset = 0;
            int iterations = 0;
            stride = 1;

            boolean isSymbol = false;
            boolean isEmulated = false; // emulated is like typing @sshole
            boolean isNumber = false;

            while ((end < in.length) && (!isEmulated || !isNumber)) {
                int charSize;
                char b = in[end];
                char c = 0;

                if ((end + 1) < in.length) {
                    c = in[end + 1];
                }

                if ((fragmentOffset < fragment.length) && (((charSize = getEmulatedSize(fragment[fragmentOffset], b, c))) > 0)) {
                    if ((charSize == 1) && isNumber(b)) {
                        isEmulated = true;
                    }

                    if ((charSize == 2) && (isNumber(b) || isNumber(c))) {
                        isEmulated = true;
                    }

                    end += charSize;
                    fragmentOffset++;
                    continue;
                }

                if (fragmentOffset == 0) {
                    break;
                }

                if ((charSize = getEmulatedSize(fragment[fragmentOffset - 1], b, c)) > 0) {
                    end += charSize;
                    if (fragmentOffset == 1) {
                        stride++;
                    }
                    continue;
                }

                if ((fragmentOffset >= fragment.length) || isLowerCaseAlpha(b)) {
                    break;
                }

                if (isSymbol(b) && (b != '\'')) {
                    isSymbol = true;
                }

                if (isNumber(b)) {
                    isNumber = true;
                }

                end++;

                if (((++iterations * 100) / (end - start)) > 90) {
                    break;
                }
            }

            if ((fragmentOffset < fragment.length) || (isEmulated && isNumber)) {
                continue;
            }

            boolean bad = true;

            if (!isSymbol) {
                char a = ' ';
                char b = ' ';

                if ((start - 1) >= 0) {
                    a = in[start - 1];
                }

                if (end < in.length) {
                    b = in[end];
                }

                if ((badCombinations != null) && comboMatches(getIndex(a), badCombinations, getIndex(b))) {
                    bad = false;
                }
            } else {
                boolean badCurrent = false;
                boolean badNext = false;

                // if the previous is out of range or a symbol
                if (((start - 1) < 0) || (isSymbol(in[start - 1]) && (in[start - 1] != '\''))) {
                    badCurrent = true;
                }

                // if the current is out of range or a symbol
                if ((end >= in.length) || (isSymbol(in[end]) && (in[end] != '\''))) {
                    badNext = true;
                }

                if (!badCurrent || !badNext) {
                    boolean allow = false;
                    int current = start - 2;

                    if (badCurrent) {
                        current = start;
                    }

                    for (; !allow && (current < end); current++) {
                        if ((current < 0) || (isSymbol(in[current]) && (in[current] != '\''))) {
                            continue;
                        }

                        char[] frag = new char[3];
                        int offset;
                        for (offset = 0; offset < 3; offset++) {
                            if (((current + offset) >= in.length) || (isSymbol(in[current + offset]) && (in[current + offset] != '\''))) {
                                break;
                            }
                            frag[offset] = in[current + offset];
                        }

                        boolean valid = offset != 0;

                        if ((offset < 3) && ((current - 1) >= 0) && (!isSymbol(in[current - 1]) || (in[current - 1] == '\''))) {
                            valid = false;
                        }

                        if (valid && !isBadFragment(frag)) {
                            allow = true;
                        }
                    }
                    if (!allow) {
                        bad = false;
                    }
                }
            }

            if (!bad) {
                continue;
            }

            int numberCount = 0;
            int alphaCount = 0;
            int alphaIndex = -1;

            for (int i = start; i < end; i++) {
                if (isNumber(in[i])) {
                    numberCount++;
                } else if (isAlpha(in[i])) {
                    alphaCount++;
                    alphaIndex = i;
                }
            }

            if (alphaIndex > -1) {
                numberCount -= end - 1 - alphaIndex;
            }

            if (numberCount <= alphaCount) {
                for (int i = start; i < end; i++) {
                    in[i] = '*';
                }
            } else {
                stride = 1;
            }
        }
    }

    public static boolean comboMatches(byte byte0, byte[][] abyte0, byte byte2) {
        int i = 0;
        if ((abyte0[i][0] == byte0) && (abyte0[i][1] == byte2)) {
            return true;
        }
        int j = abyte0.length - 1;
        if ((abyte0[j][0] == byte0) && (abyte0[j][1] == byte2)) {
            return true;
        }
        do {
            int k = (i + j) / 2;
            if ((abyte0[k][0] == byte0) && (abyte0[k][1] == byte2)) {
                return true;
            }
            if ((byte0 < abyte0[k][0]) || ((byte0 == abyte0[k][0]) && (byte2 < abyte0[k][1]))) {
                j = k;
            } else {
                i = k;
            }
        } while ((i != j) && ((i + 1) != j));
        return false;
    }

    public static int getEmulatedDomainCharSize(char a, char b, char c) {
        if (a == b) {
            return 1;
        }
        if ((a == 'o') && (b == '0')) {
            return 1;
        }
        if ((a == 'o') && (b == '(') && (c == ')')) {
            return 2;
        }
        if ((a == 'c') && ((b == '(') || (b == '<') || (b == '['))) {
            return 1;
        }
        if ((a == 'e') && (b == '€')) {
            return 1;
        }
        if ((a == 's') && (b == '$')) {
            return 1;
        }
        return ((a != 'l') || (b != 'i')) ? 0 : 1;
    }

    public static int getEmulatedSize(char original, char b, char c) {
        if (original == b) {
            return 1;
        }

        if ((original >= 'a') && (original <= 'm')) {
            if (original == 'a') {
                if ((b == '4') || (b == '@') || (b == '^')) {
                    return 1;
                }
                return ((b != '/') || (c != '\\')) ? 0 : 2;
            }
            if (original == 'b') {
                if ((b == '6') || (b == '8')) {
                    return 1;
                }
                return (((b != '1') || (c != '3')) && ((b != 'i') || (c != '3'))) ? 0 : 2;
            }
            if (original == 'c') {
                return ((b != '(') && (b != '<') && (b != '{') && (b != '[')) ? 0 : 1;
            }
            if (original == 'd') {
                return (((b != '[') || (c != ')')) && ((b != 'i') || (c != ')'))) ? 0 : 2;
            }
            if (original == 'e') {
                return ((b != '3') && (b != '€')) ? 0 : 1;
            }
            if (original == 'f') {
                if ((b == 'p') && (c == 'h')) {
                    return 2;
                }
                return (b != '£') ? 0 : 1;
            }
            if (original == 'g') {
                return ((b != '9') && (b != '6') && (b != 'q')) ? 0 : 1;
            }
            if (original == 'h') {
                return (b != '#') ? 0 : 1;
            }
            if (original == 'i') {
                return ((b != 'y') && (b != 'l') && (b != 'j') && (b != '1') && (b != '!') && (b != ':') && (b != ';') && (b != '|')) ? 0 : 1;
            }
            if (original == 'j') {
                return 0;
            }
            if (original == 'k') {
                return 0;
            }
            if (original == 'l') {
                return ((b != '1') && (b != '|') && (b != 'i')) ? 0 : 1;
            }
            if (original == 'm') {
                return 0;
            }
        }
        if ((original >= 'n') && (original <= 'z')) {
            if (original == 'n') {
                return 0;
            }
            if (original == 'o') {
                if ((b == '0') || (b == '*')) {
                    return 1;
                }
                return (((b != '(') || (c != ')')) && ((b != '[') || (c != ']')) && ((b != '{') || (c != '}')) && ((b != '<') || (c != '>'))) ? 0 : 2;
            }
            if (original == 'p') {
                return 0;
            }
            if (original == 'q') {
                return 0;
            }
            if (original == 'r') {
                return 0;
            }
            if (original == 's') {
                return ((b != '5') && (b != 'z') && (b != '$') && (b != '2')) ? 0 : 1;
            }
            if (original == 't') {
                return ((b != '7') && (b != '+')) ? 0 : 1;
            }

            int v = (((b != '\\') || (c != '/')) && ((b != '\\') || (c != '|')) && ((b != '|') || (c != '/'))) ? 0 : 2;

            if (original == 'u') {
                if (b == 'v') {
                    return 1;
                }
                return v;
            }

            if (original == 'v') {
                return v;
            }
            if (original == 'w') {
                return ((b != 'v') || (c != 'v')) ? 0 : 2;
            }
            if (original == 'x') {
                return (((b != ')') || (c != '(')) && ((b != '}') || (c != '{')) && ((b != ']') || (c != '[')) && ((b != '>') || (c != '<'))) ? 0 : 2;
            }
            if (original == 'y') {
                return 0;
            }
            if (original == 'z') {
                return 0;
            }
        }
        if ((original >= '0') && (original <= '9')) {
            if (original == '0') {
                if ((b == 'o') || (b == 'O')) {
                    return 1;
                }
                return (((b != '(') || (c != ')')) && ((b != '{') || (c != '}')) && ((b != '[') || (c != ']'))) ? 0 : 2;
            }
            if (original == '1') {
                return (b != 'l') ? 0 : 1;
            } else {
                return 0;
            }
        }
        if (original == ',') {
            return (b != '.') ? 0 : 1;
        }
        if (original == '.') {
            return (b != ',') ? 0 : 1;
        }
        if (original == '!') {
            return (b != 'i') ? 0 : 1;
        } else {
            return 0;
        }
    }

    public static byte getIndex(char c) {
        if ((c >= 'a') && (c <= 'z')) {
            return (byte) ((c - 97) + 1);
        }
        if (c == '\'') {
            return 28;
        }
        if ((c >= '0') && (c <= '9')) {
            return (byte) ((c - 48) + 29);
        } else {
            return 27;
        }
    }

    public static void filterFragments(char[] in) {
        int index;
        int end = 0;
        int count = 0;
        int start = 0;

        while ((index = indexOfNumber(in, end)) != -1) {
            boolean foundLowercase = false;

            for (int i = end; (i >= 0) && (i < index) && !foundLowercase; i++) {
                if (!isSymbol(in[i]) && isLowerCaseAlpha(in[i])) {
                    foundLowercase = true;
                }
            }

            if (foundLowercase) {
                count = 0;
            }

            if (count == 0) {
                start = index;
            }

            end = indexOfNonNumber(in, index);

            // parse number from string
            int value = 0;
            for (int i = index; i < end; i++) {
                value = ((value * 10) + in[i]) - '0';
            }

            // if our value is over 0xFF or the number uses over 8 characters
            // then reset the counter
            if ((value > 255) || ((end - index) > 8)) {
                count = 0;
            } else {
                count++;
            }

            // If we found 4 separate numbers with their parsed values under
            // 255 then replace everything from start to end of these number
            // with asterisks.
            if (count == 4) {
                for (int i2 = start; i2 < end; i2++) {
                    in[i2] = '*';
                }
                count = 0;
            }
        }
    }

    public static int indexOfNumber(char[] in, int off) {
        for (int i = off; (i < in.length) && (i >= 0); i++) {
            if ((in[i] >= '0') && (in[i] <= '9')) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfNonNumber(char[] in, int off) {
        for (int i = off; (i < in.length) && (i >= 0); i++) {
            if ((in[i] < '0') || (in[i] > '9')) {
                return i;
            }
        }
        return in.length;
    }

    public static boolean isSymbol(char c) {
        return !isAlpha(c) && !isNumber(c);
    }

    public static boolean isLowerCaseAlpha(char c) {
        if ((c < 'a') || (c > 'z')) {
            return false;
        }
        return (c != 'v') && (c != 'x') && (c != 'j') && (c != 'q') && (c != 'z');
    }

    public static boolean isAlpha(char c) {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    public static boolean isNumber(char c) {
        return (c >= '0') && (c <= '9');
    }

    public static boolean isLowerCase(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    public static boolean isUpperCase(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static boolean isBadFragment(char[] in) {
        boolean bad = true;
        for (char c : in) {
            if (!isNumber(c) && (c != 0)) {
                bad = false;
                break;
            }
        }

        if (bad) {
            return true;
        }

        int id = firstFragmentID(in);
        int start = 0;
        int end = fragments.length - 1;

        if ((id == fragments[start]) || (id == fragments[end])) {
            return true;
        }

        do {
            int mid = (start + end) / 2;

            if (id == fragments[mid]) {
                return true;
            }

            if (id < fragments[mid]) {
                end = mid;
            } else {
                start = mid;
            }
        } while ((start != end) && ((start + 1) != end));
        return false;
    }

    public static int firstFragmentID(char[] in) {
        if (in.length > 6) {
            return 0;
        }

        int index = 0;
        for (int l = 0; l < in.length; l++) {
            char c = in[in.length - l - 1];
            if ((c >= 'a') && (c <= 'z')) {
                index = (((index * 38) + c) - 97) + 1;
            } else if (c == '\'') {
                index = (index * 38) + 27;
            } else if ((c >= '0') && (c <= '9')) {
                index = (((index * 38) + c) - 48) + 28;
            } else if (c != 0) {
                return 0;
            }
        }
        return index;
    }

}
