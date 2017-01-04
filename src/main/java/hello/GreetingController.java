package hello;



import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.management.*;
import javax.management.*;
//import java.util.Set;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class GreetingController {
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    public static Map<BadKey,String> leakMap = new HashMap<>();
    
   //@system.out.println("GCE")
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        //memory leak code  
       /* for(int i =0;i<9;i++){
           leakMap.put(new BadKey("key"), "value");
         }  */ 
         System.out.println("HashMap size : "+ leakMap.size());
        return new Greeting(MetricsRegistryClient.client().incrRequestCount(),
                            String.format(template, name));
    }

    @RequestMapping(value = "/health")
    public void health() {
    }
    
    
    @RequestMapping(value = "/mbeans")
    public void mbeans() throws Exception {
        System.out.println("New changes in mbeans");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set mbeans = server.queryNames(null, null);
        /*for (Object mbean : mbeans)
        {
            WriteAttributes(server, (ObjectName)mbean);
        }*/
        
        //RequestProcessor
        ObjectName requestObjName = new ObjectName("Tomcat:type=GlobalRequestProcessor,name=*");
        Set<ObjectName> requestObjNameSet = server.queryNames(requestObjName, null);
        Long maxProcessingTime = 0L;
        Long processingTime = 0L;
        Long requstCount = 0L;
        Long errorCount = 0L;
        BigDecimal bytesReceived = BigDecimal.ZERO;
        BigDecimal bytesSend = BigDecimal.ZERO;
        for (ObjectName obj : requestObjNameSet) {
            long nowMaxProcessingTime = Long.parseLong(server.getAttribute(obj, "maxTime").toString());
            if (maxProcessingTime < nowMaxProcessingTime)
                maxProcessingTime = nowMaxProcessingTime;
            processingTime += Long.parseLong(server.getAttribute(obj, "processingTime").toString());
            requstCount += Long.parseLong(server.getAttribute(obj, "requestCount").toString());
            errorCount += Long.parseLong(server.getAttribute(obj, "errorCount").toString());
            bytesReceived = bytesReceived.add(new BigDecimal(server.getAttribute(obj, "bytesReceived").toString()));
            bytesSend = bytesSend.add(new BigDecimal(server.getAttribute(obj, "bytesSent").toString()));
            System.out.println(processingTime+" : "+requstCount+" : "+bytesReceived+" : "+bytesSend);
        }
    }
    
    private void WriteAttributes(final MBeanServer mBeanServer, final ObjectName http)
        throws InstanceNotFoundException, IntrospectionException, ReflectionException
    {
        MBeanInfo info = mBeanServer.getMBeanInfo(http);
        MBeanAttributeInfo[] attrInfo = info.getAttributes();

        System.out.println("Attributes for object: " + http +":\n");
        for (MBeanAttributeInfo attr : attrInfo)
        {
            System.out.println("  " + attr.getName() + "\n");
        }
    }
    
    static class BadKey {
        // no hashCode or equals();
        public final String key;
        public BadKey(String key) { this.key = key; }
    }
    
    
}
