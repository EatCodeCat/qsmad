package com.qsm.ad.repositroys;

import com.qsm.ad.entitys.Task;
import com.qsm.ad.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by think on 2017/7/30.
 */
public interface TaskRepository extends BaseRepository<Task> {

}
