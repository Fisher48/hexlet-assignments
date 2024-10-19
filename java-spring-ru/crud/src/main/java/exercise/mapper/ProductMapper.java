package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// BEGIN
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Mapping(target = "category.id", source = "categoryId")
    public abstract Product map(ProductCreateDTO createDTO);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    public abstract ProductDTO map(Product product);

    @Mapping(target = "category", source = "categoryId",qualifiedByName = "getCategoryById")
    public abstract void update(ProductUpdateDTO updateDTO, @MappingTarget Product model);

    @Named("getCategoryById")
    public Category getCategoryById(Long id) {
        if (id == null) {
            return null;
        } else {
            return categoryRepository.findById(id).orElse(null);
        }
    }


}
// END
