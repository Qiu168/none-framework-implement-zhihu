package com.huangTaiQi.www.utils;

import java.util.*;

/**
 * @author _qqiu
 */
public class ACFilter {
    private final TrieNode root;

    /**
     * 内部类，当结构体用了
     */
   private static class TrieNode {
        /**
         * 以此节点结尾的敏感词数目
         */
        private int endCount;
        private final List<Integer> depths;
        /**
         * 是否是一个敏感词的结尾
         */
        private boolean isEnd;
        /**
         * 指向下一个字符的指针
         * 用HashMap存储子节点
         */
        private final HashMap<Character, TrieNode> next;
        /**
         * 失配指针
         */
        private TrieNode fail;

        public TrieNode() {
            endCount = 0;
            depths=new ArrayList<>();
            isEnd = false;
            next = new HashMap<>();
            fail = null;
        }
    }

    /**
     * 构建成字典树
     * @param words 违禁词
     */
    public ACFilter(Set<String> words) {
        root = new TrieNode();
        constructTrie(words);
        constructACAutomation();
    }

    private void constructTrie(Set<String> words) {
        for (String word : words) {
            //每个单词都是跟节点的分支
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (!node.next.containsKey(c)) {
                    //如果父节点没有该字符，新增字符
                    node.next.put(c, new TrieNode());
                }
                //子节点
                node = node.next.get(c);
            }
            //单词遍历完在尾节点单词数++，标记为结尾
            node.endCount++;
            node.depths.add(word.length());
            node.isEnd = true;
        }
    }

    /**
     * 构建AC自动机，使用BFS遍历Trie树
     * 逐层遍历
     */
    private void constructACAutomation() {
        Queue<TrieNode> queue = new LinkedList<>();
        for (HashMap.Entry<Character, TrieNode> entry : root.next.entrySet()) {
            TrieNode child = entry.getValue();
            //添加进队列
            queue.offer(child);
            //将它们的失败指针指向根节点
            child.fail = root;
        }
        while (!queue.isEmpty()) {
            //从队列中取出一个节点
            TrieNode node = queue.poll();
            for (HashMap.Entry<Character, TrieNode> entry : node.next.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();
                //当前子节点的失败指针指向其父节点的失败指针指向节点的相应子节点，如果没有则指向根节点
                child.fail = node.fail.next.getOrDefault(c, root);
                //如果当前子节点指向的节点是一个模式串的结尾，则将当前子节点也标记为模式串的结尾
                if (child.fail.isEnd) {
                    child.isEnd = true;
                }
                //将当前子节点加入队列中。
                queue.offer(child);
            }
        }
    }

    /**
     * 进行匹配
     */
    public Set<String> acMatch(String content) {
        //匹配到的敏感词
        Set<String> res = new HashSet<>();
        TrieNode node = root;
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            while (!node.next.containsKey(c) && node != root) {
                node = node.fail;
            }
            node = node.next.getOrDefault(c, root);
            if (node.isEnd) {
                for (Integer depth : node.depths) {
                    res.add(content.substring(i - depth + 1, i + 1));
                }
            }
        }
        return res;
    }
}