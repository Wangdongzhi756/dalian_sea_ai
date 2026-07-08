package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaBusinessNotice;

/**
 * 业务通知 数据层
 *
 * @author daliansea
 */
public interface SeaBusinessNoticeMapper
{
    public List<SeaBusinessNotice> selectNoticeList(SeaBusinessNotice notice);

    public int insertNotice(SeaBusinessNotice notice);

    public int readNoticeByIds(Long[] noticeIds);

    public int deleteNoticeByApplicationIds(Long[] applicationIds);
}
