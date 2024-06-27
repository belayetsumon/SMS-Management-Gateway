/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.customercontroller;

import com.ecommerce.app.module.sms.model.SmsSend;
import com.ecommerce.app.module.sms.model.SmsType;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
import com.ecommerce.app.module.sms.ripository.ContactsnoRepository;
import com.ecommerce.app.module.sms.ripository.GatewayRepository;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ecommerce.app.module.sms.ripository.SmssendRepository;
import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.services.LoggedUserService;
import static org.springframework.data.domain.Sort.sort;

/**
 *
 * @author libertyerp_local
 */
@Controller
@RequestMapping("/customer_smssend")
public class SmssendCustomerController {

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
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("list", smsRepository.findByCustomerOrderByIdDesc(userId));
        return "sms/customers/sms_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, SmsSend smsSend) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        smsSend.setCustomer(userId);
        model.addAttribute("contactGroupList", contactGroupRepository.findByCustomerOrderByIdDesc(userId));
        model.addAttribute("smsType", SmsType.values());
        model.addAttribute("gatewayList", gatewayRepository.findAll());
        return "sms/customers/add_sms";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid SmsSend smsSend,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            smsSend.setCustomer(userId);
            model.addAttribute("contactGroupList", contactGroupRepository.findByCustomerOrderByIdDesc(userId));
            model.addAttribute("smsType", SmsType.values());
            model.addAttribute("gatewayList", gatewayRepository.findAll());
            return "sms/customers/add_sms";
        }
        smsRepository.save(smsSend);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customer_smssend/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, SmsSend smsSend) {
        model.addAttribute("smsSend", smsRepository.findById(id));
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("contactsGroupList", contactGroupRepository.findByCustomerOrderByIdDesc(userId));
        model.addAttribute("smsType", SmsType.values());
        model.addAttribute("gatewayList", gatewayRepository.findAll());
        return "sms/customers/add_sms";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, SmsSend smsSend, RedirectAttributes redirectAttributes) {
        smsRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customer_smssend/index";
    }

}
