package endeavour.network.reactor

import endeavour.network.codec.handshake.HandshakeDecoder
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.SocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleStateHandler

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 24/9/17
 */
class ServerBinding(port: Int, socket: ServerSocketChannel) : ChannelInboundHandlerAdapter() {

    private val bootstrap = ServerBootstrap()

    init {

        val parent = NioEventLoopGroup(1)
        val child = NioEventLoopGroup()

        bootstrap.group(parent, child)
                .channel(socket::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(ChannelInitialiser(this))
                .bind(port).sync()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        ctx.channel().close()
        cause.printStackTrace()
    }

    private class ChannelInitialiser(private val binding: ServerBinding) : ChannelInitializer<SocketChannel>() {

        override fun initChannel(channel: SocketChannel) {

            val pipeline = channel.pipeline()

            pipeline.addLast(HandshakeDecoder::class.simpleName, HandshakeDecoder())
            pipeline.addLast("timeout", IdleStateHandler(IDLE_TIME, 0, 0))
            pipeline.addLast("binding", binding)

        }

        companion object {

            val IDLE_TIME = 15

        }

    }

}