package endeavour.network.codec.login;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 21/11/16
 */
@Getter
@RequiredArgsConstructor
public class LoginResponse {

	protected final ByteBuf response;

}
