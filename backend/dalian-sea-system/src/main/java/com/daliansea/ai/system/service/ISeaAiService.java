package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaAiCallLog;
import com.daliansea.ai.system.domain.SeaAiChatRequest;
import com.daliansea.ai.system.domain.SeaAiChatResponse;
import com.daliansea.ai.system.domain.SeaAiKnowledge;

/**
 * AI 服务层
 *
 * @author daliansea
 */
public interface ISeaAiService
{
    public List<SeaAiKnowledge> selectKnowledgeList(SeaAiKnowledge knowledge);

    public SeaAiKnowledge selectKnowledgeById(Long knowledgeId);

    public int insertKnowledge(SeaAiKnowledge knowledge);

    public int updateKnowledge(SeaAiKnowledge knowledge);

    public int deleteKnowledgeByIds(Long[] knowledgeIds);

    public List<SeaAiCallLog> selectCallLogList(SeaAiCallLog callLog);

    public SeaAiCallLog selectCallLogById(Long logId);

    public int deleteCallLogByIds(Long[] logIds);

    public SeaAiChatResponse ask(SeaAiChatRequest request);
}
