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

    /**
     * Update evaluation. 更新图书评分/评价数量
     */
    public void updateEvaluation();

    /**
     * Create book book.
     * 创建新的图书
     *
     * @param book the book
     * @return the book
     */
    public Book createBook(Book book);

    /**
     * Update book book.
     * 更新图书
     *
     * @param book the book 新图书数据
     * @return the book 更新后的数据
     */
    public Book updateBook(Book book);

    /**
     * Delete book.
     * 删除图书以及相关数据
     *
     * @param bookId the book id
     */
    public void deleteBook(Long bookId);
}
