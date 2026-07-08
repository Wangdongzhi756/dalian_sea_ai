package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaVessel;

/**
 * 船舶档案 服务层
 *
 * @author daliansea
 */
public interface ISeaVesselService
{
    public List<SeaVessel> selectVesselList(SeaVessel vessel);

    public SeaVessel selectVesselById(Long vesselId);

    public boolean checkVesselCodeUnique(SeaVessel vessel);

    public int insertVessel(SeaVessel vessel);

    public int updateVessel(SeaVessel vessel);

    public int deleteVesselByIds(Long[] vesselIds);
}
