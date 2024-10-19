package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {

    // Товар находится в определенной категории
    // Цена товара выше указанной
    // Цена товара ниже указанной
    // Рейтинг товара выше указанного
    // Название товара содержит указанную подстроку без учета регистра

    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryId(params.getCategoryId())
                .and(greaterThan(params.getPriceGt()))
                .and(lessThan(params.getPriceLt()))
                .and(rateGreaterThan(params.getRatingGt()))
                .and(contTitle(params.getTitleCont()));
    }

    public Specification<Product> withCategoryId(Long categoryId) {
        return ((root, query, cb) -> categoryId == null ? cb.conjunction() :
                cb.equal(root.get("category").get("id"), categoryId));
    }

    public Specification<Product> greaterThan(Integer priceLt) {
        return ((root, query, cb) -> priceLt == null ? cb.conjunction() :
                cb.greaterThan(root.get("price"), priceLt));
    }

    public Specification<Product> lessThan(Integer priceGt) {
        return ((root, query, cb) -> priceGt == null ? cb.conjunction() :
                cb.lessThan(root.get("price"), priceGt));
    }

    public Specification<Product> rateGreaterThan(Double ratingGt) {
        return ((root, query, cb) -> ratingGt == null ? cb.conjunction() :
                cb.greaterThan(root.get("rating"), ratingGt));
    }

    public Specification<Product> contTitle(String titleCont) {
        return ((root, query, cb) -> titleCont == null ? cb.conjunction() :
                cb.like(root.get("title"), titleCont.toLowerCase()));
    }
}
// END
