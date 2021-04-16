package com.example.mall.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mall.entity.Category;
import com.example.mall.entity.Product;
import com.example.mall.service.impl.CategoryServiceImpl;
import com.example.mall.service.impl.ProductServiceImpl;
import com.example.mall.utils.ResultMessage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 周瑜
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/mall/product")
@Api(tags = "商品信息")
public class ProductController {
    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @ApiOperation(value = "获取某一种类商品信息")
    @GetMapping("/getProductByCategoryId/{categoryId}")
    public ResultMessage getProductByCategoryId(@PathVariable Integer categoryId) {
        List<Product> list = productService.getProductByCategoryId(categoryId);
        resultMessage.success("001", list);
        return resultMessage;
    }

    @GetMapping("/getHotProduct")
    @ApiOperation(value = "获取热门商品信息")
    public ResultMessage getHotProduct() {
        List<Product> list = productService.getHotProduct();
        resultMessage.success("001", list);
        return resultMessage;

    }

    @GetMapping("/getProduct/{productId}")
    @ApiOperation(value = "获取某个商品具体信息")
    public ResultMessage getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        resultMessage.success("001", product);
        return resultMessage;
    }
    @GetMapping("/getProductByPage/{pageNum}/{pageSize}/{categoryId}")
    @ApiOperation(value = "分页查询商品信息")
    public Map<String, Object> getProductByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize,@PathVariable Integer categoryId) {
        PageInfo<Product> ipage = productService.getProductByPage(pageNum, pageSize, categoryId);
        resultMessage.success("001", ipage);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "001");
        map.put("data",ipage.getList());
        map.put("total", ipage.getTotal());
        return map;
    }
    @RequestMapping(value = "/getProductBySearch")
    public Map getProductBySearch(@RequestBody Map<String,Object> paramMap){
        String search = (String) paramMap.get("search");
        int pageNum = (int) paramMap.get("pageNum");
        int pageSize = (int) paramMap.get("pageSize");
        int offset = (pageNum-1)*pageSize;
        List<Category> categorys = categoryService.getCategoryAll();
        HashMap<String, Object> map = new HashMap<>();
        for(Category category:categorys){
            if(category.getCategoryName().equals(search)){
                List<Product> product = productService.getProductByCategory(category.getCategoryId(), offset, pageSize);
                map.put("code","001");
                map.put("Product",product);
                map.put("total",product.size());
                return map;
            }
        }
        List<Product> product = productService.getProductBySearch(search);
        map.put("code","001");
        map.put("Product",product);
        map.put("total",product.size());
        return map;
    }
}
