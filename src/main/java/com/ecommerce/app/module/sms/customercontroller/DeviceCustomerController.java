/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.ecommerce.app.module.sms.customercontroller;

import com.ecommerce.app.module.sms.model.Device;
import com.ecommerce.app.module.sms.ripository.DeviceRepository;
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
@RequestMapping("/devicecustomer")
public class DeviceCustomerController {

    @Autowired
    DeviceRepository deviceRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("list", deviceRepository.findAll());
        return "sms/customers/device_index";
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model, Device device) {
        return "sms/customers/add_device";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Device device, BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {

            return "sms/customers/add_device";

        }
        deviceRepository.save(device);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/devicecustomer/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Device device) {
        model.addAttribute("device", deviceRepository.findById(id));
        return "sms/customers/add_device";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Device device, RedirectAttributes redirectAttributes) {
        deviceRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/devicecustomer/index";
    }

}
