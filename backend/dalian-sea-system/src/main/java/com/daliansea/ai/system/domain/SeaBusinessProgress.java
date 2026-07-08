package com.daliansea.ai.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 业务进度 sea_business_progress
 *
 * @author daliansea
 */
public class SeaBusinessProgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "进度ID", cellType = ColumnType.NUMERIC)
    private Long progressId;

    private Long applicationId;

    @Excel(name = "申请单号")
    private String applicationNo;

    @Excel(name = "节点名称")
    private String stepName;

    @Excel(name = "节点状态")
    private String stepStatus;

    @Excel(name = "处理人")
    private String handlerName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    @Excel(name = "处理意见")
    private String handleOpinion;

    public Long getProgressId()
    {
        return progressId;
    }

    public void setProgressId(Long progressId)
    {
        this.progressId = progressId;
    }

    public Long getApplicationId()
    {
        return applicationId;
    }

    public void setApplicationId(Long applicationId)
    {
        this.applicationId = applicationId;
    }

    public String getApplicationNo()
    {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo)
    {
        this.applicationNo = applicationNo;
    }

    public String getStepName()
    {
        return stepName;
    }

    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    public String getStepStatus()
    {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus)
    {
        this.stepStatus = stepStatus;
    }

    public String getHandlerName()
    {
        return handlerName;
    }

    public void setHandlerName(String handlerName)
    {
        this.handlerName = handlerName;
    }

    public Date getHandleTime()
    {
        return handleTime;
    }

    public void setHandleTime(Date handleTime)
    {
        this.handleTime = handleTime;
    }

    public String getHandleOpinion()
    {
        return handleOpinion;
    }

    public void setHandleOpinion(String handleOpinion)
    {
        this.handleOpinion = handleOpinion;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("progressId", getProgressId())
            .append("applicationId", getApplicationId())
            .append("applicationNo", getApplicationNo())
            .append("stepName", getStepName())
            .append("stepStatus", getStepStatus())
            .append("handlerName", getHandlerName())
            .append("handleTime", getHandleTime())
            .append("handleOpinion", getHandleOpinion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
