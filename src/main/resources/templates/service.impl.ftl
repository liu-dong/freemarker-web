package ${packageName};

@Service
public class ${className?cap_first}InfoServiceImpl implements ${className?cap_first}InfoService {

   /**
   * 查询${classAnnotation}信息列表
   *
   * @param bean
   * @param limit
   * @param page
   * @return
   */
   @Override
   public ResponseResult find${className?cap_first}InfoList(${className?cap_first}InfoBean bean, int limit, int page) {
      return null;
   }

   /**
   * 保存${classAnnotation}信息
   *
   * @param bean
   * @return
   */
   @Override
   public ResponseResult save${className?cap_first}Info(${className?cap_first}InfoBean bean) {
      return null;
   }

   /**
   * 查询${classAnnotation}信息详细页面
   *
   * @param id
   * @return
   */
   @Override
   public ResponseResult get${className?cap_first}InfoView(String id) {
      return null;
   }

   /**
   * 删除${classAnnotation}信息
   *
   * @param id
   * @return
   */
   @Override
   public ResponseResult delete${className?cap_first}Info(String id) {
      return null;
   }

}