package cn.xiongdi.ocdm.common.utils;

import cn.xiongdi.ocdm.common.constants.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @创建人 汪礼
 * @创建时间 2018-09-12
 * @描述 数据共同检查
 */
public class CommonCheck {
    /**
     * 移动电话校验
     * @param mobile
     * @return boolean
     */
    public static boolean checkMobile(String mobile){
		boolean isOK = true;
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0-9]))|(18[0-9]))\\d{8}$|^0\\d{2,3}-?\\d{7,8}$";
		if (StringUtils.isNotBlank(mobile)) {
			Pattern p = Pattern.compile(regex);
			Matcher c = p.matcher(mobile);
			boolean isMatch = c.matches();
			if (isMatch) {
				isOK = true;
			} else {
				isOK = false;
			}
		}
        return isOK;
    }

	/**
	 * 会员卡号校验
	 * @param cardno
	 * @return boolean
	 */
	public static boolean checkCardno(String cardno){
		boolean isOK = true;
		if (null != cardno) {
			if (cardno.length() != 8) {
				isOK = false;
			} else {
				Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
				boolean cardnoIsOk = pattern.matcher(cardno).matches();
				if (cardnoIsOk) {
					isOK = true;
				} else {
					isOK = false;
				}
			}
		}
		return isOK;
	}

	/**
	 * 字符长度校验
	 * @param str
	 * @param length
	 * @return boolean
	 */
	public static boolean checkLength(String str,int length){
		boolean isOK = true;
		if (StringUtils.isNotEmpty(str)) {
			if (str.length()>length) {
				isOK = false;
			}
		}
		return isOK;
	}

	/**
	 * 判断字符串是否是日期格式
	 *
	 * @param str
	 * @param stringFormat
	 * @return boolean
	 */
	public static boolean isValidDate(String str,String stringFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(stringFormat);
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 日期是否合法
	 * @param dateString
	 * @return
	 */
	public static boolean validate(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{2}+[-]\\d{2}+");
		dateString= dateString.replaceAll("/","-");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;}

		//得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);

		if(month<1 || month>12){	return false;}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		}else{
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if(day<1 || day>monthLength){
			return false;
		}
		return true;
	}
	public static boolean validateDay(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{2}+[-]\\d{2}+");
		dateString= dateString.replaceAll("/","-");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;}

		//得到年月日
		String[] array = dateString.split("-");
		int month = Integer.valueOf(array[0]);
		int day = Integer.valueOf(array[1]);

		if(month<1 || month>12){	return false;}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		monthLengths[2] = 29;
		int monthLength = monthLengths[month];
		if(day<1 || day>monthLength){
			return false;
		}
		return true;
	}

	/**
	 * 未来日校验
	 * @param strYYYY_MM_DD
	 * @return boolean
	 */
	public static boolean checkFutureday (String strYYYY_MM_DD){
		boolean isOK = true;
		if (StringUtils.isNotBlank(strYYYY_MM_DD)) {
			if (strYYYY_MM_DD.compareTo(DateUtil.dateFormat(DateUtil.now(), Constant.DATE_FORMAT)) > 0) {
				isOK = false;
			}
		}
		return isOK;
	}

	/**
	 * 未来日校验
	 * @param mdate
	 * @param dateFormat
	 * @return
	 */
	public static boolean checkFutureday (String mdate,String dateFormat){
		boolean isOK = true;
		if (StringUtils.isNotBlank(mdate)) {
			if (mdate.compareTo(DateUtil.dateFormat(DateUtil.now(), dateFormat)) > 0) {
				isOK = false;
			}
		}
		return isOK;
	}

	/**
	 * 未来日校验
	 * @param date
	 * @return boolean
	 */
	public static boolean checkFutureday (Date date){
		boolean isOK = true;
		if (DateUtil.dateFormat(date, Constant.DATE_FORMAT).compareTo(DateUtil.dateFormat(DateUtil.now(), Constant.DATE_FORMAT)) > 0) {
			isOK = false;
		}
		return isOK;
	}

	/**
	 * 过去日校验
	 * @param strYYYY_MM_DD
	 * @return boolean
	 */
	public static boolean checkPastday (String strYYYY_MM_DD){
		boolean isOK = true;
		if (StringUtils.isNotBlank(strYYYY_MM_DD)) {
			if (strYYYY_MM_DD.compareTo(DateUtil.dateFormat(DateUtil.now(), Constant.DATE_FORMAT)) < 0) {
				isOK = false;
			}
		}
		return isOK;
	}

	/**
	 * 过去日校验
	 * @param mdate
	 * @param dateFormat
	 * @return
	 */
	public static boolean checkPastday (String mdate,String dateFormat ){
		boolean isOK = true;
		if (StringUtils.isNotBlank(mdate)) {
			if (mdate.compareTo(DateUtil.dateFormat(DateUtil.now(), dateFormat)) < 0) {
				isOK = false;
			}
		}
		return isOK;
	}

	/**
	 * 过去日校验
	 * @param date
	 * @return boolean
	 */
	public static boolean checkPastday (Date date){
		boolean isOK = true;
		if (DateUtil.dateFormat(date, Constant.DATE_FORMAT).compareTo(DateUtil.dateFormat(DateUtil.now(), Constant.DATE_FORMAT)) < 0) {
			isOK = false;
		}
		return isOK;
	}

	/**
	 * 日期大小检查
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static boolean compare_date(Date starDate, Date endDate) {

		boolean isOK = true;

		if (DateUtil.dateFormat(starDate, Constant.DATE_FORMAT).compareTo(DateUtil.dateFormat(endDate, Constant.DATE_FORMAT)) > 0) {
			isOK = false;
		}

		return isOK;
	}

	/**
	 * 日期大小检查
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static boolean compare_date(String starDate, String endDate) {

		boolean isOK = true;
		starDate = starDate.replaceAll("/","-");
		endDate = endDate.replaceAll("/","-");
		if (StringUtils.isNotBlank(starDate)) {
			return isOK;
		}

		if (StringUtils.isNotBlank(endDate)) {
			return isOK;
		}

		if (starDate.compareTo(endDate) > 0) {
			isOK = false;
		}

		return isOK;
	}

	/**
	 * 证件号码校验
	 * @param credentialtype  证件类型
	 * @param credentialNo    证件
	 * @return boolean
	 */
	public static boolean checkCredentialno (int credentialtype,String credentialNo){
		boolean isOK = true;
		if (StringUtils.isNotBlank(credentialNo)) {
			if (credentialtype == 1) {//身份证
						String reg = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
				if (credentialNo.length() != 18) {
					isOK = false;
				} else {
					if (!credentialNo.matches(reg)) {
						isOK = false;
					}
				}
			}
			if (credentialtype == 2) {//护照
				String reg = "^[a-zA-Z0-9]*$";
				if (!credentialNo.matches(reg)) {
					isOK = false;
				}
			}
		}
		return isOK;
	}

	/**
	 * EC电商会员号
	 * @param ecdecimal
	 * @return boolean
	 */
	public static boolean checkEcdecimal (String ecdecimal){
		boolean isOK = true;
		if (StringUtils.isNotBlank(ecdecimal)) {
			if (ecdecimal.length() > 32) {
				isOK = false;
			} else {
				Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
				if (!pattern.matcher(ecdecimal).matches()) {
					isOK = false;
				}
			}
		}
		return isOK;
	}

	/**
	 * 移动电话、实体卡卡号、会员ID
	 * @param strNo
	 * @return boolean
	 */
	public static boolean checkMobileCardnoMemberid (String strNo){
		boolean isOK = false;
		if (StringUtils.isNotBlank(strNo)) {
			//实体卡卡号
			if (strNo.length()==8) {
				isOK = checkCardno(strNo);
				///移动电话
			}else if (strNo.length()==11) {
				isOK = checkMobile(strNo);
				//会员ID
			}else if (strNo.length()==13) {
				Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
				isOK = pattern.matcher(strNo).matches();
			}
		}
		return isOK;
	}

	/**
	 * 金额验证
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str){
		Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match=pattern.matcher(str);
		if(match.matches()==false){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 字符转换日期
	 * @param str
	 * @return
	 */
	public static Date str2Date(String str){
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 是否是闰年 */
	private static boolean isLeapYear(int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ;
	}
}
