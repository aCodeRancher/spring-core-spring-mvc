package guru.springframework.services.mapservices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.DomainObject;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jt on 11/6/15.
 */
@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {

    private ProductFormToProduct productFormToProduct;

    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductFormToProduct (ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setProductToProductForm (ProductToProductForm productToProductForm){
        this.productToProductForm = productToProductForm;
    }
    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product getById(Integer id) {
        return (Product) super.getById(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return (Product) super.saveOrUpdate(domainObject);
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
        super.delete(id);
    }



   }
