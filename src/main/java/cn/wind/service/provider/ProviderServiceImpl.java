package cn.wind.service.provider;

import cn.wind.dao.bill.BillDao;
import cn.wind.dao.provider.ProviderDao;
import cn.wind.pojo.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author Wind
 * @see 2020-02-18 19:10
 */
@Service("providerService")
@Scope
public class ProviderServiceImpl implements ProviderService{

    @Autowired
    private ProviderDao providerDao;
    @Autowired
    private BillDao billDao;

    @Override
    public List<Provider> findProviderList(String proName, String proCode, Integer currentPageNo, Integer pageSize) {
        currentPageNo = (currentPageNo-1)*pageSize;
        return providerDao.findProviderList(proName,proCode,currentPageNo,pageSize);
    }

    @Override
    public List<Provider> findProviderList() {
        return providerDao.findProList();
    }

    @Override
    public int findProviderCount(String proName, String proCode) {
        return providerDao.findProviderCount(proName,proCode);
    }

    @Override
    public boolean addProvider(Provider provider) {
        return providerDao.addProvider(provider) != 0;
    }

    @Override
    public Provider findProviderById(Integer id) {
        return providerDao.findProviderById(id);
    }

    @Override
    public boolean updateProvider(Provider provider) {
        return providerDao.updateProvider(provider) != 0;
    }

    @Override
    public boolean deleteProviderById(Integer delId){
        /*boolean flag = true;
        int billCount = billDao.getBillCountByProviderId(delId);
        if(billCount > 0 ){//先订单信息
            billDao.deleteBillByProviderId(delId);
        }
        //先删除该条记录的上传文件
        Provider provider = providerDao.getProviderById(delId);
        if(provider.getCompanyLicPicPath() != null && !provider.getCompanyLicPicPath().equals("")){
            //删除服务器上企业营业执照照片
            File file = new File(provider.getCompanyLicPicPath());
            if(file.exists()){
                flag = file.delete();
            }
        }
        if(flag && provider.getOrgCodePicPath() != null && !provider.getOrgCodePicPath().equals("")){
            //删除服务器上组织机构代码证照片
            File file = new File(provider.getOrgCodePicPath());
            if(file.exists()){
                flag = file.delete();
            }
        }
        if(!flag){
            throw new Exception();
        }
        providerDao.deleteProviderById(delId);*/
        return false;
    }
}
