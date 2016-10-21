package game.server.action;

import org.springframework.stereotype.Service;

import game.core.net.action.AbsChannelAction;
import game.core.net.action.IActionAnnotation;
import game.core.net.manager.HandleManager;
import game.server.protobuf.AnyProto.LoginRequest;
import game.server.role.Role;
import io.netty.channel.Channel;

/**
 * @author nullzZ
 *
 */
@IActionAnnotation(cmd = 1000)
@Service
public class LoginAction extends AbsChannelAction<Channel, LoginRequest> {

	@Override
	public void execute(Channel t, LoginRequest m) {
		Role role = new Role();
		role.setUid(1L);

		HandleManager.putRole(t, role);
	}

}
