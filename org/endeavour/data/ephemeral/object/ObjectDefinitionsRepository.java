package endeavour.data.ephemeral.object;

import endeavour.data.Repository;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 20/11/16
 */
public class ObjectDefinitionsRepository implements Repository {

	private final HashMap<Integer, ObjectDefinition> definitions = new HashMap<>();

	@Override
	public void load(ByteBuf buffer) {
		for (;;) {
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
		}
	}

}
