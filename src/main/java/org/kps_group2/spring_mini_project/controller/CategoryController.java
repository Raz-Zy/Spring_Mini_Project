package org.kps_group2.spring_mini_project.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.ApiResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;
import org.kps_group2.spring_mini_project.service.categories.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public  ResponseEntity<?>findAllCategory(@RequestParam(defaultValue = "1") @Positive Integer offset, @RequestParam(defaultValue = "5") @Positive Integer limit){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "All categories have been successfully founded.",
                        categoryService.findAllCategory(offset,limit),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );

    }
    @GetMapping("{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "The category has been successfully founded.",
                        categoryService.findCategoryById(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "The category has been successfully removed.",
                        categoryService.deleteCategoryById(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @PostMapping
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "The category has been successfully created.",
                        categoryService.insertCategory(categoryRequest),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateRequest categoryRequest){
       CategoryResponse categoryResponse = categoryService.updateCategoryByID(id,categoryRequest);
       return ResponseEntity.status(HttpStatus.OK).body(
               new ApiResponse<>(
                       "The category has been successfully updated.",
                       categoryResponse,
                       HttpStatus.OK,
                       LocalDateTime.now()
               )
       );
    }
}
