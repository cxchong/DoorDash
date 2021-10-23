package com.laioffer.onlineOrder.dao;

import com.laioffer.onlineOrder.entity.Authorities;
import com.laioffer.onlineOrder.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    // DAO只保证customer被放进数据库；customer怎么来的是service管
    public void signUp(Customer customer) {
        // 给customer权限
        Authorities authorities = new Authorities();
        authorities.setEmail(customer.getEmail());
        authorities.setAuthorities("ROLE_USER");


        Session session = null;
        try {
            session = sessionFactory.openSession();
            // transaction开头结尾的对数据库的操作，保证对数据库的操作atomic（都执行或者都不执行）
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            // 都不执行
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }



    public Customer getCustomer(String email) {
        Customer customer = null;
        try (Session session = sessionFactory.openSession()) {
            // 通过一行里的一个attribute搜索这行 -> criteria
            // 也可以直接session.get(Customer.class, email取代下面两行)
            Criteria criteria = session.createCriteria(Customer.class);
            customer = (Customer) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } /* finally {
            if (session != null) {
                session.close();
            }
        } */
        return customer;
    }



}


