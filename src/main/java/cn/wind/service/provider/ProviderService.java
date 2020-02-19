package cn.wind.service.provider;

import cn.wind.pojo.Provider;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-18 19:10
 */
public interface ProviderService {

    /**
     * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    List<Provider> findProviderList(String proName, String proCode, Integer currentPageNo, Integer pageSize);

    /**
     * 获取供应商列表
     * @return
     * @throws Exception
     */
    List<Provider> findProviderList();

    /**
     * 通过条件查询-供应商表记录数
     * @param proName
     * @param proCode
     * @return
     * @throws Exception
     */
    int findProviderCount(String proName,String proCode);

    /**
     * 增加供应商
     * @param provider
     * @return
     * @throws Exception
     */
    boolean addProvider(Provider provider);

    /**
     * 通过proId获取Provider
     * @param id
     * @return
     * @throws Exception
     */
    Provider findProviderById(Integer id);

    /**
     * 修改供应商信息
     * @param provider
     * @return
     * @throws Exception
     */
    boolean updateProvider(Provider provider);

    /**
     * 通过proId删除Provider
     * @param delId
     * @return
     * @throws Exception
     */
    boolean deleteProviderById(Integer delId);
}
