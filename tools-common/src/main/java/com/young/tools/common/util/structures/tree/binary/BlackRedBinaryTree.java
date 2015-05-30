package com.young.tools.common.util.structures.tree.binary;

public class BlackRedBinaryTree<AnyType extends Comparable<? super AnyType>> implements Tree<AnyType>{

	enum NodeType{
		black,red;
	}
	
	class BlackRedNode{
		
		private AnyType element;
		
		private BlackRedNode left;
		
		private BlackRedNode right;
	
		private BlackRedNode parent;
		
		private NodeType nodeType;
	}
	
	private BlackRedNode root;
	
	public void makeEmpty() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean contains(AnyType element) {
		return contains(element,root);
	}

	private boolean contains(AnyType element,BlackRedNode root2) {
		if(root2 == null){
			return false;
		}
		int compareResult = element.compareTo(root2.element);
		if(compareResult<0){
			return contains(element,root2.left);
		}else if(compareResult>0){
			return contains(element, root2.right);
		}else{
			return true;
		}
	}

	public AnyType findMin() throws Exception {
		return findMin(root).element;
	}

	private BlackRedNode findMin(BlackRedNode root2) throws Exception {
		if(isEmpty()){
			throw new Exception("tree is empty");
		}
		while(root2.left!=null){
			root2 = root2.left;
		}
		return root2;
	}

	public AnyType findMax() throws Exception {
		return findMax(root).element;
	}

	private BlackRedNode findMax(BlackRedNode root2) throws Exception {
		if(isEmpty()){
			throw new Exception("tree is empty");
		}
		while(root2.right!=null){
			root2 = root2.right;
		}
		return root2;
	}

	public void insert(AnyType element) {
		root = insert(element,root);
	}

	private BlackRedNode insert(AnyType element, BlackRedNode root2) {
		/**
		 * 第一种情况,插入前是空树,这个最简单,直接插入并着色为black
		 */
		if(isEmpty()){
			root2 = new BlackRedNode();
			root2.element = element;
			root2.nodeType = NodeType.black;
		}
		/**
		 * 找到位置并且先插入
		 */
		if(root2==null){
			root2 = new BlackRedNode();
			root2.element = element;
			root2.nodeType = NodeType.red;
		}
		
		return null;
	}

//	public AnyType remove(AnyType element) {
//		return null;
//	}

	public void printTree(Sort sort) {
		
	}

	public int size() {
		return 0;
	}

	@Override
	public void remove(AnyType element) {
		// TODO Auto-generated method stub
		
	}

}
