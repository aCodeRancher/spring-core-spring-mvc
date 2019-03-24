package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class ProductFormToProduct implements Converter<ProductForm, Product> {

    @Override
    public Product convert(ProductForm productForm) {

        Product product = new Product();
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setImageUrl(productForm.getImageUrl());
        return product;
    }
}
