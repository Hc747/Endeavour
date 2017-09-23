package endeavour;

import com.google.common.base.Preconditions;

import java.util.Random;

public class CoordinateNode {

	public static final int DEFAULT_PHASE = 0;

	/*private static final int Y_SHIFT = 15;
	private static final int Z_SHIFT = 30;
	private static final int PHASE_SHIFT = 32;

	private static final long XY_ACCESS_MASK = (1 << (Z_SHIFT - Y_SHIFT)) - 1;
	private static final long Z_ACCESS_MASK = (1 << (PHASE_SHIFT - Z_SHIFT)) - 1;
	private static final long P_ACCESS_MASK = ~0;

	private static final long X_CLEAR_MASK = ~XY_ACCESS_MASK;
	private static final long Y_CLEAR_MASK = ~(XY_ACCESS_MASK << Y_SHIFT);
	private static final long Z_CLEAR_MASK = ~(Z_ACCESS_MASK << Z_SHIFT);
	private static final long P_CLEAR_MASK = 0;*/

	private static final long X_SHIFT = 32L;
	private static final long Y_SHIFT = 47L;
	private static final long Z_SHIFT = 60L;

	private static final long P_ACCESS_MASK = 0b00_0000_0000_0000_0000_0000_0000_0000_0011_1111_1111_1111_1111_1111_1111_1111_11L;//0xFFFFFFFFL;//32 bits
	private static final long X_ACCESS_MASK = 0b00_0000_0000_0000_0001_1111_1111_1111_1100_0000_0000_0000_0000_0000_0000_0000_00L;//0x7FFFL;//15 bits
	private static final long Y_ACCESS_MASK = 0b00_1111_1111_1111_1110_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_00L;//0x7FFFL;//15 bits
	private static final long Z_ACCESS_MASK = 0b11_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_00L;//0x3L;//2 bits

	private static final long P_CLEAR_MASK = 0b11_1111_1111_1111_1111_1111_1111_1111_1100_0000_0000_0000_0000_0000_0000_0000_00L;//0xFFFFFFFF00000000L
	private static final long X_CLEAR_MASK = 0b11_1111_1111_1111_1110_0000_0000_0000_0011_1111_1111_1111_1111_1111_1111_1111_11L;//~0L & (~X_ACCESS_MASK << X_SHIFT);//0L;//~X_ACCESS_MASK << X_SHIFT;//~(X_ACCESS_MASK << X_SHIFT);
	private static final long Y_CLEAR_MASK = 0b11_0000_0000_0000_0001_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_11L;//~Y_ACCESS_MASK << Y_SHIFT;//~((Y_ACCESS_MASK << Y_SHIFT));
	private static final long Z_CLEAR_MASK = 0b00_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_11L;//~Z_ACCESS_MASK << Z_SHIFT;//~(Z_ACCESS_MASK << Z_SHIFT);

	//bit structure is as follows
	//z -> y -> x -> phase
	//zz-yyyyyyyyyyyyyyy-xxxxxxxxxxxxxxx-pppppppppppppppppppppppppppppppp

	//To access phase we need to mask by 0xFFFFFFFF;
	//To access x we need to shift right by 32 then mask by 0x7FFF;

	private long hash;

	public CoordinateNode(int x, int y, int z) {
		this(x, y, z, DEFAULT_PHASE);
	}

	public CoordinateNode(int x, int y, int z, int phase) {
		//TODO MASKING
		//this.hash = (phase << PHASE_SHIFT) | (z << Z_SHIFT) | (y << Y_SHIFT) | x;
	}

	public CoordinateNode setX(int x) {
		debug("-X", x);
		hash = (hash & X_CLEAR_MASK) | (((long) x << X_SHIFT) & X_ACCESS_MASK);
		debug("+X", getX());
		return this;
	}

	public CoordinateNode setY(int y) {
		debug("-Y", y);
		hash = (hash & Y_CLEAR_MASK) | (((long) y << Y_SHIFT) & Y_ACCESS_MASK);
		debug("+Y", getY());
		return this;
	}

	public CoordinateNode setZ(int z) {
		debug("-Z", z);
		hash = (hash & Z_CLEAR_MASK) | (((long) z << Z_SHIFT) & Z_ACCESS_MASK);
		debug("+Z", getZ());
		return this;
	}

