package dev.enderman.minecraft.plugins.first.utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NumberUtility {
	public static int max(final @NotNull List<Integer> numbers) {
		int max = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			if (numbers.get(i) > max) {
				max = numbers.get(i);
			}
		}

		return max;
	}

	public static int min(final @NotNull List<Integer> numbers) {
		int min = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			if (numbers.get(i) < min) {
				min = numbers.get(i);
			}
		}

		return min;
	}

	public static double absMin(@NotNull final List<Double> numbers) {
		double min = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			final double currentNumber = numbers.get(i);

			if (Math.abs(currentNumber) < Math.abs(min)) {
				min = currentNumber;
			}
		}

		return min;
	}

	public static double absMax(@NotNull final List<Integer> numbers) {
		double max = Math.abs(numbers.get(0));

		for (int i = 1; i < numbers.size(); i++) {
			final double currentNumber = Math.abs(numbers.get(i));

			if (currentNumber > max) {
				max = currentNumber;
			}
		}

		return max;
	}
}
