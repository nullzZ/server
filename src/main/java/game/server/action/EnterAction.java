package game.server.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import game.core.net.action.AbsRoleAction;
import game.core.net.action.IActionAnnotation;
import game.server.protobuf.AnyProto.EnterRequest;
import game.server.role.Role;
import game.server.service.RoleService;

@IActionAnnotation(cmd = 1001)
@Service
public class EnterAction extends AbsRoleAction<Role, EnterRequest> {

	@Resource
	private RoleService roleService;

	public void execute(Role role, EnterRequest m) {
		int mapId = m.getMapId();
		roleService.enter(role, mapId);
		// this.sendMsg(t, msg.build());

	}

}
