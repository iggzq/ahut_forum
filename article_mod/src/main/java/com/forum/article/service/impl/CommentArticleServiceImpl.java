package com.forum.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.CommentArticle;
import com.forum.article.mapper.CommentArticleMapper;
import com.forum.article.service.CommentArticleService;
import com.forum.article.vo.CommentArticleVO;
import com.forum.article.vo.ReplyListVO;
import com.forum.article.vo.ReplyVO;
import com.forum.article.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.forum.article.constants.Constants.ARTICLE_COMMENTS_REDIS_PRE_KEY;

/**
 * <p>
 * 文章评论 服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-21 09:44:10
 */
@Service
public class CommentArticleServiceImpl extends ServiceImpl<CommentArticleMapper, CommentArticle> implements CommentArticleService {

    @Resource
    private CommentArticleMapper commentArticleMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<CommentArticleVO> getCommentsById(String id) {
        List<CommentArticleVO> commentArticleVOS = new ArrayList<>();
        List<CommentArticle> commentArticles = new ArrayList<>();
        if (Objects.isNull(redisTemplate.opsForValue().get(ARTICLE_COMMENTS_REDIS_PRE_KEY + id))) {
            LambdaQueryWrapper<CommentArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CommentArticle::getArticleId, id);
            commentArticles = commentArticleMapper.selectList(lambdaQueryWrapper);
            redisTemplate.opsForValue().set(ARTICLE_COMMENTS_REDIS_PRE_KEY + id, commentArticles);
        } else {
            List<LinkedHashMap<String, String>> linkedHashMaps = (List<LinkedHashMap<String, String>>) redisTemplate.opsForValue().get("comment:articleId:" + id);
            commentArticles = linkedHashMaps.stream().map(linkedHashMap -> {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String create_time = linkedHashMap.get("createTime");
                String update_time = linkedHashMap.get("updateTime");
                Date createTime = null;
                Date updateTime = null;
                try {
                    createTime = simpleDateFormat.parse(create_time);
                    updateTime = simpleDateFormat.parse(update_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CommentArticle commentArticle = new CommentArticle();
                commentArticle.setId(Long.valueOf(linkedHashMap.get("id")));
                commentArticle.setArticleId(Long.valueOf(linkedHashMap.get("articleId")));
                commentArticle.setUid(Long.valueOf(linkedHashMap.get("uid")));
                commentArticle.setToUserId(linkedHashMap.get("toUserId") != null ? Long.valueOf(linkedHashMap.get("toUserId")) : null);
                commentArticle.setUsername(linkedHashMap.get("username"));
                commentArticle.setContent(linkedHashMap.get("content"));
                commentArticle.setParentId(linkedHashMap.get("parentId") != null ? Long.valueOf(linkedHashMap.get("parentId")) : null);
                commentArticle.setStatus(Byte.valueOf(linkedHashMap.get("status")));
                commentArticle.setCreateTime(createTime);
                commentArticle.setUpdateTime(updateTime);
                commentArticle.setLikes(Integer.valueOf(linkedHashMap.get("likes")));
                return commentArticle;
            }).toList();
        }
        List<CommentArticle> fatherCommentsArticle = new ArrayList<>();
        List<CommentArticle> sonCommentsArticle = new ArrayList<>();
        commentArticles.forEach(commentArticle -> {
            if (commentArticle.getParentId() == null) {
                fatherCommentsArticle.add(commentArticle);
            } else {
                sonCommentsArticle.add(commentArticle);
            }
        });
        for (CommentArticle fatherCommentArticle : fatherCommentsArticle) {
            CommentArticleVO commentArticleVO = new CommentArticleVO();
            UserVO userVO = new UserVO();
            List<ReplyVO> list = new ArrayList<>();
            ReplyListVO reply = new ReplyListVO();
            userVO.setUsername(fatherCommentArticle.getUsername());
            commentArticleVO.setUser(userVO);
            BeanUtils.copyProperties(fatherCommentArticle, commentArticleVO);
            for (CommentArticle sonCommentArticle : sonCommentsArticle) {
                if (Objects.equals(sonCommentArticle.getParentId(), fatherCommentArticle.getId())) {
                    ReplyVO replyVO = new ReplyVO();
                    BeanUtils.copyProperties(sonCommentArticle, replyVO);
                    UserVO userVO1 = new UserVO();
                    userVO1.setUsername(sonCommentArticle.getUsername());
                    replyVO.setUser(userVO1);
                    list.add(replyVO);
                }
            }
            reply.setList(list);
            reply.setTotal(list.size());
            commentArticleVO.setReply(reply);
            commentArticleVOS.add(commentArticleVO);
        }
        return commentArticleVOS;
    }
}
