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
import com.daliansea.ai.system.domain.SeaContentPublish;
import com.daliansea.ai.system.service.ISeaContentPublishService;

/**
 * 内容发布操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/content")
public class SeaContentPublishController extends BaseController
{
    @Autowired
    private ISeaContentPublishService contentService;

    @PreAuthorize("@ss.hasPermi('business:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaContentPublish content)
    {
        startPage();
        List<SeaContentPublish> list = contentService.selectContentList(content);
        return getDataTable(list);
    }

    @Log(title = "内容发布", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:content:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaContentPublish content)
    {
        List<SeaContentPublish> list = contentService.selectContentList(content);
        ExcelUtil<SeaContentPublish> util = new ExcelUtil<SeaContentPublish>(SeaContentPublish.class);
        util.exportExcel(response, list, "内容发布数据");
    }

    @PreAuthorize("@ss.hasPermi('business:content:query')")
    @GetMapping("/{contentId}")
    public AjaxResult getInfo(@PathVariable Long contentId)
    {
        return success(contentService.selectContentById(contentId));
    }

    @PreAuthorize("@ss.hasPermi('business:content:add')")
    @Log(title = "内容发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaContentPublish content)
    {
        content.setCreateBy(getUsername());
        return toAjax(contentService.insertContent(content));
    }

    @PreAuthorize("@ss.hasPermi('business:content:edit')")
    @Log(title = "内容发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaContentPublish content)
    {
        content.setUpdateBy(getUsername());
        return toAjax(contentService.updateContent(content));
    }

    @PreAuthorize("@ss.hasPermi('business:content:generate')")
    @Log(title = "AI文案生成", businessType = BusinessType.OTHER)
    @PostMapping("/generate")
    public AjaxResult generate(@Validated @RequestBody SeaContentPublish content)
    {
        content.setCreateBy(getUsername());
        return success(contentService.generateContent(content));
    }

    @PreAuthorize("@ss.hasPermi('business:content:publish')")
    @Log(title = "内容推流发布", businessType = BusinessType.UPDATE)
    @PutMapping("/{contentId}/publish")
    public AjaxResult publish(@PathVariable Long contentId)
    {
        return toAjax(contentService.publishContent(contentId, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('business:content:remove')")
    @Log(title = "内容发布", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds)
    {
        return toAjax(contentService.deleteContentByIds(contentIds));
    }
}
