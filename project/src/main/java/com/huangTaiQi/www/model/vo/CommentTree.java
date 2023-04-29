package com.huangTaiQi.www.model.vo;


import com.huangTaiQi.www.model.entity.CommentEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 14629
 */
public  class CommentTree {
    /**
     * 一级评论树根集合
     */
    private final List<CommentTreeNode> roots=new ArrayList<>();

    public void setRoot(CommentTreeNode root) {
        this.roots.add(root);
    }

    public List<CommentTreeNode> createCommentTree(List<CommentEntity> commentList){
        //根据topId分组
        Map<Long, List<CommentEntity>> longListMap = groupCommentsByTid(commentList);
        for (Map.Entry<Long, List<CommentEntity>> longListEntry : longListMap.entrySet()) {
            //生成评论树
            CommentTreeNode commentTreeNode = buildCommentTree(longListEntry.getValue());
            setRoot(commentTreeNode);
        }
        return roots;
    }

    private Map<Long, List<CommentEntity>> groupCommentsByTid(List<CommentEntity> commentList) {
        return commentList.stream().collect(Collectors.groupingBy(CommentEntity::getTopId));
    }

    private CommentTreeNode buildCommentTree(List<CommentEntity> commentList) {
        // 用一个Map来存储评论节点，key为评论id，value为该评论节点
        Map<Long, CommentTreeNode> commentMap = new HashMap<>();
        CommentTreeNode root = null;
        for (CommentEntity commentEntity : commentList) {
            commentMap.put(commentEntity.getId(),new CommentTreeNode(commentEntity));
        }
        // 将所有评论按照pid分类存储在Map中
        for (CommentEntity comment : commentList) {
            CommentTreeNode node = commentMap.get(comment.getId());
            if (comment.getPid() == 0) {
                // 如果pid为0，则说明该评论是一级评论，即根节点
                root = node;
            } else {
                // 否则为子评论，将其添加到父评论的children中
                CommentTreeNode parent = commentMap.get(comment.getPid());
                parent.addChild(node);
            }
        }
        return root;
    }
}