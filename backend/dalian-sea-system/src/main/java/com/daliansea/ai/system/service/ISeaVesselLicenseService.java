package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaVesselLicense;

/**
 * 船舶证照附件 服务层
 *
 * @author daliansea
 */
public interface ISeaVesselLicenseService
{
    public List<SeaVesselLicense> selectLicenseList(SeaVesselLicense license);

    public SeaVesselLicense selectLicenseById(Long licenseId);

    public boolean checkLicenseNoUnique(SeaVesselLicense license);

    public int insertLicense(SeaVesselLicense license);

    public int updateLicense(SeaVesselLicense license);

    public int deleteLicenseByIds(Long[] licenseIds);
}
