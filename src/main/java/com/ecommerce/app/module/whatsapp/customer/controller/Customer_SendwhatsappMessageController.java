/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.whatsapp.customer.controller;

import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
import com.ecommerce.app.module.whatsapp.model.SendwhatsappMessage;
import com.ecommerce.app.module.whatsapp.model.WhatsappMsgType;
import com.ecommerce.app.module.whatsapp.repository.SendwhatsappMessageRepository;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactGroupRepository;
import com.ecommerce.app.module.whatsapp.repository.WhatsappContactsnoRepository;
import com.ecommerce.app.module.whatsapp.repository.WhatsappTemplateRepository;
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
@RequestMapping("/customersendwhatsappmessage")
public class Customer_SendwhatsappMessageController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WhatsappContactGroupRepository whatsappContactGroupRepository;

    @Autowired
    WhatsappContactsnoRepository whatsappContactsnoRepository;

    @Autowired
    SendwhatsappMessageRepository sendwhatsappMessageRepository;

    @Autowired
    LoggedUserService loggedUserService;
    
       @Autowired
    WhatsappTemplateRepository whatsappTemplateRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        model.addAttribute("username", loggedUserService.activeUserName());
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", sendwhatsappMessageRepository.findAll(sort));
        return "whatsapp/customer/sms_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, SendwhatsappMessage sendwhatsappMessage) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        sendwhatsappMessage.setCustomer(userId);

        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findByCustomerOrderByIdDesc(userId));
        model.addAttribute("templateList", whatsappTemplateRepository.findByCustomerOrderByIdDesc(userId));
        model.addAttribute("smsType", WhatsappMsgType.values());
        return "whatsapp/customer/add_sms";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid SendwhatsappMessage sendwhatsappMessage,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {

            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            sendwhatsappMessage.setCustomer(userId);

            model.addAttribute("contactGroupList", whatsappContactGroupRepository.findByCustomerOrderByIdDesc(userId));
            model.addAttribute("templateList", whatsappContactGroupRepository.findByCustomerOrderByIdDesc(userId));
            model.addAttribute("smsType", WhatsappMsgType.values());

            return "whatsapp/customer/add_sms";
        }
        sendwhatsappMessageRepository.save(sendwhatsappMessage);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customersendwhatsappmessage/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, SendwhatsappMessage sendwhatsappMessage) {
        model.addAttribute("sendwhatsappMessage", sendwhatsappMessageRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", whatsappContactGroupRepository.findAll());
        model.addAttribute("smsType", WhatsappMsgType.values());
        return "whatsapp/customer/add_sms";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, SendwhatsappMessage sendwhatsappMessage, RedirectAttributes redirectAttributes) {
        sendwhatsappMessageRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customersendwhatsappmessage/index";
    }

}
