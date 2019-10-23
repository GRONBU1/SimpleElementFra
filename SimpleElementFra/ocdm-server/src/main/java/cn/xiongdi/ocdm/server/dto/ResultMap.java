package cn.xiongdi.ocdm.server.dto;

import java.util.HashMap;


/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 结果对象
 */
public class ResultMap extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;
	
	private ResultMap() { }

	/**
	 * 返回成功
	 */
	public static ResultMap ok() {
		return ok("操作成功！");
	}

	/**
	 * 返回成功
	 */
	public static ResultMap ok(String message) {
		return ok("1", message);
	}
	
	/**
	 * 返回成功
	 */
	public static ResultMap ok(String code,String message) {
		ResultMap resultMap = new ResultMap();
		resultMap.put("code", code);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * 返回失败
	 */
	public static ResultMap error() {
		return error("操作失败！");
	}
	
	/**
	 * 返回失败
	 */
	public static ResultMap error(String messag) {
		return error("0", messag);
	}

	/**
	 * 返回失败
	 */
	public static ResultMap error(String code, String message) {
		return ok(code, message);
	}
	
	/**
	 * 设置code
	 */
	public ResultMap setCode(String code){
		super.put("code", code);
		return this;
	}
	
	/**
	 * 设置message
	 */
	public ResultMap setMessage(String message){
		super.put("message", message);
		return this;
	}

	/**
	 * 设置data
	 */
	public ResultMap setData(Object data){
		super.put("data", data);
		return this;
	}
	
	/**
	 * 放入object
	 */
	@Override
	public ResultMap put(String key, Object object){
		super.put(key, object);
		return this;
	}
}