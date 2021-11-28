package com.imooc.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Test;
import com.imooc.reader.mappers.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    
    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("MyBatisPlus测试");
        testMapper.insert(test);
    }

    @org.junit.Test
    public void testUpdate() {
        Test test = testMapper.selectById(24);
        test.setContent("测试1");
        testMapper.updateById(test);
    }

    @org.junit.Test
    public void testDelete() {
        testMapper.deleteById(24);
    }

    @org.junit.Test
    public void testSelect() {
//        要传入一个特殊的包装对象
        QueryWrapper<Test> queryWrapper = new QueryWrapper<Test>();
//        queryWrapper.eq("id", 23);
        queryWrapper.gt("id", 20);
        List<Test> list = testMapper.selectList(queryWrapper);
        System.out.println(list.get(0));
    }

}

