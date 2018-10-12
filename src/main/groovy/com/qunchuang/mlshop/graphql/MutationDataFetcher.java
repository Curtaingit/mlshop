package com.qunchuang.mlshop.graphql;

import com.bos.domain.persist.CoreObject;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLInputType;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MutationDataFetcher extends CollectionJpaDataFetcher {

    protected MutationMetaInfo mutationMetaInfo = null;

    public MutationDataFetcher(EntityManager entityManager, IGraphQlTypeMapper graphQlTypeMapper, MutationMetaInfo mutationMetaInfo) {
        super(entityManager, mutationMetaInfo.getEntityType(), graphQlTypeMapper);
        this.mutationMetaInfo = mutationMetaInfo;
    }

    @Override
    public Object getResult(DataFetchingEnvironment environment, QueryFilter queryFilter) {
        Field field = environment.getFields().iterator().next();
        Map<String, Object> nameArgMaps = new HashMap<>();
        field.getArguments().stream()
                //过滤掉不需要的输入参数。
                .filter(realArg -> this.mutationMetaInfo.getGraphQLArgument(realArg.getName()) != null)
                .forEach(realArg ->
                        nameArgMaps.put(realArg.getName(), convertValue(environment,
                                (GraphQLInputType) this.mutationMetaInfo.getGraphQLArgument(realArg.getName()).getType(),
                                realArg.getValue())));
        //获取到controller中返回的结果
        Object returnValue = this.mutationMetaInfo.invoke(nameArgMaps);
        //说明返回的是简单类型,或是包装简单类型后的集合类型。
        if (this.entityType == null) {
            return returnValue;
        } else if (returnValue == null) {
            return null;
        } else {//说明返回类型要么是实体类型，要么是实体类型的集合类型
            if (CoreObject.class.isAssignableFrom(returnValue.getClass())) {//说明返回的是拿到主键
                //todo  这里为什么还要再去通过id过滤呢
//                String id = ((CoreObject) returnValue).getId();
//                queryFilter = new QueryFilter("id", QueryFilterOperator.EQUEAL, id, QueryFilterCombinator.AND, queryFilter);
//                return super.getForEntity(environment, queryFilter);
                return returnValue;
            } else if (Collection.class.isAssignableFrom(returnValue.getClass())) {//实体类型的集合类型
//                Collection entityCollection = ((Collection) returnValue);
//                List<String> idList = (List<String>) entityCollection.stream().map(entity -> ((CoreObject) entity).getId()).collect(Collectors.toList());
//                String idstring = StringUtils.collectionToDelimitedString(idList, ",", "'", "'");
//                //设置分页和过滤条件
//                queryFilter = new QueryFilter("id", QueryFilterOperator.IN, idstring, QueryFilterCombinator.AND, queryFilter);
//                return super.getResult(environment, queryFilter);

//                //todo 如果不需要分页  (因为这里不能获取到分页信息)  那么 这里直接返回就可以了
                LinkedHashMap<String, Object> result = new LinkedHashMap<>();
                result.put("content",returnValue);
                result.put("totalElements",((Collection)returnValue).size());  //这个似乎也不需要
                return result;
            } else {
                //TODO 只能抛错
                throw new RuntimeException("返回类型不对头，mutation中不允许出现该类型");
            }
        }
    }


}
