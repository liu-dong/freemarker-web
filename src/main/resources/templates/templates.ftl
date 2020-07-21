package ${packageName};

/**
*  @author ${author}
*/
public class ${className} {
<#list propertyList as property>
    private ${property.typeName} ${property.columnName};// ${property.remark}
</#list>

<#list propertyList as property>

    public void set${property.columnName?cap_first}(${property.typeName} ${property.columnName}){
        this.${property.columnName} = ${property.columnName};
    }

    public ${property.typeName} get${property.columnName?cap_first}(){
        return this.${property.columnName};
    }
</#list>
}