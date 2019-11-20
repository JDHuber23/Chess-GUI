package chess.engine;

import java.util.BitSet;

@SuppressWarnings("serial")
public final class ChessBitSet extends BitSet {

	public ChessBitSet() {
		super(64);
	}

	public ChessBitSet(final ChessBitSet bSet) {
		super(64);
		or(bSet);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			boolean bit_set = get(i);
			if (bit_set) {
				sb.append(" 1 ");
			} else {
				sb.append(" . ");
			}
			if ((i + 1) % 8 == 0) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

}
