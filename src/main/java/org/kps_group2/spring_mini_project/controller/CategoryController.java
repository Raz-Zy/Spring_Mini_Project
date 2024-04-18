package org.kps_group2.spring_mini_project.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.kps_group2.spring_mini_project.model.appUserModel.Request.AppUserRequest;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.ApiResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;
import org.kps_group2.spring_mini_project.service.categories.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public  ResponseEntity<?>findAllCategory(@RequestParam(defaultValue = "1")Integer offset,@RequestParam(defaultValue = "5")Integer limit){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "successfully",
                        categoryService.findAllCategory(offset,limit),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );

    }
    @GetMapping("{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Get category by id: " + id,
                        categoryService.findCategoryById(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "succesfull",
                        categoryService.deleteCategoryById(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @PostMapping("post")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = categoryService.insertCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "successfull",
                        categoryResponse,
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateCategory(@PathVariable Integer id, @RequestBody @Valid CategoryUpdateRequest categoryRequest){
       CategoryResponse categoryResponse = categoryService.updateCategoryByID(id,categoryRequest);
       return ResponseEntity.status(HttpStatus.OK).body(
               new ApiResponse<>(
                       "successfull",
                       categoryResponse,
                       HttpStatus.OK,
                       LocalDateTime.now()
               )
       );
    }

}
