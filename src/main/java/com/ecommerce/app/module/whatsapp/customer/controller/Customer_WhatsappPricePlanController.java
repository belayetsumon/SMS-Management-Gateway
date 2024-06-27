package com.ecommerce.app.module.whatsapp.customer.controller;

import com.ecommerce.app.module.user.ripository.UsersRepository;
import com.ecommerce.app.module.whatsapp.model.WhatsappPricePlan;
import com.ecommerce.app.module.whatsapp.model.WhatsappPricePlanStatus;
import com.ecommerce.app.module.whatsapp.repository.WhatsappPricePlanRepository;
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
@RequestMapping("/customerwhatsapppriceplan")
public class Customer_WhatsappPricePlanController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WhatsappPricePlanRepository whatsappPricePlanRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        model.addAttribute("list", whatsappPricePlanRepository.findAll(sort));
        return "whatsapp/customer/plan_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, WhatsappPricePlan whatsappPricePlan) {
        model.addAttribute("planStatus", WhatsappPricePlanStatus.values());
        return "whatsapp/customer/add_plan";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid WhatsappPricePlan whatsappPricePlan,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("planStatus", WhatsappPricePlanStatus.values());
            return "whatsapp/customer/add_plan";
        }
        whatsappPricePlanRepository.save(whatsappPricePlan);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/customerwhatsapppriceplan/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, WhatsappPricePlan whatsappPricePlan) {
        model.addAttribute("whatsappPricePlan", whatsappPricePlanRepository.findById(id));
        model.addAttribute("planStatus", WhatsappPricePlanStatus.values());
        return "whatsapp/customer/add_plan";
    }
    

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, WhatsappPricePlan whatsappPricePlan, RedirectAttributes redirectAttributes) {
        whatsappPricePlanRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/customerwhatsapppriceplan/index";
    }

}
