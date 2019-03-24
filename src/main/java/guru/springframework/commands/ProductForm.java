package guru.springframework.commands;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class ProductForm {

    private Integer id;
    private Integer version;

    @NotEmpty
    @Size(min=5, max=2000)
    private String description;

    @Range(min=1L, max=5000L)
    private BigDecimal price;

    @URL
    private String imageUrl;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) { this.version = version; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
