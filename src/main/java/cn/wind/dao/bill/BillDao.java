package cn.wind.dao.bill;

import cn.wind.pojo.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-18 21:15
 */
@Repository
@Scope
public interface BillDao {
    /**
     * 根据供应商ID查询订单数量
     * @param providerId
     * @return
     * @throws Exception
     */
    int findBillCountByProviderId(@Param("providerId")Integer providerId);


    /**
     * 增加订单
     * @param bill
     * @return
     * @throws Exception
     */
    int addBill(Bill bill);


    /**
     * 通过查询条件获取供应商列表-getBillList
     * @param productName
     * @param providerId
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    List<Bill> findBillList(@Param("productName")String productName, @Param("providerId")Integer providerId, @Param("isPayment")Integer isPayment,
                                  @Param("from")Integer currentPageNo, @Param("pageSize")Integer pageSize);

    /**
     * 通过条件查询-订单表记录数
     * @param productName
     * @param providerId
     * @return
     */
    int findBillCount(@Param("productName")String productName,@Param("providerId")Integer providerId,@Param("isPayment")Integer isPayment);

    /**
     * 通过delId删除Bill
     * @param delId
     * @return
     */
    int deleteBillById(@Param("id")Integer delId);


    /**
     * 通过billId获取Bill
     * @param id
     * @return
     */
    Bill findBillById(@Param("id")Integer id);

    /**
     * 修改订单信息
     * @param bill
     * @return
     */
    int updateBill(Bill bill);

    /**
     * 根据供应商ID删除订单信息
     * @param providerId
     * @return
     */
    int deleteBillByProviderId(@Param("providerId")Integer providerId);
}
