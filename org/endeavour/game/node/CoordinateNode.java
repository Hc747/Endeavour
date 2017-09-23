package endeavour.game.node;

import java.util.Objects;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 12/11/16
 */
public class CoordinateNode {

	private static final short XY_ACCESS_MASK = 0x7FFF;
	private static final byte Z_ACCESS_MASK = 0x3;

	private static final int X_CLEAR_MASK = 0xFFFF8000;
	private static final int Y_CLEAR_MASK = 0xC0007FFF;
	private static final int Z_CLEAR_MASK = 0x3FFFFFFF;

	private static final byte Y_SHIFT = 15;
	private static final byte Z_SHIFT = 30;

	//zz-yyyyyyyyyyyyyyy-xxxxxxxxxxxxxxx
	private int hash;

	public CoordinateNode(int x, int y, int z) {
		this.hash = ((z & Z_ACCESS_MASK) << Z_SHIFT) | ((y & XY_ACCESS_MASK) << Y_SHIFT) | (x & XY_ACCESS_MASK);
	}

	public CoordinateNode(CoordinateNode node) {
		this.hash = Objects.requireNonNull(node).hash;
	}

	public CoordinateNode(int hash) {
		this.hash = hash;
	}

	public final void copyCoordinates(CoordinateNode node) {
		this.hash = Objects.requireNonNull(node).hash;
	}

	public final void copyCoordinates(int hash) {
		this.hash = hash;
	}

	public final void setX(int x) {
		hash = (hash & X_CLEAR_MASK) | (x & XY_ACCESS_MASK);
	}

	public final void setY(int y) {
		hash = (hash & Y_CLEAR_MASK) | (y & XY_ACCESS_MASK) << Y_SHIFT;
	}

	public final void setZ(int z) {
		hash = (hash & Z_CLEAR_MASK) | (z & Z_ACCESS_MASK) << Z_SHIFT;
	}

	public final int getX() {
		return hash & XY_ACCESS_MASK;
	}

	public final int getY() {
		return (hash >> Y_SHIFT) & XY_ACCESS_MASK;
	}

	public final int getZ() {
		return (hash >> Z_SHIFT) & Z_ACCESS_MASK;
	}

	public final int getLocationHash() { return hash; }

}
