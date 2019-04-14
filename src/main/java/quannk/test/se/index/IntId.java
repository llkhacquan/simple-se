package quannk.test.se.index;

import com.google.common.base.Preconditions;

public class IntId {
	private final int id;

	public IntId(int id) {
		Preconditions.checkState(id >= 0);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IntId intId = (IntId) o;

		return id == intId.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
