package com.huangTaiQi.www.model.vo;

import com.huangTaiQi.www.model.entity.CommentEntity;



import java.util.ArrayList;
import java.util.List;


/**
 * @author 14629
 */
public class CommentTreeNode {
    private Long id;
    private String content;
    private String username;
    private String avatar;
    private Long commentTime;
    private List<CommentTreeNode> children;


    /**
     * 构造函数
     */
    public CommentTreeNode() {
    }
    public CommentTreeNode(Long id, String content,String username,String avatar,Long commentTime) {
        this.username=username;
        this.commentTime=commentTime;
        this.avatar=avatar;
        this.id = id;
        this.content = content;
        this.children = new ArrayList<>();
    }

    public CommentTreeNode(CommentEntity comment) {
        this.avatar=comment.getAvatar();
        this.username=comment.getUsername();
        this.content=comment.getContent();
        this.id=comment.getId();
        this.commentTime=comment.getCommentTime();
        this.children=new ArrayList<>();
    }

    /**
     * 添加子节点
      */

    public void addChild(CommentTreeNode node) {
        this.children.add(node);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<CommentTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<CommentTreeNode> children) {
        this.children = children;
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }
}

