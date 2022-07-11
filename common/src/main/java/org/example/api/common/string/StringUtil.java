package org.example.api.common.string;



import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public final class StringUtil {


    /**
     * 默认的文本分隔符
     */
    public static final String Default_Split = "#";
    /**
     * ^ 匹配输入字符串开始的位置
     * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
     * $ 匹配输入字符串结尾的位置
     */
    public static final String CHINA_REGEX_EXP = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
    public static final String HK_REGEX_EXP = "^(5|6|8|9)\\d{7}$";
    private static final char[] charArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static long MAX_TS;

    /**
     * 异常转string
     *
     * @param e
     * @return
     */
    public static String exceptionToString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        stringWriter.flush();
        printWriter.flush();
        return stringWriter.toString();
    }

    public static String get_domain(String domain) {

        if (domain.contains("https://")) {
            return domain.replaceAll("https://", "");
        } else if (domain.contains("http://")) {
            return domain.replaceAll("http://", "");
        }
        return domain;
    }

    public static String get_password() {


        String password = "12345678";
        int[] pwdindex = {0, 1, 2, 3, 4, 5, 6, 7};

        char[] specialCharacters = {'@', '^', '@', '!', '$'};

        char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '*'};

        char[] upperLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'};

        char[] lowerLetters = {'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'};


        char[] allCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'};


        //1. 随机生成特殊字符，随机 放到密码2-7位置中(特殊字符不出现在开头或结尾)
        int aindex = new Random().nextInt(5);
        int aposition = new Random().nextInt(6) + 1;
        password = password.replace(password.charAt(aposition), specialCharacters[aindex]);

        //2. 随机生成数据，随机放到1-8位置中（除去第1步占用的位置）
        int bindex = new Random().nextInt(10);
        int bposition = 0;
        do {
            bposition = new Random().nextInt(8);
        } while (bposition == aposition);
        password = password.replace(password.charAt(bposition), numbers[bindex]);

        //3. 随机生成大写字母，随机放到1-8位置中（除去第1、2步占用的位置）
        int cindex = new Random().nextInt(26);
        int cposition = 0;
        do {
            cposition = new Random().nextInt(8);
        } while (cposition == aposition || cposition == bposition);
        password = password.replace(password.charAt(cposition), upperLetters[cindex]);

        //4. 随机生成小写字母，随机放到1-8位置中（除去第1、2、3步占用的位置）
        int dindex = new Random().nextInt(26);
        int dposition = 0;
        do {
            dposition = new Random().nextInt(8);
        } while (dposition == aposition || dposition == cposition || dposition == bposition);
        password = password.replace(password.charAt(dposition), lowerLetters[dindex]);

        //前4步保证密码包含（特殊字符&大写字母&小写字母&数字  且位置是随机的）
        //5. 随机生成数字大小写字母，随机放到1-8位置中（除去第1、2、3、4步占用的位置，余下四位）
        for (int i = 0; i < pwdindex.length; i++) {
            if (pwdindex[i] != aposition && pwdindex[i] != bposition
                    && pwdindex[i] != cposition && pwdindex[i] != dposition) {
                int eindex = new Random().nextInt(62);
                password = password.replace(password.charAt(pwdindex[i]), allCharacters[eindex]);
            }
        }
        return password;
    }


    public static String trim(String s) {
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray())
            if (' ' != ch)
                sb.append(ch);
        s = sb.toString();

        return s.replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("　", "").replaceAll("\t", "").replaceAll("\n", "");
    }

    public static String getExt(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String s) {
        return isNullOrEmpty(s);
    }

    public static boolean isNullOrEmpty(String s) {
        return null == s || 0 == s.trim().length();
    }

    public static String randomCode() {
        return "" + (new Random().nextInt(899999) + 100000);
    }

    public static String randomPassword() {
        return randomString(6);
    }

    public static String getOutTradeNo() {
        int r1 = (int) (Math.random() * (10));// 产生2个0-9的随机数
        int r2 = (int) (Math.random() * (10));
        long now = System.currentTimeMillis();// 一个13位的时间戳
        String id = String.valueOf(r1) + String.valueOf(r2)
                + String.valueOf(now);// 订单ID
        return id;
    }

    public static String randomString(int length) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(36);
            sb.append(charArray[index]);
        }

        return sb.toString();
    }

    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");

        return uuidStr;
    }

    public static String getFormatName(String fileName) {
        int index = fileName.lastIndexOf('.');

        return -1 == index ? "jpg" : fileName.substring(index + 1);
    }

    public static String[] getStringList(String str) {
        str = trim(str);
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        String sep = ",";
        if (str.indexOf(':') >= 0) {
            sep = ":";
        }
        return str.split(sep);
    }

    public static String[] getStringList(String str, String sep) {
        str = trim(str);
        return str.split(sep);
    }

    public static int[] getIntArray(String str, String sep) {
        String[] prop = getStringList(str, sep);
        List<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < prop.length; i++) {
            try {
                int r = Integer.parseInt(prop[i]);
                tmp.add(r);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        int[] ints = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            ints[i] = tmp.get(i);
        }
        return ints;
    }

    public static List<Integer> getIntList(String str, String sep) {
        List<Integer> tmp = new ArrayList<Integer>();
        if (str == null || str.trim().equals("")) {
            return tmp;
        }
        String[] prop = getStringList(str, sep);
        for (int i = 0; i < prop.length; i++) {
            try {
                int r = Integer.parseInt(prop[i]);
                tmp.add(r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }

    public static String join(String[] strs, String sep) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            buffer.append(sep).append(strs[i]);
        }
        return buffer.toString();
    }

    public static String join(List<Integer> ints, String sep) {
        StringBuffer sb = new StringBuffer();
        sb.append(ints.get(0));
        for (int i = 1; i < ints.size(); i++) {
            sb.append(sep).append(ints.get(i));
        }
        return sb.toString();
    }

    public static String join(String sep, List list) {
        StringBuffer sb = new StringBuffer();
        sb.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            sb.append(sep).append(list.get(i));
        }
        return sb.toString();
    }

    public static String getStringByList(List<String> ints, String sep) {
        StringBuffer sb = new StringBuffer();
        sb.append(ints.get(0));
        for (int i = 1; i < ints.size(); i++) {
            sb.append(sep).append(ints.get(i));
        }
        return sb.toString();
    }

    public static double[] getDoubleList(String str) {
        String[] prop = getStringList(str);
        double[] ds = new double[prop.length];
        for (int i = 0; i < ds.length; i++) {
            ds[i] = Double.parseDouble(prop[i]);
        }
        return ds;
    }

    public static List<String> getListBySplit(String str, String split) {
        List<String> list = new ArrayList<String>();
        //|| str.trim().equalsIgnoreCase("")
        if (str == null)
            return list;
        String[] strs = str.split(split);
        for (String temp : strs) {
            //&& !temp.trim().equalsIgnoreCase("")
            if (temp != null) {
                list.add(temp);
            }
        }
        return list;
    }

    public static int[] getIntList(String str) {
        String[] prop = getStringList(str);
        List<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < prop.length; i++) {
            try {
                String sInt = prop[i].trim();
                if (sInt.length() < 20) {
                    int r = Integer.parseInt(prop[i].trim());
                    tmp.add(r);
                }
            } catch (Exception e) {
            }
        }
        int[] ints = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            ints[i] = tmp.get(i);
        }
        return ints;

    }

    public static String toWrapString(Object obj, String content) {
        if (obj == null) {
            return "null";
        } else {
            return obj.getClass().getName() + "@" + obj.hashCode() + "[\r\n"
                    + content + "\r\n]";
        }
    }

    // 将1,2,3和{1,2,3}格式的字符串转化为JDK的bitset
    // 考虑了两边是否有{}，数字两边是否有空格，是否合法数字
    public static BitSet bitSetFromString(String str) {
        if (str == null) {
            return new BitSet();
        }
        if (str.startsWith("{")) {
            str = str.substring(1);
        }
        if (str.endsWith("}")) {
            str = str.substring(0, str.length() - 1);
        }
        int[] ints = getIntList(str);
        BitSet bs = new BitSet();
        for (int i : ints) {
            bs.set(i);
        }
        return bs;
    }

    public static boolean hasExcludeChar(String str) {
        if (str != null) {
            char[] chs = str.toCharArray();
            for (int i = 0; i < chs.length; i++) {

                if (Character.getType(chs[i]) == Character.PRIVATE_USE) {

                    return true;
                }

            }
        }
        return false;
    }

    public static String replaceSql(String str) {
        if (str != null) {
            return str.replaceAll("'", "’").replaceAll("<", "&lt;").replaceAll(
                    ">", "&gt;").replaceAll("\"", "&quot;");
        }
        return "";
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param s1
     * @param s2
     * @return true, 字符串相等;false,字符串不相等
     */
    public static boolean isEquals(String s1, String s2) {
        if (s1 != null) {
            return s1.equals(s2);
        }
        if (s2 != null) {
            return false;
        }
        // 两个字符串都是null
        return true;
    }

    /**
     * 判断字符串是否时数字
     *
     * @param text
     * @return
     */
    public static boolean isDigit(String text) {
        String reg = "[-]*[\\d]+[\\.\\d+]*";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

    /**
     * 判断一句话是否是汉语
     *
     * @param text
     * @return
     */
    public static boolean isChiness(String text) {
        String reg = "[\\w]*[\\u4e00-\\u9fa5]+[\\w]*";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(text);
        boolean result = mat.matches();
        return result;
    }

    /**
     * 判断单个字符是否是汉语
     *
     * @param cha
     * @return
     */
    public static boolean isChineseChar(char cha) {
        String reg = "[\\u4e00-\\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        String text = Character.toString(cha);
        Matcher mat = pat.matcher(text);
        boolean result = mat.matches();
        return result;
    }

    /**
     * 判断字符是否是字母(包括大小写)或者数字
     *
     * @param cha
     * @return
     */
    public static boolean isLetterAndDigit(String cha) {
        String reg = "[\\w]+";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(cha);
        boolean result = mat.matches();
        return result;
    }

    /**
     * 返回字符串中汉字的数量
     *
     * @param test
     * @return
     */
    public static int getChineseCount(String test) {
        int count = 0;
        boolean tempResult = false;
        for (int i = 0; i < test.length(); i++) {
            char cha = test.charAt(i);
            tempResult = isChineseChar(cha);
            if (tempResult) {
                count++;
            }
        }
        return count;
    }

    /**
     * 返回字符串中字母和数字的个数，其中字母包括大小写
     *
     * @param text
     * @return
     */
    public static int getLetterAndDigitCount(String text) {
        int count = 0;
        boolean tempResult = false;
        for (int i = 0; i < text.length(); i++) {
            tempResult = isLetterAndDigit(text);
            if (tempResult) {
                count++;
            }
        }
        return count;
    }

    /**
     * 将字符串首字母大写
     *
     * @param s
     * @return
     */
    public static String upperCaseFirstCharOnly(String s) {
        if (s == null || s.length() < 1) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * 数组转化为String
     *
     * @param s
     * @param sep
     * @return
     */
    public static String arrayToString(String[] s, char sep) {
        if (s == null || s.length == 0) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        if (s != null) {
            for (int i = 0; i < s.length; i++) {
                if (i > 0)
                    buf.append(sep);
                buf.append(s[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 获取拼起来的key
     *
     * @param splitString
     * @param strings
     * @return
     */
    public static String getString(String splitString, String... strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            stringBuffer.append(strings[i]);
            if (i == strings.length - 1) {
                break;
            }
            stringBuffer.append(splitString);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取拼起来的key
     *
     * @param splitString
     * @param strings
     * @return
     */
    public static String getString(String splitString, int start, Serializable... strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = start; i < strings.length; i++) {
            stringBuffer.append(strings[i]);
            if (i == strings.length - 1) {
                break;
            }
            stringBuffer.append(splitString);
        }
        return stringBuffer.toString();
    }


    // 运算相关处理   double 类型 转为  BigDecimal

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * @param source
     * @param n
     * @return
     * @Description:均分ArrayList，分n份
     **/
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;

    }

    /**
     * 加法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double addDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.add(p2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double subDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.subtract(p2).doubleValue();
    }

    /**
     * 乘法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double mul(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.multiply(p2).doubleValue();
    }

    /**
     * 除法运算
     *
     * @param m1
     * @param m2
     * @param scale 保留小数点多少位含有四舍五入
     * @return
     */
    public static double div(double m1, double m2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Parameter error");
        }
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 校验是否为大陆号码或香港号码
     *
     * @param str
     * @return 符合规则返回true
     * @throws PatternSyntaxException
     */
    public static boolean isPhoneNum(String str) throws PatternSyntaxException {
        return isChinaPhoneNum(str) || isHkPhoneNum(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+任意数
     * 18+任意数
     * 198,199
     *
     * @param str
     * @return 正确返回true
     * @throws PatternSyntaxException
     */
    public static boolean isChinaPhoneNum(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        Pattern p = Pattern.compile(CHINA_REGEX_EXP);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     *
     * @param str
     * @return 正确返回true
     * @throws PatternSyntaxException
     */
    public static boolean isHkPhoneNum(String str) throws PatternSyntaxException {
        Pattern p = Pattern.compile(HK_REGEX_EXP);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String get_processes_pid(String name) {
        try {
            final Process exec = Runtime.getRuntime().exec("ps aux > /tmp/processes");
            exec.waitFor();
            final Process grep_mysql = Runtime.getRuntime().exec("grep " + name + " /tmp/processes");
            grep_mysql.waitFor();
            final String text = new BufferedReader(
                    new InputStreamReader(grep_mysql.getInputStream(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.toList()).get(0);
            return text.split("\\s+")[1];

        } catch (IOException | InterruptedException ignored) {
            return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        }
    }


}
