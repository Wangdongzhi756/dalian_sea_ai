package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaAiCallLog;

/**
 * AI调用日志 数据层
 *
 * @author daliansea
 */
public interface SeaAiCallLogMapper
{
    public List<SeaAiCallLog> selectCallLogList(SeaAiCallLog callLog);

    public SeaAiCallLog selectCallLogById(Long logId);

    public int insertCallLog(SeaAiCallLog callLog);

    public int deleteCallLogByIds(Long[] logIds);
}
