package com.zhp.sys.dbservice;

import com.zhp.sys.dao.SysLogMapper;
import com.zhp.sys.model.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhouhh2 on 2018/3/23.
 */

@Service
@Transactional
public class SysLogService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysLogMapper sysLogMapper;

    @Async
    public void asyncSaveLog(SysLog log) {
        sysLogMapper.insert(log);
    }
}
