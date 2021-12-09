package com.cx.springboot.service.Impl;

import com.cx.springboot.dao.MessageMapper;
import com.cx.springboot.entity.Message;
import com.cx.springboot.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceImplTest {


    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void findConversationCount() {
        int userId = 101;
        int result = messageMapper.selectConversationCount(userId);
        System.out.println(result);
    }
    @Test
    public void findConversations(){
        List<Message> conversationList = messageMapper.selectConversations(101,0,20);
        System.out.println(conversationList);
    }

    @Test
    public void findLettles(){
        List<Message> messages = messageMapper.selectLetters("101_102",0,20);
        System.out.println(messages);
    }

    @Test
    public void findById(){
        Message message = messageMapper.selectById(51);
        System.out.println(message);
    }
}