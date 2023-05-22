package dev.marvin.shop.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "categories";
    }

    @GetMapping("/categories/create")
    public String createCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "createCategory";
    }

    @PostMapping("/categories/create")
    public String createCategory(@ModelAttribute("category") @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return "createCategory";
        }
        categoryService.createCategory(category);
        log.info("Category Created : {}", category);
        return "redirect:/categories";
    }

    @RequestMapping(path = "/categories/delete/{categoryId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteCategoryItem(@PathVariable("categoryId") Long id) {
        categoryService.removeCategoryById(id);
        return "redirect:/categories";
    }


    @GetMapping("/categories/update/{categoryId}")
    public String updateCategoryPage(@PathVariable("categoryId") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "updateCategory";
    }


    @PostMapping("/categories/update/{categoryId}")
    public String updateCategory(@ModelAttribute("category") @Valid Category categoryUpdateRequest, @PathVariable("categoryId") Long id) {
        Category category = categoryService.getCategoryById(id);

        category.setName(categoryUpdateRequest.getName());

        categoryService.updateCategory(category);

        return "redirect:/categories";
    }
}
