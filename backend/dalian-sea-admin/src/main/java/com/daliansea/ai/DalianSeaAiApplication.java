package com.daliansea.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author daliansea
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DalianSeaAiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DalianSeaAiApplication.class, args);
        System.out.println("Dalian Sea AI SaaS backend started.");
    }
}
