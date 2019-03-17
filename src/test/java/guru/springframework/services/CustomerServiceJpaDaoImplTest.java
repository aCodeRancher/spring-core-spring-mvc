package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jt on 12/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)

@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;
    private UserService userService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService =userService;
    }

    @Test
    public void testList() throws Exception {
        List<Customer> customers = (List<Customer>) customerService.listAll();

        assert customers.size() == 3;

    }

    @Test
    public void testSaveWithUser() {

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("MyAwesomePassword");
        customer.setUser(user);
        user.setCustomer(customer);
        User savedUser = userService.saveOrUpdate(user);
        Customer savedCustomer = customerService.saveOrUpdate(customer);

        assert userService.listAll().size() == 4;
        assert savedUser.getCustomer() != null;
        assert savedUser.getCustomer().getId() !=null;
    }
}
