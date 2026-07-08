package com.daliansea.ai.system.domain;

import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AI调用日志 sea_ai_call_log
 *
 * @author daliansea
 */
public class SeaAiCallLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "日志ID", cellType = ColumnType.NUMERIC)
    private Long logId;

    private Long tenantId;

    @Excel(name = "租户名称")
    private String tenantName;

    @Excel(name = "场景")
    private String scene;

    @Excel(name = "问题")
    private String question;

    @Excel(name = "回答")
    private String answer;

    @Excel(name = "供应商")
    private String providerName;

    @Excel(name = "模型")
    private String modelName;

    @Excel(name = "请求Token")
    private Integer requestTokens;

    @Excel(name = "响应Token")
    private Integer responseTokens;

    @Excel(name = "总Token")
    private Integer totalTokens;

    @Excel(name = "耗时毫秒")
    private Long latencyMs;

    @Excel(name = "是否成功", readConverterExp = "0=成功,1=失败")
    private String successFlag;

    @Excel(name = "错误信息")
    private String errorMessage;

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantName()
    {
        return tenantName;
    }

    public void setTenantName(String tenantName)
    {
        this.tenantName = tenantName;
    }

    public String getScene()
    {
        return scene;
    }

    public void setScene(String scene)
    {
        this.scene = scene;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
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

    public Integer getRequestTokens()
    {
        return requestTokens;
    }

    public void setRequestTokens(Integer requestTokens)
    {
        this.requestTokens = requestTokens;
    }

    public Integer getResponseTokens()
    {
        return responseTokens;
    }

    public void setResponseTokens(Integer responseTokens)
    {
        this.responseTokens = responseTokens;
    }

    public Integer getTotalTokens()
    {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens)
    {
        this.totalTokens = totalTokens;
    }

    public Long getLatencyMs()
    {
        return latencyMs;
    }

    public void setLatencyMs(Long latencyMs)
    {
        this.latencyMs = latencyMs;
    }

    public String getSuccessFlag()
    {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag)
    {
        this.successFlag = successFlag;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("scene", getScene())
            .append("question", getQuestion())
            .append("answer", getAnswer())
            .append("providerName", getProviderName())
            .append("modelName", getModelName())
            .append("requestTokens", getRequestTokens())
            .append("responseTokens", getResponseTokens())
            .append("totalTokens", getTotalTokens())
            .append("latencyMs", getLatencyMs())
            .append("successFlag", getSuccessFlag())
            .append("errorMessage", getErrorMessage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
