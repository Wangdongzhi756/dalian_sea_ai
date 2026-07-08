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
 * AI知识库 sea_ai_knowledge
 *
 * @author daliansea
 */
public class SeaAiKnowledge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "知识ID", cellType = ColumnType.NUMERIC)
    private Long knowledgeId;

    private Long tenantId;

    @Excel(name = "租户名称")
    private String tenantName;

    @Excel(name = "知识标题")
    private String title;

    @Excel(name = "知识分类")
    private String category;

    @Excel(name = "来源类型")
    private String sourceType;

    @Excel(name = "知识内容")
    private String content;

    @Excel(name = "标签")
    private String tags;

    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    public Long getKnowledgeId()
    {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId)
    {
        this.knowledgeId = knowledgeId;
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

    @NotBlank(message = "知识标题不能为空")
    @Size(max = 100, message = "知识标题长度不能超过100个字符")
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @NotBlank(message = "知识分类不能为空")
    @Size(max = 32, message = "知识分类长度不能超过32个字符")
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    @Size(max = 32, message = "来源类型长度不能超过32个字符")
    public String getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }

    @NotBlank(message = "知识内容不能为空")
    @Size(max = 4000, message = "知识内容长度不能超过4000个字符")
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Size(max = 200, message = "标签长度不能超过200个字符")
    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("knowledgeId", getKnowledgeId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("title", getTitle())
            .append("category", getCategory())
            .append("sourceType", getSourceType())
            .append("content", getContent())
            .append("tags", getTags())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
