package com.qsm.ad.services;

import com.qsm.ad.entitys.QsmUser;
import com.qsm.ad.entitys.User;
import com.qsm.ad.repositroys.QsmUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by think on 2017/8/11.
 */
@Service("qsmUserService")
public class QsmUserService extends CrudService<QsmUser> {

    QsmUserRepository qsmUserRepository;

    @Autowired
    public void setRepository(QsmUserRepository qsmUserRepository) {
        this.qsmUserRepository = qsmUserRepository;
        super.setRepository(qsmUserRepository);
    }

    public QsmUser findByUsername(String username) {
        return qsmUserRepository.findByQsmUsername(username);
    }

}
