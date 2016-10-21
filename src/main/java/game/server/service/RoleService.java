package game.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import game.core.rpg.manager.MapManager;
import game.core.rpg.map.Position;
import game.server.role.Role;

/**
 * @author nullzZ
 *
 */
@Service
public class RoleService {

	private static final Logger logger = Logger.getLogger(RoleService.class);

	public void enter(Role role, int mapId) {
		role.setMapId(mapId);
		MapManager.getInstance().setRolePosition(role, new Position(0, 0));
		logger.debug("[进入地图]");
	}

	public void move(Role role, int x, int y) {
		List<Integer> roads = new ArrayList<>();
		roads.add(10000);
		roads.add(20000);
		MapManager.getInstance().readyRun(role, new Position(0, 0), roads);
	}
}
