/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.ecommerce.app.module.sms.ripository;

import com.ecommerce.app.module.sms.model.SmsSend;
import com.ecommerce.app.module.user.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author libertyerp_local
 */
public interface SmssendRepository extends JpaRepository<SmsSend, Long> {
     List<SmsSend>   findByCustomerOrderByIdDesc(Users customer);
}
