package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaTenant;

/**
 * 租户主体 数据层
 *
 * @author daliansea
 */
public interface SeaTenantMapper
{
    public List<SeaTenant> selectTenantList(SeaTenant tenant);

    public List<SeaTenant> selectTenantOptions();

    public SeaTenant selectTenantById(Long tenantId);

    public int insertTenant(SeaTenant tenant);

    public int updateTenant(SeaTenant tenant);

    public int deleteTenantById(Long tenantId);

    public int deleteTenantByIds(Long[] tenantIds);

    public SeaTenant checkTenantCodeUnique(String tenantCode);
}
