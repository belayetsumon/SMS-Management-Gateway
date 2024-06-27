/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.customercontroller;

import com.ecommerce.app.module.sms.model.ContactGroup;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
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
@RequestMapping("/customer_contactgroup")
public class ContactGroupCustomerController {

    @Autowired
    ContactGroupRepository contactGroupRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String page(Model model) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("list", contactGroupRepository.findByCustomerOrderByIdDesc(userId));

        return "sms/customers/contactgroup_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, ContactGroup contactGroup) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());

        contactGroup.setCustomer(userId);

        return "sms/customers/add_contactgroup";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid ContactGroup contactGroup, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {

            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());

            contactGroup.setCustomer(userId);

            model.addAttribute("customerlist", usersRepository.findAll());
            return "sms/customers/add_contactgroup";
        }
        contactGroupRepository.save(contactGroup);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customer_contactgroup/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, ContactGroup contactGroup) {
        model.addAttribute("contactGroup", contactGroupRepository.findById(id));
        return "sms/customers/add_contactgroup";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, ContactGroup contactGroup, RedirectAttributes redirectAttributes) {
        contactGroupRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customer_contactgroup/index";
    }

}
