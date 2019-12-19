package com.telefonica.coco.api.cache.service.impl;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.telefonica.coco.api.cache.service.CacheApi;
import com.telefonica.coco.lib.propertysources.EnvironmentProvider;


@Service
public class CacheApiImpl implements CacheApi{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheApiImpl.class);
	
	//@Value("#{'${drot.list}'.split(',')}")
	private List<String> cacheNames;
	
	public List<String> getCacheNames() {
		return cacheNames;
	}

	public void setCacheNames(List<String> cacheNames) {
		this.cacheNames = cacheNames;
	}

	private static Map<String,NamedCache> caches;
	
	
	@PostConstruct
	public void initializeCaches()
	{
		if (cacheNames!=null)
			for (String element : cacheNames) {
				getCache(element);
			}
		
	}
	
	@SuppressWarnings("rawtypes")
	public NamedCache getCache(String _cacheName)
	{
		if (caches==null) caches=new HashMap<String,NamedCache>();
		Environment env = EnvironmentProvider.getEnvironment(); 
		if (caches.get(_cacheName)!=null)
			return caches.get(_cacheName);
		LOGGER.debug("Obteniendo el nombre de la caché");
		String name = env.getProperty("coco.api.drot.cache")+_cacheName;
		LOGGER.debug("Caché " + _cacheName);
		NamedCache cache = CacheFactory.getCache(name);
		if (cache!=null)
			caches.put(_cacheName, cache);
		return cache;
	}

	@Override
    public Map<String,String> getAllValues(String drotName, Collection<String> keys) {
        try {
        		return getCache(drotName).getAll(keys);
         } catch (Exception e) {
        	LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    @Override
    public String getValue(String drotName, String key) {
        try {
        		
            LOGGER.debug("Obteniendo entrada en " + drotName);
            String res = (String)getCache(drotName).get(key);
            if (res !=null)
            	LOGGER.debug("Encontrado");
            else
            	LOGGER.debug("No Encontrado");	
            return res;
        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    @Override
    public void removeEntry(String drotName,String key) {
        try {
            LOGGER.debug("Eliminando entrada en " + drotName);
            getCache(drotName).remove(key);
            LOGGER.debug("Eliminado");
        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
        }
    }
    
    

}
