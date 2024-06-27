/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.ecommerce.app.module.sms.services;

import com.ecommerce.app.module.sms.config.SmsConfig;
import java.io.IOException;
import java.util.Date;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author libertyerp_local
 */
@Service
public class SmsService {

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private SMPPSession smppSession;

    private TimeFormatter timeFormatter = new AbsoluteTimeFormatter();

    public SmsService(SMPPSession smppSession) {
        this.smppSession = smppSession;
    }

//    @Autowired
//    public void setSmppSession(SMPPSession smppSession) {
//        this.smppSession = smppSession;
//    }
    public void sendSms(String phoneNumber, String message) throws PDUException, ResponseTimeoutException, InvalidResponseException, NegativeResponseException {
        try {
            smppSession.connectAndBind(
                    smsConfig.getSmscHost(),
                    smsConfig.getSmscPort(),
                    new BindParameter(
                            BindType.BIND_TX,
                            smsConfig.getSystemId(),
                            smsConfig.getPassword(),
                            "cp",
                            TypeOfNumber.UNKNOWN,
                            NumberingPlanIndicator.UNKNOWN,
                            null
                    )
            );

            smppSession.submitShortMessage(
                    "CMT", // Service Type
                    TypeOfNumber.INTERNATIONAL, // Source TON
                    NumberingPlanIndicator.ISDN, // Source NPI
                    "1234", // Source Address
                    TypeOfNumber.INTERNATIONAL, // Destination TON
                    NumberingPlanIndicator.ISDN, // Destination NPI
                    phoneNumber, // Destination Address
                    new ESMClass(), // ESM Class
                    (byte) 0, // Protocol ID
                    (byte) 1, // Priority Flag
                    timeFormatter.format(new Date()), // Schedule Delivery Time
                    null, // Validity Period
                    new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), // Registered Delivery
                    (byte) 0,
                    new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false),
                    (byte) 0,
                    message.getBytes());
            System.out.println("Message submitted");
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        } finally {
            if (smppSession != null && smppSession.getSessionState().isBound()) {
                try {
                    smppSession.unbindAndClose();
                } catch (Exception e) {
                    // Handle unbindAndClose exception if needed
                    e.printStackTrace();
                }
            }
        }
    }
}
