package cn.xiongdi.ocdm.server;

import cn.xiongdi.ocdm.server.filter.AuthFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import java.net.UnknownHostException;


@ImportResource("classpath:transaction.xml")
@Configuration
@SpringBootApplication
@ServletComponentScan
@ComponentScan("cn.xiongdi.ocdm")
@MapperScan("cn.xiongdi.ocdm.mapper.dao")
public class BmsServerApplication extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(BmsServerApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(BmsServerApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "服务端 '{}' 启动完成! \n\t" +
                        "环境(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getActiveProfiles());
    }

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean authFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/webapi/*", "*.html");
        registration.setName("authFilter");
        return registration;

    }

    /**
     * 创建一个bean
     *
     * @return
     */
    @Bean(name = "authFilter")
    public Filter authFilter() {
        return new AuthFilter();
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${maxfilesize}")
    private String fileSize;//允许文件上传最大限制
    public String getFileSize() {
        return fileSize;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * 资源映射路径
         * addResourceHandler：访问映射路径
         * addResourceLocations：资源绝对路径
         */
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }

    /**
     * 文件上传配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(getFileSize());
        return factory.createMultipartConfig();
    }
}
