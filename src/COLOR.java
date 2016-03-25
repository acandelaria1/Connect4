
public enum COLOR { RED, YELLOW;
	
	@Override
	public String toString() {
	    switch(this) {
	      case RED: return "RED";
	      case YELLOW: return "YELLOW";
	      default: throw new IllegalArgumentException();
	    }
	}
}
