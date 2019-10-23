package cn.xiongdi.ocdm.common.constants;

/**
 * 全局常量
 *
 * @author ICSS
 */
public class Constant {

    /**
     * 日期格式字符串
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日期格式字符串
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期时间格式字符串
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式字符串
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 开始时间
     */
    public static final String START_TIME = " 00:00:00";

    /**
     * 结束时间
     */
    public static final String END_TIME = " 23:59:59";

    /**
     * 密钥("VEHICLELICENSE"SHA256加密)
     */
    public static final String ENCRYPTION_KEY = "dbdb9a34fbe204f5464468d004477b83e699081af2da9c14533610cebc8d118a";


    /**
     * 换行符
     */
    public static final Object NEWLINECHAR = "<br>";

    /**
     * 年第一天字符常量
     */
    public static final String MONTHDAYCHAR = "-01-01";
    /**
     * 年最后一天字符常量
     */
    public static final String LASTMONTHDAYCHAR = "-12-31";

    /**
     * 日字符常量
     */
    public static final String DAYCHAR = "-01";

    /**
     * 日期分割字符常量
     */
    public static final String SEPCHAR = ".";

    /**
     * 日期分割字符常量
     */
    public static final String DATESEPCHAR = "-";

    /**
     * 下划线常量
     */
    public static final String UNDERLINE = "_";

    /**
     * 百分号常量
     */
    public static final String PERCENT = "%";

    /**
     * SET字符常量
     */
    public static final String SETCHAR = "set";

    /**
     * 逗号分割字符常量
     */
    public static final String COMMACHAR = ",";

    /**
     * 男
     */
    public static final String MAN = "男";

    /**
     * 女
     */
    public static final String WOMAN = "女";


    /**
     * 当前登录用户的保存session的KEY
     */
    public static final String LOGIN_USER_KEY = "login_user";

    /**
     * 没有登录访问返回CODE
     */
    public static final String NO_LOGIN_CODE = "999";

    /**
     * 暂时保存数据用session的KEY
     */
    public static final String TEMP_DATA_KEY = "temp_data";

    /**
     * 成功标记
     */
    public static final String FLAG_SUCCESS = "success";

    /**
     * 失败标记
     */
    public static final String FLAG_FAILURE = "failure";

    /**
     * 成功标志位（0）
     */
    public static final int CODE_SUCCESS = 1;

    /**
     * 失败标志位（-1）
     */
    public static final int CODE_FAILURE = -1;

    /**
     * 字符串常量"1"
     */
    public static final String ONE = "1";

    /**
     * 字符串常量"0"
     */
    public static final String ZERO = "0";

    /**
     * 字符常量"2"
     */
    public static final String TWO = "2";


    /**
     * 字符常量"add"
     */
    public static final String ADD = "add";

    /**
     * 字符常量"update"
     */
    public static final String UPDATE = "update";


    /**
     * 成功
     */
    public static final String APP_STATUS_OK = "200";

    /**
     * 未登录
     */
    public static final String APP_STATUS_NOT_LOGIN = "400";

    /**
     * 没有权限
     */
    public static final String APP_STATUS_NOT_POWER = "401";

    /**
     * get提交方式
     */
    public static final String GET = "GET";

    /**
     * post提交方式
     */
    public static final String POST = "POST";

    /**
     * message的Key常量
     */
    public static final String MESSAGE_KEY = "message";

    /**
     * code的Key常量
     */
    public static final String CODE_KEY = "code";
    /**
     * total的Key常量
     */
    public static final String TOTAL_KEY = "total";

    /**
     * data的Key常量
     */
    public static final String DATA_KEY = "data";

    /**
     * 字体
     */
    public static final String FONT_NAME = "宋体";

    /**
     * 字体大小
     */
    public static final short FONT_SIZE = 11;

    //------------ 常用参数参数----------------------------------------------------------//

    /**
     * 正常
     */
    public static final String COMMON_NOMAL="0";

    /**
     * 操作中
     */
    public static final String COMMON_DOING="1";

    /**
     * 结束
     */
    public static final String COMMON_END="9";


    /**
     * 未删除
     */
    public static final String UNDELETED="0";
    /**
     * 已删除
     */
    public static final String DELETED="1";

    //------------ 护照信息表参数----------------------------------------------------------//
    /**
     * 转保状态-正常
     */
    public static final String ZBZT_CPP_NORMAL = "0";
    /**
     * 转保状态-保存中
     */
    public static final String ZBZT_CPP_SAVING = "1";
    /**
     * 转保状态-转保中
     */
    public static final String ZBZT_CPP_SENDING = "2";

    /**
     * 证件状态-在库
     */
    public static final String ZJZT_CPP_INLIBRARY = "0";
    /**
     * 证件状态-借出
     */
    public static final String ZJZT_CPP_LENDING = "1";
    /**
     * 证件状态-失效
     */
    public static final String ZJZT_CPP_FAILURE = "9";

    /**
     * 证件状态-手动失效
     */
    public static final String ZJZT_CPP_LOSEEFFICACY = "99";

