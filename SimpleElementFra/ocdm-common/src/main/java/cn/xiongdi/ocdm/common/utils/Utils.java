package cn.xiongdi.ocdm.common.utils;


import cn.xiongdi.ocdm.common.constants.Constant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author NEC
 * @version 1.0.0
 * @since 2016-04-14
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 缓冲大小
     */
    private static final int BUFFER_SIZE = 8192;

    /**
     * 取得密钥
     *
     * @return 密钥
     */
    public static String getEncryptionKey() {
        return Constant.ENCRYPTION_KEY;
    }

    /**
     * 从Request对象获取当前登录用户对象
     *
     * @param request HttpServletRequest
     * @return StaffM 登录用户对象
     */
    public static Object getLoginUserInfo(HttpServletRequest request) {
        return WebUtils.getSessionAttribute(request,
                Constant.LOGIN_USER_KEY);
    }

    /**
     * 获取客户端访问真实IP
     *
     * @param req 请求
     * @return 真实路径
     */
    public static String getRemortIP(HttpServletRequest req) {

        String currUserIp = req.getHeader("x-forwarded-for");// req.getRemoteAddr();
        if (StringUtils.isEmpty(currUserIp)) {
            currUserIp = req.getRemoteAddr();
        } else {
            if (currUserIp.indexOf(",") > -1) {
                currUserIp = currUserIp.substring(0, currUserIp.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(currUserIp)){
            currUserIp="127.0.0.1";
        }
        return currUserIp;

    }

    /**
     * 生成UUID
     *
     * @return 生成的UUID
     * @author NEC
     */
    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 处理页面显示的br标签
     *
     * @param oldValue String
     * @return String
     * @author NEC
     */
    public static String replaceBr(String oldValue) {
        String strValue = oldValue;
        if (oldValue != null) {
            if (strValue.contains("\\r<br/>")) {
                strValue = strValue.replaceAll("<br/>", "");
            }
            if (strValue.contains("<br/>")) {
                strValue = strValue.replaceAll("<br/>", "\\\\r");
            }
            if (strValue.contains("<br>")) {
                strValue = strValue.replaceAll("<br>", "\\\\r");
            }
        }
        return strValue;
    }

    /**
     * 将字符串中的\r\n或\n替换成<br/>
     * 修正程序，含有换行的Json数据导致界面无法显示
     *
     * @param strValue String
     * @return strValue替换后的值
     * @author NEC
     */
    public static String replaceStr(String strValue) {
        if (strValue != null) {
            return strValue.replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\r\n", "<br/>").replace("\n", "<br/>")
                    .replace("\\n", "<br/>");
        } else {
            return null;
        }
    }

    /**
     * 判断字符串是否数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern
                .compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否是日期格式（格式：yyyy-MM-dd）
     *
     * @param str String
     * @return boolean
     */
    public static boolean isValidDate(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            dateFormat.parse(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取当前工作路径
     *
     * @return String
     */
    public static String getWorkspacePath() {
        return Thread.currentThread().getContextClassLoader().getResource("")
                .getPath().substring(1)
                .replace("WebRoot/WEB-INF/classes", "src");
    }

    /**
     * 格式化日期时间对象
     *
     * @param d Date
     * @return Date
     */
    public static Date parseTime(Date d) {
        try {
            if (d != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        Constant.DATETIME_FORMAT);
                String str = dateFormat.format(d);
                return dateFormat.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return d;
    }

    /**
     * 获取应用的基础路径
     *
     * @param req HttpServletRequest
     * @return String
     */
    public static String getBasePath(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":"
                + req.getServerPort() + req.getContextPath();
    }


    /**
     * 字符串替换
     *
     * @param reg   正则表达式
     * @param str   待替换字符
     * @param beStr 替换成该字符串
     * @return String
     */
    public static String replaceAll(String reg, String str, String beStr) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll(beStr).trim();
    }

    /**
     * 超出长度以其他字符代替
     *
     * @param str1       待处理字符串
     * @param length     长度
     * @param replaceStr 替换字符串
     * @return String
     */
    public static String replaceStr(String str1, int length, String replaceStr) {
        String str = str1;
        if (str.length() > length) {
            str = str.substring(0, length) + replaceStr;
        }
        return str;
    }

    /**
     * jsonobj转map
     *
     * @param obj 请求对象
     * @return map 请求Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> jsonStrToMap(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        JSONObject jobj = JSONObject.fromObject(obj);
        // 获取json集合中的所有键的Set集合
        Set<String> keySet = jobj.keySet();
        // 有了Set集合就可以获取其迭代器，取值
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            map.put(key, jobj.get(key).toString());
        }
        return map;
    }

    /**
     * json集合字符串转map集合
     *
     * @param jArrStr json集合字符
     * @return map map集合
     */
    public static List<Map<String, String>> jsonArrTolist(String jArrStr) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        JSONArray jArray = JSONArray.fromObject(jArrStr);
        for (int i = 0; i < jArray.size(); i++) {
            list.add(jsonStrToMap(jArray.get(i)));
        }
        return list;
    }

    /**
     * @param source Object
     * @param dest   Object
     * @return Object
     * @throws Exception Exception
     *                   反射机制:此方法将调用source的getter方法，将得到的值作为相应的参数传给obj2的setter方法
     *                   注意，source的getter方法和obj2方法必须是public类型
     */

    public static Object copyBeanToBean(Object source, Object dest) throws Exception {
        Method[] method1 = source.getClass().getMethods();
        Method[] method2 = dest.getClass().getMethods();
        String methodName1;
        String methodFix1;
        String methodName2;
        String methodFix2;

        for (int i = 0; i < method1.length; i++) {
            methodName1 = method1[i].getName();
            methodFix1 = methodName1.substring(3, methodName1.length());
            if (methodName1.startsWith("get")) {
                for (int j = 0; j < method2.length; j++) {
                    methodName2 = method2[j].getName();
                    methodFix2 = methodName2.substring(3, methodName2.length());
                    if (methodName2.startsWith("set")) {
                        if (methodFix2.equals(methodFix1)) {
                            Object[] objs1 = new Object[0];
                            Object[] objs2 = new Object[1];
                            // 激活obj1的相应的get的方法，objs1数组存放调用该方法的参数,此例中没有参数，该数组的长度为0
                            objs2[0] = method1[i].invoke(source, objs1);
                            // 激活obj2的相应的set的方法，objs2数组存放调用该方法的参数
                            method2[j].invoke(dest, objs2);
                            continue;
                        }
                    }
                }
            }
        }
        return dest;
    }

    /**
     * 获取随机字母数字组合
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //数字
            str += String.valueOf(random.nextInt(10));
        }
        return str;
    }

    /**
     * 文件上传
     *
     * @param file         多媒体文件
     * @param fileRootPath 文件基础路径
     * @return 返回文件相对路径 \2015\02\15\*.jpg
     * @throws IOException 异常抛出
     */
    public static String uploadMyFile(MultipartFile file, String fileRootPath) throws IOException {
        Calendar c = DateUtil.nowCalendar();// 日期
        String filename = file.getOriginalFilename();
        String suffix = StringUtils.substring(filename,
                filename.lastIndexOf(".") + 1);
        String realName = String.valueOf(System.currentTimeMillis()
                + ((int) (Math.random() * 900) + 100));// 新文件名
        String destPath = "";// 相对路劲
        //String fileRootPath = getConfigMValue(Constant.FILE_ROOT_PATH);// 根目录
        logger.debug("FILE_ROOT_PATH:" + fileRootPath);
        InputStream is = null;
        OutputStream os = null;

            File dir = null;
            dir = new File(fileRootPath);
            dir.mkdir();// 创建根目录
            destPath = "/" + c.get(Calendar.YEAR);
            dir = new File(fileRootPath + destPath);
            dir.mkdir();// 创建年份文件夹
            destPath += "/" + (c.get(Calendar.MONTH) + 1);
            dir = new File(fileRootPath + destPath);
            dir.mkdir();// 创建月份文件夹
            destPath += "/" + c.get(Calendar.DAY_OF_MONTH);
            dir = new File(fileRootPath + destPath);
            dir.mkdir();// 创建日文件夹

            logger.debug("FILE_PATH:" + fileRootPath + destPath
                    + "/" + realName + "." + suffix);
            File df = new File(fileRootPath + destPath + "/"
                    + realName + "." + suffix);
            if (!df.exists()) {// 如果不存在先创建
                df.createNewFile();
            }

            is = file.getInputStream();
            os = new FileOutputStream(df);// 写入服务器
            int bt = 0;
            byte[] bf = new byte[8192];
            while ((bt = is.read(bf, 0, 8192)) != -1) {
                os.write(bf, 0, bt);
            }

            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }

        return destPath + "/" + realName + "." + suffix;
    }

    /**
     * 获取字母及数字组合的随机字符串
     *
     * @param length 表示生成字符串的长度
     * @return String 随机字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /***
     * 利用Apache的工具类实现SHA-256加密
     * @param value 需要加密的内容
     * @return 加密后的内容
     */
    public static String getSHA256Str(String value) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(value.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

}
