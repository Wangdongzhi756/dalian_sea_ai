package com.daliansea.ai.system.domain;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.daliansea.ai.common.annotation.Excel;
import com.daliansea.ai.common.annotation.Excel.ColumnType;
import com.daliansea.ai.common.core.domain.BaseEntity;

/**
 * 船员档案 sea_crew
 *
 * @author daliansea
 */
public class SeaCrew extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 船员ID */
    @Excel(name = "船员ID", cellType = ColumnType.NUMERIC)
    private Long crewId;

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

    /** 船员编码 */
    @Excel(name = "船员编码")
    private String crewCode;

    /** 船员姓名 */
    @Excel(name = "船员姓名")
    private String crewName;

    /** 性别 */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String idCard;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 岗位职务 */
    @Excel(name = "岗位职务")
    private String positionName;

    /** 在船状态 */
    @Excel(name = "在船状态")
    private String onboardStatus;

    /** 健康状态 */
    @Excel(name = "健康状态")
    private String healthStatus;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getCrewId()
    {
        return crewId;
    }

    public void setCrewId(Long crewId)
    {
        this.crewId = crewId;
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

    @NotBlank(message = "船员编码不能为空")
    @Size(max = 64, message = "船员编码长度不能超过64个字符")
    public String getCrewCode()
    {
        return crewCode;
    }

    public void setCrewCode(String crewCode)
    {
        this.crewCode = crewCode;
    }

    @NotBlank(message = "船员姓名不能为空")
    @Size(max = 50, message = "船员姓名长度不能超过50个字符")
    public String getCrewName()
    {
        return crewName;
    }

    public void setCrewName(String crewName)
    {
        this.crewName = crewName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    @Size(max = 30, message = "证件号码长度不能超过30个字符")
    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Size(max = 50, message = "岗位职务长度不能超过50个字符")
    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getOnboardStatus()
    {
        return onboardStatus;
    }

    public void setOnboardStatus(String onboardStatus)
    {
        this.onboardStatus = onboardStatus;
    }

    public String getHealthStatus()
    {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus)
    {
        this.healthStatus = healthStatus;
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
            .append("crewId", getCrewId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("vesselId", getVesselId())
            .append("vesselName", getVesselName())
            .append("crewCode", getCrewCode())
            .append("crewName", getCrewName())
            .append("gender", getGender())
            .append("birthDate", getBirthDate())
            .append("idCard", getIdCard())
            .append("phone", getPhone())
            .append("positionName", getPositionName())
            .append("onboardStatus", getOnboardStatus())
            .append("healthStatus", getHealthStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
