package com.qsm.ad.repositroys;

import com.qsm.ad.entitys.Task;
import com.qsm.ad.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by think on 2017/7/30.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByUsername(String username);

}
