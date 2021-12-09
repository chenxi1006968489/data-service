package com.cx.springboot.listen;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.cx.springboot.common.Const;
import com.cx.springboot.entity.Message;
import com.cx.springboot.service.IMessageService;
import com.cx.springboot.util.CanalUtil;
import com.cx.springboot.util.JSONUtil;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */

@Service
@CanalEventListener
public class CanalListen {
    @Autowired
    private IMessageService iMessageService;
    //自定义监听
    @ListenPoint(
            eventType = {CanalEntry.EventType.INSERT},   // 监听的事件类型
            schema = {"eartquakeinfo"},      // 数据库
            table = {"info"},         //表
            destination = "example"     //指向  canal客户端 的 example配置
    )
    public void onEventMyListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//        System.out.println(rowData);
        Map<String, String> afterMap = CanalUtil.convertToMap(rowData.getAfterColumnsList());
        String message =  JSON.toJSONString(afterMap);


        System.out.println(("地震数据更新："+JSON.toJSONString(afterMap)));

      String result = sendLettle("102",message);
        System.out.println(result);
    }

    public String sendLettle(String toName,String content){
        // 检测输入内容是否为空
        if (StringUtils.isBlank(toName) || StringUtils.isBlank(content)) {
            return JSONUtil.getJSONString(2, "用户名或内容不能为空");
        }
        int userId = 201;
        int sourceId = 102;
        Message message = new Message();
        message.setFromId(userId);
        message.setToId(sourceId);
        message.setStatus(Const.status.UNREAD);
        message.setContent(content);
        message.setCreateTime(new Date());
        message.setConversationId(message.getFromId() + "_" + message.getToId());

        iMessageService.addMessage(message);

        return "success";
    }

}