package beer.cheese;

import beer.cheese.util.VertxRunner;
import beer.cheese.web.handler.Login;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class CheeseMilkApp extends AbstractVerticle {

    public static void main(String[] args) {
        VertxRunner.runExample(CheeseMilkApp.class);
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/api/hello").handler(Login::hello);
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(5147);
    }
}
