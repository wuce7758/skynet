package io.github.fengya90.skynet;

import io.github.fengya90.skynet.heartbeat.HTTPBeat;
import io.github.fengya90.skynet.util.Config;
import io.github.fengya90.skynet.util.GlobalTool;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengya on 15-9-30.
 */



public class SkynetPuller {
    public static void main(String[] args) throws ConfigurationException {
        GlobalTool.logger.info("start...");
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        XMLConfiguration config = Config.getConfig();
        String[] checktypes = config.getString("checktypes").split(";");
        for(String s:checktypes){
            if(s.equals("http")){
                XMLConfiguration checkconfig = new XMLConfiguration("checkconfig/"+s+".xml");
                List<HierarchicalConfiguration> valueitems = checkconfig.configurationsAt("item");
                for(HierarchicalConfiguration item:valueitems) {
                    String name = item.getString("name");
                    String[] ip_ports = item.getString("ip_port").split(";");
                    String path = item.getString("path");
                    String host = item.getString("host");
                    int return_size = item.getInt("return_size");
                    int threshold = item.getInt("threshold");
                    String emails = item.getString("emails");
                    int interval = item.getInt("interval");
                    for(String ip_port:ip_ports){
                        Runnable runnable = new HTTPBeat(name,ip_port,path,host,return_size,threshold,emails);
                        service.scheduleAtFixedRate(runnable, 0, interval, TimeUnit.MILLISECONDS);
                    }
                }
            };
            }
        }
    }
