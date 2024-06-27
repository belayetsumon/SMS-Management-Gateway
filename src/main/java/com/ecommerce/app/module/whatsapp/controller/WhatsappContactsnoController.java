/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.controller;

import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactStatus;
import com.ecommerce.app.module.whatsapp.model.WhatsappContactsno;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactGroupRepository;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactsnoRepository;
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
@RequestMapping("/whatsappcontactsno")
public class WhatsappContactsnoController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WhatsappContactGroupRepository whatsappContactGroupRepository;

    @Autowired
    WhatsappContactsnoRepository whatsappContactsnoRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", whatsappContactsnoRepository.findAll(sort));
        return "whatsapp/admin/contactsno_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, WhatsappContactsno whatsappContactsno) {
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
        model.addAttribute("contactStatus", WhatsappContactStatus.values());
        return "whatsapp/admin/add_contacs";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid WhatsappContactsno whatsappContactsno,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerlist", usersRepository.findAll());
            model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
            model.addAttribute("contactStatus", WhatsappContactStatus.values());
            return "whatsapp/admin/add_contacs";
        }
        whatsappContactsnoRepository.save(whatsappContactsno);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/whatsappcontactsno/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, WhatsappContactsno whatsappContactsno) {
        model.addAttribute("contactsno", whatsappContactsnoRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
        model.addAttribute("contactStatus", WhatsappContactStatus.values());
        return "whatsapp/admin/add_contacs";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, WhatsappContactsno whatsappContactsno, RedirectAttributes redirectAttributes) {
        whatsappContactsnoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/whatsappcontactsno/index";
    }
}
