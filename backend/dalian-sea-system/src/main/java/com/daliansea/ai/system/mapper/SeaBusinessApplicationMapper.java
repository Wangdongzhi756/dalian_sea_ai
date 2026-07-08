package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaBusinessApplication;

/**
 * 业务申请 数据层
 *
 * @author daliansea
 */
public interface SeaBusinessApplicationMapper
{
    public List<SeaBusinessApplication> selectApplicationList(SeaBusinessApplication application);

    public SeaBusinessApplication selectApplicationById(Long applicationId);

    public SeaBusinessApplication checkApplicationNoUnique(String applicationNo);

    public int insertApplication(SeaBusinessApplication application);

    public int updateApplication(SeaBusinessApplication application);

    public int deleteApplicationByIds(Long[] applicationIds);
}
