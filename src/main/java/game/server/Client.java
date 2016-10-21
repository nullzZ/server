package game.server;

import java.util.Random;

import org.apache.log4j.Logger;

import game.server.protobuf.AnyProto.EnterRequest;
import game.server.protobuf.AnyProto.LoginRequest;
import game.server.protobuf.AnyProto.MoveRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 
 * @author nullzZ
 *
 */
public class Client {
	private static final Logger logger = Logger.getLogger(Client.class);
	public String host;
	public int port;

	private Channel channel;

	public Client(String host, int port) {
		channel = getChannel(host, port);
	}

	public final Channel getChannel(String host, int port) {
		Channel channel = null;
		try {
			Bootstrap bootstrap = getBootstrap();
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
		return channel;
	}

	public static final Bootstrap getBootstrap() {
		EventLoopGroup workerGroup = new NioEventLoopGroup(1);

		Bootstrap b = new Bootstrap(); // (1)
		b.group(workerGroup); // (2)
		b.channel(NioSocketChannel.class); // (3)
		b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
				ch.pipeline().addLast("encoder", new LengthFieldPrepender(2));
				// ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
				// ch.pipeline().addLast(new MyClientHandler());
			}
		});
		return b;
	}

	public void sendMsg(int cmd, byte[] msg) throws Exception {
		if (channel != null && channel.isActive()) {
			ByteBuf buffer = Unpooled.buffer(msg.length + 4);
			buffer.writeInt(cmd);
			buffer.writeBytes(msg);
			channel.writeAndFlush(buffer).sync();
		} else {
			logger.error("消息发送失败,连接尚未建立!");
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			Random r = new Random();
			Client c = new Client("127.0.0.1", 20000);

			LoginRequest.Builder loginmsg = LoginRequest.newBuilder();
			c.sendMsg(1000, loginmsg.build().toByteArray());
			Thread.sleep(100);
			
			EnterRequest.Builder m = EnterRequest.newBuilder();
			m.setMapId(1);
			c.sendMsg(1001, m.build().toByteArray());
			Thread.sleep(100);
			
			MoveRequest.Builder mm = MoveRequest.newBuilder();
			mm.setX(0);
			mm.setY(0);
			c.sendMsg(1002, m.build().toByteArray());
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
