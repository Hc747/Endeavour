package endeavour.network.codec.handshake;

import endeavour.network.codec.jaggrab.JagGrabDecoder;
import endeavour.network.codec.login.LoginDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
public final class HandshakeDecoder extends ByteToMessageDecoder {

	private static final int DECODE_LOGIN = 14;
	private static final int DECODE_JAG_GRAB = 15;
	//private static final int DECODE_ACCOUNT_CREATION = 28;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		if (buffer.isReadable()) {
			int opcode = buffer.readUnsignedByte();

			switch (opcode) {
				case DECODE_LOGIN:
					ctx.pipeline().addLast(LoginDecoder.class.getSimpleName(), new LoginDecoder());
					break;
				case DECODE_JAG_GRAB:
					ctx.pipeline().addLast(JagGrabDecoder.class.getSimpleName(), new JagGrabDecoder());
					break;
				default:
					ctx.channel().close();
					return;
			}
			
			ctx.pipeline().remove(this);
		}
	}

}
