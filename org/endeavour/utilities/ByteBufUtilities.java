package endeavour.utilities;

import io.netty.buffer.ByteBuf;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
public final class ByteBufUtilities {

	private static final int DEFAULT_DELIMITER = 10;

	private ByteBufUtilities() {}

	public static String readString(ByteBuf buffer) {
		return readString(buffer, DEFAULT_DELIMITER);
	}

	public static String readString(ByteBuf buffer, int delimiter) {
		final StringBuilder builder = new StringBuilder();
		int value;
		while (buffer.isReadable() && (value = buffer.readByte()) != delimiter) {
			builder.append((char) value);
		}
		return builder.toString();
	}

}
