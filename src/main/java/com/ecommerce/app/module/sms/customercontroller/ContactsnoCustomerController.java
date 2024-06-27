/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.customercontroller;

import com.ecommerce.app.module.sms.model.ContactStatus;
import com.ecommerce.app.module.sms.model.Contactsno;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
import com.ecommerce.app.module.sms.ripository.ContactsnoRepository;
import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
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
@RequestMapping("/customer_contactsno")
public class ContactsnoCustomerController {

    @Autowired
    ContactGroupRepository contactGroupRepository;

    @Autowired
    ContactsnoRepository contactsnoRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("list", contactsnoRepository.findByCustomerOrderByIdDesc(userId));
        return "sms/customers/contactsno_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, Contactsno contactsno) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        contactsno.setCustomer(userId);
        model.addAttribute("contactGroupList", contactGroupRepository.findAll());
        model.addAttribute("contactStatus", ContactStatus.values());
        return "sms/customers/add_contacs";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Contactsno contactsno,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            contactsno.setCustomer(userId);

            model.addAttribute("contactGroupList", contactGroupRepository.findAll());
            model.addAttribute("contactStatus", ContactStatus.values());

            return "sms/customers/add_contacs";
        }
        contactsnoRepository.save(contactsno);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customer_contactsno/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Contactsno contactsno) {
        model.addAttribute("contactsno", contactsnoRepository.findById(id));
        model.addAttribute("contactGroupList", contactGroupRepository.findAll());
        model.addAttribute("contactStatus", ContactStatus.values());
        return "sms/customers/add_contacs";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Contactsno contactsno, RedirectAttributes redirectAttributes) {
        contactsnoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customer_contactsno/index";
    }

}
