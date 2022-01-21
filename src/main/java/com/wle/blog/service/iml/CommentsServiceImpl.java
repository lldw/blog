package com.wle.blog.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wle.blog.mapper.CommentMapper;
import com.wle.blog.mapper.SysUserMapper;
import com.wle.blog.pojo.Comment;
import com.wle.blog.pojo.SysUser;
import com.wle.blog.service.CommentsService;
import com.wle.blog.service.SysUserService;
import com.wle.blog.utils.UserThreadLocal;
import com.wle.blog.vo.CommentVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.UserVo;
import com.wle.blog.vo.params.CommentParams;
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

    /**
     * 获取评论
     *
     * @param id
     * @return
     */
    @Override
    public List<CommentVo> findCommentById(Long id) {
        LambdaQueryWrapper<Comment> querywapper = new LambdaQueryWrapper<>();
        querywapper.eq(Comment::getArticleId, id);
        querywapper.eq(Comment::getLevel, 1);
        List<Comment> commentsList = commentMapper.selectList(querywapper);
        List<CommentVo> commentVo = copyList(commentsList);
        return commentVo;
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
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(comment.getId());
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long commentId = comment.getId();
            List<CommentVo> commentVoList = findComentParentById(commentId);
            commentVo.setChildrens(commentVoList);
        }
        //给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    private List<CommentVo> findComentParentById(Long commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, commentId);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        return copyList(commentList);
    }

    /**
     * 评论功能
     *
     * @param commentParams
     * @return
     */
    @Override
    public Result comment(CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setContent(commentParams.getContent());
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }
}
