package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaCrew;
import com.daliansea.ai.system.mapper.SeaCrewMapper;
import com.daliansea.ai.system.service.ISeaCrewService;

/**
 * 船员档案 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaCrewServiceImpl implements ISeaCrewService
{
    @Autowired
    private SeaCrewMapper crewMapper;

    @Override
    public List<SeaCrew> selectCrewList(SeaCrew crew)
    {
        return crewMapper.selectCrewList(crew);
    }

    @Override
    public SeaCrew selectCrewById(Long crewId)
    {
        return crewMapper.selectCrewById(crewId);
    }

    @Override
    public boolean checkCrewCodeUnique(SeaCrew crew)
    {
        Long crewId = StringUtils.isNull(crew.getCrewId()) ? -1L : crew.getCrewId();
        SeaCrew info = crewMapper.checkCrewCodeUnique(crew.getCrewCode());
        if (StringUtils.isNotNull(info) && info.getCrewId().longValue() != crewId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertCrew(SeaCrew crew)
    {
        return crewMapper.insertCrew(crew);
    }

    @Override
    public int updateCrew(SeaCrew crew)
    {
        return crewMapper.updateCrew(crew);
    }

    @Override
    public int deleteCrewByIds(Long[] crewIds)
    {
        return crewMapper.deleteCrewByIds(crewIds);
    }
}
