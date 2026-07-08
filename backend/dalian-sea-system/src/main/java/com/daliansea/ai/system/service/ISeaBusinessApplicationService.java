package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaBusinessApplication;
import com.daliansea.ai.system.domain.SeaBusinessNotice;
import com.daliansea.ai.system.domain.SeaBusinessProgress;

/**
 * 业务申请 服务层
 *
 * @author daliansea
 */
public interface ISeaBusinessApplicationService
{
    public List<SeaBusinessApplication> selectApplicationList(SeaBusinessApplication application);

    public SeaBusinessApplication selectApplicationById(Long applicationId);

    public List<SeaBusinessProgress> selectProgressList(Long applicationId);

    public List<SeaBusinessNotice> selectNoticeList(SeaBusinessNotice notice);

    public boolean checkApplicationNoUnique(SeaBusinessApplication application);

    public int insertApplication(SeaBusinessApplication application);

    public int updateApplication(SeaBusinessApplication application);

    public int auditApplication(SeaBusinessApplication application);

    public int readNoticeByIds(Long[] noticeIds);

    public int deleteApplicationByIds(Long[] applicationIds);
}
