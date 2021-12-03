package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mappers.BookMapper;
import com.imooc.reader.mappers.EvaluationMapper;
import com.imooc.reader.mappers.MemberMapper;
import com.imooc.reader.mappers.MemberReadStateMapper;
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
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * Paging page.
     *
     * @param page 页号
     * @param row  每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer row) {
        Page<Book> p = new Page<Book>(page, row);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();


        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);

        }
        if (order != null) {
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    /**
     * 根据图书id选择图书对象.
     *
     * @param bookId the book id
     * @return the book
     */
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    @Transactional
    @Override
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * Create book book.
     * 创建新的图书
     *
     * @param book the book
     * @return the book
     */
    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * Update book book.
     * 更新图书
     *
     * @param book the book 新图书数据
     * @return the book 更新后的数据
     */
    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * Delete book.
     * 删除图书以及相关数据
     * 影响到了多张表
     *
     * @param bookId the book id
     */
    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<>();
        mrsQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
