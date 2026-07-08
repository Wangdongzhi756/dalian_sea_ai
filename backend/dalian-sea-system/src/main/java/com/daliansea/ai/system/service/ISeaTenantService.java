package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaTenant;

/**
 * 租户主体 服务层
 *
 * @author daliansea
 */
public interface ISeaTenantService
{
    public List<SeaTenant> selectTenantList(SeaTenant tenant);

    public List<SeaTenant> selectTenantOptions();

    public SeaTenant selectTenantById(Long tenantId);

    public boolean checkTenantCodeUnique(SeaTenant tenant);

    public int insertTenant(SeaTenant tenant);

    public int updateTenant(SeaTenant tenant);

    public int deleteTenantByIds(Long[] tenantIds);
}
