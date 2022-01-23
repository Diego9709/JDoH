package pers.diego.dns.util;

import pers.diego.dns.bo.DomainTree;
import pers.diego.dns.exceptions.DomainTreeBuildException;
import pers.diego.dns.exceptions.ErrorType;

import java.io.*;

/**
 * @author kang.zhang
 * @date 2021/11/25 21:50
 */
public class DomainTreeUtil {
    private static DomainTree gfWDomainTree;
    private static DomainTree cnDomainTree;
    public static DomainTree getGfwDomainTree(String path){
        if(gfWDomainTree == null){
            synchronized(DomainTreeUtil.class){
                gfWDomainTree = buildDomainTree(path);
            }
        }

        return gfWDomainTree;
    }

    public static DomainTree getCnDomainTree(String path){
        if(cnDomainTree == null){
            synchronized(DomainTreeUtil.class){
                cnDomainTree = buildDomainTree(path);
            }
        }

        return gfWDomainTree;
    }


    private static DomainTree buildDomainTree(String path) {
        DomainTree domainTree = new DomainTree();
        InputStream resourceAsStream = DomainTreeUtil.class.getClassLoader().getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                domainTree.insertDomain(line);
            }

        } catch (FileNotFoundException e) {
            throw new DomainTreeBuildException(ErrorType.GFW_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new DomainTreeBuildException(ErrorType.GFW_FILE_NOT_FOUND);
        }
        return domainTree;
    }

}
