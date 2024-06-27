/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.customer.controller;

import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactStatus;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactsno;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactGroupRepository;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactsnoRepository;
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
@RequestMapping("/customerwhatsappcontactsno")
public class Customer_WhatsappContactsnoController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WhatsappContactGroupRepository whatsappContactGroupRepository;

    @Autowired
    WhatsappContactsnoRepository whatsappContactsnoRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());

        model.addAttribute("list", whatsappContactsnoRepository.findByCustomerOrderByIdDesc(userId));
        return "whatsapp/customer/contactsno_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, WhatsappContactsno whatsappContactsno) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());

        whatsappContactsno.setCustomer(userId);

        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
        model.addAttribute("contactStatus", WhatsappContactStatus.values());
        return "whatsapp/customer/add_contacs";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid WhatsappContactsno whatsappContactsno,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());

            whatsappContactsno.setCustomer(userId);
            model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
            model.addAttribute("contactStatus", WhatsappContactStatus.values());
            return "whatsapp/customer/add_contacs";
        }
        whatsappContactsnoRepository.save(whatsappContactsno);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customerwhatsappcontactsno/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, WhatsappContactsno whatsappContactsno) {
        model.addAttribute("whatsappContactsno", whatsappContactsnoRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
        model.addAttribute("contactStatus", WhatsappContactStatus.values());
        return "whatsapp/customer/add_contacs";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, WhatsappContactsno whatsappContactsno, RedirectAttributes redirectAttributes) {
        whatsappContactsnoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customerwhatsappcontactsno/index";
    }
}
