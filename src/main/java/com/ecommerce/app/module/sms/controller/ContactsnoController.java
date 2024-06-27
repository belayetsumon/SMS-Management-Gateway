/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.controller;

import com.ecommerce.app.module.sms.model.ContactStatus;
import com.ecommerce.app.module.sms.model.Contactsno;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
import com.ecommerce.app.module.sms.ripository.ContactsnoRepository;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/contactsno")
public class ContactsnoController {

    @Autowired
    ContactGroupRepository contactGroupRepository;

    @Autowired
    ContactsnoRepository contactsnoRepository;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", contactsnoRepository.findAll(sort));
        return "sms/admin/contactsno_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, Contactsno contactsno) {
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", contactGroupRepository.findAll());
        model.addAttribute("contactStatus", ContactStatus.values());
        return "sms/admin/add_contacs";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Contactsno contactsno,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerlist", usersRepository.findAll());
            model.addAttribute("contactGroupList", contactGroupRepository.findAll());
            model.addAttribute("contactsStatus", ContactStatus.values());
            return "sms/admin/add_contacs";
        }
        contactsnoRepository.save(contactsno);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/contactsno/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Contactsno contactsno) {
        model.addAttribute("contactsno", contactsnoRepository.findById(id));

        model.addAttribute("contactGroupList", contactGroupRepository.findAll());
        model.addAttribute("contactStatus", ContactStatus.values());
        return "sms/admin/add_contacs";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Contactsno contactsno, RedirectAttributes redirectAttributes) {
        contactsnoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/contactsno/index";
    }

}
