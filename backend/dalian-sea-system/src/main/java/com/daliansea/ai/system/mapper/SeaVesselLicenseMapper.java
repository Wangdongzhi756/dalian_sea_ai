package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaVesselLicense;

/**
 * 船舶证照附件 数据层
 *
 * @author daliansea
 */
public interface SeaVesselLicenseMapper
{
    public List<SeaVesselLicense> selectLicenseList(SeaVesselLicense license);

    public SeaVesselLicense selectLicenseById(Long licenseId);

    public SeaVesselLicense checkLicenseNoUnique(String licenseNo);

    public int insertLicense(SeaVesselLicense license);

    public int updateLicense(SeaVesselLicense license);

    public int deleteLicenseByIds(Long[] licenseIds);
}
