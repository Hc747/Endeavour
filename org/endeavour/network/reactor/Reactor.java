package endeavour.network.reactor;

import endeavour.network.NetworkConstants;
import endeavour.network.codec.handshake.HandshakeDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 20/11/16
 */
public final class Reactor extends ChannelInboundHandlerAdapter {

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	public void initiate() throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();

		bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitialiser(this))
				.bind(NetworkConstants.GAME_PORT).sync();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		cause.printStackTrace();
	}

	public static void main(String[] a) throws InterruptedException {
		new Reactor().initiate();
	}

	@RequiredArgsConstructor
	private class ChannelInitialiser extends ChannelInitializer<SocketChannel> {

		private final ChannelInboundHandlerAdapter handler;

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			final ChannelPipeline pipeline = channel.pipeline();

			pipeline.addLast(HandshakeDecoder.class.getSimpleName(), new HandshakeDecoder());
			pipeline.addLast("timeout", new IdleStateHandler(NetworkConstants.IDLE_TIMER, 0, 0));
			pipeline.addLast("handler", handler);
		}
	}
}
