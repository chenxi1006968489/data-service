package com.cx.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.cx.springboot.common.Const;
import com.cx.springboot.common.Page;
import com.cx.springboot.entity.Message;
import com.cx.springboot.service.IMessageService;
import com.cx.springboot.util.HostHolder;
import com.cx.springboot.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sound.midi.Soundbank;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */

@Controller

public class NoticeController {

    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private HostHolder hostHolder;


    // 私信列表
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)

    public String getLetterList(Model model, Page page) {
        int userId = 102;
        // 设置分页信息
        page.setLimit(15);

        page.setPath("/letter/list");
        // 查询有多少条会话
        page.setRows(iMessageService.findConversationCount(userId));
        // conversationList返回的是根据消费ID的数据列
        List<Message> conversationList = iMessageService.findConversations(userId, page.getOffset(), page.getLimit());
        System.out.println(conversationList);
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("letterCount", iMessageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount", iMessageService.findLetterUnreadCount(userId, message.getConversationId()));
                int targetId = userId == message.getFromId() ? message.getToId() : message.getFromId();
                // 永远只显示对话双方的另一方
                String messageSender = "地震台网数据更新";
                map.put("target", messageSender); // 显示消息来源
                map.put("conversation", message);

//[Message[id = 4fromId = 101toId = 102conversationId = 101_102status = 0createTime = Sun Nov 21 22:13:17 CST 2021content = 22]
                // 消息显示内容转换

                String result = message.getContent();

                System.out.println(result);

                JSONObject obj = JSONUtil.stringToJson(result);

                System.out.println("json:" + obj);

                String content = obj.getString("epicenter") + "发生" + obj.getString("num_mag") + "级地震";
                map.put("content", content);

                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);


        // 查询总的未读消息数量
        // 私信总未读数量
        int letterUnreadCount = iMessageService.findLetterUnreadCount(userId, null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);
        // 系统通知未读数量
        int noticeUnreadCount = iMessageService.findNoticeUnreadCount(userId, null);
        model.addAttribute("noticeUnreadCount", noticeUnreadCount);

//        return null;
        return "site/sxlettle";
    }

    // 会话详情页展示

    @RequestMapping(path = "/letter/detail/{conversationId}", method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Model model, Page page) {

        // 分页信息
        page.setLimit(20);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(iMessageService.findLetterCount(conversationId));
        String messageSender = "地震台网数据更新";
        int userId = 102;
//[LettleList [id = 4fromId = 101toId = 102conversationId = 101_102status = 0createTime = Sun Nov 21 22:13:17 CST 2021content = 22,
        List<Message> letterList = iMessageService.findLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>();
                map.put("fromUser", messageSender);
                map.put("letter", message);
                // 地震时间转换
                JSONObject obj2 = JSONUtil.stringToJson(message.getContent());
                String createTime = obj2.get("orig_time").toString();
//                System.out.println(createTime);
                map.put("createTime", createTime);
//                System.out.println(map.get("content").getClass());  // string
                // 地震内容转换
                String content = obj2.getString("epicenter") + "发生" + obj2.getString("num_mag") + "级地震";
                map.put("content", content);
                letters.add(map);
            }
        }
        model.addAttribute("letters", letters);
        model.addAttribute("source", messageSender);

        // 私信目标
        model.addAttribute("target", "应急通知平台");
    /*    // 设置已读
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()) {
            iMessageService.readMessage(ids);
        }*/
        int letterUnreadCount = iMessageService.findLetterUnreadCount(userId, null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);
        // 未读系统通知数量
        int noticeUnreadCount = iMessageService.findNoticeUnreadCount(userId, null);
        model.addAttribute("noticeUnreadCount", noticeUnreadCount);

        return "site/sxlettle-detail";

    }

    @RequestMapping(path = "/event/{conversationId}", method = RequestMethod.GET)
    public String getContent(@PathVariable("conversationId") String conversationId, Model model, Page page) {

        page.setLimit(15);
        page.setPath("/event" + conversationId);
        List<Message> letterList = iMessageService.findLetters(conversationId, page.getOffset(), page.getLimit());
//            System.out.println(letterList);
        String messageSender = "地震台网数据更新";
        List<Map<String, Object>> letters = new ArrayList<>();
        for (Message message : letterList) {
            Map<String, Object> map = new HashMap<>();
            map.put("fromUser", messageSender);
            map.put("letter", message);
            map.put("id", message.getId());

            // 地震时间转换
            JSONObject obj = JSONUtil.stringToJson(message.getContent());
            String createTime = obj.get("orig_time").toString();
//                System.out.println(createTime);
            map.put("createTime", createTime);
//                System.out.println(map.get("content").getClass());  // string
            // 地震内容转换
            String content = obj.getString("epicenter") + "发生" + obj.getString("num_mag") + "级地震";
            map.put("content", content);
            letters.add(map);
        }
        model.addAttribute("event", letters);
        System.out.println(letters);

        return "site/event";
    }


/*    @RequestMapping(path = "/letter/list2/pageTest", method = RequestMethod.GET)
    public String LetterController(Model model){
        return "site/lettle";
    }*/

/*    @RequestMapping(path = "/letter/detail", method = RequestMethod.GET)
    public String LetterDetailController(Model model){
        return "/site/letter-detail";
    }*/


    @RequestMapping(path = "/notice/list", method = RequestMethod.GET)
    public String noticeController() {
        return "site/notice";
    }

    // 拿到所有未读私信的id
    private List<Integer> getLetterIds(List<Message> letterList) {
        List<Integer> ids = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                if (message.getToId().equals(hostHolder.getUser().getId()) && message.getStatus() == Const.status.UNREAD) {
                    ids.add(message.getId());
                }
            }
        }
        return ids;
    }

    @RequestMapping( path= "/event/report/{Id}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("Id") int Id, Model model){
        Message message = iMessageService.findById(Id);

        String result = message.getContent();

        JSONObject container = JSONUtil.stringToJson(result);

        model.addAttribute("content", container);

        System.out.println(result);

        System.out.println(container);

        return "/site/report";
    }


}
