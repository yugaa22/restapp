package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.management.*;
import javax.management.*;
import java.util.Set;
import java.math.BigDecimal;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping(value = "/health")
    public void health() {
    }
    
    @RequestMapping(value = "/mbeans")
    public void mbeans() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set mbeans = server.queryNames(null, null);
        for (Object mbean : mbeans)
        {
            WriteAttributes(server, (ObjectName)mbean);
        }
        
        //RequestProcessor
        ObjectName requestObjName = new ObjectName("Tomcat:type=RequestProcessor,*");
        Set<ObjectName> requestObjNameSet = server.queryNames(requestObjName, null);
        Integer aliveSocketsCount = 0;
        Long maxProcessingTime = 0L;
        Long processingTime = 0L;
        Long requstCount = 0L;
        Long errorCount = 0L;
        BigDecimal bytesReceived = BigDecimal.ZERO;
        BigDecimal bytesSend = BigDecimal.ZERO;
        for (ObjectName obj : requestObjNameSet) {
            if (server.getAttribute(obj, "stage").toString().trim().equals("1"))
                aliveSocketsCount++;
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
    
    
}
