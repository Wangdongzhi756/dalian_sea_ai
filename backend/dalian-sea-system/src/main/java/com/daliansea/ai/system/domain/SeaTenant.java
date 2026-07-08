package com.daliansea.ai.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 租户主体 sea_tenant
 *
 * @author daliansea
 */
public class SeaTenant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 租户ID */
    @Excel(name = "租户ID", cellType = ColumnType.NUMERIC)
    private Long tenantId;

    /** 租户编码 */
    @Excel(name = "租户编码")
    private String tenantCode;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String contactEmail;

    /** 套餐类型 */
    @Excel(name = "套餐类型")
    private String planType;

    /** 船舶额度 */
    @Excel(name = "船舶额度")
    private Integer maxVessels;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }

    @NotBlank(message = "租户编码不能为空")
    @Size(max = 64, message = "租户编码长度不能超过64个字符")
    public String getTenantCode()
    {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode)
    {
        this.tenantCode = tenantCode;
    }

    @NotBlank(message = "租户名称不能为空")
    @Size(max = 100, message = "租户名称长度不能超过100个字符")
    public String getTenantName()
    {
        return tenantName;
    }

    public void setTenantName(String tenantName)
    {
        this.tenantName = tenantName;
    }

    @Size(max = 50, message = "联系人长度不能超过50个字符")
    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    @Size(max = 100, message = "联系邮箱长度不能超过100个字符")
    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    @Size(max = 32, message = "套餐类型长度不能超过32个字符")
    public String getPlanType()
    {
        return planType;
    }

    public void setPlanType(String planType)
    {
        this.planType = planType;
    }

    @NotNull(message = "船舶额度不能为空")
    public Integer getMaxVessels()
    {
        return maxVessels;
    }

    public void setMaxVessels(Integer maxVessels)
    {
        this.maxVessels = maxVessels;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
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
            .append("tenantId", getTenantId())
            .append("tenantCode", getTenantCode())
            .append("tenantName", getTenantName())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("contactEmail", getContactEmail())
            .append("planType", getPlanType())
            .append("maxVessels", getMaxVessels())
            .append("expireTime", getExpireTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
