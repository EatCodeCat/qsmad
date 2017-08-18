package com.qsm.ad.repositroys;

import com.qsm.ad.entitys.QsmUser;
import com.qsm.ad.entitys.User;

/**
 * Created by think on 2017/7/30.
 */
public interface QsmUserRepository extends BaseRepository<QsmUser> {
    QsmUser findByQsmUsername(String qsmUsername);

}
