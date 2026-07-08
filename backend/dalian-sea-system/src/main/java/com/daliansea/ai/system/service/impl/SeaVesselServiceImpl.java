package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaVessel;
import com.daliansea.ai.system.mapper.SeaVesselMapper;
import com.daliansea.ai.system.service.ISeaVesselService;

/**
 * 船舶档案 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaVesselServiceImpl implements ISeaVesselService
{
    @Autowired
    private SeaVesselMapper vesselMapper;

    @Override
    public List<SeaVessel> selectVesselList(SeaVessel vessel)
    {
        return vesselMapper.selectVesselList(vessel);
    }

    @Override
    public SeaVessel selectVesselById(Long vesselId)
    {
        return vesselMapper.selectVesselById(vesselId);
    }

    @Override
    public boolean checkVesselCodeUnique(SeaVessel vessel)
    {
        Long vesselId = StringUtils.isNull(vessel.getVesselId()) ? -1L : vessel.getVesselId();
        SeaVessel info = vesselMapper.checkVesselCodeUnique(vessel.getVesselCode());
        if (StringUtils.isNotNull(info) && info.getVesselId().longValue() != vesselId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertVessel(SeaVessel vessel)
    {
        return vesselMapper.insertVessel(vessel);
    }

    @Override
    public int updateVessel(SeaVessel vessel)
    {
        return vesselMapper.updateVessel(vessel);
    }

    @Override
    public int deleteVesselByIds(Long[] vesselIds)
    {
        return vesselMapper.deleteVesselByIds(vesselIds);
    }
}
