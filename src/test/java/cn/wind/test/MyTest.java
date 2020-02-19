package cn.wind.test;

import cn.wind.pojo.Bill;
import cn.wind.pojo.Provider;
import cn.wind.pojo.Role;
import cn.wind.pojo.User;
import cn.wind.service.bill.BillService;
import cn.wind.service.provider.ProviderService;
import cn.wind.service.role.RoleService;
import cn.wind.service.user.UserService;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:Wild
 * @create:2020-02-14 21:34
 */
public class MyTest {

    @Test
    public void testUser(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService bean = context.getBean("userService", UserService.class);
        for (User user : bean.findUserList("", 2, 1, 10)) {
            System.out.println("User========>"+user);
        }
    }

    @Test
    public void testProvider(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ProviderService bean = context.getBean("providerService", ProviderService.class);
        for (Provider provider : bean.findProviderList()) {
            System.out.println("provider========>"+provider);
        }
    }

    @Test
    public void testRole(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        RoleService bean = context.getBean("roleService", RoleService.class);
        for (Role role : bean.findRoleList()) {
            System.out.println("role==========>"+role);
        }
    }

    @Test
    public void testBill(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BillService bean = context.getBean("billService", BillService.class);
        for (Bill bill : bean.findBillList("", 0, 0, 1, 10)) {
            System.out.println("bill=========>"+bill);
        }
    }
}
