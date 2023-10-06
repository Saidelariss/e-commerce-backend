package ecom.backendecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageProduct {
    private List<Product> products;
    private Integer page;
    private Integer size;
    private Integer totalPages;

    public static PageProduct map(Page<Product> pageable){
        PageProduct pageProduct= new PageProduct();
        pageProduct.setProducts(pageable.getContent());
        pageProduct.setPage(pageable.getNumber());
        pageProduct.setSize(pageable.getSize());
        pageProduct.setTotalPages(pageable.getTotalPages());

      return pageProduct;

    }
}
