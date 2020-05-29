package stc.inno.realExample.dao;


import stc.inno.realExample.pojo.Mobile;

public interface MobileDao {
    Long addMobile(Mobile mobile);

    Mobile getMobileById(Long id);

    boolean updateMobileById(Mobile mobile);

    boolean deleteMobileById(Long id);
}
