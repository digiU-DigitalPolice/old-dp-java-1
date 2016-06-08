package ua.in.zloch.converters;

import org.junit.Test;
import ua.in.zloch.dto.CategoryDTO;
import ua.in.zloch.entity.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CategoryToCategoryDtoConverterTest {

    private CategoryToCategoryDtoConverter categoryToCategoryDtoConverter = new CategoryToCategoryDtoConverter();

    @Test
    public void testConvert() throws Exception {
        CategoryDTO categoryDTO = categoryToCategoryDtoConverter.convert(createTestCategoryWithParent());

        assertNotNull(categoryDTO);

        assertEquals("DTP", categoryDTO.getTitle());
        assertEquals(new Long(1), categoryDTO.getId());
        assertEquals(new Long(2), categoryDTO.getParentId());
    }

    @Test
    public void testCategoryWithNullParent(){
        CategoryDTO categoryDTO = categoryToCategoryDtoConverter.convert(createTestCategory());

        assertNull(categoryDTO.getParentId());
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setId(1l);
        category.setTitle("DTP");
        return category;
    }

    private Category createTestCategoryWithParent() {
        Category category = createTestCategory();
        Category parent = new Category();
        parent.setId(2l);
        category.setParent(parent);
        return category;
    }

}