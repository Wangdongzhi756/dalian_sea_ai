package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaCrewCertificate;

/**
 * 船员证书档案 数据层
 *
 * @author daliansea
 */
public interface SeaCrewCertificateMapper
{
    public List<SeaCrewCertificate> selectCertificateList(SeaCrewCertificate certificate);

    public SeaCrewCertificate selectCertificateById(Long certificateId);

    public SeaCrewCertificate checkCertificateNoUnique(String certificateNo);

    public int insertCertificate(SeaCrewCertificate certificate);

    public int updateCertificate(SeaCrewCertificate certificate);

    public int deleteCertificateByIds(Long[] certificateIds);
}
