package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book") //与前台功能相区分
public class MBookController {
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }


    /**
     * 文件上传
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        //得到上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        //文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            //在设置图片的url的时候遇到了麻烦，这个时候引入了jsoup，一个处理html片段的组件
            Document doc = Jsoup.parse(book.getDescription());//解析图书详情
            Element img = doc.select("img").first();//获取图书详情第一图的元素对象
            String cover = img.attr("src");
            book.setCover(cover);
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        //直接返回layui是无法识别的
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());
        result.put("count", pageObject.getTotal());
        return result;
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map SelectById(@PathVariable("id") Long bookId) {
        Book book = bookService.selectById(bookId);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map result = new HashMap();
        try {
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

//    @GetMapping("/evalist")
//    @ResponseBody
//    public Map descriptionList(Integer page, Integer limit) {
//
//        if (page == null) {
//            page = 1;
//        }
//        if (limit == null) {
//            limit = 20;
//        }
//        IPage<Evaluation> pageObject = evaluationService.paging(null, null, page, limit);
//        //直接返回layui是无法识别的
//
//        Map result = new HashMap();
//        result.put("code", "0");
//        result.put("msg", "success");
//        result.put("data", pageObject.getRecords());
//        result.put("count", pageObject.getTotal());
//        return result;
//    }
}
