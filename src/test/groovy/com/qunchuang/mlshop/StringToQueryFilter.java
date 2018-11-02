package com.qunchuang.mlshop;

import com.qunchuang.mlshop.graphql.QueryFilter;
import com.qunchuang.mlshop.graphql.QueryFilterCombinator;
import com.qunchuang.mlshop.graphql.QueryFilterOperator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Curtain
 * @date 2018/11/1 16:18
 */

public class StringToQueryFilter {

    private static final Map<String,QueryFilterOperator> m = new HashMap();

    @Test
    public void test() {
//        {key:"id",operator:"EQUEAL",value:"#p.id",next:{key:"id",operator:"EQUEAL",value:"#p.id"}}
        String s = "k=admin.id,v=#p.id,o=equal && k=admin.name,o=isnotnull";
        m.put("equal",QueryFilterOperator.EQUEAL);
        m.put("isnotnull",QueryFilterOperator.ISNOTNULL);
        parse(s);
    }

    public QueryFilter parse(String s) {
        String start;
        String end;
        QueryFilterCombinator combinator = null;
        QueryFilter queryFilter = new QueryFilter();
        //如果存在&&  ||  找到第一个出现的位置 切分
        int i = s.indexOf("&&");
        int j = s.indexOf("||");
        if (i >0 || j >0){
            if (i>j){
                start = s.substring(0,i);
                end = s.substring(i+2);
                combinator = QueryFilterCombinator.AND;
            }else {
                start = s.substring(0,j);
                end = s.substring(j);
                combinator = QueryFilterCombinator.OR;
            }
        }else {
            start = s;
            end = "";
        }

        //解析start
        start = start.trim();
        for (String item : start.split(",")){
            if (item.contains("k=")){
                queryFilter.setKey(item.substring(item.indexOf("k=")+2));
            }else if(item.contains("v=")){
                queryFilter.setValue(item.substring(item.indexOf("v=")+2));
            }else if (item.contains("o=")){
                //todo  是否需要匹配 >= > == < <=
                String o = item.substring(item.indexOf("o=") + 2).toLowerCase();
                queryFilter.setOperator(m.get(o));
            }
        }
        if (end.length()>0){
            queryFilter.setCombinator(combinator);
            queryFilter.setNext(parse(end));
        }

        return queryFilter;
    }
}
