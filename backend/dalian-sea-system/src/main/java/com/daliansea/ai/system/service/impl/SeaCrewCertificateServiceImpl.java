package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaCrewCertificate;
import com.daliansea.ai.system.mapper.SeaCrewCertificateMapper;
import com.daliansea.ai.system.service.ISeaCrewCertificateService;

/**
 * 船员证书档案 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaCrewCertificateServiceImpl implements ISeaCrewCertificateService
{
    @Autowired
    private SeaCrewCertificateMapper certificateMapper;

    @Override
    public List<SeaCrewCertificate> selectCertificateList(SeaCrewCertificate certificate)
    {
        return certificateMapper.selectCertificateList(certificate);
    }

    @Override
    public SeaCrewCertificate selectCertificateById(Long certificateId)
    {
        return certificateMapper.selectCertificateById(certificateId);
    }

    @Override
    public boolean checkCertificateNoUnique(SeaCrewCertificate certificate)
    {
        Long certificateId = StringUtils.isNull(certificate.getCertificateId()) ? -1L : certificate.getCertificateId();
        SeaCrewCertificate info = certificateMapper.checkCertificateNoUnique(certificate.getCertificateNo());
        if (StringUtils.isNotNull(info) && info.getCertificateId().longValue() != certificateId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertCertificate(SeaCrewCertificate certificate)
    {
        return certificateMapper.insertCertificate(certificate);
    }

    @Override
    public int updateCertificate(SeaCrewCertificate certificate)
    {
        return certificateMapper.updateCertificate(certificate);
    }

    @Override
    public int deleteCertificateByIds(Long[] certificateIds)
    {
        return certificateMapper.deleteCertificateByIds(certificateIds);
    }
}
