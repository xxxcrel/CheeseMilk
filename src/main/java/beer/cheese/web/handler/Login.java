package beer.cheese.web.handler;

import io.vertx.ext.web.RoutingContext;

public class Login {

    public static void hello(RoutingContext context){
        context.response().send("hello world");
    }
}
