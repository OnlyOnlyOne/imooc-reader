package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.mappers.BookMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("BookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    /**
     * Paging page.
     *
     * @param page 页号
     * @param row  每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Integer page, Integer row) {
        Page<Book> p = new Page<Book>(page, row);
        IPage<Book> pageObject = bookMapper.selectPage(p, new QueryWrapper<Book>());
        return pageObject;
    }
}
