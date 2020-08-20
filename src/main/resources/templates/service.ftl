package ${packageName};

 public interface ${className?cap_first}InfoService {

    /**
    * 查询${classAnnotation}信息列表
    *
    * @param bean
    * @param limit
    * @param page
    * @return
    */
    ResponseResult find${className?cap_first}InfoList(${className?cap_first}InfoBean bean, int limit, int page);

    /**
    * 保存${classAnnotation}信息
    *
    * @param bean
    * @return
    */
    ResponseResult save${className?cap_first}Info(${className?cap_first}InfoBean bean);

    /**
    * 查询${classAnnotation}信息详细页面
    *
    * @param id
    * @return
    */
    ResponseResult get${className?cap_first}InfoView(String id);

    /**
    * 删除${classAnnotation}信息
    *
    * @param id
    * @return
    */
    ResponseResult delete${className?cap_first}Info(String id);
}