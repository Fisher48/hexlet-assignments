package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    // GET /products – просмотр списка всех товаров
    @GetMapping("")
    public List<ProductDTO> index() {
       var products = productRepository.findAll();
       return products.stream()
               .map(productMapper::map)
               .toList();
    }

    // GET /products/{id} – просмотр конкретного товара
    @GetMapping("/{id}")
    public ProductDTO show(@PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return productMapper.map(product);
    }

    // POST /products – добавление нового товара.
    // При указании id несуществующей категории должен вернуться ответ с кодом 400 Bad request
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO createDTO) {
        var product = productMapper.map(createDTO);
        var dto = productRepository.save(product);
        return productMapper.map(dto);
    }

    // PUT /products/{id} – редактирование товара.
    // При редактировании мы должны иметь возможность поменять название, цену и категорию товара.
    // При указании id несуществующей категории также должен вернуться ответ с кодом 400 Bad request
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO updateDTO) {
       var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
       var category = categoryRepository.findById(product.getId());
       productMapper.update(updateDTO, product);
       product.setCategory(category.get());
       product.setTitle(product.getTitle());
       product.setPrice(product.getPrice());
       productRepository.save(product);
       return productMapper.map(product);
    }


    // DELETE /products/{id} – удаление товара
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    // END
}
