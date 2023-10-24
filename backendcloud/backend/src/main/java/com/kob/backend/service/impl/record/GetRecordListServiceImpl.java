package com.kob.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.snakeRecord;
import com.kob.backend.pojo.User;
import com.kob.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getList(Integer page) {
        IPage<snakeRecord> recordIpage = new Page<>(page,10);
        QueryWrapper<snakeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<snakeRecord> snakeRecords = recordMapper.selectPage(recordIpage,queryWrapper).getRecords();

        JSONObject resp = new JSONObject();
        List<JSONObject> items = new LinkedList<>();
        for(snakeRecord snakeRecord : snakeRecords){
            User userA = userMapper.selectById(snakeRecord.getAId());
            User userB = userMapper.selectById(snakeRecord.getBId());

            JSONObject item = new JSONObject();
            String aPhoto = (userA != null) ? userA.getPhoto() : null;
            String aUsername = (userA != null) ? userA.getUsername() : null;
            item.put("a_photo", aPhoto);
            item.put("a_username", aUsername);
            String bPhoto = (userB != null) ? userB.getPhoto() : null;
            String bUsername = (userB != null) ? userB.getUsername() : null;
            item.put("b_photo", bPhoto);
            item.put("b_username", bUsername);
            String result = "Draw";
            if("A".equals(snakeRecord.getLoser())){
                result = "B is Winner";
            }
            else if("B".equals(snakeRecord.getLoser())){
                result = "A is Winner";
            }
            item.put("result", result);
            item.put("record", snakeRecord);
            items.add(item);
        }
        resp.put("records", items);
        resp.put("records_count", recordMapper.selectCount(null));
        return resp;
    }

}
