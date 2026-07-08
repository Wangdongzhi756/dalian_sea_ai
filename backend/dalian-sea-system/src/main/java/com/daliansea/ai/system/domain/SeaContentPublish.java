package com.daliansea.ai.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 内容发布 sea_content_publish
 *
 * @author daliansea
 */
public class SeaContentPublish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    @Excel(name = "内容ID", cellType = ColumnType.NUMERIC)
    private Long contentId;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 船舶ID */
    private Long vesselId;

    /** 船舶名称 */
    @Excel(name = "船舶名称")
    private String vesselName;

    /** 船长 */
    @Excel(name = "船长")
    private String captainName;

    /** 船长电话 */
    @Excel(name = "船长电话")
    private String captainPhone;

    /** 内容类型 */
    @Excel(name = "内容类型")
    private String contentType;

    /** 发布渠道 */
    @Excel(name = "发布渠道")
    private String publishChannel;

    /** 主题 */
    @Excel(name = "主题")
    private String topic;

    /** 语气风格 */
    private String tone;

    /** 亮点 */
    private String highlights;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 正文 */
    private String content;

    /** 发布状态 draft/published */
    @Excel(name = "发布状态")
    private String publishStatus;

    /** 推流状态 pending/success/failed */
    @Excel(name = "推流状态")
    private String pushStatus;

    /** 推流消息 */
    private String pushMessage;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** AI供应商 */
    private String aiProvider;

    /** AI模型 */
    private String aiModel;

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId(Long contentId)
    {
        this.contentId = contentId;
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

    public Long getVesselId()
    {
        return vesselId;
    }

    public void setVesselId(Long vesselId)
    {
        this.vesselId = vesselId;
    }

    public String getVesselName()
    {
        return vesselName;
    }

    public void setVesselName(String vesselName)
    {
        this.vesselName = vesselName;
    }

    public String getCaptainName()
    {
        return captainName;
    }

    public void setCaptainName(String captainName)
    {
        this.captainName = captainName;
    }

    public String getCaptainPhone()
    {
        return captainPhone;
    }

    public void setCaptainPhone(String captainPhone)
    {
        this.captainPhone = captainPhone;
    }

    @NotBlank(message = "内容类型不能为空")
    @Size(max = 32, message = "内容类型长度不能超过32个字符")
    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    @NotBlank(message = "发布渠道不能为空")
    @Size(max = 32, message = "发布渠道长度不能超过32个字符")
    public String getPublishChannel()
    {
        return publishChannel;
    }

    public void setPublishChannel(String publishChannel)
    {
        this.publishChannel = publishChannel;
    }

    @NotBlank(message = "主题不能为空")
    @Size(max = 100, message = "主题长度不能超过100个字符")
    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getTone()
    {
        return tone;
    }

    public void setTone(String tone)
    {
        this.tone = tone;
    }

    public String getHighlights()
    {
        return highlights;
    }

    public void setHighlights(String highlights)
    {
        this.highlights = highlights;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPublishStatus()
    {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus)
    {
        this.publishStatus = publishStatus;
    }

    public String getPushStatus()
    {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus)
    {
        this.pushStatus = pushStatus;
    }

    public String getPushMessage()
    {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage)
    {
        this.pushMessage = pushMessage;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public String getAiProvider()
    {
        return aiProvider;
    }

    public void setAiProvider(String aiProvider)
    {
        this.aiProvider = aiProvider;
    }

    public String getAiModel()
    {
        return aiModel;
    }

    public void setAiModel(String aiModel)
    {
        this.aiModel = aiModel;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("contentId", getContentId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("vesselId", getVesselId())
            .append("vesselName", getVesselName())
            .append("captainName", getCaptainName())
            .append("captainPhone", getCaptainPhone())
            .append("contentType", getContentType())
            .append("publishChannel", getPublishChannel())
            .append("topic", getTopic())
            .append("tone", getTone())
            .append("highlights", getHighlights())
            .append("title", getTitle())
            .append("content", getContent())
            .append("publishStatus", getPublishStatus())
            .append("pushStatus", getPushStatus())
            .append("pushMessage", getPushMessage())
            .append("publishTime", getPublishTime())
            .append("aiProvider", getAiProvider())
            .append("aiModel", getAiModel())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
