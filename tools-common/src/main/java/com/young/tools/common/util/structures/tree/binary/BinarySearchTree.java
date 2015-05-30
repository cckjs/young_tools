package com.young.tools.common.util.structures.tree.binary;

import java.util.Iterator;
import java.util.Random;

/**
 * 二叉搜索树/二叉排序树
 * 
 * @author yangy
 * 
 * @param <AnyType>
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
		implements Tree<AnyType>, Iterable<AnyType> {

	/**
	 * 节点对象
	 * 
	 * @author yangy
	 * 
	 * @param <AnyType>
	 */
	public static class BinaryNode<AnyType> {

		private AnyType element;

		private BinaryNode<AnyType> left;

		private BinaryNode<AnyType> right;

		public BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		public BinaryNode(AnyType theElement, BinaryNode<AnyType> lt,
				BinaryNode<AnyType> rt) {
			this.element = theElement;
			this.left = lt;
			this.right = rt;
		}
	}
	private int size = 0;
	// 根节点
	private BinaryNode<AnyType> root;

	public BinarySearchTree() {
		root = null;
	}

	/**
	 * 清空二叉树
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * 是否为空树
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * 是否包含element元素
	 * 
	 * @param element
	 * @return
	 */
	public boolean contains(AnyType element) {
		return contains(element, root);
	}

	private boolean contains(AnyType element, BinaryNode<AnyType> root2) {
		if (root2 == null) {
			return false;
		}
		int compareResult = element.compareTo(root2.element);
		if (compareResult < 0) {
			return contains(element, root2.left);
		} else if (compareResult > 0) {
			return contains(element, root2.right);
		} else {
			return true;
		}
	}

	/**
	 * 获取树中最小的节点
	 * 
	 * @return
	 * @throws Exception
	 */
	public AnyType findMin() throws Exception {
		if (isEmpty()) {
			throw new Exception("the Tree is empty");
		}
		return findMin(root).element;
	}

	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null) {
			return null;
		} else if (t.left == null) {
			return t;
		}
		return findMin(t.left);
	}

	/**
	 * 获取树中的最大节点
	 * 
	 * @return
	 * @throws Exception
	 */
	public AnyType findMax() throws Exception {
		if (isEmpty()) {
			throw new Exception("the Tree is empty");
		}
		return findMax(root).element;
	}

	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> root2) {
		while(root2.right!=null){
			root2 = root2.right;
		}
		return root2;
	}

	public void insert(AnyType element) {
		if(contains(element)){
			return;
		}
		root = insert(element, root);
		this.size++;
	}

	private BinaryNode<AnyType> insert(AnyType element,
			BinaryNode<AnyType> root2) {
		if (root2 == null) {
			root2 = new BinaryNode<AnyType>(element);
			return root2;
		}
		int compareResult = element.compareTo(root2.element);
		if (compareResult < 0) {
			root2.left = insert(element, root2.left);
		} else if (compareResult > 0) {
			root2.right = insert(element, root2.right);
		} else {
			// 说明相同了,那么就更新,或者干点其他的都可以
			root2.element = element;
		}
		return root2;
	}

	public void remove(AnyType element) {
		remove(element, root);
	}

	private BinaryNode<AnyType> remove(AnyType element, BinaryNode<AnyType> root2) {
		if (root2 == null) {
			return root2;
		}
		//首先查找element
		int compareResult = element.compareTo(root2.element);
		if(compareResult<0){
			root2.left = remove(element, root2.left);
		}else if(compareResult>0){
			root2.right = remove(element, root2.right);
		//说明已经找到了element元素
		}else if(root2.left!=null&&root2.right!=null){
			//如果有两个儿子的话,删除分为2步
			//1.要删除的节点的数据修改为他的右子树的最小节点的值
			root2.element = findMin(root.right).element;
			//2.然后删除他的右子树的那个最小节点
			root2.right = remove(root2.element, root2.right);
		}else{
			//如果要删除的节点只有一个儿子
			//若果有左儿子,那么要删除的节点,那么只要让姚删除的节点=他的左儿子即可
			//如果有右儿子 同理
			root2 = (root2.left!=null)?root2.left:root2.right;
		}
		return root2;
	}

	// 遍历树
	public void printTree(Sort sort) {
		if (isEmpty()) {
			System.out.println("tree is empty");
		}
		if (sort == Sort.asc)
			printAsc(root);
		else if (sort == Sort.desc)
			printDesc(root);
	}

	// 采用中序遍历打印树 顺序为根左右 打印完成为升序
	private void printAsc(BinaryNode<AnyType> root2) {
		if (root2.left != null) {
			printAsc(root2.left);
		}
		System.out.println(root2.element.toString());
		if (root2.right != null) {
			printAsc(root2.right);
		}
	}

	// 降序输出
	private void printDesc(BinaryNode<AnyType> root2) {
		if (root2.right != null) {
			printDesc(root2.right);
		}
		System.out.println(root2.element.toString());
		if (root2.left != null) {
			printDesc(root2.left);
		}

	}

	private class BinarySearchTreeIterator implements Iterator<AnyType> {

		private int count;
		
		private BinaryNode<AnyType> point;
		
		public BinarySearchTreeIterator(int count){
			this.count = count;
			point = root;
		}
		
		public boolean hasNext() {
			return size==0;
		}

		public AnyType next() {
			count--;
			return null;
		}

		public void remove() {

		}

	}

	public Iterator<AnyType> iterator() {
		return new BinarySearchTreeIterator(size);
	}

	public int size() {
		return size;
	}
	
	public static void main(String[] args) throws Exception {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		for (int i = 0; i < 10; i++) {
			tree.insert(new Random().nextInt(10));
		}
		tree.printTree(Sort.desc);
		System.out.println(tree.contains(99));
//		System.out.println(tree.findMax());
//		System.out.println(tree.findMin());
		tree.remove(5);
		tree.printTree(Sort.asc);
	}

	
}
