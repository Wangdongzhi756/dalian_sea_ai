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
 * 船舶证照附件 sea_vessel_license
 *
 * @author daliansea
 */
public class SeaVesselLicense extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 证照ID */
    @Excel(name = "证照ID", cellType = ColumnType.NUMERIC)
    private Long licenseId;

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

    /** 证照类型 */
    @Excel(name = "证照类型")
    private String licenseType;

    /** 证照编号 */
    @Excel(name = "证照编号")
    private String licenseNo;

    /** 签发日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签发日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /** 签发机构 */
    @Excel(name = "签发机构")
    private String issuingAuthority;

    /** 附件名称 */
    @Excel(name = "附件名称")
    private String attachmentName;

    /** 附件地址 */
    private String attachmentUrl;

    /** 证照状态 */
    @Excel(name = "证照状态")
    private String licenseStatus;

    public Long getLicenseId()
    {
        return licenseId;
    }

    public void setLicenseId(Long licenseId)
    {
        this.licenseId = licenseId;
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

    @NotNull(message = "所属船舶不能为空")
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

    @NotBlank(message = "证照类型不能为空")
    @Size(max = 32, message = "证照类型长度不能超过32个字符")
    public String getLicenseType()
    {
        return licenseType;
    }

    public void setLicenseType(String licenseType)
    {
        this.licenseType = licenseType;
    }

    @NotBlank(message = "证照编号不能为空")
    @Size(max = 64, message = "证照编号长度不能超过64个字符")
    public String getLicenseNo()
    {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }

    public Date getIssueDate()
    {
        return issueDate;
    }

    public void setIssueDate(Date issueDate)
    {
        this.issueDate = issueDate;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    @Size(max = 100, message = "签发机构长度不能超过100个字符")
    public String getIssuingAuthority()
    {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority)
    {
        this.issuingAuthority = issuingAuthority;
    }

    @Size(max = 200, message = "附件名称长度不能超过200个字符")
    public String getAttachmentName()
    {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName)
    {
        this.attachmentName = attachmentName;
    }

    @Size(max = 500, message = "附件地址长度不能超过500个字符")
    public String getAttachmentUrl()
    {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    public String getLicenseStatus()
    {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus)
    {
        this.licenseStatus = licenseStatus;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("licenseId", getLicenseId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("vesselId", getVesselId())
            .append("vesselName", getVesselName())
            .append("licenseType", getLicenseType())
            .append("licenseNo", getLicenseNo())
            .append("issueDate", getIssueDate())
            .append("expireDate", getExpireDate())
            .append("issuingAuthority", getIssuingAuthority())
            .append("attachmentName", getAttachmentName())
            .append("attachmentUrl", getAttachmentUrl())
            .append("licenseStatus", getLicenseStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
