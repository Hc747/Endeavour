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
class HandshakeDecoder : ByteToMessageDecoder() {

    enum class HandshakeOpcode(private val id: Int) {

        DECODE_LOGIN(14),
        DECODE_JAGGRAB(15);
        //DECODE_ACCOUNT_CREATION(28)//TODO

        companion object {

            fun lookup(id: Number) : Optional<HandshakeOpcode> {
                return Arrays.stream(values()).filter({ it.id == id}).findAny()
            }

        }
    }

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
        if (buffer.isReadable()) {

            val opcode = HandshakeOpcode.lookup(buffer.readUnsignedByte())

            opcode.ifPresent { System.out.println("Handshake Decoder: $it") } //TODO: implementation

            ctx.pipeline().remove(this)
        }
    }

}