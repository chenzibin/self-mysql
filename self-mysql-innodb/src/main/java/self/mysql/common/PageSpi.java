package self.mysql.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import self.mysql.innodb.parse.entity.PageType;

/**
 * @author chenzibin
 * @date 2023/2/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageSpi {

    PageType value();
}
