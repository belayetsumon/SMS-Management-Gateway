/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springboot/CfgProperties.java to edit this template
 */
package com.ecommerce.app.module.sms.config;

import org.jsmpp.session.SMPPSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author libertyerp_local
 */
//@ConfigurationProperties(prefix = "prefix")
@Configuration
public class SmsConfig {
    
      @Bean
    public SMPPSession smppSession() {
        return new SMPPSession();
    }
    
    @Value("${sms.smpp.host}")
    private String smscHost;

    @Value("${sms.smpp.port}")
    private int smscPort;

    @Value("${sms.smpp.user-id}")
    private String systemId;

    @Value("${sms.smpp.password}")
    private String password;

    public SmsConfig() {
    }

    public SmsConfig(String smscHost, int smscPort, String systemId, String password) {
        this.smscHost = smscHost;
        this.smscPort = smscPort;
        this.systemId = systemId;
        this.password = password;
    }
    
    public String getSmscHost() {
        return smscHost;
    }

    public void setSmscHost(String smscHost) {
        this.smscHost = smscHost;
    }

    public int getSmscPort() {
        return smscPort;
    }

    public void setSmscPort(int smscPort) {
        this.smscPort = smscPort;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
