package com.daliansea.ai.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 文案模板 sea_content_template
 *
 * @author daliansea
 */
public class SeaContentTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    @Excel(name = "模板ID", cellType = ColumnType.NUMERIC)
    private Long templateId;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 内容类型 */
    @Excel(name = "内容类型")
    private String contentType;

    /** 发布渠道 */
    @Excel(name = "发布渠道")
    private String publishChannel;

    /** 提示词/文案骨架 */
    private String promptTemplate;

    /** 状态 0启用 1停用 */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    /** 排序 */
    @Excel(name = "排序", cellType = ColumnType.NUMERIC)
    private Integer sortOrder;

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    @NotNull(message = "所属租户不能为空")
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

    @NotBlank(message = "模板名称不能为空")
    @Size(max = 100, message = "模板名称长度不能超过100个字符")
    public String getTemplateName()
    {
        return templateName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
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

    @NotBlank(message = "模板内容不能为空")
    public String getPromptTemplate()
    {
        return promptTemplate;
    }

    public void setPromptTemplate(String promptTemplate)
    {
        this.promptTemplate = promptTemplate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("templateName", getTemplateName())
            .append("contentType", getContentType())
            .append("publishChannel", getPublishChannel())
            .append("promptTemplate", getPromptTemplate())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
