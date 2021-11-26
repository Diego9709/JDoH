package pers.diego.dns.util;

import pers.diego.dns.bo.DomainTree;
import pers.diego.dns.exceptions.DomainTreeBuildException;
import pers.diego.dns.exceptions.ErrorType;

import java.io.*;
import java.util.Scanner;

/**
 * @author kang.zhang
 * @date 2021/11/25 21:50
 */
public class DomainTreeUtil {
    private String gfwFilePath;
    private static DomainTree gfWDomainTree;
    public DomainTree getGFWDomainTree(){
        if(gfWDomainTree == null){
            synchronized(this){
                gfWDomainTree = buildGfwDomainTree();
            }
        }

        return gfWDomainTree;
    }

    private DomainTree buildGfwDomainTree() {
        DomainTree domainTree = new DomainTree();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(gfwFilePath))){
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

    public static void main(String[] args) {
        DomainTreeUtil domainTreeUtil = new DomainTreeUtil();
        domainTreeUtil.gfwFilePath = "C:\\Users\\zhang\\IdeaProjects\\JDoH\\http-doh\\src\\main\\resources\\domain\\gfw.txt";
        DomainTree gfwDomainTree = domainTreeUtil.getGFWDomainTree();
        String domain = null;
        Scanner scanner = new Scanner(System.in);
        while((domain = scanner.next()) != null){
            boolean b = gfwDomainTree.include(domain);
            System.out.println(b);
        }


    }
}
