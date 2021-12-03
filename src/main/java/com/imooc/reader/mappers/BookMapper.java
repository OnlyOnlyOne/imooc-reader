package com.imooc.reader.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    /**
     * Update evaluation.
     * 更新图书评分/评价数量
     */
    public void updateEvaluation();
}
