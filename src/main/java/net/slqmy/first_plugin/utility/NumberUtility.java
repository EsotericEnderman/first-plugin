package net.slqmy.first_plugin.utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NumberUtility {
	public static int min(final @NotNull List<Integer> numbers) {
		int min = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			if (numbers.get(i) < min) {
				min = numbers.get(i);
			}
		}

		return min;
	}
}
