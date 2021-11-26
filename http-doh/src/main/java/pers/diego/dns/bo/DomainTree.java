package pers.diego.dns.bo;

import java.io.Serializable;
import java.util.*;

/**
 * @author kang.zhang
 * @date 2021/11/25 21:51
 * signature based HashMap Tree
 */
public class DomainTree implements Serializable {
    private Map<String, Map<String,HashSet>> domainTree = new HashMap<>();


    public void insertDomain(String domain){
        String[] strings = domain.split("\\.");
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        int length = list.size();
        if(length < 2){
            return;
        }

        if(length == 2){
            list.add(length,"*");
            length++;
        }

        String s1 = list.get(length - 1);
        String s2 = list.get(length - 2);
        String s3 = list.get(0);

        if(!domainTree.containsKey(s1)){
            HashMap<String, HashSet> map = new HashMap<>();
            HashSet<String> set = new HashSet<>();
            set.add(s3);
            map.put(s2,set);
            domainTree.put(s1,map);

        }else {
            Map<String, HashSet> sigMap = domainTree.get(s1);
            if(!sigMap.containsKey(s2)){
                HashSet<String> set = new HashSet<>();
                set.add(s3);
                sigMap.put(s2,set);
            }else {
                HashSet hashSet = sigMap.get(s2);
                hashSet.add(s3);
            }
        }
    }

    public boolean include(String domain){
        if(domain.length() < 2){
            return true;
        }
        String[] strings = domain.split("\\.");
        int length = strings.length;
        int index = length - 1;
        Map<String, HashSet> map = domainTree.get("*");
        if(map.containsKey(strings[index])){
            HashSet set = map.get(strings[index]);
            if(set.contains(strings[--index])){
                return true;
            }
        }
       index = length - 1;
        if(domainTree.containsKey(strings[index])){
            Map<String, HashSet> map1 = domainTree.get(strings[index]);
            if (map1.containsKey(strings[--index])){
                if(index == 0){
                    return true;
                }else {
                    HashSet set = map1.get(strings[index]);
                    if(set.contains(strings[--index])){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
