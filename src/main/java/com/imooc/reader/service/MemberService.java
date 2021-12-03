package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mappers.MemberReadStateMapper;

public interface MemberService {

    /**
     * Create member member.
     * 会员注册，创建新会员
     *
     * @param username the username
     * @param password the password
     * @param nickname the nickname
     * @return the member
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * Check login member.
     * 登录检查
     *
     * @param username the username
     * @param password the password
     * @return the member
     */
    public Member checkLogin(String username, String password);

    /**
     * Select member read state member read state.
     * 获取阅读状态
     *
     * @param memberId the member id
     * @param bookId   the book id
     * @return the member read state
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    /**
     * Update member read state member read state.
     * 更新阅读状态
     *
     * @param memberId  the member id
     * @param bookId    the book id
     * @param readState the read state
     * @return the member read state
     */
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);

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
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content);

    /**
     * Enjoy evaluation.
     * 短评点赞
     *
     * @param evaluationId the evaluation id
     * @return the evaluation 短评对象
     */
    public Evaluation enjoy(Long evaluationId);
}