	public CoordinateNode setPhase(int phase) {
		debug("-P", phase);
		hash = (hash & P_CLEAR_MASK) | ((long) phase & P_ACCESS_MASK);
		debug("+P", getPhase());
		return this;
	}

	public long getX() {
		return (hash & X_ACCESS_MASK) >> X_SHIFT;
		//return (hash >> X_SHIFT) & X_ACCESS_MASK;
	}

	public long getY() {
		return (hash & Y_ACCESS_MASK) >> Y_SHIFT;
		//return (hash >> Y_SHIFT) & Y_ACCESS_MASK;
	}

	public long getZ() {
		return (hash & Z_ACCESS_MASK) >> Z_SHIFT;
		//return (hash >> Z_SHIFT) & Z_ACCESS_MASK;
	}

	public long getPhase() {
		return (hash & P_ACCESS_MASK);
		//return hash & P_ACCESS_MASK;
	}

	private void debug(String parameter, long value) {
		System.out.println(String.format("%s: %d, Binary: %s", parameter, value, Long.toBinaryString(value)));
		System.out.println(String.format("Hash: %d, Binary: %s", hash, Long.toBinaryString(hash)));
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof CoordinateNode && ((CoordinateNode) other).hash == hash;
	}

	public static void main(String[] args) throws Exception {

		final Random random = new Random();
		final int rounds = 1_000_000;

		for (int i = 0; i < rounds; i++) {

			final int x = random.nextInt(1 << 14 - 1);
			final int y = random.nextInt(1 << 14 - 1);
			final int z = random.nextInt(2);
			final int phase = random.nextInt(1 << 30);

			final CoordinateNode node = new CoordinateNode(0, 0, 0, 0);

			try {
				Preconditions.checkArgument(node.setX(x).getX() == x, String.format("Expected X: %d, Actual X: %d", x, node.getX()));
				Preconditions.checkArgument(node.setY(y).getY() == y, String.format("Expected Y: %d, Actual Y: %d", y, node.getY()));
				Preconditions.checkArgument(node.setZ(z).getZ() == z, String.format("Expected Z: %d, Actual Z: %d", z, node.getZ()));
				Preconditions.checkArgument(node.setPhase(phase).getPhase() == phase, String.format("Expected Phase: %d, Actual Phase: %d", phase, node.getPhase()));

				if (i % 100_000 == 0)
					System.out.println(String.format("\t\tX: %d, Y: %d, Z: %d, Phase: %d, Binary: %s", x, y, z, phase, Long.toBinaryString(node.hash)));

			} catch (Exception e) {
				throw new RuntimeException(String.format("Exception on round: %d - Binary: %s", i, Long.toBinaryString(node.hash)), e);
			}

		}

		System.out.println(String.format("Testing completed successfully after %d rounds!", rounds));
	}

	private static final class Long {

		private static String toBinaryString(long value) {
			return toUnsignedString0(value, 1);
		}

		private static String toUnsignedString0(long val, int shift) {
			int mag = java.lang.Long.SIZE - java.lang.Long.numberOfLeadingZeros(val);
			int chars = Math.max(((mag + (shift - 1)) / shift), 1);
			char[] buf = new char[chars];

			formatUnsignedLong(val, shift, buf, 0, chars);
			StringBuilder retval = new StringBuilder(new String(buf));
			for (int i = 0; i < java.lang.Long.numberOfLeadingZeros(val); i++) {
				retval.insert(0, "0");
			}
			return retval.toString();
		}

		private final static char[] digits = {
				'0' , '1' , '2' , '3' , '4' , '5' ,
				'6' , '7' , '8' , '9' , 'a' , 'b' ,
				'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
				'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
				'o' , 'p' , 'q' , 'r' , 's' , 't' ,
				'u' , 'v' , 'w' , 'x' , 'y' , 'z'
		};

		private static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len) {
			int charPos = len;
			int radix = 1 << shift;
			int mask = radix - 1;
			do {
				buf[offset + --charPos] = digits[((int) val) & mask];
				val >>>= shift;
			} while (val != 0 && charPos > 0);

			return charPos;
		}

	}

}
