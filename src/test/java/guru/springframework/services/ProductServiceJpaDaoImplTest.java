package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;

import guru.springframework.domain.Product;
import java.math.BigDecimal;
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
@ActiveProfiles("springdatajpa")
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() throws Exception {

        List<Product> products = (List<Product>) productService.listAll();

        assert products.size() == 5;

    }

    @Test
    public void testSaveWithUser() {

        Product product = new Product();
        product.setPrice( new BigDecimal(500.00));
        product.setDescription("Dell Latitude E4550 Laptop");
        product.setImageUrl("www.dell.com/laptop/latitude/E4550");
        Product savedProduct = productService.saveOrUpdate(product);

        assert savedProduct.getId() != null;
    }
}
