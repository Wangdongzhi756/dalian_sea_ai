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
 * 船舶档案 sea_vessel
 *
 * @author daliansea
 */
public class SeaVessel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 船舶ID */
    @Excel(name = "船舶ID", cellType = ColumnType.NUMERIC)
    private Long vesselId;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 船舶编码 */
    @Excel(name = "船舶编码")
    private String vesselCode;

    /** 船舶名称 */
    @Excel(name = "船舶名称")
    private String vesselName;

    /** 船舶类型 */
    @Excel(name = "船舶类型")
    private String vesselType;

    /** 载客人数 */
    @Excel(name = "载客人数")
    private Integer capacity;

    /** 母港 */
    @Excel(name = "母港")
    private String homePort;

    /** 船长 */
    @Excel(name = "船长")
    private String captainName;

    /** 船长电话 */
    @Excel(name = "船长电话")
    private String captainPhone;

    /** 服务区域 */
    @Excel(name = "服务区域")
    private String serviceArea;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getVesselId()
    {
        return vesselId;
    }

    public void setVesselId(Long vesselId)
    {
        this.vesselId = vesselId;
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

    @NotBlank(message = "船舶编码不能为空")
    @Size(max = 64, message = "船舶编码长度不能超过64个字符")
    public String getVesselCode()
    {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode)
    {
        this.vesselCode = vesselCode;
    }

    @NotBlank(message = "船舶名称不能为空")
    @Size(max = 100, message = "船舶名称长度不能超过100个字符")
    public String getVesselName()
    {
        return vesselName;
    }

    public void setVesselName(String vesselName)
    {
        this.vesselName = vesselName;
    }

    @Size(max = 32, message = "船舶类型长度不能超过32个字符")
    public String getVesselType()
    {
        return vesselType;
    }

    public void setVesselType(String vesselType)
    {
        this.vesselType = vesselType;
    }

    public Integer getCapacity()
    {
        return capacity;
    }

    public void setCapacity(Integer capacity)
    {
        this.capacity = capacity;
    }

    @Size(max = 100, message = "母港长度不能超过100个字符")
    public String getHomePort()
    {
        return homePort;
    }

    public void setHomePort(String homePort)
    {
        this.homePort = homePort;
    }

    @Size(max = 50, message = "船长姓名长度不能超过50个字符")
    public String getCaptainName()
    {
        return captainName;
    }

    public void setCaptainName(String captainName)
    {
        this.captainName = captainName;
    }

    @Size(max = 20, message = "船长电话长度不能超过20个字符")
    public String getCaptainPhone()
    {
        return captainPhone;
    }

    public void setCaptainPhone(String captainPhone)
    {
        this.captainPhone = captainPhone;
    }

    @Size(max = 200, message = "服务区域长度不能超过200个字符")
    public String getServiceArea()
    {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea)
    {
        this.serviceArea = serviceArea;
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
            .append("vesselId", getVesselId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("vesselCode", getVesselCode())
            .append("vesselName", getVesselName())
            .append("vesselType", getVesselType())
            .append("capacity", getCapacity())
            .append("homePort", getHomePort())
            .append("captainName", getCaptainName())
            .append("captainPhone", getCaptainPhone())
            .append("serviceArea", getServiceArea())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
