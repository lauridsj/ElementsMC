package elementsmc.client.util;

import java.util.HashMap;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import elementsmc.common.util.Coords4;

public class Animation {

	public static class AnimKey {
		public Coords4 coords;
		public String animType;

		public AnimKey(Coords4 coords, String animType) {
			this.coords = coords;
			this.animType = animType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((animType == null) ? 0 : animType.hashCode());
			result = prime * result
					+ ((coords == null) ? 0 : coords.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AnimKey other = (AnimKey) obj;
			if (animType == null) {
				if (other.animType != null)
					return false;
			} else if (!animType.equals(other.animType))
				return false;
			if (coords == null) {
				if (other.coords != null)
					return false;
			} else if (!coords.equals(other.coords))
				return false;
			return true;
		}

	}

	public static HashMap<AnimKey, Animation> animationMap = new HashMap<AnimKey, Animation>();

	public Coords4 coords;
	public String animType;
	public long startTime;
	public long duration;
	public int millisPerDegreeX = 0;
	public int millisPerDegreeY = 0;
	public int millisPerDegreeZ = 0;
	public Vec3 translationPerMillis = null;
	public Vec3 scalePerMillis = null;

	public void apply() {
		long time = System.currentTimeMillis() - startTime;
		if (time > duration) {
			if (animationMap.containsKey(coords)) {
				animationMap.remove(coords);
			}
		} else {
			if (millisPerDegreeX != 0)
				GL11.glRotated((time / millisPerDegreeX) % 360L, 1, 0, 0);
			if (millisPerDegreeY != 0)
				GL11.glRotated((time / millisPerDegreeY) % 360L, 0, 1, 0);
			if (millisPerDegreeZ != 0)
				GL11.glRotated((time / millisPerDegreeZ) % 360L, 0, 0, 1);
			if (scalePerMillis != null)
				GL11.glScaled(scalePerMillis.xCoord * time,
						scalePerMillis.yCoord * time, scalePerMillis.zCoord
								* time);
			if (translationPerMillis != null)
				GL11.glTranslated(translationPerMillis.xCoord * time,
						translationPerMillis.yCoord * time,
						translationPerMillis.zCoord * time);
		}
	}

	public Animation(long duration) {
		this.duration = duration;
	}

	public Animation setRotation(int millisPerDegreeX, int millisPerDegreeY,
			int millisPerDegreeZ) {
		this.millisPerDegreeX = millisPerDegreeX;
		this.millisPerDegreeY = millisPerDegreeY;
		this.millisPerDegreeZ = millisPerDegreeZ;
		return this;
	}

	public double getRotationAngleX() {
		return ((System.currentTimeMillis() - startTime) / millisPerDegreeX) % 360L;
	}

	public double getRotationAngleY() {
		return ((System.currentTimeMillis() - startTime) / millisPerDegreeY) % 360L;
	}

	public double getRotationAngleZ() {
		return ((System.currentTimeMillis() - startTime) / millisPerDegreeZ) % 360L;
	}

	public static void addAnimation(Animation ani, Coords4 c, String animType) {
		ani.coords = c;
		ani.animType = animType;
		ani.startTime = System.currentTimeMillis();
		animationMap.put(new AnimKey(c, animType), ani);
	}

	public static Animation getAnimation(Coords4 c, String animType) {
		return animationMap.get(new AnimKey(c, animType));
	}

	public static void removeAnimation(Coords4 c, String animType) {
		AnimKey key = new AnimKey(c, animType);
		if (animationMap.get(key) != null) {
			animationMap.remove(key);
		}
	}

	public static void apply(Coords4 c, String animType) {
		AnimKey key = new AnimKey(c, animType);
		if (animationMap.get(key) != null) {
			animationMap.get(key).apply();
		}
	}
	
	public static boolean hasAnimation(Coords4 c, String animType)
	{
		AnimKey key = new AnimKey(c, animType);
		return animationMap.get(key) != null;
	}

	@Override
	public String toString() {
		return "Animation [coords=" + coords + ", animType=" + animType
				+ ", startTime=" + startTime + ", duration=" + duration
				+ ", millisPerDegreeX=" + millisPerDegreeX
				+ ", millisPerDegreeY=" + millisPerDegreeY
				+ ", millisPerDegreeZ=" + millisPerDegreeZ
				+ ", translationPerMillis=" + translationPerMillis
				+ ", scalePerMillis=" + scalePerMillis + "]";
	}

}
