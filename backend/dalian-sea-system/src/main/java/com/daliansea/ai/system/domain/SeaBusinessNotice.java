package com.daliansea.ai.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 业务通知 sea_business_notice
 *
 * @author daliansea
 */
public class SeaBusinessNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "通知ID", cellType = ColumnType.NUMERIC)
    private Long noticeId;

    private Long applicationId;

    @Excel(name = "申请单号")
    private String applicationNo;

    @Excel(name = "通知标题")
    private String noticeTitle;

    @Excel(name = "通知内容")
    private String noticeContent;

    @Excel(name = "通知类型")
    private String noticeType;

    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private String readFlag;

    @Excel(name = "接收人")
    private String receiverName;

    public Long getNoticeId()
    {
        return noticeId;
    }

    public void setNoticeId(Long noticeId)
    {
        this.noticeId = noticeId;
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

    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getReadFlag()
    {
        return readFlag;
    }

    public void setReadFlag(String readFlag)
    {
        this.readFlag = readFlag;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("applicationId", getApplicationId())
            .append("applicationNo", getApplicationNo())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeContent", getNoticeContent())
            .append("noticeType", getNoticeType())
            .append("readFlag", getReadFlag())
            .append("receiverName", getReceiverName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
