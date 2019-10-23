package cn.xiongdi.ocdm.server.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @创建者 pengfuming
 * @创建时间 2018-11-06
 * @描述 年审管理job
 */
@Component
@EnableScheduling
@PropertySource(value = "classpath:/properties/config.properties", ignoreResourceNotFound = true)
public class DemoJob {

    private final static Logger logger = Logger.getLogger(DemoJob.class);



    @Scheduled(cron = "${DemoJob.cron}")
    public void annualAuditJob() {
        logger.info("--------DemoJob start---------");

        try {
            int resultCount;
            //resultCount = annualAuditService.annualAuditJob();
            //logger.info("更新了" + resultCount + "行年审状态");
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            logger.info("--------DemoJob end---------");
        }

    }

}
