package endeavour.network.codec.login;

import endeavour.network.NetworkConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
public final class LoginDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		if (buffer.isReadable()) {
			int opcode = buffer.readUnsignedByte();
			int length = buffer.readUnsignedShort();

			if (length != buffer.readableBytes()) {
				ctx.channel().close();
				return;
			}

			if (buffer.readInt() != NetworkConstants.CLIENT_BUILD_MAJOR) {
				//outdated packet //TODO
				return;
			}

			if (opcode == 16 || opcode == 18) {
				//decode world login
				System.out.println("Decode login: "+opcode);
			} else {
				System.out.println("Close channel: "+opcode);
				ctx.channel().close();
			}
		}
	}

}
