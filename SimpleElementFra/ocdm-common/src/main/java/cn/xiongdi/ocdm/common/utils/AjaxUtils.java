package cn.xiongdi.ocdm.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * 描述：ajax传送对象公共类，负责将数据传输到前台
 * 
 * @author 汪礼
 */
public class AjaxUtils {

	static Logger log = Logger.getLogger(AjaxUtils.class);

	/**
	 * 将map对象传到前台
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param map
	 *            Map<?, ?>
	 */
	public static void sendAjaxForMap(HttpServletResponse response,
			Map<?, ?> map) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			log.error("ajax传送map对象出错", e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 将Object对象传到前台
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param obj
	 *            Object
	 */
	public static void sendAjaxForObject(HttpServletResponse response,
			Object obj) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);
		} catch (IOException e) {
			log.error("ajax传送Object对象出错", e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 将list对象传到前台
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param list
	 *            List<String>
	 */
	public static void sendAjaxForSelect(HttpServletResponse response,
			List<String> list) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			String str = "";
			if (list != null && list.size() > 0) {
				for (String str1 : list) {
					sb.append("{").append(str1).append("}").append(",");
				}
				str = sb.toString()
						.substring(0, sb.toString().lastIndexOf(","));
			} else {
				str = "[{\"name\":\"\",\"value\":\"\"}";
			}
			// log.info(str);
			out.print(str + "]");

		} catch (IOException e) {
			log.error("ajax传送map对象出错", e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 为complete控件传送对象
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param list
	 *            List<String>
	 */
	@Deprecated
	public static void sendAjaxForComplete(HttpServletResponse response,
			List<String> list) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (String str : list) {
				sb.append("\"").append(str).append("\"").append(",");
			}
			String str = "";
			if (sb.indexOf(",") > -1) {
				str = sb.substring(0, sb.lastIndexOf(","));
			} else {
				str = sb.toString();
			}
			response.getWriter().print(str + "]");

		} catch (IOException e) {
			log.error("ajax传送map对象出错", e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
