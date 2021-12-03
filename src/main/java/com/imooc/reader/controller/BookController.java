package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.*;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

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

    //    url绑定
    @GetMapping("/book/{id}")
//    @ResponseBody
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session) {//路径变量
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
        Member member = (Member) session.getAttribute("loginMember");
        ModelAndView mav = new ModelAndView("/detail");
        if (member != null) {
            //获取会员阅读状态
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), id);
            //还要在前台进行阅读状态的非空校验
            mav.addObject("memberReadState", memberReadState);
        }
        //跳转到/detail

        /*
         * 放入mav之后就可以在detail.ftl实时渲染了
         * */
        mav.addObject("book", book);
        mav.addObject("evaluationList", evaluationList);
        return mav;

    }
}
