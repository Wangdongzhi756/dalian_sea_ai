package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.constant.UserConstants;
import com.daliansea.ai.common.exception.ServiceException;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaTenant;
import com.daliansea.ai.system.mapper.SeaTenantMapper;
import com.daliansea.ai.system.mapper.SeaVesselMapper;
import com.daliansea.ai.system.service.ISeaTenantService;

/**
 * 租户主体 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaTenantServiceImpl implements ISeaTenantService
{
    @Autowired
    private SeaTenantMapper tenantMapper;

    @Autowired
    private SeaVesselMapper vesselMapper;

    @Override
    public List<SeaTenant> selectTenantList(SeaTenant tenant)
    {
        return tenantMapper.selectTenantList(tenant);
    }

    @Override
    public List<SeaTenant> selectTenantOptions()
    {
        return tenantMapper.selectTenantOptions();
    }

    @Override
    public SeaTenant selectTenantById(Long tenantId)
    {
        return tenantMapper.selectTenantById(tenantId);
    }

    @Override
    public boolean checkTenantCodeUnique(SeaTenant tenant)
    {
        Long tenantId = StringUtils.isNull(tenant.getTenantId()) ? -1L : tenant.getTenantId();
        SeaTenant info = tenantMapper.checkTenantCodeUnique(tenant.getTenantCode());
        if (StringUtils.isNotNull(info) && info.getTenantId().longValue() != tenantId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertTenant(SeaTenant tenant)
    {
        return tenantMapper.insertTenant(tenant);
    }

    @Override
    public int updateTenant(SeaTenant tenant)
    {
        return tenantMapper.updateTenant(tenant);
    }

    @Override
    public int deleteTenantByIds(Long[] tenantIds)
    {
        for (Long tenantId : tenantIds)
        {
            SeaTenant tenant = selectTenantById(tenantId);
            if (tenant != null && vesselMapper.countVesselByTenantId(tenantId) > 0)
            {
                throw new ServiceException(String.format("%1$s已关联船舶,不能删除", tenant.getTenantName()));
            }
        }
        return tenantMapper.deleteTenantByIds(tenantIds);
    }
}
