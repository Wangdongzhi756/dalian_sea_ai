package com.daliansea.ai.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * AI问答请求
 *
 * @author daliansea
 */
public class SeaAiChatRequest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long tenantId;

    private String scene;

    private String category;

    private String question;

    @NotNull(message = "所属租户不能为空")
    public Long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }

    @Size(max = 32, message = "场景长度不能超过32个字符")
    public String getScene()
    {
        return scene;
    }

    public void setScene(String scene)
    {
        this.scene = scene;
    }

    @Size(max = 32, message = "知识分类长度不能超过32个字符")
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    @NotBlank(message = "问题不能为空")
    @Size(max = 1000, message = "问题长度不能超过1000个字符")
    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }
}
