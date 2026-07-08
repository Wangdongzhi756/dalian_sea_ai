package com.daliansea.ai.web.controller.business;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daliansea.ai.common.annotation.Log;
import com.daliansea.ai.common.core.controller.BaseController;
import com.daliansea.ai.common.core.domain.AjaxResult;
import com.daliansea.ai.common.core.page.TableDataInfo;
import com.daliansea.ai.common.enums.BusinessType;
import com.daliansea.ai.common.utils.poi.ExcelUtil;
import com.daliansea.ai.system.domain.SeaVesselLicense;
import com.daliansea.ai.system.service.ISeaVesselLicenseService;

/**
 * 船舶证照附件操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/vesselLicense")
public class SeaVesselLicenseController extends BaseController
{
    @Autowired
    private ISeaVesselLicenseService licenseService;

    @PreAuthorize("@ss.hasPermi('business:vesselLicense:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaVesselLicense license)
    {
        startPage();
        List<SeaVesselLicense> list = licenseService.selectLicenseList(license);
        return getDataTable(list);
    }

    @Log(title = "船舶证照", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:vesselLicense:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaVesselLicense license)
    {
        List<SeaVesselLicense> list = licenseService.selectLicenseList(license);
        ExcelUtil<SeaVesselLicense> util = new ExcelUtil<SeaVesselLicense>(SeaVesselLicense.class);
        util.exportExcel(response, list, "船舶证照数据");
    }

    @PreAuthorize("@ss.hasPermi('business:vesselLicense:query')")
    @GetMapping(value = "/{licenseId}")
    public AjaxResult getInfo(@PathVariable Long licenseId)
    {
        return success(licenseService.selectLicenseById(licenseId));
    }

    @PreAuthorize("@ss.hasPermi('business:vesselLicense:add')")
    @Log(title = "船舶证照", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaVesselLicense license)
    {
        if (!licenseService.checkLicenseNoUnique(license))
        {
            return error("新增证照'" + license.getLicenseNo() + "'失败，证照编号已存在");
        }
        license.setCreateBy(getUsername());
        return toAjax(licenseService.insertLicense(license));
    }

    @PreAuthorize("@ss.hasPermi('business:vesselLicense:edit')")
    @Log(title = "船舶证照", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaVesselLicense license)
    {
        if (!licenseService.checkLicenseNoUnique(license))
        {
            return error("修改证照'" + license.getLicenseNo() + "'失败，证照编号已存在");
        }
        license.setUpdateBy(getUsername());
        return toAjax(licenseService.updateLicense(license));
    }

    @PreAuthorize("@ss.hasPermi('business:vesselLicense:remove')")
    @Log(title = "船舶证照", businessType = BusinessType.DELETE)
    @DeleteMapping("/{licenseIds}")
    public AjaxResult remove(@PathVariable Long[] licenseIds)
    {
        return toAjax(licenseService.deleteLicenseByIds(licenseIds));
    }
}
