package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaVesselLicense;
import com.daliansea.ai.system.mapper.SeaVesselLicenseMapper;
import com.daliansea.ai.system.service.ISeaVesselLicenseService;

/**
 * 船舶证照附件 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaVesselLicenseServiceImpl implements ISeaVesselLicenseService
{
    @Autowired
    private SeaVesselLicenseMapper licenseMapper;

    @Override
    public List<SeaVesselLicense> selectLicenseList(SeaVesselLicense license)
    {
        return licenseMapper.selectLicenseList(license);
    }

    @Override
    public SeaVesselLicense selectLicenseById(Long licenseId)
    {
        return licenseMapper.selectLicenseById(licenseId);
    }

    @Override
    public boolean checkLicenseNoUnique(SeaVesselLicense license)
    {
        Long licenseId = StringUtils.isNull(license.getLicenseId()) ? -1L : license.getLicenseId();
        SeaVesselLicense info = licenseMapper.checkLicenseNoUnique(license.getLicenseNo());
        if (StringUtils.isNotNull(info) && info.getLicenseId().longValue() != licenseId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertLicense(SeaVesselLicense license)
    {
        return licenseMapper.insertLicense(license);
    }

    @Override
    public int updateLicense(SeaVesselLicense license)
    {
        return licenseMapper.updateLicense(license);
    }

    @Override
    public int deleteLicenseByIds(Long[] licenseIds)
    {
        return licenseMapper.deleteLicenseByIds(licenseIds);
    }
}
