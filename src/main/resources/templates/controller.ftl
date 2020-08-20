package ${packageName};

/**
*  ${classAnnotation}信息管理
*
*  @author ${author}
*/
@Api(tags = "${classAnnotation}管理模块")
@RestController
@RequestMapping("/${className}Info")
public class ${className?cap_first}InfoController {

    @Autowired
    private ${className?cap_first}InfoService ${className}InfoService;

    /**
    * 查询${classAnnotation}信息列表
    *
    * @param bean
    * @param limit
    * @param page
    * @return
    */
    @PostMapping("/find${className?cap_first}InfoList")
    public ResponseResult find${className?cap_first}InfoList(${className?cap_first}InfoBean bean, int limit, int page) {
        return ${className}InfoService.find${className?cap_first}InfoList(bean, limit, page);
    }

    /**
    * 保存${classAnnotation}信息
    *
    * @param bean
    * @return
    */
    @PostMapping("/save${className?cap_first}Info")
    public ResponseResult save${className?cap_first}Info(${className?cap_first}InfoBean bean) {
        return ${className}InfoService.save${className?cap_first}Info(bean);
    }

    /**
    * 查询${classAnnotation}信息详细页面
    *
    * @param id
    * @return
    */
    @GetMapping("/get${className?cap_first}InfoView")
    public ResponseResult get${className?cap_first}InfoView(String id) {
        return ${className}InfoService.get${className?cap_first}InfoView(id);
    }

    /**
    * 删除${classAnnotation}信息
    *
    * @param id
    * @return
    */
    @PostMapping("/delete${className?cap_first}Info")
    public ResponseResult delete${className?cap_first}Info(String id) {
        return ${className}InfoService.delete${className?cap_first}Info(id);
    }
}