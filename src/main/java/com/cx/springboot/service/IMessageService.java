package com.cx.springboot.service;

import com.cx.springboot.entity.Message;

import java.util.List;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */
public interface IMessageService {
    int findConversationCount(int id);

    List<Message> findConversations(int userId, int offset, int limit);

    int findLetterCount(String conversationId);

    int findLetterUnreadCount(int userId, String conversationId);

    List<Message> findLetters(String conversationId, int offset, int limit);

    int readMessage(List<Integer> ids);

    int addMessage(Message message);

    Message findLatestNotice(int userId, String topic);

    Message findById(int Id);

    int findNoticeCount(int userId, String topic);

    int findNoticeUnreadCount(int userId, String topic);

    List<Message> findNotices(int userId, String topic, int offset, int limit);
}
