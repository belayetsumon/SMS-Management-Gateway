/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.controller;

import com.ecommerce.app.module.sms.model.SmsSend;
import com.ecommerce.app.module.sms.model.SmsType;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
import com.ecommerce.app.module.sms.ripository.ContactsnoRepository;
import com.ecommerce.app.module.sms.ripository.GatewayRepository;
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
import com.ecommerce.app.module.sms.ripository.SmssendRepository;
import com.ecommerce.app.module.sms.services.SmsService;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author libertyerp_local
 */
@Controller
@RequestMapping("/smssend")
public class SmssendController {

    @Autowired
    ContactGroupRepository contactGroupRepository;

    @Autowired
    ContactsnoRepository contactsnoRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SmssendRepository smsRepository;
    @Autowired
    GatewayRepository gatewayRepository;

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", smsRepository.findAll(sort));
        return "sms/admin/sms_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, SmsSend smsSend) {
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactGroupList", contactGroupRepository.findAll());
        model.addAttribute("smsType", SmsType.values());
        model.addAttribute("gatewayList", gatewayRepository.findAll());
        return "sms/admin/add_sms";
    }

    @GetMapping("/sendSms")
    public String sendSms(
            //            @RequestParam String phoneNumber,
            //            @RequestParam String message,
            SmsSend smsSend) throws PDUException, NegativeResponseException, ResponseTimeoutException, ResponseTimeoutException, InvalidResponseException {
        smsService.sendSms(smsSend.getRecipient(), smsSend.getMessage()); // Handle exceptions
        return "SMS sent!";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid SmsSend smsSend,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerlist", usersRepository.findAll());
            model.addAttribute("contactGroupList", contactGroupRepository.findAll());
            model.addAttribute("smsType", SmsType.values());
            model.addAttribute("gatewayList", gatewayRepository.findAll());
            return "sms/admin/add_sms";
        }
        smsRepository.save(smsSend);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/smssend/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, SmsSend smsSend) {
        model.addAttribute("smsSend", smsRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        model.addAttribute("contactsGroupList", contactGroupRepository.findAll());
        model.addAttribute("smsType", SmsType.values());
        model.addAttribute("gatewayList", gatewayRepository.findAll());
        return "sms/admin/add_sms";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, SmsSend smsSend, RedirectAttributes redirectAttributes) {
        smsRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/smssend/index";
    }

}
