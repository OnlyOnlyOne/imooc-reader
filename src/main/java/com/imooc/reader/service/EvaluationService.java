package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    public List<Evaluation> selectByBookId(Long bookId);

    public IPage<Evaluation> paging(Long evalutionId, String order, Integer page, Integer row);

    public Evaluation selectById(Long evalutionId);

    public Evaluation updateEvaluation(Evaluation evaluation);
}
