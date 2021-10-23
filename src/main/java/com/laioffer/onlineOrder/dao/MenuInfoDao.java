package com.laioffer.onlineOrder.dao;

import com.laioffer.onlineOrder.entity.MenuItem;
import com.laioffer.onlineOrder.entity.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuInfoDao {

    // hibernate用于链接数据库的API：sessionFactory；做增删改查的API：session；
    // 通过SF new一个session
    @Autowired
    private SessionFactory sessionFactory;

    // login进来后把所有数据库里的restaurant作为list返回
    public List<Restaurant> getRestaurant() {
        try (Session session = sessionFactory.openSession()) {
            // 去restaurant table里select
            return session.createCriteria(Restaurant.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) // 去重，因为restaurant entity的fetchtype是eager（join?）
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    // 根据UI发来的餐厅ID，返回餐厅里的menu
    public List<MenuItem> getAllMenuItem(int restaurantId) {
        // 将openSession卸载try block里，自动session.close()
        try (Session session = sessionFactory.openSession()) {
            // get相当于SQL SELECT；get是通过主键搜索，criteria可以进行的操作更多
            // 指定restaurantId，不用去重
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);
            if (restaurant != null) {
                return restaurant.getMenuItemList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    // 想从cart里通过itemId选择具体menuItem
    public MenuItem getMenuItem(int menuItemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MenuItem.class, menuItemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
