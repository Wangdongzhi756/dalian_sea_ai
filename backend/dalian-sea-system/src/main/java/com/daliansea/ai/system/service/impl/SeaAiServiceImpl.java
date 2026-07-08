package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaAiCallLog;
import com.daliansea.ai.system.domain.SeaAiChatRequest;
import com.daliansea.ai.system.domain.SeaAiChatResponse;
import com.daliansea.ai.system.domain.SeaAiKnowledge;
import com.daliansea.ai.system.mapper.SeaAiCallLogMapper;
import com.daliansea.ai.system.mapper.SeaAiKnowledgeMapper;
import com.daliansea.ai.system.service.ISeaAiService;

/**
 * AI 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaAiServiceImpl implements ISeaAiService
{
    private static final String DEFAULT_PROVIDER = "local";

    private static final String DEFAULT_MODEL = "knowledge-rule-v1";

    @Autowired
    private SeaAiKnowledgeMapper knowledgeMapper;

    @Autowired
    private SeaAiCallLogMapper callLogMapper;

    @Override
    public List<SeaAiKnowledge> selectKnowledgeList(SeaAiKnowledge knowledge)
    {
        return knowledgeMapper.selectKnowledgeList(knowledge);
    }

    @Override
    public SeaAiKnowledge selectKnowledgeById(Long knowledgeId)
    {
        return knowledgeMapper.selectKnowledgeById(knowledgeId);
    }

    @Override
    public int insertKnowledge(SeaAiKnowledge knowledge)
    {
        if (StringUtils.isBlank(knowledge.getSourceType()))
        {
            knowledge.setSourceType("manual");
        }
        if (StringUtils.isBlank(knowledge.getStatus()))
        {
            knowledge.setStatus("0");
        }
        return knowledgeMapper.insertKnowledge(knowledge);
    }

    @Override
    public int updateKnowledge(SeaAiKnowledge knowledge)
    {
        return knowledgeMapper.updateKnowledge(knowledge);
    }

    @Override
    public int deleteKnowledgeByIds(Long[] knowledgeIds)
    {
        return knowledgeMapper.deleteKnowledgeByIds(knowledgeIds);
    }

    @Override
    public List<SeaAiCallLog> selectCallLogList(SeaAiCallLog callLog)
    {
        return callLogMapper.selectCallLogList(callLog);
    }

    @Override
    public SeaAiCallLog selectCallLogById(Long logId)
    {
        return callLogMapper.selectCallLogById(logId);
    }

    @Override
    public int deleteCallLogByIds(Long[] logIds)
    {
        return callLogMapper.deleteCallLogByIds(logIds);
    }

    @Override
    @Transactional
    public SeaAiChatResponse ask(SeaAiChatRequest request)
    {
        long start = System.currentTimeMillis();
        SeaAiCallLog log = buildBaseLog(request);
        SeaAiChatResponse response = new SeaAiChatResponse();
        response.setProviderName(DEFAULT_PROVIDER);
        response.setModelName(DEFAULT_MODEL);
        try
        {
            SeaAiKnowledge match = findKnowledge(request);
            String answer = buildAnswer(request, match);
            log.setAnswer(answer);
            log.setResponseTokens(estimateTokens(answer));
            log.setSuccessFlag("0");
            log.setLatencyMs(System.currentTimeMillis() - start);
            log.setTotalTokens(log.getRequestTokens() + log.getResponseTokens());
            callLogMapper.insertCallLog(log);

            response.setAnswer(answer);
            response.setLogId(log.getLogId());
            if (StringUtils.isNotNull(match))
            {
                response.setMatchedKnowledgeId(match.getKnowledgeId());
                response.setMatchedKnowledgeTitle(match.getTitle());
            }
            return response;
        }
        catch (RuntimeException e)
        {
            log.setAnswer("");
            log.setResponseTokens(0);
            log.setTotalTokens(log.getRequestTokens());
            log.setLatencyMs(System.currentTimeMillis() - start);
            log.setSuccessFlag("1");
            log.setErrorMessage(StringUtils.substring(e.getMessage(), 0, 500));
            callLogMapper.insertCallLog(log);
            throw e;
        }
    }

    private SeaAiCallLog buildBaseLog(SeaAiChatRequest request)
    {
        SeaAiCallLog log = new SeaAiCallLog();
        log.setTenantId(request.getTenantId());
        log.setScene(StringUtils.defaultIfBlank(request.getScene(), "knowledge_qa"));
        log.setQuestion(request.getQuestion());
        log.setProviderName(DEFAULT_PROVIDER);
        log.setModelName(DEFAULT_MODEL);
        log.setRequestTokens(estimateTokens(request.getQuestion()));
        log.setCreateBy(request.getCreateBy());
        return log;
    }

    private SeaAiKnowledge findKnowledge(SeaAiChatRequest request)
    {
        SeaAiKnowledge query = new SeaAiKnowledge();
        query.setTenantId(request.getTenantId());
        query.setCategory(request.getCategory());
        query.setStatus("0");
        List<SeaAiKnowledge> knowledgeList = knowledgeMapper.selectKnowledgeList(query);
        SeaAiKnowledge best = null;
        int bestScore = 0;
        for (SeaAiKnowledge item : knowledgeList)
        {
            int score = scoreKnowledge(item, request.getQuestion());
            if (score > bestScore)
            {
                best = item;
                bestScore = score;
            }
        }
        return best;
    }

    private int scoreKnowledge(SeaAiKnowledge knowledge, String question)
    {
        String source = StringUtils.defaultString(knowledge.getTitle()) + " "
                + StringUtils.defaultString(knowledge.getCategory()) + " "
                + StringUtils.defaultString(knowledge.getTags()) + " "
                + StringUtils.defaultString(knowledge.getContent());
        String normalizedSource = source.toLowerCase();
        String normalizedQuestion = StringUtils.defaultString(question).toLowerCase();
        int score = 0;
        if (StringUtils.containsIgnoreCase(source, question))
        {
            score += 12;
        }
        String[] words = normalizedQuestion.split("[\\s,，。；;、]+");
        for (String word : words)
        {
            if (StringUtils.isNotBlank(word) && normalizedSource.contains(word))
            {
                score += Math.min(word.length(), 8);
            }
        }
        score += scoreChineseHits(normalizedSource, normalizedQuestion);
        return score;
    }

    private int scoreChineseHits(String normalizedSource, String normalizedQuestion)
    {
        int hits = 0;
        for (int i = 0; i < normalizedQuestion.length(); i++)
        {
            char ch = normalizedQuestion.charAt(i);
            if (Character.UnicodeScript.of(ch) == Character.UnicodeScript.HAN
                    && normalizedSource.indexOf(ch) >= 0)
            {
                hits++;
            }
        }
        return Math.min(hits, 12);
    }

    private String buildAnswer(SeaAiChatRequest request, SeaAiKnowledge knowledge)
    {
        if (StringUtils.isNull(knowledge))
        {
            return "暂未在当前知识库中匹配到明确内容。建议补充相关海事规则、业务流程或材料要求后再提问。";
        }
        return "根据知识库《" + knowledge.getTitle() + "》："
                + StringUtils.abbreviate(knowledge.getContent(), 600)
                + "\n\n问题：" + request.getQuestion();
    }

    private int estimateTokens(String text)
    {
        return Math.max(1, StringUtils.defaultString(text).length() / 2);
    }
}
