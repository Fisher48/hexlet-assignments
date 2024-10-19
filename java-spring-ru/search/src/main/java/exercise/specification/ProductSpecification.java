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
                .and(betweenPrice(params.getPriceLt(), params.getPriceGt()))
                .and(contTitle(params.getTitleCont()));
    }

    public Specification<Product> withCategoryId(Long categoryId) {
        return ((root, query, cb) -> categoryId == null ? cb.conjunction() :
                cb.in(root.get("category").get("id")));
    }

    public Specification<Product> greaterThan(Integer price) {
        return ((root, query, cb) -> price == null ? cb.conjunction() :
                cb.greaterThan(root.get("priceBt"), price));
    }

    public Specification<Product> lessThan(Integer price) {
        return ((root, query, cb) -> price == null ? cb.conjunction() :
                cb.lessThan(root.get("priceLt"), price));
    }

    public Specification<Product> rateGreaterThan(Double rating) {
        return ((root, query, cb) -> rating == null ? cb.conjunction() :
                cb.greaterThan(root.get("ratingGt"), rating));
    }

    public Specification<Product> betweenPrice(Integer priceBt, Integer priceLt) {
        return ((root, query, cb) -> priceBt == null || priceLt == null ? cb.conjunction() :
                cb.between(root.get("priceLt"), priceLt, priceBt));
    }

    public Specification<Product> contTitle(String title) {
        return ((root, query, cb) -> title == null ? cb.conjunction() :
                cb.like(root.get("titleCont"), title));
    }
}
// END
