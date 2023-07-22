package net.slqmy.first_plugin.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import net.slqmy.rank_system.utility.Utility;

public final class VectorUtility {
	private static final float GRAVITY = 32F;

	@NotNull
	public static Vector calculateLeapVelocityVector(@NotNull final Entity entity, @NotNull final Location targetLocation,
			final float heightGainBlocks) {
		// The entity's starting location is treated as the origin.
		final Location origin = entity.getLocation();

		// The rise is the altitude difference between the entity and the target
		// location.
		final double y_n = targetLocation.getY();
		final double rise = y_n - origin.getY();

		Utility.log("Rise: " + rise);

		// The maximum height gain is the Y value of the vertex point of the parabola.

		// If the target location is higher than the entity's location, the maximum
		// height gain will be the sum of the rise and the height gain blocks value,
		// otherwise (the target is lower than the entity, or has the same altitude),
		// the maximum height gain will simply be the height gain blocks value.
		final double maxHeightGain = (rise > heightGainBlocks) ? rise + heightGainBlocks : heightGainBlocks;

		Utility.log("Max Height Gain: " + maxHeightGain);

		final double x_0 = origin.getX();
		final double z_0 = origin.getZ();

		final double x_n = targetLocation.getX();
		final double z_n = targetLocation.getZ();

		// Difference in x and z coordinates.
		final double dx = x_n - x_0;
		final double dz = z_n - z_0;

		Utility.log("dx: " + dx);
		Utility.log("dz: " + dz);

		// This is the length of the diagonal on the horizontal axes from the entity to
		// the target location.
		final double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

		Utility.log("horizontal distance: " + horizontalDistance);

		// This is the X value of the midpoint, between the entity and the target, which
		// is also the X value of the vertex of the parabola.
		final double midPointX = horizontalDistance / 2F;

		Utility.log("midPointX: " + midPointX);

		// Alpha is the angle from the entity to the vertex of the parabola.
		final double alphaRadians = Math.atan(maxHeightGain / midPointX);

		// Theta is the optimal launch angle. It's the mean average of the angle alpha,
		// and 90 degrees. This makes sense because alpha is just not enough to make it,
		// no matter how much velocity you have, and being launched straight up also
		// obviously won't make it to the needed location.

		// This means that the required velocity approaches infinity as theta approaches
		// either of these values (alpha and 90 degrees). This creates two vertical
		// asymptotes, in the middle of which is the optimal angle to need the least
		// possible velocity.

		// A full graph of the angle theta to the required velocity:
		// https://www.desmos.com/calculator/ijp2u8kyvm
		final double thetaRadians = alphaRadians / 2F + Math.PI / 4F;

		Utility.log("Alpha (Degrees): " + Math.toDegrees(alphaRadians));
		Utility.log("Theta (Degrees): " + Math.toDegrees(thetaRadians));

		final double cosine = Math.cos(thetaRadians);

		// Use formula to calculate the magnitude of the velocity.
		final double magnitude = Math
				.sqrt((-GRAVITY * midPointX * midPointX)
						/ (2 * cosine * (maxHeightGain * cosine - midPointX * Math.sin(thetaRadians))));

		Utility.log("Magnitude: " + magnitude);

		final double v_x_0 = (Math.cos(thetaRadians) * magnitude) / 4.5F;
		final double v_y_0 = (Math.sin(thetaRadians) * magnitude) / 20F;

		Utility.log("Initial horizontal velocity: " + v_x_0);
		Utility.log("Initial vertical velocity: " + v_y_0);

		final double beta = Math.atan2(dz, dx);

		Utility.log("Beta: " + Math.toDegrees(beta));

		final double x = Math.cos(beta) * v_x_0;
		final double z = Math.sin(beta) * v_x_0;

		Utility.log("X component: " + x);
		Utility.log("Z component: " + z);

		return new Vector(x, v_y_0, z);
	}
}
