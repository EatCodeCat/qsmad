package com.qsm.ad.services;

import com.qsm.ad.repositroys.BaseRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by think on 2017/7/30.
 */
public abstract class CrudService<T> {
    private org.slf4j.Logger log = LoggerFactory.getLogger(CrudService.class);
    @PersistenceContext
    protected EntityManager em;


    protected BaseRepository<T> repository;

    public void setRepository(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Transactional
    public T save(T t) {
        return repository.save(t);
    }

    @Transactional
    public T update(T t) {
        return em.merge(t);
    }

    public void delete(T t) {
        repository.delete(t);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public T findOne(int id) {
        return repository.findOne(id);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> findAll(Pageable pageable, Map<String, Object> criterial) {
        criterial.remove("page");
        criterial.remove("size");
        Specification specification = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (criterial != null) {
                    List<Predicate> list = new ArrayList();
                    for (Map.Entry<String, Object> p : criterial.entrySet()) {

                        if ("String".equals(root.get(p.getKey()).getJavaType().getSimpleName())) {
                            list.add(criteriaBuilder.equal(root.get(p.getKey()), p.getValue()));
                        } else {
                            list.add(criteriaBuilder.equal(root.get(p.getKey()), p.getValue()));
                        }
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                } else {
                    return null;
                }
            }
        };
        Page<T> page = repository.findAll(specification, pageable);
        return page;
    }

    /**
     * 查询数据集合
     *
     * @param sql    查询sql sql中的参数用:name格式
     * @param params 查询参数map格式，key对应参数中的:name
     * @return List
     */
    @SuppressWarnings("rawtypes")
    public <T> List<T> queryListEntity(String sql, Map<String, Object> params, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        log.info(sql);
        Query query = em.createNativeQuery(sql);
        setParameter(params, query);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List rows = query.getResultList();
        return mapList2JavaList(clazz.newInstance(), rows);
    }

    public <T> Page<T> queryListEnityWithPage(String sql, String countSQl, Map<String, Object> params, Class<T> clazz, int page, int size) {
        log.info(sql);
        Query query = em.createNativeQuery(sql);
        setParameter(params, query);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setMaxResults(size);
        query.setFirstResult(page * size);
        List<T> rows = query.getResultList();
        Query countQuery = em.createNativeQuery(countSQl);
        setParameter(params, countQuery);
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        return new PageImpl<T>(rows, new PageRequest(page, size), total.longValue());
    }

    private void setParameter(Map<String, Object> params, Query query) {
        if (params != null) {
            for (Map.Entry<String, Object> key : params.entrySet()) {
                query.setParameter(key.getKey(), key.getValue());
            }
        }
    }

    /**
     * 从map集合中获取属性值
     *
     * @param <E>
     * @param map          map集合
     * @param key          键对
     * @param defaultValue 默认值
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final <E> E get(Map map, Object key, E defaultValue) {
        Object o = map.get(key);
        if (o == null)
            return defaultValue;
        return (E) o;
    }

    /**
     * Map集合对象转化成 JavaBean集合对象
     *
     * @param javaBean JavaBean实例对象
     * @param mapList  Map数据集对象
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    public <T> List<T> mapList2JavaList(T javaBean, List<Map> mapList) {
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        List<T> objectList = new ArrayList<T>();

        T object = null;
        for (Map map : mapList) {
            if (map != null) {
                object = map2Java(javaBean, map);
                objectList.add(object);
            }
        }

        return objectList;

    }

    /**
     * Map对象转化成 JavaBean对象
     *
     * @param javaBean JavaBean实例对象
     * @param map      Map对象
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "hiding"})
    public <T> T map2Java(T javaBean, Map map) {
        // 创建 JavaBean 对象
        Object obj = null;
        try {
            obj = javaBean.getClass().newInstance();
            BeanUtils.populate(obj, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }


}
