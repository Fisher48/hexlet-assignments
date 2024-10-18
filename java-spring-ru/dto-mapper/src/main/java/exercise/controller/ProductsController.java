package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping("")
    public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return productMapper.index(products);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable long id) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found"));

        return productMapper.map(product);
    }


    @PutMapping("/{id}")
    public ProductDTO update(@RequestBody @Validated ProductUpdateDTO productData,
                             @PathVariable long id) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found"));
        productMapper.update(productData, product);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO productData) {
        // Преобразование в сущность
        var product = productMapper.map(productData);
        productRepository.save(product);

        // Преобразование в DTO
        return productMapper.map(product);
    }
    // END
}
