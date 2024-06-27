/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.repository;

import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author libertyerp_local
 */
public interface WhatsappContactGroupRepository extends JpaRepository<WhatsappContactGroup, Long> {
    
    List<WhatsappContactGroup> findByCustomerOrderByIdDesc(Users customer);
    
    
}
