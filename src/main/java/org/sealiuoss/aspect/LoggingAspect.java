package org.sealiuoss.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.ConnectionImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("within(org..service.*) || within(org..repo.*)")
//    @Pointcut("within(org.sealiuoss.service.*)")
    public void logMethodPointCut() {

    }

    @Around("logMethodPointCut()")
    public Object logBeforeAndAfter(ProceedingJoinPoint point) throws Throwable {
        logger.info("Calling method: "+ point.getSignature().toString());
        Object returnValue= point.proceed();
        logger.info("Return value: "+returnValue);
        return returnValue;
    }

    @Around("target(javax.sql.DataSource)")
    public Object logDataSourceConnectionInfo(ProceedingJoinPoint point) throws Throwable {
        logger.info("Calling method: "+ point.getSignature().toString());
        Object result = point.proceed();
        if(result instanceof Connection) {
            Connection connection = (Connection) Proxy.newProxyInstance(ConnectionImpl.class.getClassLoader(),
                    new Class[]{Connection.class}, new ConnectionInvocationHandler((Connection) result));
            return connection;
        }
        return result;
    }

    class ConnectionInvocationHandler implements InvocationHandler{
        private Connection connection;
        private ObjectMapper objectMapper = new ObjectMapper();

        public ConnectionInvocationHandler(Connection connection){
            this.connection=connection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("Connection trace: " + method.toGenericString() +
                    " | Args: "+objectMapper.writeValueAsString(args));
            Object returnValue = method.invoke(connection,args);
            return returnValue;
        }
    }
}
