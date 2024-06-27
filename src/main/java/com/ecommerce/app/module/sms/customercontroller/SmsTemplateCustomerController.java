/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.customercontroller;

import com.ecommerce.app.module.sms.model.SmsTemplate;
import com.ecommerce.app.module.sms.ripository.SmsTemplateRepository;
import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
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
@RequestMapping("/customer_smstemplate")
public class SmsTemplateCustomerController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SmsTemplateRepository smsTemplateRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("list", smsTemplateRepository.findByCustomerOrderByIdDesc(userId));
        return "sms/customers/smstemplate_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, SmsTemplate smsTemplate) {
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        smsTemplate.setCustomer(userId);
        return "sms/customers/add_smstemplate";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid SmsTemplate smsTemplate,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            smsTemplate.setCustomer(userId);

            return "sms/customers/add_smstemplate";
        }
        smsTemplateRepository.save(smsTemplate);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customer_smstemplate/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, SmsTemplate smsTemplate) {
        model.addAttribute("smsTemplate", smsTemplateRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        return "sms/customers/add_smstemplate";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, SmsTemplate smsTemplate, RedirectAttributes redirectAttributes) {
        smsTemplateRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customer_smstemplate/index";
    }

}
