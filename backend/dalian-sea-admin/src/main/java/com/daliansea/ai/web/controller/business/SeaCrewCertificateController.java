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
import com.daliansea.ai.system.domain.SeaCrewCertificate;
import com.daliansea.ai.system.service.ISeaCrewCertificateService;

/**
 * 船员证书档案操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/crewCertificate")
public class SeaCrewCertificateController extends BaseController
{
    @Autowired
    private ISeaCrewCertificateService certificateService;

    @PreAuthorize("@ss.hasPermi('business:crewCertificate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaCrewCertificate certificate)
    {
        startPage();
        List<SeaCrewCertificate> list = certificateService.selectCertificateList(certificate);
        return getDataTable(list);
    }

    @Log(title = "船员证书", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:crewCertificate:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaCrewCertificate certificate)
    {
        List<SeaCrewCertificate> list = certificateService.selectCertificateList(certificate);
        ExcelUtil<SeaCrewCertificate> util = new ExcelUtil<SeaCrewCertificate>(SeaCrewCertificate.class);
        util.exportExcel(response, list, "船员证书数据");
    }

    @PreAuthorize("@ss.hasPermi('business:crewCertificate:query')")
    @GetMapping(value = "/{certificateId}")
    public AjaxResult getInfo(@PathVariable Long certificateId)
    {
        return success(certificateService.selectCertificateById(certificateId));
    }

    @PreAuthorize("@ss.hasPermi('business:crewCertificate:add')")
    @Log(title = "船员证书", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaCrewCertificate certificate)
    {
        if (!certificateService.checkCertificateNoUnique(certificate))
        {
            return error("新增证书'" + certificate.getCertificateNo() + "'失败，证书编号已存在");
        }
        certificate.setCreateBy(getUsername());
        return toAjax(certificateService.insertCertificate(certificate));
    }

    @PreAuthorize("@ss.hasPermi('business:crewCertificate:edit')")
    @Log(title = "船员证书", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaCrewCertificate certificate)
    {
        if (!certificateService.checkCertificateNoUnique(certificate))
        {
            return error("修改证书'" + certificate.getCertificateNo() + "'失败，证书编号已存在");
        }
        certificate.setUpdateBy(getUsername());
        return toAjax(certificateService.updateCertificate(certificate));
    }

    @PreAuthorize("@ss.hasPermi('business:crewCertificate:remove')")
    @Log(title = "船员证书", businessType = BusinessType.DELETE)
    @DeleteMapping("/{certificateIds}")
    public AjaxResult remove(@PathVariable Long[] certificateIds)
    {
        return toAjax(certificateService.deleteCertificateByIds(certificateIds));
    }
}
