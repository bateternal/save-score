

import ir.oddrun.score.data.hibernate.HibernateUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class App {
    public static void main(String[] args) throws Exception {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setDescriptor("src/main/resources/WEB-INf/web.xml");
        context.setResourceBase("src/main/resources");
        context.setParentLoaderPriority(true);
        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        Server jettyServer = new Server(2140);
        jettyServer.setHandler(context);
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
            HibernateUtil.closeSession();
        }
    }
}

