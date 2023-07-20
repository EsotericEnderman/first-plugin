package net.slqmy.first_plugin.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class VectorUtility {
	private static final float GRAVITY = 32F;

	@NotNull
	public static Vector calculateLeapVelocityVector(@NotNull final Entity entity, @NotNull final Location destination,
			@NotNull final float heightGainBlocks) {
		final Location origin = entity.getLocation();

		final double y_n = destination.getY();
		final double rise = y_n - origin.getY();

		final double maxHeightGain = (rise > heightGainBlocks) ? rise + heightGainBlocks : heightGainBlocks;

		final double x_0 = origin.getX();
		final double z_0 = origin.getZ();

		final double x_n = destination.getX();
		final double z_n = destination.getZ();

		final double dx = x_n - x_0;
		final double dz = z_n - z_0;

		final double distance = Math.sqrt(dx * dx + dz * dz);

		final double x_3 = distance / 2;

		final double alpha = Math.toDegrees(Math.atan2(maxHeightGain, x_3));
		final double theta = alpha + 2F;

		final double thetaRadians = Math.toRadians(theta);

		final double cosine = Math.cos(thetaRadians);

		final double magnitude = Math
				.sqrt((-GRAVITY * x_3 * x_3) / (2 * cosine * cosine * (maxHeightGain - Math.tan(thetaRadians) * x_3)));

		final double v_x_0 = Math.cos(thetaRadians) * magnitude;
		final double v_y_0 = Math.sin(thetaRadians) * magnitude;

		final double beta = Math.atan2(dz, dx);

		final double x = Math.cos(beta) * v_x_0;
		final double z = Math.sin(beta) * v_x_0;

		return new Vector(x / 20, v_y_0 / 20, z / 20);
	}
}
