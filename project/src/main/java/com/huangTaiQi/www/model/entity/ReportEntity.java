package com.huangTaiQi.www.model.entity;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class ReportEntity {
    private Long id;
    private Long userId;
    private Long messageId;
    private String type;
    private String content;
    private Long time;
    private Integer legal;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLegal() {
        return legal;
    }

    public void setLegal(Integer legal) {
        this.legal = legal;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
