package guru.springframework.controllers;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testList() throws Exception{

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));

    }

    @Test
    public void testShow() throws Exception{
        Integer id = 1;
        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception{
        Integer id = 1;
        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testNewCustomer() throws Exception {

        //should not call service
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String firstname = "Jane";
        String lastname = "Doe";
        String addressLine1 = "1 Main St";
        String city = "Miami";
        String state = "Florida";
        String zipCode = "33101";
        String email = "janedoe@gmail.com";
        String phone = "305.333.0101";

        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstname);
        customer.setLastName(lastname);
        customer.setAddressLine1(addressLine1);
        customer.setCity(city);
        customer.setState(state);
        customer.setZipCode(zipCode);
        customer.setEmail(email);
        customer.setPhoneNumber(phone);

        when(customerService.saveOrUpdate(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customer")
                .param("id", "1")
                .param("firstName", "Jane")
                .param("lastName", "Doe")
                .param ("addressLine1", "1 Main St")
                .param ("city", "Miami")
                .param ("state", "Florida")
                .param ("zipCode", "33101")
                .param("email", "janedoe@gmail.com")
                .param("phoneNumber", "305.333.0101"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:customer/show/1"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstname))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastname))))
                .andExpect(model().attribute("customer", hasProperty("addressLine1", is(addressLine1))))
                .andExpect(model().attribute("customer", hasProperty("city", is(city))))
                .andExpect(model().attribute("customer", hasProperty("state", is(state))))
                .andExpect(model().attribute("customer", hasProperty("zipCode", is(zipCode))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phone))));

        //verify properties of bound object
        ArgumentCaptor<Customer> customerArgCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(customerArgCaptor.capture());

        assertEquals(id, customerArgCaptor.getValue().getId());
        assertEquals(firstname, customerArgCaptor.getValue().getFirstName());
        assertEquals(lastname, customerArgCaptor.getValue().getLastName());
        assertEquals(addressLine1, customerArgCaptor.getValue().getAddressLine1());
        assertEquals(city, customerArgCaptor.getValue().getCity());
        assertEquals(state, customerArgCaptor.getValue().getState());
        assertEquals(zipCode, customerArgCaptor.getValue().getZipCode());
        assertEquals(state, customerArgCaptor.getValue().getState());
        assertEquals(zipCode, customerArgCaptor.getValue().getZipCode());
        assertEquals(email, customerArgCaptor.getValue().getEmail());
        assertEquals(phone, customerArgCaptor.getValue().getPhoneNumber());
    }

    @Test
    public void testDelete() throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).delete(id);
        verify(customerService, times(0)).listAll();
    }
}
