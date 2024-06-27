/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.app.ripository;

import com.ecommerce.app.model.Profile;
import com.ecommerce.app.module.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Users users);
}
