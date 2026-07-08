package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaCrew;

/**
 * 船员档案 数据层
 *
 * @author daliansea
 */
public interface SeaCrewMapper
{
    public List<SeaCrew> selectCrewList(SeaCrew crew);

    public SeaCrew selectCrewById(Long crewId);

    public SeaCrew checkCrewCodeUnique(String crewCode);

    public int insertCrew(SeaCrew crew);

    public int updateCrew(SeaCrew crew);

    public int deleteCrewByIds(Long[] crewIds);
}