    /**
     * 催缴状态-正常
     */
    public static final String CJZT_CPP_NORMAL="0";

    /**
     * 催缴状态-催缴
     */
    public static final String CJZT_CPP_REMINDER="1";

    /**
     * 年审状态-待审
     */
    public static final String NSTZ_CPP_WAITING="0";

    /**
     * 年审状态-通过
     */
    public static final String NSTZ_CPP_THROUGH="1";

    /**
     * 年审状态-未通过
     */
    public static final String NSTZ_CPP_FAILED="2";

    /**
     * 年审状态-已过期
     */
    public static final String NSTZ_CPP_EXPIRED="9";

    /**
     * 申请状态-正常
     */
    public static final String SQZT_CPP_NOMAL="0";

    /**
     * 申请状态-准备中
     */
    public static final String SQZT_CPP_READY="1";

    /**
     * 申请状态-申请中
     */
    public static final String SQZT_CPP_APPLICATION="2";

    /**
     * 申请状态-申请通过
     */
    public static final String SQZT_CPP_THROUGH="3";

    //------------ 流程资料表参数----------------------------------------------------------//
    /**
     * 资料类型-非必填
     */
    public static final String ZLLX_CPM_OPTIONAL="0";

    /**
     * 资料类型-必填
     */
    public static final String ZLLX_CPM_REQUIRED="1";

    //------------ 待办工作表参数----------------------------------------------------------//
    /**
     * 待办类型-资料申请
     */
    public static final String DBLX_CPB_APPLY="0";
    /**
     * 待办类型-一级审核
     */
    public static final String DBLX_CPB_FIRSTAUDIT="1";
    /**
     * 待办类型-二级审核
     */
    public static final String DBLX_CPB_SECONDAUDIT="2";
    /**
     * 待办类型-转保
     */
    public static final String DBLX_CPB_TRANSFER="9";

    /**
     * 操作状态-处理中
     */
    public static final String CZZT_CPB_DOING="0";
    /**
     * 操作状态-完了
     */
    public static final String CZZT_CPB_END="9";

    //------------ 单位表参数----------------------------------------------------------//

    /**
     * 单位类型-上级单位
     */
    public static final String DWLX_OGZ_SUPERIOR="0";
    /**
     * 单位类型-下级单位
     */
    public static final String DWLX_OGZ_SUBORDINATE="1";
    /**
     * 单位类型-外部单位
     */
    public static final String DWLX_OGZ_EXTERNAL="2";

    /**
     * 单位级别-省
     */
    public static final String DWJB_OGZ_PROVINCE="0";
    /**
     * 单位级别-市
     */
    public static final String DWJB_OGZ_CITY="1";
    /**
     * 单位级别-县、区
     */
    public static final String DWJB_OGZ_AREA="2";
    //------------ 原因表参数----------------------------------------------------------//
    /**
     * 原因类别-事由
     */
    public static final String TYPE_CAUSE_REASON="1";
    /**
     * 原因类别-民族
     */
    public static final String TYPE_CAUSE_NATION="2";
    /**
     * 原因类别-背景
     */
    public static final String TYPE_CAUSE_BACKGROUND="3";
    //------------ 原因表参数----------------------------------------------------------//
    /**
     * 流程状态-资料准备中
     */
    public static final String LCZT_CBP_PREPARATION="0";
    /**
     * 流程状态-1级审批中
     */
    public static final String LCZT_CBP_FIRSTAUDIT="1";
    /**
     * 流程状态-2级审批中
     */
    public static final String LCZT_CBP_SECONDAUDIT="2";
    /**
     * 流程状态-审批未通过
     */
    public static final String LCZT_CBP_NOTPASS="3";
    /**
     * 流程状态-审批通过
     */
    public static final String LCZT_CBP_END="9";

    //------------审批结果参数----------------------------------------------------------//
    /**
     * 申请或审批中
     */
    public static final String SPJG_CPP_APPLY = "0";
    /**
     * 通过
     */
    public static final String SPJG_CPP_PASS = "1";
    /**
     * 转改错
     */
    public static final String SPJG_CPP_TURNWRONG = "2";
    /**
     * 未通过
     */
    public static final String SPJG_CPP_NOTPASS = "9";
    //------------ 流程活动表参数----------------------------------------------------------//
    /**
     * 提交申请
     */
    public static final String SPHJ_CPS_SUBMIT = "0";
    /**
     * 一级审核
     */
    public static final String SPHJ_CPS_FIRSTAUDIT = "1";
    /**
     * 二级审核
     */
    public static final String SPHJ_CPS_SECONDAUDIT = "2";

    //------------ 发证归还表参数----------------------------------------------------------//
    /**
     * 发证
     */
    public static final String CZLX_CGT_GRANT = "0";
    /**
     * 归还
     */
    public static final String CZLX_CGT_RETURN = "1";
    /**
     * 初次导入发证
     */
    public static final String FZLX_CGT_FIRSTIMPORT = "0";
    /**
     * 审批通过后发证
     */
    public static final String FZLX_CGT_AUDITPASS = "1";
}
