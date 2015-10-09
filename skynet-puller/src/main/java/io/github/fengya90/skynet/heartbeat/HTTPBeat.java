package io.github.fengya90.skynet.heartbeat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fengya90.skynet.util.CacheTool;
import io.github.fengya90.skynet.util.Config;
import io.github.fengya90.skynet.util.GlobalTool;
import io.github.fengya90.skynet.util.HTTPTool;
import io.github.fengya90.skynet.util.data.EmailBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengya on 15-9-30.
 */


public class HTTPBeat implements heartbeat,Runnable{
    private String serviceName;
    private String ip;
    private int port;
    private String path;
    private String host;
    private int size;
    private int threshold;
    private String emails;



    public HTTPBeat(String serviceName,String ip,int port,String path,String host,int size,int threshold,String emails){
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.host = host;
        this.size = size;
        this.threshold = threshold;
        this.emails = emails;
    }
    public HTTPBeat(String serviceName,String ip_port,String path,String host,int size,int threshold,String emails){
        this.serviceName = serviceName;
        String[] ips = ip_port.split(":");
        if(ips.length != 2){
            return;
        }
        this.ip = ips[0];
        this.port = Integer.valueOf(ips[1]);
        this.path = path;
        this.host = host;
        this.size = size;
        this.threshold = threshold;
        this.emails = emails;
    }
    @Override
    public void alarm() {
        String[] es = emails.split(";");
        EmailBean eb = new EmailBean();
        eb.setTo(es);
        eb.setSubject("服务错误告警:" + "httpbeat_" + serviceName + "_" + ip + ":" + port);
        eb.setMsg("请速往查看!");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonstr = mapper.writeValueAsString(eb);
            String eurl = Config.getConfig().getString("emailurl");
            HTTPTool.doPost(eurl,5000,5000,null,jsonstr.getBytes());
            GlobalTool.logger.info(jsonstr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean beat() {
        Map<String,String> head = new HashMap<String,String>();
        head.put("Host",host);
        String url = "http://"+ip+":"+port+path;
        try {
            byte[] ret = HTTPTool.doGet(url,5000,5000,head);
            if(ret != null && ret.length>size){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    @Override
    public void run() {
        String key ="httpbeat_"+serviceName+"_"+ip+":"+port;
        GlobalTool.logger.debug("HTTPBeat start: "+key);
        if(beat()){
            GlobalTool.logger.debug("HTTPBeat ok: "+key);
            CacheTool.set(key,0);
        }else{
            GlobalTool.logger.debug("HTTPBeat fail: "+key);
            if(CacheTool.inc(key) >= threshold){
                alarm();
                GlobalTool.logger.debug("HTTPBeat alarm: "+key);
                CacheTool.set(key,0);
            }
        }
    }
}
