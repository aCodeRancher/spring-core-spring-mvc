package guru.springframework.services.jpaservices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by jt on 12/9/15.
 */
@Service
@Profile("jpadao")
public class ProductServiceJpaDaoImpl extends AbstractJpaDaoService implements ProductService {


    private ProductFormToProduct productFormToProduct;

    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductToProductForm (ProductToProductForm productToProductForm){
        this.productToProductForm = productToProductForm;
    }

    @Autowired
    public void setProductFormToProduct (ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct ;
    }

    @Override
    public List<Product> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product savedProduct = em.merge(domainObject);
        em.getTransaction().commit();

        return savedProduct;
    }

    @Override
    public ProductForm saveOrUpdateProductForm(ProductForm productForm) {
        Product aProduct = null;
        if (productForm.getId() != null){
            aProduct = this.getById(productForm.getId());
            aProduct.setVersion(productForm.getVersion());
            aProduct.setDescription(productForm.getDescription());
            aProduct.setPrice(productForm.getPrice());
            aProduct.setImageUrl(productForm.getImageUrl());
            return productToProductForm.convert(this.saveOrUpdate(aProduct));
        }
        else {
             return productToProductForm.convert(this.saveOrUpdate(productFormToProduct.convert(productForm)));
        }
    }


    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }
}
