package tools;

import org.springframework.beans.BeanUtils;

/**
 * 实现VO与Entity之间的相互转换，VO与Entity之间的属性名及数据类型必须完全一致
 * 使用范围：适用于基本类型、基本类型对应的包装类型、String类型的复制，当复制的对象中
 *         存在引用类型、集合类型、Map类型时根据业务逻辑进行处理，不能实现深度拷贝
 */
public class ConvertTools {

    /**
     * 将请求的VO转换为对应的Entity
     * @param requestVo
     * @param entity
     * @param <T>
     * @return
     */
    public static <T>  T convertRequestVoToEntity(Object requestVo,T entity){
        doConvert(requestVo,entity,null);
        return entity;
    }

    /**
     * 将请求的VO转换成Entity的同时忽略指定的字段
     * @param requestVo
     * @param entity
     * @param ignoreProperty
     * @param <T>
     * @return
     */
    public static <T> T convertRequestVoToEntityByIgnoreProperty(Object requestVo,T entity,String... ignoreProperty){
        doConvert(requestVo,entity,ignoreProperty);
        return entity;
    }

    /**
     * 将Entity转换为响应的VO
     * @param entity
     * @param requestVo
     * @param <T>
     * @return
     */
    public static <T> T convertEntityToRequestVo(Object entity,T requestVo){
        doConvert(entity,requestVo,null);
        return requestVo;
    }

    /**
     * 将Entity转换成响应的VO的同时忽略指定的字段
     * @param requestVo
     * @param entity
     * @param ignoreProperty
     * @param <T>
     * @return
     */
    public static <T> T convertEntityToRequestVoByIgnoreProperty(Object entity,T requestVo,String... ignoreProperty){
        doConvert(entity,requestVo,ignoreProperty);
        return requestVo;
    }


    /**
     * 使用spring的BeanUtils进行实际的转换工作
     * @param source
     * @param target
     * @param ignorePropertys
     */
    private static void doConvert(Object source, Object target, String... ignorePropertys){
        BeanUtils.copyProperties(source,target,ignorePropertys);
    }
}
