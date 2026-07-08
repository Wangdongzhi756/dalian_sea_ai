package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaCrew;

/**
 * 船员档案 服务层
 *
 * @author daliansea
 */
public interface ISeaCrewService
{
    public List<SeaCrew> selectCrewList(SeaCrew crew);

    public SeaCrew selectCrewById(Long crewId);

    public boolean checkCrewCodeUnique(SeaCrew crew);

    public int insertCrew(SeaCrew crew);

    public int updateCrew(SeaCrew crew);

    public int deleteCrewByIds(Long[] crewIds);
}
