package orgl.client;

import orgl.server.ServerConfig;
import orgl.model.User;
import orgl.pattern.RequestPattern;
import orgl.server.MurmurServerRunnable;
import orgl.task.Request;

public class ClientApplicationThread extends ClientThread {
    public ClientApplicationThread(MurmurServerRunnable server, User user, ClientBuffer clientBuffer) {
        super(server);
        super.user = user;
        super.clientBuffer = clientBuffer;

        ServerConfig serverConfig = server.getConfig();
        super.senderString = serverConfig.currentDomain.buildAddress(user.getLogin());

        sendMessage("+OK");
        this.start();
    }

    @Override
    protected void handleRequest(Request request) {
        if (!RequestPattern.APP_REQUEST.matcher(request.getRequestString()).matches()) {
            return;
        }

        server.sendRequestToExecutor(request);
    }
}
