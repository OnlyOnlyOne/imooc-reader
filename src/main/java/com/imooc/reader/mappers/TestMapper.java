package com.imooc.reader.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    //自动生成实例
    void insertSample();

}
