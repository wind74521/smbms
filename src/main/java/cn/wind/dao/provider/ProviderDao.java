package cn.wind.dao.provider;

import cn.wind.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-18 19:11
 */
@Repository
@Scope
public interface ProviderDao {

    /**
     * 增加用户信息
     *
     * @param provider
     * @return
     */
    int addProvider(Provider provider);

    /**
     * 通过条件查询-providerList
     *
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    List<Provider> findProviderList(@Param("proName") String proName, @Param("proCode") String proCode,
                                          @Param("currentPageNo") Integer currentPageNo, @Param("pageSize") Integer pageSize);

    /**
     * 获取供应商列表
     *
     * @return
     */
    List<Provider> findProList();


    /**
     * 通过条件查询-供应商表记录数
     *
     * @param proName
     * @param proCode
     * @return
     */
    int findProviderCount(@Param("proName") String proName, @Param("proCode") String proCode);

    /**
     * 通过供应商id删除供应商信息
     *
     * @param delId
     * @return
     */
    int deleteProviderById(@Param("id") Integer delId);

    /**
     * 根据provider id 获取供应商信息
     *
     * @param id
     * @return
     */
    Provider findProviderById(@Param("id") Integer id);

    /**
     * 修改供应商
     *
     * @param provider
     * @return
     */
    int updateProvider(Provider provider);
}