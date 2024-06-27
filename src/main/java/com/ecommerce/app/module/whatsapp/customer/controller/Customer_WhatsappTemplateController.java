package com.ecommerce.app.module.whatsapp.customer.controller;

import com.ecommerce.app.module.user.model.Users;
import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.user.services.LoggedUserService;
import com.ecommerce.app.module.whatsapp.model.WhatsappTemplate;
import com.ecommerce.app.module.whatsapp.repository.WhatsappTemplateRepository;
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
@RequestMapping("/customerwhatsapptemplate")
public class Customer_WhatsappTemplateController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WhatsappTemplateRepository whatsappTemplateRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("list", whatsappTemplateRepository.findByCustomerOrderByIdDesc(userId));
        return "whatsapp/customer/smstemplate_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, WhatsappTemplate whatsappTemplate) {
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        whatsappTemplate.setCustomer(userId);

        return "whatsapp/customer/add_smstemplate";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid WhatsappTemplate whatsappTemplate,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            Users userId = new Users();
            userId.setId(loggedUserService.activeUserid());
            whatsappTemplate.setCustomer(userId);

            return "whatsapp/customer/smstemplate_index";
        }
        whatsappTemplateRepository.save(whatsappTemplate);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customerwhatsapptemplate/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, WhatsappTemplate whatsappTemplate) {
        model.addAttribute("whatsappTemplate", whatsappTemplateRepository.findById(id));
        model.addAttribute("customerlist", usersRepository.findAll());
        return "whatsapp/customer/add_smstemplate";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, WhatsappTemplate whatsappTemplate, RedirectAttributes redirectAttributes) {
        whatsappTemplateRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customerwhatsapptemplate/index";
    }

}
