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
 * 业务申请 sea_business_application
 *
 * @author daliansea
 */
public class SeaBusinessApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "申请ID", cellType = ColumnType.NUMERIC)
    private Long applicationId;

    private Long tenantId;

    @Excel(name = "租户名称")
    private String tenantName;

    private Long vesselId;

    @Excel(name = "船舶名称")
    private String vesselName;

    private Long crewId;

    @Excel(name = "船员姓名")
    private String crewName;

    @Excel(name = "申请单号")
    private String applicationNo;

    @Excel(name = "申请类型")
    private String applicationType;

    @Excel(name = "申请标题")
    private String title;

    @Excel(name = "申请人")
    private String applicantName;

    @Excel(name = "联系电话")
    private String applicantPhone;

    @Excel(name = "紧急程度")
    private String priority;

    @Excel(name = "申请内容")
    private String content;

    @Excel(name = "当前节点")
    private String currentStep;

    @Excel(name = "进度")
    private Integer progressPercent;

    @Excel(name = "申请状态")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date submittedTime;

    @Excel(name = "审核人")
    private String auditBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @Excel(name = "审核意见")
    private String auditOpinion;

    public Long getApplicationId()
    {
        return applicationId;
    }

    public void setApplicationId(Long applicationId)
    {
        this.applicationId = applicationId;
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

    public String getApplicationNo()
    {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo)
    {
        this.applicationNo = applicationNo;
    }

    @NotBlank(message = "申请类型不能为空")
    @Size(max = 32, message = "申请类型长度不能超过32个字符")
    public String getApplicationType()
    {
        return applicationType;
    }

    public void setApplicationType(String applicationType)
    {
        this.applicationType = applicationType;
    }

    @NotBlank(message = "申请标题不能为空")
    @Size(max = 100, message = "申请标题长度不能超过100个字符")
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @NotBlank(message = "申请人不能为空")
    @Size(max = 50, message = "申请人长度不能超过50个字符")
    public String getApplicantName()
    {
        return applicantName;
    }

    public void setApplicantName(String applicantName)
    {
        this.applicantName = applicantName;
    }

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    public String getApplicantPhone()
    {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone)
    {
        this.applicantPhone = applicantPhone;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    @Size(max = 1000, message = "申请内容长度不能超过1000个字符")
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCurrentStep()
    {
        return currentStep;
    }

    public void setCurrentStep(String currentStep)
    {
        this.currentStep = currentStep;
    }

    public Integer getProgressPercent()
    {
        return progressPercent;
    }

    public void setProgressPercent(Integer progressPercent)
    {
        this.progressPercent = progressPercent;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getSubmittedTime()
    {
        return submittedTime;
    }

    public void setSubmittedTime(Date submittedTime)
    {
        this.submittedTime = submittedTime;
    }

    public String getAuditBy()
    {
        return auditBy;
    }

    public void setAuditBy(String auditBy)
    {
        this.auditBy = auditBy;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getAuditOpinion()
    {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion)
    {
        this.auditOpinion = auditOpinion;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("applicationId", getApplicationId())
            .append("tenantId", getTenantId())
            .append("tenantName", getTenantName())
            .append("vesselId", getVesselId())
            .append("vesselName", getVesselName())
            .append("crewId", getCrewId())
            .append("crewName", getCrewName())
            .append("applicationNo", getApplicationNo())
            .append("applicationType", getApplicationType())
            .append("title", getTitle())
            .append("applicantName", getApplicantName())
            .append("applicantPhone", getApplicantPhone())
            .append("priority", getPriority())
            .append("content", getContent())
            .append("currentStep", getCurrentStep())
            .append("progressPercent", getProgressPercent())
            .append("status", getStatus())
            .append("submittedTime", getSubmittedTime())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("auditOpinion", getAuditOpinion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
