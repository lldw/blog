package com.wle.blog.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wle.blog.mapper.CommentMapper;
import com.wle.blog.mapper.SysUserMapper;
import com.wle.blog.pojo.Comment;
import com.wle.blog.service.CommentsService;
import com.wle.blog.service.SysUserService;
import com.wle.blog.vo.CommentVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result findCommentById(Long id) {
        LambdaQueryWrapper<Comment> querywapper = new LambdaQueryWrapper<>();
        querywapper.eq(Comment::getArticleId,id);
        querywapper.eq(Comment::getLevel, 1);
        List<Comment> commentsList = commentMapper.selectList(querywapper);
        List<CommentVo> commentVo=copyList(commentsList);
        return Result.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> commentsList) {
        ArrayList<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentsList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        //相同属性copy
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(comment.getId());
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level==1){
            Long commentId = comment.getId();
            List<CommentVo> commentVoList = findComentParentById(commentId);
            commentVo.setChildrens(commentVoList);
        }
        //给谁评论
        if (level>1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    private List<CommentVo> findComentParentById(Long commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,commentId);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        return copyList(commentList);
    }
}
