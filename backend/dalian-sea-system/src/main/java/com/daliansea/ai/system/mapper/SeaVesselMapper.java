package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaVessel;

/**
 * 船舶档案 数据层
 *
 * @author daliansea
 */
public interface SeaVesselMapper
{
    public List<SeaVessel> selectVesselList(SeaVessel vessel);

    public SeaVessel selectVesselById(Long vesselId);

    public int countVesselByTenantId(Long tenantId);

    public int insertVessel(SeaVessel vessel);

    public int updateVessel(SeaVessel vessel);

    public int deleteVesselById(Long vesselId);

    public int deleteVesselByIds(Long[] vesselIds);

    public SeaVessel checkVesselCodeUnique(String vesselCode);
}
