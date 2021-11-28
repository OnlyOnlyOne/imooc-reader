package com.imooc.reader.service.impl;

import com.imooc.reader.entity.Category;
import com.imooc.reader.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceImplTest {
    @Resource
    private CategoryService categoryService;//在CategoryService上面的BeanId为CategoryService

    @Test
    public void selectAll() {
        List<Category> list = categoryService.selectAll();
        System.out.println(list);
        System.out.println("操作成功");
    }
}