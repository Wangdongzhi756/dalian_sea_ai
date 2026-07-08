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
 * 船员证书档案 sea_crew_certificate
 *
 * @author daliansea
 */
public class SeaCrewCertificate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 证书ID */
    @Excel(name = "证书ID", cellType = ColumnType.NUMERIC)
    private Long certificateId;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 船员ID */
    private Long crewId;

    /** 船员姓名 */
    @Excel(name = "船员姓名")
    private String crewName;

    /** 证书类型 */
    @Excel(name = "证书类型")
    private String certificateType;

    /** 证书编号 */
    @Excel(name = "证书编号")
    private String certificateNo;

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

    /** 证书状态 */
    @Excel(name = "证书状态")
    private String certificateStatus;

    public Long getCertificateId()
    {
        return certificateId;
    }

    public void setCertificateId(Long certificateId)
    {
        this.certificateId = certificateId;
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

    @NotNull(message = "所属船员不能为空")
    public Long getCrewId()
    {
        return crewId;
    }

    public void setCrewId(Long crewId)
    {
        this.crewId = crewId;
    }

    public String getCrewName()
    {
        return crewName;
    }

    public void setCrewName(String crewName)
    {
        this.crewName = crewName;
    }

    @NotBlank(message = "证书类型不能为空")
    @Size(max = 32, message = "证书类型长度不能超过32个字符")
    public String getCertificateType()
    {
        return certificateType;
    }

    public void setCertificateType(String certificateType)
    {
        this.certificateType = certificateType;
    }

    @NotBlank(message = "证书编号不能为空")
    @Size(max = 64, message = "证书编号长度不能超过64个字符")
    public String getCertificateNo()
    {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo)
    {
        this.certificateNo = certificateNo;
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

    public String getCertificateStatus()
    {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus)
    {
        this.certificateStatus = certificateStatus;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("certificateId", getCertificateId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("crewId", getCrewId())
            .append("crewName", getCrewName())
            .append("certificateType", getCertificateType())
            .append("certificateNo", getCertificateNo())
            .append("issueDate", getIssueDate())
            .append("expireDate", getExpireDate())
            .append("issuingAuthority", getIssuingAuthority())
            .append("certificateStatus", getCertificateStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
