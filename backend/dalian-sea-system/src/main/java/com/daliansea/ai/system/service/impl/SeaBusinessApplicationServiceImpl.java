package com.daliansea.ai.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.exception.ServiceException;
import com.daliansea.ai.common.utils.DateUtils;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.common.utils.uuid.IdUtils;
import com.daliansea.ai.system.domain.SeaBusinessApplication;
import com.daliansea.ai.system.domain.SeaBusinessNotice;
import com.daliansea.ai.system.domain.SeaBusinessProgress;
import com.daliansea.ai.system.mapper.SeaBusinessApplicationMapper;
import com.daliansea.ai.system.mapper.SeaBusinessNoticeMapper;
import com.daliansea.ai.system.mapper.SeaBusinessProgressMapper;
import com.daliansea.ai.system.service.ISeaBusinessApplicationService;

/**
 * 业务申请 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaBusinessApplicationServiceImpl implements ISeaBusinessApplicationService
{
    private static final String STATUS_SUBMITTED = "submitted";

    private static final String STATUS_APPROVED = "approved";

    private static final String STATUS_REJECTED = "rejected";

    @Autowired
    private SeaBusinessApplicationMapper applicationMapper;

    @Autowired
    private SeaBusinessProgressMapper progressMapper;

    @Autowired
    private SeaBusinessNoticeMapper noticeMapper;

    @Override
    public List<SeaBusinessApplication> selectApplicationList(SeaBusinessApplication application)
    {
        return applicationMapper.selectApplicationList(application);
    }

    @Override
    public SeaBusinessApplication selectApplicationById(Long applicationId)
    {
        return applicationMapper.selectApplicationById(applicationId);
    }

    @Override
    public List<SeaBusinessProgress> selectProgressList(Long applicationId)
    {
        SeaBusinessProgress progress = new SeaBusinessProgress();
        progress.setApplicationId(applicationId);
        return progressMapper.selectProgressList(progress);
    }

    @Override
    public List<SeaBusinessNotice> selectNoticeList(SeaBusinessNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public boolean checkApplicationNoUnique(SeaBusinessApplication application)
    {
        Long applicationId = StringUtils.isNull(application.getApplicationId()) ? -1L : application.getApplicationId();
        SeaBusinessApplication info = applicationMapper.checkApplicationNoUnique(application.getApplicationNo());
        if (StringUtils.isNotNull(info) && info.getApplicationId().longValue() != applicationId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int insertApplication(SeaBusinessApplication application)
    {
        Date now = DateUtils.getNowDate();
        if (StringUtils.isBlank(application.getApplicationNo()))
        {
            application.setApplicationNo(generateApplicationNo());
        }
        application.setStatus(STATUS_SUBMITTED);
        application.setCurrentStep("待审核");
        application.setProgressPercent(35);
        application.setSubmittedTime(now);
        int rows = applicationMapper.insertApplication(application);
        addProgress(application, "已提交", STATUS_SUBMITTED, application.getCreateBy(), "申请已提交，等待平台审核");
        addNotice(application, "业务申请已提交", "申请单 " + application.getApplicationNo() + " 已提交，当前等待审核。", "application");
        return rows;
    }

    @Override
    public int updateApplication(SeaBusinessApplication application)
    {
        return applicationMapper.updateApplication(application);
    }

    @Override
    @Transactional
    public int auditApplication(SeaBusinessApplication application)
    {
        SeaBusinessApplication oldApplication = applicationMapper.selectApplicationById(application.getApplicationId());
        if (StringUtils.isNull(oldApplication))
        {
            throw new ServiceException("业务申请不存在");
        }
        if (!StringUtils.equalsAny(application.getStatus(), STATUS_APPROVED, STATUS_REJECTED))
        {
            throw new ServiceException("审核状态只能为通过或驳回");
        }
        if (StringUtils.equalsAny(oldApplication.getStatus(), STATUS_APPROVED, STATUS_REJECTED))
        {
            throw new ServiceException("业务申请已完成审核，不能重复审核");
        }
        boolean approved = STATUS_APPROVED.equals(application.getStatus());
        application.setCurrentStep(approved ? "已办结" : "已驳回");
        application.setProgressPercent(approved ? 100 : 80);
        application.setAuditTime(DateUtils.getNowDate());
        int rows = applicationMapper.updateApplication(application);

        oldApplication.setStatus(application.getStatus());
        oldApplication.setCurrentStep(application.getCurrentStep());
        oldApplication.setAuditBy(application.getAuditBy());
        oldApplication.setAuditOpinion(application.getAuditOpinion());
        addProgress(oldApplication, approved ? "审核通过" : "审核驳回", application.getStatus(), application.getAuditBy(), application.getAuditOpinion());
        addNotice(oldApplication, approved ? "业务申请已通过" : "业务申请已驳回",
                "申请单 " + oldApplication.getApplicationNo() + (approved ? " 已审核通过。" : " 已被驳回。") + StringUtils.defaultString(application.getAuditOpinion()), "audit");
        return rows;
    }

    @Override
    public int readNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.readNoticeByIds(noticeIds);
    }

    @Override
    @Transactional
    public int deleteApplicationByIds(Long[] applicationIds)
    {
        progressMapper.deleteProgressByApplicationIds(applicationIds);
        noticeMapper.deleteNoticeByApplicationIds(applicationIds);
        return applicationMapper.deleteApplicationByIds(applicationIds);
    }

    private String generateApplicationNo()
    {
        return "APP" + DateUtils.dateTimeNow() + IdUtils.fastSimpleUUID().substring(0, 6).toUpperCase();
    }

    private void addProgress(SeaBusinessApplication application, String stepName, String stepStatus, String handlerName, String opinion)
    {
        SeaBusinessProgress progress = new SeaBusinessProgress();
        progress.setApplicationId(application.getApplicationId());
        progress.setApplicationNo(application.getApplicationNo());
        progress.setStepName(stepName);
        progress.setStepStatus(stepStatus);
        progress.setHandlerName(handlerName);
        progress.setHandleTime(DateUtils.getNowDate());
        progress.setHandleOpinion(opinion);
        progress.setCreateBy(handlerName);
        progressMapper.insertProgress(progress);
    }

    private void addNotice(SeaBusinessApplication application, String title, String content, String noticeType)
    {
        SeaBusinessNotice notice = new SeaBusinessNotice();
        notice.setApplicationId(application.getApplicationId());
        notice.setApplicationNo(application.getApplicationNo());
        notice.setNoticeTitle(title);
        notice.setNoticeContent(content);
        notice.setNoticeType(noticeType);
        notice.setReadFlag("0");
        notice.setReceiverName(application.getApplicantName());
        notice.setCreateBy(StringUtils.defaultString(application.getAuditBy(), application.getCreateBy()));
        noticeMapper.insertNotice(notice);
    }
}
