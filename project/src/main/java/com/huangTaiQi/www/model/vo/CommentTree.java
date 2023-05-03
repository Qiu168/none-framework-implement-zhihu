package com.huangTaiQi.www.model.vo;


import com.huangTaiQi.www.model.entity.CommentEntity;

import java.util.*;
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

    public List<CommentTreeNode> createCommentTree(List<CommentEntity> commentList,String sortOrder){
        //根据topId分组
        Map<Long, List<CommentEntity>> longListMap = groupCommentsByTid(commentList);
        for (Map.Entry<Long, List<CommentEntity>> longListEntry : longListMap.entrySet()) {
            // 排序并生成评论树
            List<CommentEntity> sortedComments;
            // 如果前端要求倒序
            if ("DESC".equalsIgnoreCase(sortOrder)) {
                sortedComments = longListEntry.getValue().stream()
                        .sorted(Comparator.comparing(CommentEntity::getCommentTime).reversed())
                        .collect(Collectors.toList());
            } else { // 否则按时间正序排序
                sortedComments = longListEntry.getValue().stream()
                        .sorted(Comparator.comparing(CommentEntity::getCommentTime))
                        .collect(Collectors.toList());
            }
            CommentTreeNode commentTreeNode = buildCommentTree(sortedComments);
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