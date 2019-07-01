package cn.jbone.cms.api.impl;

import cn.jbone.cms.api.SiteApi;
import cn.jbone.cms.common.dataobject.SiteDO;
import cn.jbone.cms.common.dataobject.search.SiteSearchDO;
import cn.jbone.cms.core.service.SiteService;
import cn.jbone.common.dataobject.PagedResponseDO;
import cn.jbone.common.exception.ObjectNotFoundException;
import cn.jbone.common.rpc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteApiImpl implements SiteApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SiteService siteService;

    @Override
    public Result<SiteDO> getByDomain(String domain) {
        SiteDO siteDO = null;
        try {
            siteDO = siteService.getRootSiteByDomain(domain);
        } catch (Exception e) {
            logger.warn("site getByDomain error.",e);
            return Result.wrap500Error("获取站点失败");
        }
        return Result.wrapSuccess(siteDO);
    }

    @Override
    public Result<SiteDO> getById(Integer id) {
        SiteDO siteDO = null;
        try {
            siteDO = siteService.getById(id);
        } catch (ObjectNotFoundException e){
            return Result.wrap500Error(e.getMessage());
        } catch (Exception e) {
            logger.warn("site getByDomain error.",e);
            return Result.wrap500Error("获取站点失败");
        }
        return Result.wrapSuccess(siteDO);
    }

    @Override
    public Result<Void> delete(Integer id) {
        try {
            siteService.delete(id);
        } catch (ObjectNotFoundException e){
            return Result.wrap500Error(e.getMessage());
        } catch (Exception e) {
            logger.warn("site getByDomain error.",e);
            return Result.wrap500Error("删除失败");
        }
        return Result.wrapSuccess();
    }

    @Override
    public Result<Void> addOrUpdate(@RequestBody SiteDO siteDO) {
        try {
            siteService.addOrUpdate(siteDO);
        }catch (Exception e) {
            logger.warn("site addOrUpdate error.",e);
            return Result.wrap500Error("更新站点失败");
        }

        return Result.wrapSuccess();
    }

    @Override
    public Result<PagedResponseDO<SiteDO>> commonRequest(@RequestBody SiteSearchDO siteSearchDO) {
        PagedResponseDO<SiteDO> pagedResponseDO = null;
        try {
            pagedResponseDO = siteService.commonRequest(siteSearchDO);
        } catch (Exception e) {
            logger.warn("site commonRequest error.",e);
            return Result.wrap500Error(e.getMessage());
        }
        return Result.wrapSuccess(pagedResponseDO);
    }
}
