package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaAiKnowledge;

/**
 * AI知识库 数据层
 *
 * @author daliansea
 */
public interface SeaAiKnowledgeMapper
{
    public List<SeaAiKnowledge> selectKnowledgeList(SeaAiKnowledge knowledge);

    public SeaAiKnowledge selectKnowledgeById(Long knowledgeId);

    public int insertKnowledge(SeaAiKnowledge knowledge);

    public int updateKnowledge(SeaAiKnowledge knowledge);

    public int deleteKnowledgeByIds(Long[] knowledgeIds);
}
