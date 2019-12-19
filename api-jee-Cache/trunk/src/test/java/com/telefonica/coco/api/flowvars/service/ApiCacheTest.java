package com.telefonica.coco.api.flowvars.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telefonica.coco.api.cache.service.CacheApi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/api-cache-context.xml", "/META-INF/spring/api-cache-context-test.xml"})
public class ApiCacheTest {

	@Autowired
	CacheApi cache;
	
	@Test
	public void testGetContent(){
		
        
        String valor1 = cache.getValue("DEPT","50");
        
        
        String valor2 = cache.getValue("DEPT","30");
        
        
        String valor3 = cache.getValue("DEPT","20");
        
        
		assertThat(valor1).isNotNull();
		assertThat(valor2).isNotNull();
		assertThat(valor3).isNotNull();
        
		//assertThat(partyUserDetails).isNotNull();
		//assertThat(partyUserDetails).isInstanceOf(PartyUserDetails.class);
	}
	
	@Test
	public void testAllContents(){
		
        Collection<String> keys = new ArrayList<String>();
        keys.add("50");
        keys.add("40");
        keys.add("30");
        keys.add("20");
        keys.add("10");
        
        Map<String,String> valores = cache.getAllValues("DEPT", keys);
        
        Collection<String> vals = valores.values();
        for (Iterator<String> iterator = vals.iterator(); iterator.hasNext();) {
        	  String val = (String) iterator.next();
        	  System.out.println(val);
        	}
        
        
		assertThat(vals).isNotNull();
        
		//assertThat(partyUserDetails).isNotNull();
		//assertThat(partyUserDetails).isInstanceOf(PartyUserDetails.class);
	}
	
	
	
	
}
