package com.yen.springBootAdvance1.service;

// https://www.youtube.com/watch?v=Un_YC0fBKls&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=4
// https://www.youtube.com/watch?v=4dRfvI1tnqs&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=5
// https://www.youtube.com/watch?v=9GiDJMkIdns&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=6

import com.yen.springBootAdvance1.bean.Employee;
import com.yen.springBootAdvance1.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     *  @Cacheable
     *
     *   -> Cache method result, will use cache when there is same request again
     *
     *   -> CacheManager for cache management, every cache has its name in CacheManager
     *
     *   -> Cache param
     *
     *      - cacheNames/value : cache name (put result in), array format
     *
     *      - key : key for cache (k-v), use method param name by default
     *          - use SpEl, example: #id (param id), #a0, #p0, #root.args[0]
     *
     *      - keyGenerator : generate key, we can define our own keyGenerator as well
     *          - (can only use either key or keyGenerator at once)
     *
     *      - CacheManager : declare which CacheManager to use
     *
     *      - CacheResolver : similar as CacheManager
     *          - (can only use either CacheManager or CacheResolver at once)
     *
     *      - condition : if condition is true, use cache
     *          - "#a0>1" : do cache if first param > 1
     *
     *      - unless : if unless is true, NOT use cache
     *          - e.g. : unless = "#result == null"
     *
     *      - async: whether use async in cache
     *
     *
     *   - Cache theory
     *
     *      1. AutoConfig class : CacheAutoConfiguration
     *
     *      2. cache conf class:
     *          - org.springFramework.boot.autoconfigure.cache.GenericCacheConfiguration
     *          - org.springFramework.boot.autoconfigure.cache.JCacheConfiguration
     *          - org.springFramework.boot.autoconfigure.cache.RedisCacheConfiguration ...
     *
     *      3. which caches are activated ?
     *          - org.springFramework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *              -> register a cacheManager (ConcurrentMapCacheManager) to container
     *              -> receiver or create a ConcurrentMapCacheManager (ConcurrentMap type)
     *              -> save cache tp ConcurrentMap
     *
     *   - Process steps
     *
     *       @Cacheable
     *      - step 1) before method run, check cache (component), via cacheNames
     *                (cacheManager get cache), will create cache if no cache
     *
     *      - step 2) find cache content in Cache,
     *                key is generated by some strategy (use SimpleKeyGenerator by default)
     *                - SimpleKeyGenerator strategy:
     *                  - if no param, key = new SimpleKey()
     *                  - if there is a param, key = param
     *                  - if there are multiple param, key = SimpleKey(params)
     *
     *      - step 3) if can't get cache, use target method instead, and put result to cache
     *
     *
     *   - Core
     *      - 1) via CacheManager (ConcurrentMapCacheManager), get Cache (ConcurrentMapCache) with name
     *      - 2) key is generated by KeyGenerator (default : SimpleKeyGenerator)
     */
    //@Cacheable(cacheNames = {"emp"})
    //@Cacheable(cacheNames = {"emp"}, condition = "#id>0")
    //@Cacheable(cacheNames = {"emp"}, key = "#root.methodName + '[' + #id + ']'")
    //@Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator")
    @Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator", condition = "#a0>1")
    public Employee getEmp(Integer id){
        System.out.println(">>> query employee with id = " + id);
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

}
