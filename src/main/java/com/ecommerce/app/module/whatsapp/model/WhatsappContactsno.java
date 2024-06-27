/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.model;

import com.ecommerce.app.module.user.model.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author libertyerp_local
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WhatsappContactsno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be blank.")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Users customer;

    private String firstName;

    private String lastName;

    
    @NotEmpty(message = "Contact no cannot be blank.")
    private String contactsnumber;

    
    @NotNull(message = "Contact group cannot be blank.")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private WhatsappContactGroup contactGroup;

    
    @Enumerated(EnumType.STRING)
    private WhatsappContactStatus contactStatus;
    

    /// Audit /// 
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedBy
    @Column(insertable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modified;

    public WhatsappContactsno() {
    }

    public WhatsappContactsno(Long id, Users customer, String firstName, String lastName, String contactsnumber, WhatsappContactGroup contactGroup, WhatsappContactStatus contactStatus, String createdBy, LocalDateTime created, String modifiedBy, LocalDateTime modified) {
        this.id = id;
        this.customer = customer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactsnumber = contactsnumber;
        this.contactGroup = contactGroup;
        this.contactStatus = contactStatus;
        this.createdBy = createdBy;
        this.created = created;
        this.modifiedBy = modifiedBy;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactsnumber() {
        return contactsnumber;
    }

    public void setContactsnumber(String contactsnumber) {
        this.contactsnumber = contactsnumber;
    }

    public WhatsappContactGroup getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(WhatsappContactGroup contactGroup) {
        this.contactGroup = contactGroup;
    }

    public WhatsappContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(WhatsappContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

}
