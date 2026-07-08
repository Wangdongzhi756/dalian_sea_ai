package com.daliansea.ai.system.domain;

/**
 * AI问答响应
 *
 * @author daliansea
 */
public class SeaAiChatResponse
{
    private String answer;

    private Long matchedKnowledgeId;

    private String matchedKnowledgeTitle;

    private Long logId;

    private String providerName;

    private String modelName;

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public Long getMatchedKnowledgeId()
    {
        return matchedKnowledgeId;
    }

    public void setMatchedKnowledgeId(Long matchedKnowledgeId)
    {
        this.matchedKnowledgeId = matchedKnowledgeId;
    }

    public String getMatchedKnowledgeTitle()
    {
        return matchedKnowledgeTitle;
    }

    public void setMatchedKnowledgeTitle(String matchedKnowledgeTitle)
    {
        this.matchedKnowledgeTitle = matchedKnowledgeTitle;
    }

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public String getProviderName()
    {
        return providerName;
    }

    public void setProviderName(String providerName)
    {
        this.providerName = providerName;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }
}
