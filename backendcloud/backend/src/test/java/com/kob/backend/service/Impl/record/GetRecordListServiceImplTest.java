package com.kob.backend.service.Impl.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.snakeRecord;
import com.kob.backend.pojo.User;

import com.kob.backend.service.impl.record.GetRecordListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

public class GetRecordListServiceImplTest {

    @Mock
    private RecordMapper recordMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private GetRecordListServiceImpl getRecordListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetList() {
        // Create mock data
        snakeRecord mockRecord = new snakeRecord();
        mockRecord.setAId(1);
        mockRecord.setBId(2);
        mockRecord.setLoser("A");

        User userA = new User();
        userA.setPhoto("photoA");
        userA.setUsername("userA");

        User userB = new User();
        userB.setPhoto("photoB");
        userB.setUsername("userB");

        Page<snakeRecord> mockPage = new Page<>(1, 10);
        mockPage.setRecords(Arrays.asList(mockRecord));

        // Setup mocks to return mock data
        when(recordMapper.selectPage(any(IPage.class), any(QueryWrapper.class))).thenReturn(mockPage);
        when(userMapper.selectById(1)).thenReturn(userA);
        when(userMapper.selectById(2)).thenReturn(userB);
        when(recordMapper.selectCount(null)).thenReturn(1L);

        // Call method to test
        JSONObject result = getRecordListService.getList(1);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getJSONArray("records").size());

        JSONObject recordResult = result.getJSONArray("records").getJSONObject(0);
        assertEquals("photoA", recordResult.getString("a_photo"));
        assertEquals("userA", recordResult.getString("a_username"));
        assertEquals("photoB", recordResult.getString("b_photo"));
        assertEquals("userB", recordResult.getString("b_username"));
    }

    @Test
    public void testGetListNoRecords() {
        Page<snakeRecord> emptyPage = new Page<>(1, 10);
        emptyPage.setRecords(Collections.emptyList());

        when(recordMapper.selectPage(any(IPage.class), any(QueryWrapper.class))).thenReturn(emptyPage);
        when(recordMapper.selectCount(null)).thenReturn(0L);

        JSONObject result = getRecordListService.getList(1);

        assertNotNull(result);
        assertEquals(0, result.getJSONArray("records").size());
    }

    @Test
    public void testGetListWithNullUser() {
        snakeRecord mockRecord = new snakeRecord();
        mockRecord.setAId(1);
        mockRecord.setBId(2);
        mockRecord.setLoser("A");

        User userB = new User();
        userB.setPhoto("photoB");
        userB.setUsername("userB");

        Page<snakeRecord> mockPage = new Page<>(1, 10);
        mockPage.setRecords(Arrays.asList(mockRecord));

        when(recordMapper.selectPage(any(IPage.class), any(QueryWrapper.class))).thenReturn(mockPage);
        when(userMapper.selectById(1)).thenReturn(null);
        when(userMapper.selectById(2)).thenReturn(userB);

        JSONObject result = getRecordListService.getList(1);

        assertNotNull(result);
        assertEquals(1, result.getJSONArray("records").size());

        JSONObject recordResult = result.getJSONArray("records").getJSONObject(0);
        assertEquals(null, recordResult.getString("a_photo"));
        assertEquals(null, recordResult.getString("a_username"));
    }

    @Test
    public void testGetListWithUnexpectedLoserValue() {
        snakeRecord mockRecord = new snakeRecord();
        mockRecord.setAId(1);
        mockRecord.setBId(2);
        mockRecord.setLoser("C");  // Unexpected loser value

        User userA = new User();
        userA.setPhoto("photoA");
        userA.setUsername("userA");

        User userB = new User();
        userB.setPhoto("photoB");
        userB.setUsername("userB");

        Page<snakeRecord> mockPage = new Page<>(1, 10);
        mockPage.setRecords(Arrays.asList(mockRecord));

        when(recordMapper.selectPage(any(IPage.class), any(QueryWrapper.class))).thenReturn(mockPage);
        when(userMapper.selectById(1)).thenReturn(userA);
        when(userMapper.selectById(2)).thenReturn(userB);

        JSONObject result = getRecordListService.getList(1);

        assertNotNull(result);
        assertEquals(1, result.getJSONArray("records").size());

        JSONObject recordResult = result.getJSONArray("records").getJSONObject(0);
        assertEquals("Draw", recordResult.getString("result")); // It should default to Draw
    }
}
