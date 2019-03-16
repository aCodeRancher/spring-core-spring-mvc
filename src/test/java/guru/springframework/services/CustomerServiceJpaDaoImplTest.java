package guru.springframework.services;
import guru.springframework.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testListMethod() throws Exception {

        List<Customer> customers = (List<Customer>) customerService.listAll();
        assertThat("customer size" , customers.size()==2);
    }

    @Test
    public void testGetById() throws Exception{
        Customer customer1 = customerService.getById(1);
        assertEquals(customer1.getId(), new Integer(1));
    }

    @Test
    public void testSave() throws Exception{
        Customer customer3 = new Customer();
        customer3.setFirstName("Mary");
        customer3.setLastName("Wang");
        customer3.setEmail("mwang@gmail.com");
        customer3.setPhoneNumber("212-444-1111");
        customer3.setAddressLine1("4045, North Utah Street");
        customer3.setCity("Arlington");
        customer3.setState("VA");
        customer3.setZipCode("22201");
        customerService.saveOrUpdate(customer3);
        Customer customerReturned = customerService.getById(8);
        assertEquals(customerReturned.getId(), new Integer(8));
        assertEquals(customerReturned.getFirstName(), "Mary");
        customerService.delete(8);
    }

    @Test
    public void testUpdate() throws Exception{

        Customer customer2 = customerService.getById(2);
        Integer originVersion = customer2.getVersion();
        customer2.setFirstName("Joe");
        Customer updatedCustomer = customerService.saveOrUpdate(customer2);
        assertEquals(updatedCustomer.getId(), new Integer(2));
        assertEquals(updatedCustomer.getVersion(), new Integer(originVersion.intValue() +1) );
    }

    @Test
    public void testDelete() throws Exception{

        int originSize = 2;
        Customer tempCustomer = new Customer();
        tempCustomer.setFirstName("Andy");
        tempCustomer.setLastName("Lau");
        tempCustomer.setEmail("alau@gmail.com");
        tempCustomer.setPhoneNumber("571-666-1111");
        tempCustomer.setAddressLine1("6000, North Utah Street");
        tempCustomer.setCity("Arlington");
        tempCustomer.setState("VA");
        tempCustomer.setZipCode("22201");
        customerService.saveOrUpdate(tempCustomer);

        Customer customerReturned = customerService.getById(originSize+1);
        assertEquals(customerReturned.getId(), new Integer(originSize+1));
        assertEquals(customerReturned.getFirstName(), "Andy");
        customerService.delete(originSize+1);
        assertEquals(customerService.getById(3), null);
    }
}
