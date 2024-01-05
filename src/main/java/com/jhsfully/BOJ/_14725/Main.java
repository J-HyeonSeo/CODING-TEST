package com.jhsfully.BOJ._14725;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

class Node {
    Map<String, Node> children = new TreeMap<>();
}

class Trie {
    Node root = new Node();

    public void insert(String[] data) {
        Node cur = this.root;

        for (String s : data) {
            if (!cur.children.containsKey(s)) {
                cur.children.put(s, new Node());
            }
            cur = cur.children.get(s);
        }
    }

    public void print(Node node, int depth) {
        for (Map.Entry<String, Node> map : node.children.entrySet()) {
            StringBuilder dash = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                dash.append("--");
            }
            System.out.println(dash + map.getKey());
            print(map.getValue(), depth + 1);
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(reader.readLine());

        Trie trie = new Trie();
        for (int i = 0; i < N; i++) {
            String[] data = reader.readLine().split(" ");
            trie.insert(Arrays.copyOfRange(data, 1, data.length));
        }

        trie.print(trie.root, 0);
    }
}
