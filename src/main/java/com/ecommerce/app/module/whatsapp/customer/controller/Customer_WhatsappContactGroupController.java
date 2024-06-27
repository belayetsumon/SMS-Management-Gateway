/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.customer.controller;

import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactGroup;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactGroupRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author libertyerp_local
 */
@Controller
@RequestMapping("/customerwhatsappcontactgroup")
public class Customer_WhatsappContactGroupController {

    @Autowired
    WhatsappContactGroupRepository whatsappContactGroupRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        model.addAttribute("username", loggedUserService.activeUserName());
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());

        model.addAttribute("list", whatsappContactGroupRepository.findByCustomerOrderByIdDesc(userId));
        return "whatsapp/customer/contactgroup_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, WhatsappContactGroup whatsappContactGroup) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        whatsappContactGroup.setCustomer(userId);

        //  model.addAttribute("customerlist", usersRepository.findAll());
        return "whatsapp/customer/add_contactgroup";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid WhatsappContactGroup whatsappContactGroup, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            whatsappContactGroup.setCustomer(userId);
            return "whatsapp/customer/add_contactgroup";
        }
        whatsappContactGroupRepository.save(whatsappContactGroup);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customerwhatsappcontactgroup/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, WhatsappContactGroup whatsappContactGroup) {
        model.addAttribute("whatsappContactGroup", whatsappContactGroupRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        return "whatsapp/customer/add_contactgroup";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, WhatsappContactGroup whatsappContactGroup, RedirectAttributes redirectAttributes) {
        whatsappContactGroupRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customerwhatsappcontactgroup/index";
    }

}
