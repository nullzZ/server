package game.server;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import game.core.net.Server;
import game.core.net.my.MyEncoder;
import game.core.net.my.MyServerHandler;
import game.core.quartz.QuartzManager;
import game.core.rpg.job.RoleRunJob;
import game.core.rpg.manager.MapEventManager;
import game.core.rpg.manager.MapManager;

/**
 * Hello world!
 *
 */
@Service
public class App {
	@Resource
	private MyServerHandler myServerHandler;
	@Resource
	private MyEncoder myEncoder;

	@PostConstruct
	public void init() {
		MapManager.getInstance().load();
		MapEventManager.getInstance().init(1);
		JobDataMap jdm = new JobDataMap();
		jdm.put("mapId", 1);
		QuartzManager.addJob("map-1", RoleRunJob.class, new Date(), 1, -1, jdm);
		Server s = new Server(20000, myServerHandler, myEncoder);
		s.start(false, false);
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("E:\\mywork\\server\\src\\main\\resources\\log4j.xml");
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

}
