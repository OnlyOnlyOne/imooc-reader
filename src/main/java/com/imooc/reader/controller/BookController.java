package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Category;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;

    /*
     * 显示页面
     * */
    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("/index");
        List<Category> categoryList = categoryService.selectAll();
        //将结果放入mav
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * 分裂查询图书列表
     *
     * @param p the p
     * @return the page
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Integer p, Long categoryId, String order) {
        if (p == null) {
            p = 1;
        }
//        IPage<Book> pageObject = bookService.paging(p, 10);
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }


}
