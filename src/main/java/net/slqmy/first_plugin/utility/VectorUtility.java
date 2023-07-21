package net.slqmy.first_plugin.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import net.slqmy.rank_system.utility.Utility;

public final class VectorUtility {
	private static final float GRAVITY = 32F;

	@NotNull
	public static Vector calculateLeapVelocityVector(@NotNull final Entity entity, @NotNull final Location destination,
			@NotNull final float heightGainBlocks) {
		final Location origin = entity.getLocation();

		final double y_n = destination.getY();
		final double rise = y_n - origin.getY();

		final double maxHeightGain = (rise > heightGainBlocks) ? rise + heightGainBlocks : heightGainBlocks;

		Utility.log("Max Height Gain: " + maxHeightGain);

		final double x_0 = origin.getX();
		final double z_0 = origin.getZ();

		final double x_n = destination.getX();
		final double z_n = destination.getZ();

		final double dx = x_n - x_0;
		final double dz = z_n - z_0;

		Utility.log("dx: " + dx);
		Utility.log("dz: " + dz);

		final double distance = Math.sqrt(dx * dx + dz * dz);

		Utility.log("X and Z distance: " + distance);

		final double x_3 = distance / 2;

		final double alpha = Math.toDegrees(Math.atan2(maxHeightGain, x_3));
		final double theta = Math.min(alpha + 2F, 89.99F);

		Utility.log("Alpha: " + alpha);
		Utility.log("Theta: " + theta);

		final double thetaRadians = Math.toRadians(theta);

		final double cosine = Math.cos(thetaRadians);

		final double magnitude = Math
				.sqrt((-GRAVITY * x_3 * x_3) / (2 * cosine * cosine * (maxHeightGain - Math.tan(thetaRadians) * x_3)));

		Utility.log("Magnitude: " + magnitude);

		final double v_x_0 = Math.cos(thetaRadians) * magnitude;
		final double v_y_0 = Math.sin(thetaRadians) * magnitude;

		Utility.log("Initial horizontal velocity: " + v_x_0);
		Utility.log("Initial vertical velocity: " + v_y_0);

		final double beta = Math.atan2(dz, dx);

		Utility.log("Beta: " + Math.toDegrees(beta));

		final double x = Math.cos(beta) * v_x_0;
		final double z = Math.sin(beta) * v_x_0;

		Utility.log("X component: " + x);
		Utility.log("Z component: " + z);

		return new Vector(x / 20, v_y_0 / 20, z / 20);
	}
}
