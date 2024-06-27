/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.controller;

import com.ecommerce.app.module.sms.model.Gateway;
import com.ecommerce.app.module.sms.ripository.GatewayRepository;
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
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    GatewayRepository gatewayRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", gatewayRepository.findAll(sort));

        return "sms/admin/gateway_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, Gateway gateway) {
//        model.addAttribute("customerlist", usersRepository.findAll());
        return "sms/admin/add_gateway";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Gateway gateway, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("customerlist", usersRepository.findAll());
            return "sms/admin/add_gateway";
        }
        gatewayRepository.save(gateway);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/gateway/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Gateway gateway) {
        model.addAttribute("gateway", gatewayRepository.findById(id));
//        model.addAttribute("customerlist", usersRepository.findAll());
        return "sms/admin/add_gateway";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Gateway gateway, RedirectAttributes redirectAttributes) {
        gatewayRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/gateway/index";
    }

}
