package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.management.*;
import javax.management.*;
import java.util.Set;

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
    public void mbeans() {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set mbeans = server.queryNames(null, null);
        for (Object mbean : mbeans)
        {
            WriteAttributes(server, (ObjectName)mbean);
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
