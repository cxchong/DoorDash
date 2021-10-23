package com.laioffer.onlineOrder.controller;

import com.laioffer.onlineOrder.entity.Customer;
import com.laioffer.onlineOrder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignUpController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST) // 将这个signup请求和post req关联起来
    @ResponseStatus(value = HttpStatus.CREATED) // 写死返回status：201
    public void signUp(@RequestBody Customer customer) {
        // request中的json field和Customer 的field能对应;将req放入java object
        customerService.signUp(customer);

    }
}
