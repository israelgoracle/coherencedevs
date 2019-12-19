package com.telefonica.cache.events;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.events.EventInterceptor;
import com.tangosol.net.events.annotation.Interceptor;
import com.tangosol.net.events.partition.cache.Event;
import com.tangosol.net.events.partition.cache.EntryEvent;
import com.tangosol.util.BinaryEntry;

import java.util.Iterator;

@Interceptor(identifier = "redist")
public class RedistributionInterceptor implements EventInterceptor<Event<? extends Enum<?>>> {

    public void onEvent(Event event) {
        if (event instanceof EntryEvent) {
            process((EntryEvent) event);
        }

        
    }

    protected void process(EntryEvent event) {


        //CacheFactory.log(String.format("+Evento capturado %s entryset size %d\n", event.getType().toString(),
        //                               event.getEntrySet().size()), CacheFactory.LOG_INFO);
        System.out.println("-Evento capturado " + event.getType().toString() + " entryset size " + event.getEntrySet().size());

        Iterator i = event.getEntrySet().iterator();
        while (i.hasNext()) {
            while (i.hasNext()) {
            try {
                    BinaryEntry binaryEntry = (BinaryEntry) i.next();
                     System.out.println("KEY " + binaryEntry.getKey());
                
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }
            }
        }
    }
}
