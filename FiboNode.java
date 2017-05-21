/**
 * @author Akshay
 */
public class FiboNode {
 
	//Class variables and attributes of a node in a fibonacci heap
	private int degree;
	private int data;
	private FiboNode child;
	private FiboNode leftSibling;
	private FiboNode rightSibling;
	private FiboNode parent;
	private boolean childCut;

	//Getter and setter functions for class variables
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	
	public FiboNode getChild() {
		return child;
	}

	public void setChild(FiboNode child) {
		this.child = child;
	}

	public FiboNode getLeftSibling() {
		return leftSibling;
	}

	public void setLeftSibling(FiboNode leftSibling) {
		this.leftSibling = leftSibling;
	}

	public FiboNode getRightSibling() {
		return rightSibling;
	}

	public void setRightSibling(FiboNode rightSibling) {
		this.rightSibling = rightSibling;
	}

	public FiboNode getParent() {
		return parent;
	}

	public void setParent(FiboNode parent) {
		this.parent = parent;
	}

	public boolean isChildCut() {
		return childCut;
	}

	public void setChildCut(boolean childCut) {
		this.childCut = childCut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + (childCut ? 1231 : 1237);
		result = prime * result + data;
		result = prime * result + degree;
		result = prime * result + ((leftSibling == null) ? 0 : leftSibling.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((rightSibling == null) ? 0 : rightSibling.hashCode());
		return result;
	}

	//overrides equals function to compare 2 fibonacci nodes
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiboNode other = (FiboNode) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (childCut != other.childCut)
			return false;
		if (data != other.data)
			return false;
		if (degree != other.degree)
			return false;
		if (leftSibling == null) {
			if (other.leftSibling != null)
				return false;
		} else if (!leftSibling.equals(other.leftSibling))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (rightSibling == null) {
			if (other.rightSibling != null)
				return false;
		} else if (!rightSibling.equals(other.rightSibling))
			return false;
		return true;
	}
	
}
