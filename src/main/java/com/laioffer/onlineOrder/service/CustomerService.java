package com.laioffer.onlineOrder.service;

import com.laioffer.onlineOrder.dao.CustomerDao;
import com.laioffer.onlineOrder.entity.Cart;
import com.laioffer.onlineOrder.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    // service中注入DAO
    @Autowired
    private CustomerDao customerDao;

    public void signUp(Customer customer) {
        // customer class里，cart没有被初始化，需要创建
        Cart cart = new Cart();
        // cart, customer关联
        customer.setCart(cart);

        // 保证之后可以做login
        customer.setEnabled(true);
        // 在service层定义customer后传入
        customerDao.signUp(customer);
    }

    public Customer getCustomer(String email) {
        return customerDao.getCustomer(email);
    }



}
