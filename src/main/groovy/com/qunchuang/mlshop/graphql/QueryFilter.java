package com.qunchuang.mlshop.graphql;

import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;


@SchemaDocumentation("过滤条件")
public class QueryFilter{

    @SchemaDocumentation("键，可以带导航的.号")
    public void setKey(String key) {
        this.key = key;
    }
    @SchemaDocumentation("值，可以是和like相对应的%abc%")
    public void setValue(String value) {
        this.value = value;
    }
    @SchemaDocumentation("操作符")
    public void setOperator(QueryFilterOperator operator) {
        this.operator = operator;
    }
    @SchemaDocumentation("条件组合符号")
    public void setCombinator(QueryFilterCombinator combinator) {
        this.combinator = combinator;
    }
    @SchemaDocumentation("下一个条件")
    public void setNext(QueryFilter next) {
        this.next = next;
    }

    /**
     * 指的是与上一个的关系，类似于(（last) combinator this)
     */
    private QueryFilterCombinator combinator;

    private String key;

    private String value;

    private QueryFilterOperator operator;

    private QueryFilter next;

    public QueryFilter(){

    }
    public QueryFilter(String key, QueryFilterOperator operator, String value, QueryFilterCombinator combinator, QueryFilter next) {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.combinator = combinator;
        this.next = next;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public QueryFilterOperator getOperator() {
        return operator;
    }

    public QueryFilterCombinator getCombinator() {
        return combinator;
    }

    public QueryFilter getNext() {
        return next;
    }

    public boolean isDisabledEntityAllowed() {
        return CollectionJpaDataFetcher.ENTITY_PROP_FOR_DISABLED.equals(this.getKey())
                && QueryFilterCombinator.OR.equals(this.getCombinator())
                && Boolean.valueOf(this.getValue()).booleanValue();
    }
    public boolean isOnlyDisabledEntityAllowed() {
        return CollectionJpaDataFetcher.ENTITY_PROP_FOR_DISABLED.equals(this.getKey())
                && QueryFilterCombinator.AND.equals(this.getCombinator())
                && Boolean.valueOf(this.getValue()).booleanValue();
    }


    /**
     * 合并QFilter
     * @param newQF
     * @return
     */
    public QueryFilter merge(QueryFilter newQF){
        QueryFilter tempQF = this;
        while (tempQF.getNext() != null) {
            tempQF = tempQF.getNext();
        }
        tempQF.setCombinator(QueryFilterCombinator.AND);
        tempQF.setNext(newQF);
        return this;
    }
}

//TODO 可能需要扩展或者更规范化


