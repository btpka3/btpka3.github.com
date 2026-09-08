@import com.github.btpka3.first.spring.boot4.tpl.XxxClass
@import com.github.btpka3.first.spring.boot4.tpl.XxxField
@param XxxClass xxxClass
package ${xxxClass.getPkg()};
public class ${xxxClass.getName()} {
@for(XxxField f : xxxClass.getFieldList())    private ${f.getType()} ${f.getName()};
@endfor
@for(XxxField f : xxxClass.getFieldList())    public ${f.getType()} get${f.getName()}(){
        ${f.getGetterLogic()}
    }
@endfor}