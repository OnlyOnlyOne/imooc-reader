package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mappers.EvaluationMapper;
import com.imooc.reader.mappers.MemberMapper;
import com.imooc.reader.mappers.MemberReadStateMapper;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * Create member member.
     * 会员注册，创建新会员
     *
     * @param username the username 用户名
     * @param password the password 密码
     * @param nickname the nickname 昵称
     * @return the member 新会员对象
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        // 判断用户是否存在
        if (memberList.size() > 0) {
            throw new BussinessException("M01", "用户名已存在");

        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000) + 1000;
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    /**
     * Check login member.
     * 登录检查
     *
     * @param username the username
     * @param password the password
     * @return the member
     */
    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new BussinessException("M02", "用户不存在");
        }
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if (!md5.equals(member.getPassword())) {
            throw new BussinessException("M03", "输入密码有误");
        }
        return member;
    }

    /**
     * Select member read state member read state.
     * 获取阅读状态
     *
     * @param memberId the member id
     * @param bookId   the book id
     * @return the member read state
     */
    //调用的时机在显示detail的时候就可以调用
    //告诉spring当前方法不用事务
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

    /**
     * Update member read state member read state.
     * 更新阅读状态
     *
     * @param memberId  the member id
     * @param bookId    the book id
     * @param readState the read state
     * @return the member read state
     */
    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        //无则新增，有则更新
        if (memberReadState == null) {
            memberReadState = new MemberReadState();
            memberReadState.setReadState(readState);
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        } else {
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }

    /**
     * Evaluation evaluation.
     * 发布新的短评
     *
     * @param memberId the member id
     * @param bookId   the book id
     * @param score    the score
     * @param content  the content
     * @return the evaluation 短评对象
     */
    @Override
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    /**
     * Enjoy evaluation.
     * 短评点赞
     *
     * @param evaluationId the evaluation id
     * @return the evaluation 短评对象
     */
    @Override
    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
