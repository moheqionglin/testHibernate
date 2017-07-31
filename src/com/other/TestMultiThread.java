package com.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestMultiThread {

//    private final Queue<Integer> indexlDs = new LinkedList<>();
    private final Stack<Integer> indexlDs = new Stack<>();
    public void th(){
        for( int i = 0 ;  i < 10000; i++){
            indexlDs.add(i);
        }
        new Thread(() ->{
            for( int i = 10000 ;  i < 1000000; i++){
                indexlDs.add(i);
            }
        }).start();

        new Thread(() ->{
            System.out.println(indexlDs.size());
                List<Integer> ids = new ArrayList<>();
                while (!indexlDs.isEmpty()) {
                    ids.add(indexlDs.pop());
                }

        }).start();
//        new Thread(() ->{
//            while (!indexlDs.isEmpty()) {
//                try {
//                    Integer id = indexlDs.poll();
//                    if (id != null) {
//                        System.out.println(Thread.currentThread().getName() + "indexing chemical product " +  id);
//                    }
//                } catch (EmptyStackException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        new Thread(() ->{
//            while (!indexlDs.isEmpty()) {
//                try {
//                    Integer id = indexlDs.poll();
//                    if (id != null) {
//                        System.out.println(Thread.currentThread().getName() + "indexing chemical product " +  id);
//                    }
//                } catch (EmptyStackException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public static void main(String[] args) {
        new TestMultiThread().th();
    }

}