package guru.springframework.services.reposervices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.repositories.ProductRepository;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 12/18/15.
 */
@Service
@Profile({"springdatajpa"})
public class ProductServiceRepoImpl implements ProductService {

    private ProductRepository productRepository;

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
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<?> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); //fun with Java 8
        return products;
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return productRepository.save(domainObject);
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
        productRepository.delete(id);
    }
}
