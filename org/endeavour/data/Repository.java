package endeavour.data;

import io.netty.buffer.ByteBuf;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 17/11/16
 */
public interface Repository {

	void load(ByteBuf buffer);

}
