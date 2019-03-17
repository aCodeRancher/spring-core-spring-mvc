package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by jt on 12/14/15.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class ProductServiceJapDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() throws Exception {

        List<Product> products = (List<Product>) productService.listAll();
        assertThat("product size" , products.size()==5);
    }

    @Test
    public void testGetById() throws Exception{
       Product product1 = productService.getById(1);
       assertEquals(product1.getId(), new Integer(1));
    }

     @Test
     public void testSave() throws Exception{
        Product product6 = new Product();
        product6.setDescription("Product 6");
        product6.setPrice(new BigDecimal("6.99"));
        product6.setImageUrl("http://example.com/product6");
        productService.saveOrUpdate(product6);
        Product productReturned = productService.getById(6);
        assertEquals(productReturned.getId(), new Integer(6));
        assertEquals(productReturned.getPrice(), new BigDecimal("6.99"));
        productService.delete(6);
     }

     @Test
     public void testUpdate() throws Exception{

        Product product5 = productService.getById(5);
        Integer originVersion = product5.getVersion();
        BigDecimal originPrice = product5.getPrice();
        BigDecimal updatedPrice = new BigDecimal ( originPrice.longValue() + 10L ) ;
        product5.setPrice(updatedPrice);
        Product updatedProduct = productService.saveOrUpdate(product5);
        assertEquals(updatedProduct.getId(), new Integer(5));
        assertEquals(updatedProduct.getVersion(), new Integer(originVersion.intValue() +1) );
     }

     @Test
    public void testDelete() throws Exception{

        int originSize = 5;
        Product tempProduct = new Product();
        tempProduct.setDescription("Product Temp");
        tempProduct.setPrice(new BigDecimal("10.00"));
        tempProduct.setImageUrl("http://example.com/productTemp");
        productService.saveOrUpdate(tempProduct);

        Product productReturned = productService.getById(originSize+1);
        assertEquals(productReturned.getId(), new Integer(originSize+1));
        assertEquals(productReturned.getDescription(), "Product Temp");
        productService.delete(originSize+1);
        assertEquals(productService.getById(6), null);
     }
}
