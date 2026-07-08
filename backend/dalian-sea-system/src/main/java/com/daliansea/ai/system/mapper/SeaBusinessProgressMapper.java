package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaBusinessProgress;

/**
 * 业务进度 数据层
 *
 * @author daliansea
 */
public interface SeaBusinessProgressMapper
{
    public List<SeaBusinessProgress> selectProgressList(SeaBusinessProgress progress);

    public int insertProgress(SeaBusinessProgress progress);

    public int deleteProgressByApplicationIds(Long[] applicationIds);
}
