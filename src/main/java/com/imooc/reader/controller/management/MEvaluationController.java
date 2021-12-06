package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mappers.MemberMapper;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import com.sun.prism.impl.Disposer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation") //与前台功能相区分
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;
    @Resource
    private BookService bookService;

    @GetMapping("/evalist")
    @ResponseBody
    public Map descriptionList(Integer page, Integer limit) {

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 20;
        }
        IPage<Evaluation> pageObject = evaluationService.paging(null, null, page, limit);
        //直接返回layui是无法识别的
        ArrayList<Evaluation> list = new ArrayList();
        list = (ArrayList<Evaluation>) pageObject.getRecords();

        for (Evaluation eva : list) {
            //bookName
            Book book = bookService.selectById(eva.getBookId());
            //memberName
            Member member = memberService.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        pageObject.setRecords(list);

        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());
        result.put("count", pageObject.getTotal());
        return result;
    }

    @GetMapping("/description.html")
    public ModelAndView showDescription() {
        return new ModelAndView("/management/description");
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId, String reason) {
        Evaluation evaluation = evaluationService.selectById(evaluationId);
        evaluation.setDisableReason(reason);
        evaluation.setState("disable");
        evaluation.setDisableTime(new Date());
        evaluation = evaluationService.updateEvaluation(evaluation);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", evaluation);
        return result;

    }
}
