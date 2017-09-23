package endeavour.network.codec.handshake

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.*

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 24/9/17
 */
sealed class HandshakeDecoderK : ByteToMessageDecoder() {

    enum class HandshakeOpcode(private val id: Int) {

        DECODE_LOGIN(14),
        DECODE_JAGGRAB(15);
        //DECODE_ACCOUNT_CREATION(28)

        companion object {

            fun lookup(id: Number) : Optional<HandshakeOpcode> {
                return Arrays.stream(values()).filter({ it.id == id}).findAny()
            }

        }
    }

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
        if (buffer.isReadable()) {

            val opcode = HandshakeOpcode.lookup(buffer.readUnsignedByte())

            opcode.ifPresent { System.out.println("Handshake Decoder: $it") }

            ctx.pipeline().remove(this)
        }
    }

    /*
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
     */

}