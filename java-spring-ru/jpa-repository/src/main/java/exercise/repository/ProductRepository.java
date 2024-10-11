package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceLessThanEqual(Integer price);
    List<Product> findByPriceGreaterThanEqual(Integer price);
    List<Product> findByPriceBetween(Integer price1, Integer price2);
    // END
}
