package cn.xiongdi.ocdm.server.interceptor;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-17
 * @描述
 */
public class OCDMSessionContext {

    private static OCDMSessionContext context;

    private Map<String, HttpSession> map;

    private OCDMSessionContext() {
        // map = new HashMap<>();
        // 保证线程安全
        map =  Collections.synchronizedMap(new HashMap<>());
    }


    public static OCDMSessionContext getSessionContext() {
        if(context == null) {
            context = new OCDMSessionContext();
        }
        return context;
    }


    /**
     * 添加userID
     * */
    public synchronized void addSession(String userID,HttpSession session) {

        if(session!= null){
            map.put(userID, session);
        }
    }

    /**
     * 获取
     * */
    public synchronized HttpSession getSession(String userID) {

        if(userID == null){
            return null;
        }
        return map.get(userID);

    }

    /**
     * 删除
     * */
    public synchronized void delSession(String userID) {

        if(userID!= null){
            map.remove(userID);
        }
    }
}
