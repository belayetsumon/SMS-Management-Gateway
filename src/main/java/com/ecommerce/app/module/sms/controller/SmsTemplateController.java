/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.controller;

import com.ecommerce.app.module.sms.model.SmsTemplate;
import com.ecommerce.app.module.sms.ripository.SmsTemplateRepository;
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
@RequestMapping("/smstemplate")
public class SmsTemplateController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SmsTemplateRepository smsTemplateRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", smsTemplateRepository.findAll(sort));
        return "sms/admin/smstemplate_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, SmsTemplate smsTemplate) {
        model.addAttribute("customerlist", usersRepository.findAll());
        return "sms/admin/add_smstemplate";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid SmsTemplate smsTemplate,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerlist", usersRepository.findAll());

            return "sms/admin/add_smstemplate";
        }
        smsTemplateRepository.save(smsTemplate);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/smstemplate/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, SmsTemplate smsTemplate) {
        model.addAttribute("smsTemplate", smsTemplateRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        return "sms/admin/add_smstemplate";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, SmsTemplate smsTemplate, RedirectAttributes redirectAttributes) {
        smsTemplateRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/smstemplate/index";
    }

}
