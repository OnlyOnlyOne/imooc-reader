package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

/*
 * 图书服务
 * */
public interface BookService {
    /**
     * Paging page.
     *
     * @param categoryId 分类编号
     * @param order      排序方式
     * @param page       页号
     * @param row        每页记录数
     * @return 分页对象 page
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer row);

    /**
     * 根据图书id选择图书对象.
     *
     * @param bookId the book id
     * @return the book
     */
    Book selectById(Long bookId);
}
