package endeavour.network.codec.jaggrab;

import endeavour.network.NetworkConstants;
import endeavour.utilities.ByteBufUtilities;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
public final class JagGrabDecoder extends ByteToMessageDecoder {

	private static final String GRAB_SERVER_TOKEN = "KrUm6ZYzSAhgRXPFeTXMaMxWi4ZLU4fn";

	private static final int[] GRAB_SERVER_KEYS = {
			1441,78700,44880,39771,363186,44375,0,16140,7316,271148,810710,216189,379672,454149,933950,21006,25367,17247,1244,1,14856,1494,119,882901,1818764,3963,3618
	};

	private State state = State.AUTHORISATION;

	private enum State {
		AUTHORISATION,
		TRANSMISSION
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		if (buffer.isReadable()) {
			switch (state) {
				case AUTHORISATION:
					handleAuthorisation(ctx, buffer, out);
					break;
				case TRANSMISSION:
					handleRequests(ctx, buffer, out);
					break;
			}
		}
	}

	private void handleAuthorisation(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		int length = buffer.readUnsignedByte();

		if (buffer.readableBytes() < length) {
			ctx.channel().close();
			return;
		}

		if (buffer.readInt() != NetworkConstants.CLIENT_BUILD_MAJOR || buffer.readInt() != NetworkConstants.CLIENT_BUILD_MINOR) {

			ByteBuf response = ctx.alloc().buffer(1).writeByte(6);//TODO use constants

			ChannelFuture future = ctx.writeAndFlush(response);
			future.addListener((ChannelFutureListener) -> ctx.close());
			ctx.pipeline().remove(this);
			return;
		}

		String token = ByteBufUtilities.readString(buffer);

		if (!token.equals(GRAB_SERVER_TOKEN)) {
			ctx.channel().close();
			ctx.pipeline().remove(this);
			return;
		}

		ByteBuf response = ctx.alloc().buffer(1 + (GRAB_SERVER_KEYS.length * 4));

		response.writeByte(0);
		for (int key : GRAB_SERVER_KEYS) {
			response.writeInt(key);
		}
		ChannelFuture future = ctx.channel().writeAndFlush(response);
		future.addListener((ChannelFutureListener) -> state = State.TRANSMISSION);
	}

	private void handleRequests(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		int opcode = buffer.readUnsignedByte();
		switch (opcode) {
			case 0:
			case 1:
				//request cache container
				if (buffer.isReadable(5)) {
					int index = buffer.readUnsignedByte();
					int archive = buffer.readInt();
					System.out.println("Request for index: " + index + " of archive: " + archive);
				}
				break;
			case 7:
				//close
				System.out.println("Request for channel close!");
				break;
			case 4:
				//encryption value
				if (buffer.isReadable(3)) {
					int encryption = buffer.readUnsignedByte();
					boolean close = buffer.readUnsignedShort() != 0;
					System.out.println("Encryption: " + encryption + ", Close: " + close);
				}
				break;
			//TODO opcode 6
			default:
				System.out.println("Skip 11 bytes on opcode: "+opcode);
				buffer.skipBytes(11);
				break;
		}
	}

}
