import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Akshay
 *
 */
public class FiboHeap<Tree extends FiboNode> {

	private Tree max;

	//Increase the count of a node
	public FiboHeap<Tree> increaseKey(FiboHeap<Tree> H, Tree child, int increaseValue) {
		child.setData(child.getData() + increaseValue);
		Tree parent = (Tree)child.getParent();
		if (parent != null) {
			if (parent.getData() <= child.getData()) {
				H = cut(H, child, parent);
				H = cascadingCut(H, parent);
			}
		}
		// change the max pointer if this pointer is the new max
		if (H.getMax() == null || (H.getMax().getData() <= child.getData())) {
			H.setMax(child);
		}
		return H;
	}

	//cut a node and add it to top level list
	public FiboHeap<Tree> cut(FiboHeap<Tree> H, Tree child, Tree parent) {
		child.setChildCut(false);
		parent.setDegree(parent.getDegree() - 1);
		child = removeFiboNode(child);
		H = insert(H, child);
		return H;
	}

	//check parents of a cut node and cut them if boolean childcut is set
	public FiboHeap<Tree> cascadingCut(FiboHeap<Tree> H, Tree parent) {
		Tree grandParent = (Tree) parent.getParent();
		if (grandParent != null) {
			if (!parent.isChildCut()) {
				parent.setChildCut(true);
			} else {
				H = cut(H, parent, grandParent);
			}
		}
		return H;
	}

	//removing max node of the 
	public FiboHeap<Tree> removeMax(FiboHeap<Tree> H) {
		//combine max's children to toplevel circular linked list list
		Tree max = (Tree) H.getMax();
		Tree newMax = (Tree) max.getRightSibling();
		if (!max.equals(newMax)) {
			H.setMax(newMax);

			// remove the Max pointer from hashmap
			max = removeFiboNode(max);
			// reset max pointer
			Tree tempForSettingMax = newMax;
			do {
				Tree tempLeftSib = (Tree) tempForSettingMax.getLeftSibling();
				H = insert(H, tempForSettingMax);
				tempForSettingMax = tempLeftSib;
			} while (!newMax.equals(tempForSettingMax));
		} else {
			H.setMax(null);
		}
		Tree temp = (Tree) max.getChild();
		
		if (temp != null) {
			do {
				Tree tempLeft = (Tree) temp.getLeftSibling();
				H = insert(H, temp);
				temp = tempLeft;
			} while (!max.getChild().equals(temp));
		}
		//reset max's child
		max.setChild(null);
		//perform pairwisecombine
		if (H.getMax() != null) {
			H = pairWiseCombinev5(H);
		}
		return H;
	}

	public FiboHeap<Tree> insert(FiboHeap<Tree> H, Tree x) {
		if (H.getMax() == null) {
			x.setParent(null);
			x.setLeftSibling(x);
			x.setRightSibling(x);
			H.setMax(x);
			return H;
		}
		Tree max = (Tree) H.getMax();
		Tree rightSiblingOfMax = (Tree) max.getRightSibling();
		x.setParent(null);
		x.setRightSibling(rightSiblingOfMax);
		x.setLeftSibling(max);
		max.setRightSibling(x);
		rightSiblingOfMax.setLeftSibling(x);
		if (max.getData() <= x.getData()) {
			H.setMax(x);
		}
		return H;
	}

	public FiboHeap<Tree> pairWiseCombinev5(FiboHeap<Tree> H) {
		HashMap<Integer, Tree> mapOfDegreeAndFiboNode = new HashMap<Integer, Tree>();
		Queue<Tree> rootFiboNodesList = new LinkedList<Tree>();
		// add all the root FiboNodes by resetting their left and right nodes
		Tree max = H.getMax();
		Tree tempFiboNode = max;
		do {
			Tree tempFiboNodeLeftSib = (Tree) tempFiboNode.getLeftSibling();
			tempFiboNode.setLeftSibling(null);
			tempFiboNode.setRightSibling(null);
			rootFiboNodesList.add(tempFiboNode);
			tempFiboNode = tempFiboNodeLeftSib;
		} while (!max.equals(tempFiboNode));

		// pairwise combine on new degree nodes
		while (!rootFiboNodesList.isEmpty()) {
			Tree currentFiboNode = rootFiboNodesList.poll();
			Tree sameDegreeFiboNodeInMap = mapOfDegreeAndFiboNode.get(currentFiboNode.getDegree());
			while (sameDegreeFiboNodeInMap != null) {
				mapOfDegreeAndFiboNode.remove(sameDegreeFiboNodeInMap.getDegree());
				currentFiboNode = meld(sameDegreeFiboNodeInMap, currentFiboNode);
				sameDegreeFiboNodeInMap = mapOfDegreeAndFiboNode.get(currentFiboNode.getDegree());
			}
			mapOfDegreeAndFiboNode.put(currentFiboNode.getDegree(), currentFiboNode);
		}
		// before inserting all the FiboNodes, make the max of heap null
		H.setMax(null);
		for (Iterator<Tree> iterator = mapOfDegreeAndFiboNode.values().iterator(); iterator.hasNext();) {
			Tree FiboNode = (Tree) iterator.next();
			H = insert(H, FiboNode);
		}
		return H;
	}

	public Tree meld(Tree x, Tree y) {
		if (x.getData() >= y.getData()) {
			y.setParent(x);
			Tree child = (Tree) x.getChild();
			if (child != null) {
				Tree rightSiblingOfChild = (Tree) child.getRightSibling();
				y.setRightSibling(rightSiblingOfChild);
				y.setLeftSibling(child);
				child.setRightSibling(y);
				rightSiblingOfChild.setLeftSibling(y);
			} else {
				x.setChild(y);
				y.setLeftSibling(y);
				y.setRightSibling(y);
			}
			// increase degree
			x.setDegree(x.getDegree() + 1);
			return x;
		} else {
			x.setParent(y);
			Tree child = (Tree) y.getChild();
			if (child != null) {
				Tree rightSiblingOfChild = (Tree) child.getRightSibling();
				x.setRightSibling(rightSiblingOfChild);
				x.setLeftSibling(child);
				child.setRightSibling(x);
				rightSiblingOfChild.setLeftSibling(x);
			} else {
				y.setChild(x);
				x.setLeftSibling(x);
				x.setRightSibling(x);
			}
			y.setDegree(y.getDegree() + 1);
			return y;
		}
	}

	public Tree removeFiboNode(Tree FiboNode) {
		// reset left and right nodes
		if (!FiboNode.getLeftSibling().equals(FiboNode)) {
			FiboNode.getLeftSibling().setRightSibling(FiboNode.getRightSibling());
			FiboNode.getRightSibling().setLeftSibling(FiboNode.getLeftSibling());
			if (FiboNode.getParent() != null && (FiboNode.equals(FiboNode.getParent().getChild()))) {
				FiboNode.getParent().setChild(FiboNode.getLeftSibling());
			}
		}else if (FiboNode.getParent() != null && (FiboNode.equals(FiboNode.getParent().getChild()))) {
			FiboNode.getParent().setChild(null);
		}
		FiboNode.setLeftSibling(null);
		FiboNode.setRightSibling(null);
		
		FiboNode.setParent(null);

		return FiboNode;
	}

	public Tree getMax() {
		return max;
	}

	public void setMax(Tree max) {
		this.max = max;
	}

}
