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
     * @param page 页号
     * @param row  每页记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Integer page, Integer row);
}
