package endeavour.network.codec.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
public final class LoginResponseEncoder extends MessageToMessageEncoder<LoginResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, LoginResponse message, List<Object> out) throws Exception {
		ctx.writeAndFlush(message.getResponse());
	}

}
