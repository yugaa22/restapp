package hello;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean; 
import org.springframework.beans.factory.annotation.Value; 

import org.springframework.jmx.support.ConnectorServerFactoryBean; 
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
import org.springframework.context.annotation.DependsOn;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)

@SpringBootApplication
public class Application {
    @Value("${jmx.rmi.host:localhost}")
    private String rmiHost;
    @Value("${jmx.rmi.port:9012}")
    private Integer rmiPort;
    
    @Bean
    public RmiRegistryFactoryBean rmiRegistry()  throws Exception {
        final RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
        rmiRegistryFactoryBean.setPort(rmiPort);
        rmiRegistryFactoryBean.setAlwaysCreate(true);
        return rmiRegistryFactoryBean;
    }
 
    @Bean
    @DependsOn("rmiRegistry")
    public ConnectorServerFactoryBean connectorServerFactoryBean() throws Exception {
        final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
        connectorServerFactoryBean.setObjectName("connector:name=rmi");
        connectorServerFactoryBean.setServiceUrl(String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi", rmiHost, rmiPort, rmiHost, rmiPort));
        return connectorServerFactoryBean;
    }
    
    public static void main(String[] args) {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
     
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
