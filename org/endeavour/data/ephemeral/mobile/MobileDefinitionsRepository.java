package endeavour.data.ephemeral.mobile;

import endeavour.data.Repository;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 20/11/16
 */
public final class MobileDefinitionsRepository implements Repository {

	private final HashMap<Integer, MobileDefinition> definitions = new HashMap<>();

	@Override
	public void load(ByteBuf buffer) {
		for (;;) {
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
		}
	}

}
