package com.qunchuang.mlshop

import com.fasterxml.jackson.databind.ObjectMapper
import com.qunchuang.mlshop.graphql.GraphQLExecutor
import com.qunchuang.mlshop.graphql.GraphQLInputQuery
import com.qunchuang.mlshop.graphql.annotation.GRequestMapping
import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.model.user.User
import com.qunchuang.mlshop.repo.UserRepository
import graphql.ExecutionResult
import graphql.ExecutionResultImpl
import graphql.GraphQLError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@GRestController("")
@RestController
@Validated
class GraphQlController {
    @Autowired
    private GraphQLExecutor graphQLExecutor;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*",methods = [RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS],maxAge=1800L,allowedHeaders ="*")
    @RequestMapping(path = "/graphql")
    ExecutionResult graphQl(@RequestBody GraphQLInputQuery graphQLInput) throws IOException {
        ExecutionResult result = graphQLExecutor.execute(graphQLInput.getQuery(),graphQLInput.getArguments());
        // if(result.getErrors()!=null && result.getErrors().size()==0){
        result=new ExecutionResultBos(result.getData(),result.getErrors(),result.getExtensions());
        //}
        return result;
    }

    @GRequestMapping(path = "hello", method = [RequestMethod.POST,RequestMethod.GET])
    String hello(@RequestParam(name="world",required = true)String world) {
        return "hello "+world;
    }


    @GRequestMapping(path = "hellostrlist", method = [RequestMethod.POST,RequestMethod.GET])
    List<String> hellostringlist(@RequestParam(name="world",required = true)String world) {
        List<String> resultlist=new ArrayList();
        resultlist.add("hello "+world);
        resultlist.add("haha");
        return resultlist;
    }

    @GRequestMapping(path = "helloEntryList", method = [RequestMethod.POST,RequestMethod.GET])
    List<User> helloEntryList(@RequestParam(name="world",required = true)String world) {
        List<String> resultlist=new ArrayList();
        resultlist.add(new User());
        resultlist.add(new User());
        return resultlist;
    }


    /**
     * 为了解决一个数据返回规范的问题，前端用graphql-cli/playground的时候，收到后端数据返回时如果发现有errors字段（不管是不是null，是不是为空数组）
     * 都会认为有错误；而ExecutionResultImpl里的toSpecification里是说如果不为null则肯定要返回，为null才不设置该字段），这两者之间有冲突
     * 到底谁对谁错后面需要研究清楚，然后给相应的项目提issue～ TODO
     * 这里先覆盖一个方法把问题先解决。
     */
    static final class ExecutionResultBos extends ExecutionResultImpl{
        public ExecutionResultBos(Object data, List<? extends GraphQLError> errors, Map<Object, Object> extensions) {
            super(data, errors, extensions);
        }
        @Override
        public List<GraphQLError> getErrors() {
            List<GraphQLError> errors=super.getErrors();
            if(errors!=null && errors.size()==0){
                return null;
            }
            return errors;
        }
    }






   //@SchemaDocumentation("GraphQlController.create测试下行不行")
  //  @Validate(msg="一定要有姓名和id",value="exist('role{id}')")
  //  @Validate(msg="一定要有姓名和id",value="exist('role{name,tel}')")
  //  @Validate(msg="一定要有姓名和id",value="exist('role{id,name}')")

    //1.赋值
    //2.验证
    //3.准备数据



   /*

    UserService{



        updatePassword(String id, String password){
            findByid(id).set(password);
        }

        @Assert("exist('client(id,number)')")
        createAcceptance(
        @Item("client")
        Client client){


        }
    }
*/
/*
这里有bug 似乎跟groovy没法设定某个参数的多个注解有关
    @GRequestMapping(path = "/updateuser", method = RequestMethod.POST)
    List<User> createAcceptance(@Length(min = 8, max = 10)  @RequestParam(name="userid",required = true) String userid) {
        List<User> userList=new ArrayList<>();
        userList.add(this.userRepository.findById(userid).orElse(null));
        return userList;
    }
    */

}