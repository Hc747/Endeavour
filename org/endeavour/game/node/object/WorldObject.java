package endeavour.game.node.object;

import endeavour.game.node.CoordinateNode;

/**
 * @author Harrison, Alias: Hc747, Contact: harrisoncole05@gmail.com
 * @version 1.0
 * @since 16/11/16
 */
public class WorldObject extends CoordinateNode {

	//TODO directions are only N,S,E,W -> translates to 2 bits needed (0 - 3)
	/*private static final byte NORTH = 0b00;
	private static final byte SOUTH = 0b01;
	private static final byte EAST = 0b10;
	private static final byte WEST = 0b11;*/

	//TODO could be done using one less bit
	/*private static final byte NORTH = 0b0001;
	private static final byte SOUTH = 0b0010;
	private static final byte EAST = 0b0100;
	private static final byte WEST = 0b1000;

	private static final byte NORTH_EAST = NORTH | EAST;
	private static final byte NORTH_WEST = NORTH | WEST;
	private static final byte SOUTH_EAST = SOUTH | EAST;
	private static final byte SOUTH_WEST = SOUTH | WEST;*/

	private static final int ID_ACCESS_MASK = 0x1FFFFFF;
	private static final byte TYPE_ACCESS_MASK = 0x1F;
	private static final byte ROTATION_ACCESS_MASK = 0x3;

	private static final int ID_CLEAR_MASK = 0xFE000000;
	private static final int TYPE_CLEAR_MASK = 0xC1FFFFFF;
	private static final int ROTATION_CLEAR_MASK = 0x3FFFFFFF;

	private static final byte TYPE_SHIFT = 25;
	private static final byte ROTATION_SHIFT = 30;

	//rr-ttttt-iiiiiiiiiiiiiiiiiiiiiiiii
	private int hash;

	public WorldObject(int id, int type, int rotation, int x, int y, int z) {
		super(x, y, z);
		hash = ((rotation & ROTATION_ACCESS_MASK) << ROTATION_SHIFT) | ((type & TYPE_ACCESS_MASK) << TYPE_SHIFT) | (id & ID_ACCESS_MASK);
	}

	public WorldObject(int id, int type, int rotation, CoordinateNode node) {
		this(id, type, rotation, node.getX(), node.getY(), node.getZ());
	}

	public final void setId(int id) {
		hash = (hash & ID_CLEAR_MASK) | (id & ID_ACCESS_MASK);
	}

	public final void setType(int type) {
		hash = (hash & TYPE_CLEAR_MASK) | (type & TYPE_ACCESS_MASK) << TYPE_SHIFT;
	}

	public final void setRotation(int rotation) {
		hash = (hash & ROTATION_CLEAR_MASK) | (rotation & ROTATION_ACCESS_MASK) << ROTATION_SHIFT;
	}

	public final int getId() {
		return hash & ID_ACCESS_MASK;
	}

	public final int getType() {
		return (hash >> TYPE_SHIFT) & TYPE_ACCESS_MASK;
	}

	public final int getRotation() {
		return (hash >> ROTATION_SHIFT) & ROTATION_ACCESS_MASK;
	}

	public final int getObjectHash() {
		return hash;
	}
}
