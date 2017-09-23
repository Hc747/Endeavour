package endeavour.utilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 22/11/16
 */
public final class CollectionUtilities {

	private CollectionUtilities() {}

	public static <T> T lookup(Collection<T> repository, Predicate<T> condition) {
		for (T value : repository) {
			if (condition.test(value))
				return value;
		}
		return null;
	}

	public static <T> void invokeIfApplicable(Collection<T> repository, Predicate<T> condition, Consumer<T> action) {
		for (T value : repository) {
			if (condition.test(value)) {
				action.accept(value);
			}
		}
	}

	public static void main(String[] args) {
		List<Integer> repository = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		invokeIfApplicable(repository, (value) -> value > 5, System.out::println);
	}

}
