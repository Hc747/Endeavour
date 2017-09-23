package endeavour.data.ephemeral.item;

import endeavour.data.Repository;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 20/11/16
 */
public final class ItemDefinitionsRepository implements Repository {

	private final HashMap<Integer, ItemDefinition> definitions = new HashMap<>();

	@Override
	public void load(ByteBuf buffer) {
		int opcode;
		while ((opcode = buffer.readUnsignedByte()) != 0) {
			switch (opcode) {
				case 1:
					break;
				case 2:
					break;
			}
		}
		for (;;) {
			opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
		}
	}

}
