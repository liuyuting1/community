package com.example.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/22 08:18
 */

@Component
public class SensitiveFilter {

    private  static  final Logger logger=LoggerFactory.getLogger(SensitiveFilter.class);

    public static final  String REPLACEMENT="***";

    private  TrieNode rootNode= new TrieNode();

    //在对象加载完依赖注入后执行
    @PostConstruct
    public  void init(){
        try (
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        ){
            String keyword;
            while ((keyword=reader.readLine())!=null)
                this.addKeyword(keyword);
        }
        catch (Exception e){
            logger.error("加载敏感词文件失败："+e.getMessage());
        }
    }

    //将一个敏感词添加到前缀树中
    private  void addKeyword(String keyword){
        TrieNode tempNode= rootNode;
        for(int i=0;i<keyword.length();i++){
            char c=keyword.charAt(i);
            TrieNode subNode=tempNode.getSubNode(c);
            if(subNode==null){
                subNode =new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指向子节点，进入下一轮循环
            tempNode =subNode;

            if(i==keyword.length()-1)
                tempNode.setKeywordEnd(true);
        }
    }


    /**
     * 过滤敏感词
     *
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return  null;
        }
        //指针1
        TrieNode tempNode= rootNode;
        //指针2
        int begin=0;
        //指针3
        int position=0;
        //结果
        StringBuilder sb=new StringBuilder();

        while (position<text.length()){
            char c=text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                //若指针1处于根结点，将此符号计入结果，让指针2向下走一步
                if(tempNode==rootNode){
                    sb.append(c);;begin++;
                }
                position++;
                continue;
            }

            //检查下级节点
            tempNode=tempNode.getSubNode(c);
            if(tempNode==null){
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                position=++begin;
                tempNode=rootNode;
            }else if(tempNode.isKeywordEnd()){
                //发现敏感词
                sb.append(REPLACEMENT);
                begin=++position;
                tempNode=rootNode;
            }else {
                position++;
            }
        }

        //将最后一批字符串计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }


    private  boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c)&&(c < 0x2E80 || c > 0x9FFF);
    }


    private  class TrieNode {
        //关键词结束标识
        private  boolean isKeywordEnd=false;

        private Map<Character, TrieNode> subNodes=new HashMap<>();

        public boolean isKeywordEnd(){
            return  isKeywordEnd;
        }
        public void  setKeywordEnd(boolean key){
            isKeywordEnd=key;
        }
        public  void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }

        //hash表获取子节点
        public  TrieNode getSubNode(Character c){
            return (TrieNode) subNodes.get(c);
        }
    }

}
