/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.controller;

import com.ecommerce.app.model.enumvalue.Status;
import com.ecommerce.app.module.sms.model.ContactStatus;
import com.ecommerce.app.module.sms.model.Contactsno;
import com.ecommerce.app.module.sms.model.PricePlan;
import com.ecommerce.app.module.sms.ripository.ContactGroupRepository;
import com.ecommerce.app.module.sms.ripository.ContactsnoRepository;
import com.ecommerce.app.module.sms.ripository.PlanRepository;
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
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    ContactGroupRepository contactGroupRepository;

    @Autowired
    ContactsnoRepository contactsnoRepository;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", planRepository.findAll(sort));
        return "sms/admin/plan_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, PricePlan pricePlan) {
        model.addAttribute("planStatus", Status.values());
        return "sms/admin/add_plan";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid PricePlan pricePlan,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("planStatus", Status.values());
            return "sms/admin/add_plan";
        }
        planRepository.save(pricePlan);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/plan/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, PricePlan pricePlan) {
        model.addAttribute("pricePlan", planRepository.findById(id));
        model.addAttribute("planStatus", Status.values());
        return "sms/admin/add_plan";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, PricePlan pricePlan, RedirectAttributes redirectAttributes) {
        planRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/plan/index";
    }

}
