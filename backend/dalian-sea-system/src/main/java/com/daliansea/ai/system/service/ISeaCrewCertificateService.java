package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaCrewCertificate;

/**
 * 船员证书档案 服务层
 *
 * @author daliansea
 */
public interface ISeaCrewCertificateService
{
    public List<SeaCrewCertificate> selectCertificateList(SeaCrewCertificate certificate);

    public SeaCrewCertificate selectCertificateById(Long certificateId);

    public boolean checkCertificateNoUnique(SeaCrewCertificate certificate);

    public int insertCertificate(SeaCrewCertificate certificate);

    public int updateCertificate(SeaCrewCertificate certificate);

    public int deleteCertificateByIds(Long[] certificateIds);
}
