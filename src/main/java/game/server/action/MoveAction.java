package game.server.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import game.core.net.action.AbsRoleAction;
import game.core.net.action.IActionAnnotation;
import game.server.protobuf.AnyProto.MoveRequest;
import game.server.role.Role;
import game.server.service.RoleService;

@IActionAnnotation(cmd = 1002)
@Service
public class MoveAction extends AbsRoleAction<Role, MoveRequest> {

	@Resource
	private RoleService roleService;

	public void execute(Role role, MoveRequest m) {
		int x = m.getX();
		int y = m.getY();
		roleService.move(role, x, y);
	}

}
