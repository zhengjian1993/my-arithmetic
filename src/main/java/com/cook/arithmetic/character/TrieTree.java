package com.cook.arithmetic.character;

/**
 * trie树
 *
 * @author zhengjian
 * @date 2019-01-29 14:58
 */
public class TrieTree {
    private TrieNode root = new TrieNode('/');

    // 往 MyTrieTree 树中插入一个字符串
    public void insert(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    public boolean find(char[] pattern) {
        TrieNode p = root;
        int length = pattern.length;
        for (int i = 0; i < length; ++i) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                return false;
            }
            p = p.children[index];
        }
        return p.isEndingChar;
    }

    public class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;

        public TrieNode(char data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("sb".toCharArray());
        trieTree.insert("nmb".toCharArray());
        trieTree.insert("laji".toCharArray());
        trieTree.insert("mb".toCharArray());
        trieTree.insert("rnm".toCharArray());
        System.out.println(trieTree.find("sb".toCharArray()));
    }
}
