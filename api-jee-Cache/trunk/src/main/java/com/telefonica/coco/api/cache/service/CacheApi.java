package com.telefonica.coco.api.cache.service;

import java.util.Collection;
import java.util.Map;

public interface CacheApi {
    
    public String getValue(String drotName,String key);
    
    public void removeEntry(String drotName,String key);
    
    public Map<String,String> getAllValues(String drotName, Collection<String> keys);

}
