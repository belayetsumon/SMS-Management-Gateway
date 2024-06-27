/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.ecommerce.app.module.sms.services;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.springframework.stereotype.Service;

/**
 *
 * @author libertyerp_local
 */
@Service
public class SmppConnectionCheck {

    public void smppconnectioncheck(){
        String smppServer = "localhost"; // Localhost address
        int smppPort = 9854; // Example port, replace with your actual port number
        String systemId = "sumon";
        String password = "bangladesh";

        try {
            SMPPSession session = new SMPPSession();
            session.connectAndBind(smppServer, smppPort,
                    new BindParameter(BindType.BIND_TX,
                            systemId,
                            password,
                            "cp",
                            TypeOfNumber.UNKNOWN,
                            NumberingPlanIndicator.UNKNOWN,
                            null));

            System.out.println("SMPP session connected successfully.");

            // Check connection status
            boolean isConnected = session.getSessionState().isBound();
            System.out.println("SMPP session connected: " + isConnected);

            // Perform other SMPP operations (sending/receiving messages)

            session.unbindAndClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


